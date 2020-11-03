package com.grunclepug.grunclebot.bot.commands.staff;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

/**
 * Role Command
 *
 * @author grunclepug
 */
public class Role extends ListenerAdapter {
    /**
     * Guild Message Received Method
     *
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Role Command
        if (args[0].equalsIgnoreCase(Config.getPrefix() + "role")) {
            //Member issuing command
            Member member = event.getMember();
            Member target = event.getMessage().getMentionedMembers().get(0);
            Guild guild = event.getMessage().getGuild();
            ArrayList<Member> members = new ArrayList<>(guild.getMembers());
            net.dv8tion.jda.api.entities.Role role;
            if (event.getMessage().getMentionedRoles().size() > 0 && event.getMessage().getMentionedRoles().get(0) != null) {
                role = event.getMessage().getMentionedRoles().get(0);
            } else {
                role = guild.getRolesByName(args[2], true).get(0);
            }

            //Checks to see if user has permission to manage roles, is capable of changing targets roles, and bot is capable of changing targets roles
            if (member.hasPermission(Permission.MANAGE_ROLES) && member.canInteract(target) && guild.getSelfMember().canInteract(target)) {
                if (member.getRoles().get(0).canInteract(role)) {
                    if (args[1].equalsIgnoreCase("add")) {
                        if (!target.getRoles().contains(role)) {
                            guild.addRoleToMember(target, role).queue();
                        }
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Adding `" + role.getName() + "` to **" + target.getEffectiveName() + "**.").queue();
                    } else if (args[1].equalsIgnoreCase("remove")) {
                        if (target.getRoles().contains(role)) {
                            guild.removeRoleFromMember(target, role).queue();
                        }
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Removing `" + role.getName() + "` from **" + target.getEffectiveName() + "**.").queue();
                    }
                } else {
                    // target role higher than issuing members highest role
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                            .setDescription("The role you are trying to issue is higher than your own!")
                            .setColor(0xff3923);

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(builder.build()).queue();
                    builder.clear();
                }
            } else {
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
