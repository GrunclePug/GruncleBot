package com.grunclepug.grunclebot.bot.commands.staff;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;

/**
 * Mass Role Command
 * @author grunclepug
 */
public class MassRole extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Mass Role Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "massrole"))
        {
            //Member issuing command
            Member member = event.getMember();
            Guild guild = event.getMessage().getGuild();
            ArrayList<Member> members = new ArrayList<>(guild.getMembers());
            Role role;
            if(event.getMessage().getMentionedRoles().size() > 0 && event.getMessage().getMentionedRoles().get(0) != null)
            {
                role = event.getMessage().getMentionedRoles().get(0);
            }
            else
            {
                role = guild.getRolesByName(args[2], true).get(0);
            }
            int counter = 0;

            //Checks to see if user has administrator
            if(member.hasPermission(Permission.ADMINISTRATOR))
            {
                if(args[1].equalsIgnoreCase("add"))
                {
                    for(Member m : members)
                    {
                        if(!m.getRoles().contains(role) && guild.getSelfMember().canInteract(m) && !m.getUser().isBot())
                        {
                            guild.getController().addSingleRoleToMember(m, role).queue();
                        }
                        counter++;
                    }
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Adding `" + role.getName() + "` to " + counter + " users, this might take awhile.").queue();
                }
                else if(args[1].equalsIgnoreCase("remove"))
                {
                    for(Member m : members)
                    {
                        if(m.getRoles().contains(role) && guild.getSelfMember().canInteract(m) && !m.getUser().isBot())
                        {
                            guild.getController().removeSingleRoleFromMember(m, role).queue();
                        }
                        counter++;
                    }
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Removing `" + role.getName() + "` from " + counter + " users, this might take awhile.").queue();
                }
            }
            else
            {
                // Lack in Perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                        .setDescription("You are missing the 'Administrator' permission")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
