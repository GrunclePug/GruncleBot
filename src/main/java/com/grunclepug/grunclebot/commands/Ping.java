package com.grunclepug.grunclebot.commands;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class Ping extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // Ping Command
        if(args[0].equalsIgnoreCase(Main.prefix + "ping"))
        {
            long ping;

            EmbedBuilder builder = new EmbedBuilder();
            ping = event.getJDA().getPing();
            builder.setTitle("PONG!")
                .setDescription(ping + "ms")
                .setThumbnail("https://i.imgur.com/pnNPSQN.png")
                .setColor(0xDC2F43);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();
        }
    }
}
