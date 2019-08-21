package com.grunclepug.grunclebot.commands;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class Info extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // Info Command
        if(args[0].equalsIgnoreCase(Main.prefix + "info"))
        {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("GruncleBot Information")
                .setThumbnail("https://i.imgur.com/bfwjiDz.png")
                .addField("・Description", "GruncleBot is a side project WIP bot written in java.", false)
                .addField("・Date Created", "May 03 2019", false)
                .addField("・Honorable Mentions", "<@!316636759901470720> (Bullfrog098)", false)
                .setFooter("Created by GrunclePug", "https://i.imgur.com/mK2zlbr.png")
                .setColor(0xFF00FF);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();
        }
    }
}
