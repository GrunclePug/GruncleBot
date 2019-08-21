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


public class NekoGif extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // Neko Command
        if (args[0].equalsIgnoreCase(Main.prefix + "nekogif"))
        {
            if(event.getChannel().isNSFW())
            {
                // NSFW Neko Gif
                try
                {
                    String url = "https://nekos.life/api/v2/img/nsfw_neko_gif";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response responses;
                    responses = client.newCall(request).execute();

                    String jsonData = responses.body().string();
                    String content = new JSONObject(Objects.requireNonNull(jsonData)).get("url").toString();

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("SOOO LLEEEWWWWDDDDD!")
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
                // SFW Neko Gif
                try
                {
                    String url = "https://nekos.life/api/v2/img/ngif";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response responses;
                    responses = client.newCall(request).execute();

                    String jsonData = responses.body().string();

                    String content = new JSONObject(Objects.requireNonNull(jsonData)).get("url").toString();
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("cute neko gif owo")
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
