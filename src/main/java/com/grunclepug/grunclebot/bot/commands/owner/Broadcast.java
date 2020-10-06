package com.grunclepug.grunclebot.bot.commands.owner;

import com.grunclepug.grunclebot.bot.core.Config;

import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Generate Invite Command
 * @author grunclepug
 */
public class Broadcast extends ListenerAdapter {
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Generate Invite Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "broadcast")) {
            //Checks to see if user is bot owner (me)
            if(event.getMember().getUser().getId().equals("247916497803018242")) {
                TextChannel target = event.getJDA().getTextChannelById(args[1]);
                String message = "";

                for(int i = 2; i < args.length; i++) {
                    message += " " + args[i];
                }

                target.sendTyping().queue();
                target.sendMessage(message).queue();
            } else {
                // Lack in Perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                        .setDescription("You are not the owner of this bot!")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
