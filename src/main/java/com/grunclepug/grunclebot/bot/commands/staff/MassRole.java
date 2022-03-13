package com.grunclepug.grunclebot.bot.commands.staff;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

/**
 * Mass Role Command
 * @author GrunclePug
 */
public class MassRole extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "massrole")) {
            if(event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                ArrayList<Member> members = new ArrayList<>(event.getGuild().getMembers());
                Role role = null;

                if(args.length == 3) {
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

                    if(event.getMember().canInteract(role)) {
                        if (event.getGuild().getSelfMember().canInteract(role)) {
                            if(role != null) {
                                int counter = 0;

                                switch(args[1].toUpperCase()) {
                                    case "ADD":
                                        for(Member member : members) {
                                            if(!member.getRoles().contains(role) && !member.getUser().isBot()) {
                                                event.getGuild().addRoleToMember(member, role).queue();
                                                counter++;
                                            }
                                        }

                                        event.getChannel().sendTyping().queue();
                                        event.getChannel().sendMessage("Adding `" + role.getName() + "` to " + counter + " users, this might take awhile.").queue();
                                        break;
                                    case "REMOVE":
                                        for(Member member : members) {
                                            if(member.getRoles().contains(role) && !member.getUser().isBot()) {
                                                event.getGuild().removeRoleFromMember(member, role).queue();
                                                counter++;
                                            }
                                        }

                                        event.getChannel().sendTyping().queue();
                                        event.getChannel().sendMessage("Removing `" + role.getName() + "` from " + counter + " users, this might take awhile.").queue();
                                        break;
                                    default:
                                        event.getChannel().sendTyping().queue();
                                        event.getChannel().sendMessage("Usage: " + Config.getPrefix() + "massrole {add|remove} {role}").queue();
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
                    event.getChannel().sendMessage("Usage: " + Config.getPrefix() + "massrole {add|remove} {role}").queue();
                }
            } else {
                // Lack in Perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                        .setDescription("You are missing the 'Administrator' permission")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
