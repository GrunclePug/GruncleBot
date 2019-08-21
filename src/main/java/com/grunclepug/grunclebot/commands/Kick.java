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


public class Kick extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Kick Command
        if(args[0].equalsIgnoreCase(Main.prefix + "kick"))
        {
            Member member = event.getMember();
            Member selfMember = event.getGuild().getSelfMember();
            List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
            List<User> mentionedUsers = event.getMessage().getMentionedUsers();
            User user = mentionedUsers.get(0);
            Member target = mentionedMembers.get(0);

            if(member.hasPermission(Permission.KICK_MEMBERS) && member.canInteract(target) && selfMember.canInteract(target))
            {
                if (args.length < 2)
                {
                    // Usage
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Specify member and reason of kick")
                            .setDescription("Usage: `" + Main.prefix + "kick [user] [reason]`")
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
                        builder.setTitle("✅ Successfully kicked " + _user)
                                .setDescription("command: " + reason)
                                .setColor(0x22ff2a);

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(builder.build()).queue();
                        builder.clear();

                        String content = reason;
                        user.openPrivateChannel().queue((channel) ->
                        {
                            channel.sendMessage("You have been kicked from: " + event.getGuild().getName() +
                                    "\nCommand issued: " + content).queue();
                        });


                        gc.kick(target, reason).queue();
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
                        .setDescription("Either you are missing the 'Kick Members' permission\nOr that member cannot be kicked")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
        }
    }
}
