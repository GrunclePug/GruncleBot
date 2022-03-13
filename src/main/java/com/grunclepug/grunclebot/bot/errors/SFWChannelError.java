package com.grunclepug.grunclebot.bot.errors;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * SFW Channel Error
 * @author GrunclePug
 */
public class SFWChannelError {
    private MessageReceivedEvent event;

    /**
     * Constructor for SFWChannelError
     * @param event MessageReceived event that triggered error
     */
    public SFWChannelError(MessageReceivedEvent event) {
        this.event = event;
        this.sendError();
    }

    /**
     * Send a SFWChannelError
     */
    private void sendError() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("\uD83D\uDED1 NSFW Content")
                .setDescription("Please enter a NSFW channel to view this content.")
                .setColor(0xff3923);

        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessageEmbeds(builder.build()).queue();
        builder.clear();
    }
}
