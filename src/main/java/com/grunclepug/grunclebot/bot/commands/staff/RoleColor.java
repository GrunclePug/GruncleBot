package com.grunclepug.grunclebot.bot.commands.staff;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.role.update.RoleUpdateColorEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.beryx.awt.color.ColorFactory;

import java.awt.*;

public class RoleColor extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "rolecolor")) {

            if(event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                Role role;
                if(event.getMessage().getMentionedRoles().size() > 0) {
                    role = event.getMessage().getMentionedRoles().get(0);
                } else {
                    role = event.getGuild().getRolesByName(args[1], true).get(0);
                }

                if(role != null) {
                    if(args.length == 3) {
                        try {
                            Color color = ColorFactory.valueOf("0x" + args[2]);
                            role.getManager().setColor(color).queue();

                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("<:greenTick:666069987689103370> Role color updated")
                                    .setDescription(role.getName() + " has been updated with a new color: 0x" + args[2])
                                    .setColor(color);
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();

                        } catch(Exception e) {
                            e.printStackTrace();
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage("Invalid color code: " + args[2]).queue();
                        }
                    } else {
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Usage: g!rolecolor {role} {color_code}").queue();
                    }
                } else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Role not found: " + args[1]).queue();
                }
            }
            BotLog.log(event);
        }
    }
}
