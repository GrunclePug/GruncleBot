package com.grunclepug.grunclebot.bot.commands.utility;

import com.grunclepug.grunclebot.bot.core.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class RandomColor extends ListenerAdapter
{
    private EmbedBuilder builder = new EmbedBuilder();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "randomcolor") || args[0].equalsIgnoreCase(Config.getPrefix() + "randomcolour"))
        {
            Random random = new Random();
            int color = random.nextInt(0xffffff + 1);
            String colorCode = String.format("#%06x", color);

            builder.setTitle(colorCode)
                    .setColor(color);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
        }
    }
}
