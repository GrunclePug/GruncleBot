package com.grunclepug.grunclebot.bot.commands.staff;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;

/**
 * Role Command
 * @author grunclepug
 */
public class Role extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Role Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "role"))
        {
            //Member issuing command
            Member member = event.getMember();
            Member target = event.getMessage().getMentionedMembers().get(0);
            Guild guild = event.getMessage().getGuild();
            ArrayList<Member> members = new ArrayList<>(guild.getMembers());
            net.dv8tion.jda.core.entities.Role role;
            if(event.getMessage().getMentionedRoles().size() > 0 && event.getMessage().getMentionedRoles().get(0) != null)
            {
                role = event.getMessage().getMentionedRoles().get(0);
            }
            else
            {
                role = guild.getRolesByName(args[2], true).get(0);
            }

            //Checks to see if user has permission to manage roles, is capable of changing targets roles, and bot is capable of changing targets roles
            if(member.hasPermission(Permission.MANAGE_ROLES) && member.canInteract(target) && guild.getSelfMember().canInteract(target))
            {
                if(args[1].equalsIgnoreCase("add"))
                {
                    if(!target.getRoles().contains(role))
                    {
                        guild.getController().addSingleRoleToMember(target, role).queue();
                    }
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Adding `" + role.getName() + "` to **" + target.getEffectiveName() + "**.").queue();
                }
                else if(args[1].equalsIgnoreCase("remove"))
                {
                    if(target.getRoles().contains(role))
                    {
                        guild.getController().removeSingleRoleFromMember(target, role).queue();
                    }
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Removing `" + role.getName() + "` from **" + target.getEffectiveName() + "**.").queue();
                }
            }
            else
            {
                // Lack in Perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                        .setDescription("You are missing the 'Manage Roles' permission")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
