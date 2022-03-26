package com.grunclepug.grunclebot.bot.commands.owner;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

/**
 * Get Image Command
 * @author GrunclePug
 */
public class GetImages extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "getimages")) {
            // Verify Identity
            if(event.getMember().getId().equals("247916497803018242") || event.getMember().getId().equals("474703059981697025")) {
                TextChannel channel = Driver.jda.getTextChannelById(args[1]);
                List<Message> messages = channel.getHistory().retrievePast(Integer.parseInt(args[2])).complete();

                for(int i = 0; i < messages.size(); i++) {
                    if(messages.get(i).getAttachments().size() > 0) {
                        for(int j = 0; j < messages.get(i).getAttachments().size(); j++) {
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("Image")
                                    .setImage(messages.get(i).getAttachments().get(j).getUrl());
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessageEmbeds(builder.build()).queue();
                        }
                    }
                }
            }
            BotLog.log(event);
        }
    }
}
