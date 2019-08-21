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


public class PussyGif extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // Pussy Gif Command
        if (args[0].equalsIgnoreCase(Main.prefix + "pussygif"))
        {
            if(event.getChannel().isNSFW())
            {
                try
                {
                    String url = "https://nekos.life/api/v2/img/pussy";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response responses;
                    responses = client.newCall(request).execute();

                    String jsonData = responses.body().string();
                    String content = new JSONObject(Objects.requireNonNull(jsonData)).get("url").toString();

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("*Slurp")
                        .setImage(content)
                        .setColor(0x8904B1);

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(builder.build()).queue();
                    builder.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                // SFW Channel Error
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 NSFW Content")
                    .setDescription("Please enter a NSFW channel to view this content.")
                    .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
        }
    }
}
