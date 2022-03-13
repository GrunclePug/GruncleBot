package com.grunclepug.grunclebot.bot.errors;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Rate Limit Error
 * @author GrunclePug
 */
public class RateLimitError {
    private MessageReceivedEvent event;
    private long retryAfter;

    /**
     * Constructor for RateLimitError
     * @param event MessageReceived event that triggered error
     */
    public RateLimitError(MessageReceivedEvent event, long retryAfter) {
        this.event = event;
        this.retryAfter = retryAfter;
        this.sendError();
    }

    /**
     * Send a RateLimitError
     */
    private void sendError() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("\uD83D\uDED1 Rate Limited")
                .setDescription("This command is being rate limited by the Discord API" +
                        "\nPlease wait " + retryAfter / 1000 + " seconds")
                .setColor(0xff3923);

        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessageEmbeds(builder.build()).queue();
        builder.clear();
    }
}
