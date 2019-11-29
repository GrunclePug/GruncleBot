package com.grunclepug.grunclebot.bot.commands.utility;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Avatar Command
 * @author grunclepug
 */
public class Avatar extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Avatar Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "avatar"))
        {
            Member member;
            if(args.length > 1)
            {
                member = event.getMessage().getMentionedMembers().get(0);
            }
            else
            {
                member = event.getMessage().getMember();
            }

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(member.getEffectiveName() + "'s Avatar")
                    .setImage(member.getUser().getAvatarUrl() + "?size=2048")
                    .setColor(0x8904B1);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();
        }
    }
}
