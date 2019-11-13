package com.grunclepug.grunclebot.commands.general;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Ping Command
 * @author grunclepug
 */
public class Ping extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Ping Command
        if(args[0].equalsIgnoreCase(Main.prefix + "ping"))
        {
            long ping = event.getJDA().getPing();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Pong! :ping_pong:")
                    .setDescription("delay: " + ping + " ms")
                    .setColor(0xFE2E2E);
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();
        }
    }
}
