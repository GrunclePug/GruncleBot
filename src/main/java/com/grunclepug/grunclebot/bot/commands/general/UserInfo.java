package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.LocalDateTime;

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

            String accountCreationDate = member.getUser().getCreationTime().toLocalDateTime().toString().replace("T", " at ");
            String formattedAccountCreationDate = accountCreationDate.substring(0, accountCreationDate.indexOf("."));
            String guildJoinDate = member.getJoinDate().toString().replace("T", " at ");
            String formattedGuildJoinDate = guildJoinDate.substring(0, accountCreationDate.indexOf("."));
            String currentTime = LocalDateTime.now().toString().replace("T", " at ");
            String formattedCurrentTime = currentTime.substring(0, currentTime.indexOf("."));

            EmbedBuilder builder = new EmbedBuilder();
            builder.setThumbnail(member.getUser().getAvatarUrl())
                    .setTitle("User Info:")
                    .addField("・User", member.getUser().getAsTag(), false)
                    .addField("・Nickname", "" + member.getNickname(), false)
                    .addField("・ID", member.getUser().getId(), false)
                    .addField("・Status", member.getOnlineStatus().name(), false)
                    .addField("・Account Created", formattedAccountCreationDate, false)
                    .addField("・Server Join Date", formattedGuildJoinDate, false)
                    .addField("・Roles", "" + member.getRoles().size(), false)
                    .setFooter(formattedCurrentTime, "https://i.imgur.com/WQSW5lV.png")
                    .setColor(0x00FF00);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();

            BotLog.log(event);
        }
    }
}
