package com.grunclepug.grunclebot.bot.commands.staff;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.beryx.awt.color.ColorFactory;

import java.awt.*;

/**
 * Role Color Command
 */
public class RoleColor extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "rolecolor")) {
            if(event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                if(args.length == 3) {
                    Role role = null;
                    if(event.getMessage().getMentionedRoles().size() > 0 && event.getMessage().getMentionedMembers().get(0) != null) {
                        // Role Mention
                        role = event.getMessage().getMentionedRoles().get(0);
                    } else if(event.getGuild().getRolesByName(args[1], true).size() > 0 && event.getGuild().getRolesByName(args[1], true).get(0) != null) {
                        // Role Name
                        role = event.getGuild().getRolesByName(args[1], true).get(0);
                    } else if(event.getGuild().getRoleById(args[1]) != null) {
                        // Role ID
                        role = event.getGuild().getRoleById(args[1]);
                    } else {
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Role `" + args[1] + "` not found").queue();
                    }

                    if(role != null) {
                        Color color = null;
                        try {
                            color = ColorFactory.valueOf("0x" + args[2]);
                        } catch(Exception e) {
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage("Invalid color code: " + args[2]).queue();
                        }

                        if(color != null) {
                            role.getManager().setColor(color).queue();

                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("<:greenTick:934590255590420552> Role color updated")
                                    .setDescription(role.getName() + " has been updated with a new color: 0x" + args[2])
                                    .setColor(color);
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessageEmbeds(builder.build()).queue();
                        }
                    }
                } else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Usage: " + Config.getPrefix() + "rolecolor {role} {color code}").queue();
                }
            } else {
                // Lack in Perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                        .setDescription("The 'Manage Roles' permission is required to use this command.")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
