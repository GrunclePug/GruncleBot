package com.grunclepug.grunclebot.commands;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.*;


public class Ban extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Ban Command
        if(args[0].equalsIgnoreCase(Main.prefix + "ban"))
        {
            Member member = event.getMember();
            Member selfMember = event.getGuild().getSelfMember();
            List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
            List<User> mentionedUsers = event.getMessage().getMentionedUsers();
            User user = mentionedUsers.get(0);
            Member target = mentionedMembers.get(0);

            if(member.hasPermission(Permission.BAN_MEMBERS) && member.canInteract(target) && selfMember.canInteract(target))
            {
                if (args.length < 2)
                {
                    // Usage
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Specify member and reason of ban")
                            .setDescription("Usage: `" + Main.prefix + "ban [user] [reason]`")
                            .setColor(0xff3923);

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(builder.build()).queue();
                    builder.clear();
                } else
                  {
                    try
                    {
                        String _user = target.getEffectiveName();
                        // Success
                        String reason = Arrays.toString(args).replace(",", "");
                        reason = reason.substring(1, reason.length()-1);
                        GuildController gc = event.getGuild().getController();

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("✅ Successfully banned " + _user)
                                .setDescription("command: " + reason)
                                .setColor(0x22ff2a);

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(builder.build()).queue();
                        builder.clear();

                        String content = reason;
                        user.openPrivateChannel().queue((channel) ->
                        {
                            channel.sendMessage("You have been banned from: " + event.getGuild().getName() +
                                    "\nCommand issued: " + content).queue();
                        });


                        gc.ban(target, 0, reason).queue();
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                // Lack in Perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                        .setDescription("Either you are missing the 'Ban Members' permission\nOr that member cannot be banned")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
        }
    }
}
