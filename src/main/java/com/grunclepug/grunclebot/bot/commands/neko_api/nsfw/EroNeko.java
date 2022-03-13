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
 * Ero Neko Command
 * @author GrunclePug
 */
public class EroNeko extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "eroneko")) {
            String title = "ero neko :O";
            int color = 0x8904B1;

            if(event.getTextChannel().isNSFW()) {
                try {
                    EmbedBuilder builder = new ImageAPI().getEmbed(NekoAPI.NEKO_ERO, title, color);
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
