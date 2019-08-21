package com.grunclepug.grunclebot.core;

import net.dv8tion.jda.core.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

/**
 * Neko API Class to deal with all API endpoint calls to the Neko API
 */
public class NekoAPI
{
    //Variables
    private EmbedBuilder builder;

    /**
     * Constructor
     */
    public NekoAPI()
    {
    }

    /**
     * Method to parse Http request and return a custom embed
     * @param url The url to send the Http request to
     * @param title The title for the custom embed
     * @param color The colour for the custom embed
     * @return The custom embed (EmbedBuilder)
     */
    public EmbedBuilder getEmbed(String url, String title, int color)
    {
        try
        {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response responses = client.newCall(request).execute();

            String jsonData = responses.body().string();
            String content = new JSONObject(Objects.requireNonNull(jsonData)).get("url").toString();

            builder = new EmbedBuilder();
            builder.setTitle(title)
                    .setImage(content)
                    .setColor(color);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return builder;
    }
}
