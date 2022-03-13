package com.grunclepug.grunclebot.bot.util.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Locale;

public class JikanAPI {
    // Config Constants
    private static final String KEY = "8470ae117amsh2817e09d13d07e8p18ddc5jsn01fc91082a87";
    private static final String HOST = "jikan1.p.rapidapi.com";

    // Key Constants
    private static final String ANIME = "anime";
    private static final String ID = "mal_id";
    private static final String NAME = "name";
    private static final String IMAGE = "image_url";
    private static final String ALTERNATIVE_NAMES = "alternative_names";
    private static final String ALTERNATIVE_ANIME = "alternative_anime";

    // Endpoints
    public static final String SEARCH_CHARACTER = "search/character?q=";

    private JikanAPI() {}

    public static EmbedBuilder getEmbed(String endpoint, String query, int color) throws IOException {
        EmbedBuilder builder = new EmbedBuilder();
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url("https://jikan1.p.rapidapi.com/" + endpoint + query.replace(" ", "%20"))
                .get()
                .addHeader("x-rapidapi-host", HOST)
                .addHeader("x-rapidapi-key", KEY)
                .build();

        Response response = client.newCall(request).execute();

        String jsonData = response.body().string();
        String result = gson.fromJson(gson.fromJson(jsonData, JsonObject.class).get("results").toString(), JsonArray.class).get(0).toString();

        builder.setThumbnail(getElement(gson, IMAGE, result))
                .addField("ID", getElement(gson, ID, result), false)
                .addField("Anime", getElement(gson, ANIME, result), false)
                .addField("Name", getElement(gson, NAME, result), false)
                .addField("Alternative Names", getElement(gson, ALTERNATIVE_NAMES, result), false)
                .addField("All Anime Appearances", getElement(gson, ALTERNATIVE_ANIME, result), false)
                .setFooter("Powered by MyAnimeList", "https://i.imgur.com/MyQhqZD.png")
                .setColor(color);

        response.close();

        return builder;
    }

    private static String getElement(Gson gson, String key, String jsonData) {
        String output = "";

        switch(key) {
            case ANIME:
                String animeData = gson.fromJson(gson.fromJson(jsonData, JsonObject.class).get(ANIME).toString(), JsonArray.class).get(0).toString();
                output = gson.fromJson(animeData, JsonObject.class).get(NAME).toString().replace("\"", "");
                break;
            case ALTERNATIVE_ANIME:
                JsonArray animeArray = gson.fromJson(gson.fromJson(jsonData, JsonObject.class).get(ANIME).toString(), JsonArray.class);

                for(int i = 0; i < animeArray.size(); i++) {
                    output += "\n" + gson.fromJson(animeArray.get(i).toString(), JsonObject.class).get(NAME).toString().replace("\"", "");
                }
                break;
            case NAME:
                String name = gson.fromJson(jsonData, JsonObject.class).get(key).toString().replace(",", "").replace("\"", "");
                String[] nameData = name.split("\\s+");
                for(int i = 0; i < nameData.length; i++) {
                    output += " " + nameData[i];
                }
                break;
            case ALTERNATIVE_NAMES:
                JsonArray nameArray = gson.fromJson(gson.fromJson(jsonData, JsonObject.class).get(ALTERNATIVE_NAMES).toString(), JsonArray.class);
                for(int i = 0; i < nameArray.size(); i++) {
                    output += "\n" + gson.fromJson(nameArray.get(i).toString(), JsonPrimitive.class).toString().replace("\"", "").replace("[", "").replace("]", "");
                }
                break;
            default:
                output = gson.fromJson(jsonData, JsonObject.class).get(key).toString().replace(",", "").replace("\"", "");
                break;
        }

        return output.trim();
    }
}
