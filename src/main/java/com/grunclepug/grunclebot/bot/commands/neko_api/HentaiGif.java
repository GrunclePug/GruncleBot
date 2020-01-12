package com.grunclepug.grunclebot.bot.commands.neko_api;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.errors.SFWChannelError;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import com.grunclepug.grunclebot.bot.util.neko.NekoAPI;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Hentai Gif Command
 * @author grunclepug
 */
public class HentaiGif extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Hentai Gif Command
        if (args[0].equalsIgnoreCase(Config.getPrefix() + "hentaigif"))
        {
            if(event.getChannel().isNSFW())
            {
                String url = "https://nekos.life/api/v2/img/Random_hentai_gif";
                String title = "uwu that's pretty hot";
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
