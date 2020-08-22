package com.grunclepug.grunclebot.bot.commands.owner;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;

/**
 * Generate Invite Command
 * @author grunclepug
 */
public class GenerateInvite extends ListenerAdapter {
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Generate Invite Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "generateinvite")) {
            //Checks to see if user is bot owner (me)
            if(event.getMember().getUser().getId().equals("247916497803018242")) {
                Guild guild = event.getJDA().getGuildById(args[1]);

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Guild: " + guild.getName())
                        .setDescription("id: " + guild.getId())
                        .addField("invite:", guild.getInvites().complete().get(0).getURL(), false)
                        .setColor(0xF9AF04);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();

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
