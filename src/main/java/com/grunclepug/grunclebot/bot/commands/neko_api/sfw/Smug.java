package com.grunclepug.grunclebot.bot.commands.neko_api.sfw;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.api.ImageAPI;
import com.grunclepug.grunclebot.bot.util.api.NekoAPI;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

/**
 * Smug Command
 * @author GrunclePug
 */
public class Smug extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "smug")) {
            String title = ":3";
            int color = 0x8904B1;

            try {
                EmbedBuilder builder = new ImageAPI().getEmbed(NekoAPI.SMUG, title, color);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                builder.clear();
            } catch(IOException e) {
                // Embed failed to get json data
                e.printStackTrace(System.err);
            }
            BotLog.log(event);
        }
    }
}
