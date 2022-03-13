package com.grunclepug.grunclebot.bot.commands.staff;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Role Command
 * @author GrunclePug
 */
public class Role extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "role")) {
            if(event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                net.dv8tion.jda.api.entities.Role role = null;
                Member target = null;

                if(args.length == 4) {
                    if(event.getMessage().getMentionedRoles().size() > 0 && event.getMessage().getMentionedRoles().get(0) != null) {
                        // Mentioned Role
                        role = event.getMessage().getMentionedRoles().get(0);
                    } else if(event.getGuild().getRolesByName(args[2], true).size() > 0 && event.getGuild().getRolesByName(args[2], true).get(0) != null) {
                        // Role Name
                        role = event.getGuild().getRolesByName(args[2], true).get(0);
                    } else if(event.getGuild().getRoleById(args[2]) != null) {
                        // Role ID
                        role = event.getGuild().getRoleById(args[2]);
                    } else {
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Role `" + args[2] + "` not found").queue();
                    }

                    if(event.getMessage().getMentionedMembers().size() > 0 && event.getMessage().getMentionedMembers().get(0) != null) {
                        // Mentioned User
                        target = event.getMessage().getMentionedMembers().get(0);
                    } else if(event.getGuild().getMemberById(args[3]) != null) {
                        // User ID
                        target = event.getGuild().getMemberById(args[3]);
                    } else {
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("User `" + args[3] + "` not found").queue();
                    }

                    if(event.getMember().canInteract(role)) {
                        if (event.getGuild().getSelfMember().canInteract(role)) {
                            if(role != null && target != null) {
                                switch(args[1].toUpperCase()) {
                                    case "ADD":
                                        if(!target.getRoles().contains(role)) {
                                            event.getGuild().addRoleToMember(target, role).queue();
                                            event.getChannel().sendTyping().queue();
                                            event.getChannel().sendMessage("Adding role `" + role.getName() + "` to " + target.getEffectiveName()).queue();
                                        } else {
                                            event.getChannel().sendTyping().queue();
                                            event.getChannel().sendMessage("User already has role `" + role.getName() + "`!").queue();
                                        }
                                        break;
                                    case "REMOVE":
                                        if(target.getRoles().contains(role)) {
                                            event.getGuild().removeRoleFromMember(target, role).queue();
                                            event.getChannel().sendTyping().queue();
                                            event.getChannel().sendMessage("removing role `" + role.getName() + "` from " + target.getEffectiveName()).queue();
                                        } else {
                                            event.getChannel().sendTyping().queue();
                                            event.getChannel().sendMessage("User does not have role `" + role.getName() + "`!").queue();
                                        }
                                        break;
                                    default:
                                        event.getChannel().sendTyping().queue();
                                        event.getChannel().sendMessage("Usage: " + Config.getPrefix() + "role {add|remove} {role} {user}").queue();
                                        break;
                                }
                            }
                        } else {
                            // Lack in Perms
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("\uD83D\uDED1 " + event.getGuild().getSelfMember().getEffectiveName() + " lacks the required permissions")
                                    .setDescription("That role is equal to or above the bots highest role!")
                                    .setColor(0xff3923);

                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessageEmbeds(builder.build()).queue();
                            builder.clear();
                        }
                    } else {
                        // Lack in Perms
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                                .setDescription("That role is equal to or above your highest role!")
                                .setColor(0xff3923);

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessageEmbeds(builder.build()).queue();
                        builder.clear();
                    }
                } else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Usage: " + Config.getPrefix() + "role {add|remove} {role} {user}").queue();
                }
            } else {
                // Lack in Perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                        .setDescription("You are missing the 'Manage Roles' permission")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
