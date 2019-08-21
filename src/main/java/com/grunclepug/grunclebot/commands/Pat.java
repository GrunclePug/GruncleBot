package com.grunclepug.grunclebot.commands;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;


public class Pat extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String user = event.getAuthor().getName();
        String[] args = event.getMessage().getContentDisplay().split("\\s+");

        // Pat Command
        if(args[0].equalsIgnoreCase(Main.prefix + "pat"))
        {
            if(args.length < 2)
            {
                // Usage
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Specify a member to head pat")
                        .setDescription("Usage: `" + Main.prefix + "pat <@user>`")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
            else
            {
                try
                {
                    String url = "https://nekos.life/api/v2/img/pat";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response responses;
                    responses = client.newCall(request).execute();

                    String jsonData = responses.body().string();
                    String content = new JSONObject(Objects.requireNonNull(jsonData)).get("url").toString();

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle(args[1].replaceFirst("@", "") + ", you got a head pat from " + user)
                            .setImage(content)
                            .setColor(0x8904B1);

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(builder.build()).queue();
                    builder.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
