package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * User Info Command
 * @author GrunclePug
 */
public class UserInfo extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "userinfo")) {
            Member member;
            if(args.length > 1) {
                member = event.getMessage().getMentionedMembers().get(0);
            } else {
                member = event.getMember();
            }

            Date date = new Date();
            String nickname = member.getNickname();
            String status = member.getOnlineStatus().name();

            if(nickname == null) {
                nickname = "n/a";
            }

            switch(status) {
                case "OFFLINE":
                    status = "Offline";
                    break;
                case "ONLINE":
                    status = "Online";
                    break;
                case "IDLE":
                    status = "Idle";
                    break;
                case "DO_NOT_DISTURB":
                    status = "Do Not Disturb";
                    break;
            }

            EmbedBuilder builder = new EmbedBuilder();
            builder.setThumbnail(member.getUser().getAvatarUrl())
                    .setTitle("User Info:")
                    .addField("・User", member.getUser().getAsTag(), false)
                    .addField("・Nickname", "" + nickname, false)
                    .addField("・ID", member.getUser().getId(), false)
                    .addField("・Status", status, false)
                    .addField("・Account Created", member.getUser().getTimeCreated().format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm")), false)
                    .addField("・Server Join Date", member.getTimeJoined().format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm")), false)
                    .addField("・Roles", "" + member.getRoles().size(), false)
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/WQSW5lV.png")
                    .setColor(0x00FF00);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
            builder.clear();

            BotLog.log(event);
        }
    }
}
