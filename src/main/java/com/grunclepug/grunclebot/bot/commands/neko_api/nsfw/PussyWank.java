package com.grunclepug.grunclebot.bot.commands.neko_api.nsfw;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.errors.SFWChannelError;
import com.grunclepug.grunclebot.bot.util.api.ImageAPI;
import com.grunclepug.grunclebot.bot.util.api.NekoAPI;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

/**
 * Pussy Wank Command
 * @author GrunclePug
 */
public class PussyWank extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "pussywank") || args[0].equalsIgnoreCase(Config.getPrefix() + "coochiewank")) {
            String title = "can you even wank a pussy?";
            int color = 0x8904B1;

            if(event.getTextChannel().isNSFW()) {
                try {
                    EmbedBuilder builder = new ImageAPI().getEmbed(NekoAPI.PUSSY_WANK, title, color);
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessageEmbeds(builder.build()).queue();
                    builder.clear();
                } catch(IOException e) {
                    // Embed failed to get json data
                    e.printStackTrace(System.err);
                }
            } else {
                new SFWChannelError(event);
            }
            BotLog.log(event);
        }
    }
}
