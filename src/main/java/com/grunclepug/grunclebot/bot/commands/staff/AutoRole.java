 package com.grunclepug.grunclebot.bot.commands.staff;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.autorole.FileInteraction;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.ArrayList;

 /**
 * Role Command
 * @author GrunclePug
 */
public class AutoRole extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        ArrayList<com.grunclepug.grunclebot.bot.util.autorole.Guild> guilds = null;

        if(!event.getUser().isBot()) {
            try {
                guilds = com.grunclepug.grunclebot.bot.util.autorole.FileInteraction.readAutoRoleFile();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }

        if(guilds != null) {
            for(int i = 0; i < guilds.size(); i++) {
                if(guilds.get(i).getId().equalsIgnoreCase(event.getGuild().getId())) {
                    Role role = event.getGuild().getRoleById(guilds.get(i).getRole());
                    if(!event.getMember().getRoles().contains(role)) {
                        event.getGuild().addRoleToMember(event.getMember(), role).queue();
                    }
                }
            }
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // g!autorole add/remove @role
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "autorole")) {
            if(event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                net.dv8tion.jda.api.entities.Role role = null;
                Guild guild = event.getGuild();

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
                                try {
                                    switch(args[1].trim().toUpperCase()) {
                                        case "ADD":
                                            FileInteraction.updateAutoRoleFile(args[2].trim(), guild.getId(), true);
                                            break;
                                        case "REMOVE":
                                            FileInteraction.updateAutoRoleFile(args[2].trim(), guild.getId(), false);
                                            break;
                                        default:
                                            event.getChannel().sendTyping().queue();
                                            event.getChannel().sendMessage("Usage: " + Config.getPrefix() + "autorole {add|remove} {role}").queue();
                                            break;
                                    }
                                } catch(IOException e) {
                                    e.printStackTrace(System.err);
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
                    event.getChannel().sendMessage("Usage: " + Config.getPrefix() + "autorole {add|remove} {role}").queue();
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

