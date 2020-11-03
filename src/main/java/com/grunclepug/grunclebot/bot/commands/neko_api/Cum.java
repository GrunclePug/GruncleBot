package com.grunclepug.grunclebot.bot.commands.neko_api;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.errors.SFWChannelError;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import com.grunclepug.grunclebot.bot.util.neko.NekoAPI;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Cum Command
 * @author grunclepug
 */
public class Cum extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Cum Command
        if (args[0].equalsIgnoreCase(Config.getPrefix() + "cum"))
        {
            if(event.getChannel().isNSFW())
            {
                String url = "https://nekos.life/api/v2/img/cum_jpg";
                String title = "oniichan~ im gonna cum";
                int color = 0x8904B1;
                EmbedBuilder builder = new NekoAPI().getEmbed(url, title, color);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
            else
            {
                new SFWChannelError(event);
            }
            BotLog.log(event);
        }
    }
}
