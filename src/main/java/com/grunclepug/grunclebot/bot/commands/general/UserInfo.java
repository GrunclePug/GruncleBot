package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;

import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * User Info Command
 * @author grunclepug
 */
public class UserInfo extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //User Info Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "userinfo"))
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

            Date date = new Date();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setThumbnail(member.getUser().getAvatarUrl())
                    .setTitle("User Info:")
                    .addField("・User", member.getUser().getAsTag(), false)
                    .addField("・Nickname", "" + member.getNickname(), false)
                    .addField("・ID", member.getUser().getId(), false)
                    .addField("・Status", member.getOnlineStatus().name(), false)
                    .addField("・Account Created", member.getUser().getTimeCreated().format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm")), false)
                    .addField("・Server Join Date", member.getTimeJoined().format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm")), false)
                    .addField("・Roles", "" + member.getRoles().size(), false)
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/WQSW5lV.png")
                    .setColor(0x00FF00);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();

            BotLog.log(event);
        }
    }
}
