package com.grunclepug.grunclebot.bot.commands.owner;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;
import com.grunclepug.grunclebot.bot.util.guild.Channel;
import com.grunclepug.grunclebot.bot.util.guild.FileInteraction;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Channel Log Command
 * @author GrunclePug
 */
public class ChannelLog extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(!event.getAuthor().isBot()) {
            Date date = new Date();
            ArrayList<Channel> channels = null;

            try {
                channels = FileInteraction.readChannelFile();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }

            if(channels != null) {
                for(int i = 0; i < channels.size(); i++) {
                    if(channels.get(i).getID().equals(event.getChannel().getId())) {
                        EmbedBuilder builder = new EmbedBuilder();

                        builder.setTitle("Channel Log: " + event.getChannel().getName())
                                .setDescription("ID: " + event.getChannel().getId())
                                .addField("Message Author", event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + " (" + event.getAuthor().getId() + ")", false)
                                .addField("Message", event.getMessage().getContentDisplay(), false)
                                .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/WQSW5lV.png");

                        Driver.jda.getTextChannelById(channels.get(i).getLogChannel()).sendMessageEmbeds(builder.build()).queue();
                    }
                }
            }
        }

        //Channel Log Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "logchannel") || args[0].equalsIgnoreCase(Config.getPrefix() + "channellog")) {
            if(event.getAuthor().getId().equals("247916497803018242") || event.getAuthor().getId().equals("474703059981697025")) {
                if(args.length != 4) {
                    event.getChannel().sendMessage("Usage: " + Config.getPrefix() + "logchannel add|remove {channel_id} {log_channel_id}").queue();
                } else {
                    try {
                        switch(args[1].trim().toUpperCase()) {
                            case "ADD":
                                FileInteraction.updateChannelFile(args[2].trim(), args[3].trim(), true);
                                break;
                            case "REMOVE":
                                FileInteraction.updateChannelFile(args[2].trim(), args[3].trim(), false);
                                break;
                        }
                    } catch(IOException e) {
                        e.printStackTrace(System.err);
                    }
                }
            }
            BotLog.log(event);
        }
    }
}
