package com.grunclepug.grunclebot.commands;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Spaghetti Hentai Command
 * @author grunclepug
 */
public class Spaghetti extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Spaghetti Hentai Command
        if (args[0].equalsIgnoreCase(Main.prefix + "spaghetti"))
        {
            try
            {
                String url = "https://reddit.com/r/spaghettihentai/random.json";
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

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("SSSOOOO LLEEEWWWWDDDDD!!")
                    .setImage(image)
                    .setColor(0xF9DB72);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
