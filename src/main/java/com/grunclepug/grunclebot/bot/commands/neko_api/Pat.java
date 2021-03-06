package com.grunclepug.grunclebot.bot.commands.neko_api;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import com.grunclepug.grunclebot.bot.util.neko.NekoAPI;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Pat Command
 * @author grunclepug
 */
public class Pat extends ListenerAdapter
{
    private static final String URL = "https://nekos.life/api/v2/img/pat";

    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentDisplay().split("\\s+");

        //Pat Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "pat"))
        {
            Member member = event.getMessage().getMember();
            String title = "owo pats";
            int color = 0x8904B1;

            if(args.length > 1)
            {
                Member target = event.getMessage().getMentionedMembers().get(0);
                title = target.getEffectiveName() + ", you got pats from " + member.getEffectiveName();
            }
            EmbedBuilder builder = new NekoAPI().getEmbed(URL, title, color);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();

            BotLog.log(event);
        }
    }
}
