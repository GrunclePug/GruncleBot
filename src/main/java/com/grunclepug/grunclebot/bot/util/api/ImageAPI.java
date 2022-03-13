package com.grunclepug.grunclebot.bot.util.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Creates an embed from an image API
 * @author GrunclePug
 */
public class ImageAPI {
    private EmbedBuilder builder;

    /**
     * Create Embed
     * @param url API endpoint
     * @param title title for embed
     * @param color color for embed
     * @return Embed
     * @throws IOException issue reading json from api
     */
    public EmbedBuilder getEmbed(String url, String title, int color) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response responses = client.newCall(request).execute();
        Gson gson = new Gson();

        String jsonData = responses.body().string();
        String content = gson.fromJson(jsonData, JsonObject.class).get("url").toString();

        builder = new EmbedBuilder();
        builder.setTitle(title)
                .setImage(content.replace("\"", ""))
                .setColor(color);

        return builder;
    }

    /**
     * Method to parse Http request and return a Reddit custom embed
     * @param url The url to send the Http request to
     * @param title The title for the custom embed
     * @param color The colour for the custom embed
     * @return The custom embed (EmbedBuilder)
     */
    public EmbedBuilder getRedditEmbed(String url, String title, int color) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response responses = client.newCall(request).execute();

            String jsonData = responses.body().string();
            String value = jsonData.substring(jsonData.indexOf("\"url\": \"")+1,
                    jsonData.indexOf("\", \"width\"", jsonData.indexOf("\"")+1));
            String _value = value.replace("url\": \"", "");
            String image = _value.replace("amp;", "");

            builder = new EmbedBuilder();
            builder.setTitle(title)
                    .setImage(image)
                    .setColor(color);
        } catch(Exception e) {
            e.printStackTrace(System.err);
        }
        return this.builder;
    }
}
