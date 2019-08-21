package com.grunclepug.grunclebot.commands;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

/**
 * Kick Command
 * @author grunclepug
 */
public class Kick extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        String reason = null;

        if(args.length > 3)
        {
            for(int i = 2; i < args.length; i++)
            {
                reason += (" " + args[i]);
            }
            reason = reason.substring(5);
        }
        else if(args.length > 2)
        {
            reason = args[2];
        }

        //Kick Command
        if(args[0].equalsIgnoreCase(Main.prefix + "kick"))
        {
            //Member issuing command
            Member member = event.getMember();
            //Bot issuing command
            Member selfMember = event.getGuild().getSelfMember();
            //Member being targetted
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
                        //Success
                        String _user = target.getEffectiveName();
                        GuildController gc = event.getGuild().getController();

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("✅ Successfully kicked " + _user)
                                .setDescription("command: " + reason)
                                .setColor(0x22ff2a);

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(builder.build()).queue();
                        builder.clear();

                        String content = reason;
                        try
                        {
                            user.openPrivateChannel().queue((channel) ->
                            {
                                channel.sendMessage("You have been kicked from: " + event.getGuild().getName() +
                                        "\nCommand issued: " + content).queue();
                            });
                        }
                        catch(ErrorResponseException e)
                        {
                        }

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
