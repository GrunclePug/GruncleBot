package com.grunclepug.grunclebot.bot.commands.utility;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Enlarge Command
 * @author grunclepug
 */
public class Enlarge extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Enlarge Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "enlarge"))
        {
            if(args.length > 1 && event.getMessage().getEmotes().size() > 0)
            {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle(event.getMessage().getEmotes().get(0).getName())
                        .setImage(event.getMessage().getEmotes().get(0).getImageUrl())
                        .setColor(0x8904B1);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
            else
            {
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("**invalid argument.** Usage: `" + Config.getPrefix() + "enlarge {emote}` (custom emotes only)").queue();
            }

            BotLog.log(event);
        }
    }
}
