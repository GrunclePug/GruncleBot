package com.grunclepug.grunclebot.bot.util.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Creates an embed from a custom API
 * @author GrunclePug
 */
public class CustomAPI {
    private EmbedBuilder builder;

    /**
     * Create Embed
     * @param url API endpoint
     * @param category name of json object
     * @param title title for embed
     * @param color color for embed
     * @return Embed
     * @throws IOException issue reading json from API
     */
    public EmbedBuilder getEmbed(String url, String category, String title, int color) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response responses = client.newCall(request).execute();
        Gson gson = new Gson();

        String jsonData = responses.body().string();
        String content = gson.fromJson(jsonData, JsonObject.class).get(category).toString();

        builder = new EmbedBuilder();
        builder.setTitle(title)
                .setDescription(content.replace("\"", ""))
                .setColor(color);

        return builder;
    }

    /**
     * Create Embed for 2 json objects
     * @param url API endpoint
     * @param category name of json object
     * @param title title for embed
     * @param color color for embed
     * @return Embed
     * @throws IOException issue reading json from API
     */
    public EmbedBuilder getEmbedWithImage(String url, String category, String title, int color) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response responses = client.newCall(request).execute();
        Gson gson = new Gson();

        String jsonData = responses.body().string();
        String image = gson.fromJson(jsonData, JsonObject.class).get("url").toString();
        String content = gson.fromJson(jsonData, JsonObject.class).get(category).toString();

        builder = new EmbedBuilder();
        builder.setTitle(title)
                .setImage(image.replace("\"", ""))
                .setDescription(content.replace("\"", ""))
                .setColor(color);

        return builder;
    }
}
