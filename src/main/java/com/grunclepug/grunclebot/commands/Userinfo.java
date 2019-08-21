package com.grunclepug.grunclebot.commands;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.LocalDateTime;


public class Userinfo extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //User Info Command
        if(args[0].equalsIgnoreCase(Main.prefix + "userinfo"))
        {
            if(args.length < 2)
            {
                String user = event.getAuthor().getAsTag();
                String userIcon = event.getAuthor().getAvatarUrl();
                String userCreationDate = event.getAuthor().getCreationTime().toLocalDateTime().toString().replace("T", " at ");
                String _userCreationDate = userCreationDate.substring(0, userCreationDate.indexOf("."));
                String userStatus = event.getMember().getOnlineStatus().toString();
                User _user = event.getAuthor();
                String userNickname = _user.getName();
                String userId = _user.getId();
                String userGuildJoinDate = event.getGuild().getMember(_user).getJoinDate().toString().replace("T", " at ");
                String _userGuildJoinDate = userGuildJoinDate.substring(0, userGuildJoinDate.indexOf("."));
                String currentTime = LocalDateTime.now().toString().replace("T", " at ");
                String _currentTime = currentTime.substring(0, currentTime.indexOf("."));

                //String online = "<:online:574875183349760000> Online";
                //String away = "<:away:574875237850677268> Away";
                //String dnd = "<:dnd:574874877744513056> Do Not Disturb";
                //String offline = "<:offline:574875398714687488> Offline";


                int userRoles = event.getGuild().getMember(_user).getRoles().size();

                EmbedBuilder builder = new EmbedBuilder();
                builder.setThumbnail(userIcon)
                    .addField("・User", user, true)
                    .addField("・Nickname", userNickname, true)
                    .addField("・ID", userId, true)
                    .addField("・Status", userStatus, true)
                    .addField("・Account Created", _userCreationDate, true)
                    .addField("・Server Join Date", _userGuildJoinDate, true)
                    .addField("・Roles", "" + userRoles, true)
                    .setFooter(_currentTime, "https://i.imgur.com/WQSW5lV.png")
                    .setColor(0x00FF00);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
            else
            {
                Member user = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                String userIcon = user.getUser().getAvatarUrl();
                String userCreationDate = user.getUser().getCreationTime().toLocalDateTime().toString().replace("T", " at ");
                String _userCreationDate = userCreationDate.substring(0, userCreationDate.indexOf("."));
                String userStatus = user.getOnlineStatus().toString();
                User _user = user.getUser();
                String userNickname = _user.getName();
                String userId = _user.getId();
                String userGuildJoinDate = user.getJoinDate().toString().replace("T", " at ");
                String _userGuildJoinDate = userGuildJoinDate.substring(0, userGuildJoinDate.indexOf("."));
                String currentTime = LocalDateTime.now().toString().replace("T", " at ");
                String _currentTime = currentTime.substring(0, currentTime.indexOf("."));


                int userRoles = user.getGuild().getMember(_user).getRoles().size();

                EmbedBuilder builder = new EmbedBuilder();
                builder.setThumbnail(userIcon)
                        .addField("・User", user.getEffectiveName(), true)
                        .addField("・Nickname", userNickname, true)
                        .addField("・ID", userId, true)
                        .addField("・Status", userStatus, true)
                        .addField("・Account Created", _userCreationDate, true)
                        .addField("・Server Join Date", _userGuildJoinDate, true)
                        .addField("・Roles", "" + userRoles, true)
                        .setFooter(_currentTime, "https://i.imgur.com/WQSW5lV.png")
                        .setColor(0x00FF00);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
        }
    }
}
