package com.grunclepug.grunclebot.bot.util.reddit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Spaghetti Hentai Command
 * @author grunclepug
 */
public class RedditAPI extends ListenerAdapter
{
    //Variables
    private EmbedBuilder builder;

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
            String value = jsonData.substring(jsonData.indexOf("\"url\": \"")+1,
                    jsonData.indexOf("\", \"width\"", jsonData.indexOf("\"")+1));
            String _value = value.replace("url\": \"", "");
            String image = _value.replace("amp;", "");

            builder = new EmbedBuilder();
            builder.setTitle(title)
                    .setImage(image)
                    .setColor(color);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
        }
        return this.builder;
    }
}
