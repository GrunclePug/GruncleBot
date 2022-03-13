package com.grunclepug.grunclebot.bot.commands.utility;

import com.grunclepug.grunclebot.bot.core.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

/**
 * Random Color Command
 * @author GrunclePug
 */
public class RandomColor extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "randomcolor")) {
            Random rand = new Random();
            int color = rand.nextInt(0xffffff + 1);

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(String.format("#%06x", color))
                    .setColor(color);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
        }
    }
}
