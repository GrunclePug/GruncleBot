package com.grunclepug.grunclebot.bot.errors;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class SFWChannelError
{
    GuildMessageReceivedEvent event;

    public SFWChannelError(GuildMessageReceivedEvent event)
    {
        this.event = event;
        sendError();
    }

    public void sendError()
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("\uD83D\uDED1 NSFW Content")
                .setDescription("Please enter a NSFW channel to view this content.")
                .setColor(0xff3923);

        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage(builder.build()).queue();
        builder.clear();
    }
}
