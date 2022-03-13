package com.grunclepug.grunclebot.bot.commands.neko_api.both;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.api.ImageAPI;
import com.grunclepug.grunclebot.bot.util.api.NekoAPI;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

/**
 * Neko Gif Command
 * @author GrunclePug
 */
public class NekoGif extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "nekogif")) {
            String title;
            String url;
            int color = 0x8904B1;

            if(event.getTextChannel().isNSFW()) {
                title = "lewd neko :O";
                url = NekoAPI.NEKO_GIF_LEWD;
            } else {
                title = "uwu what's this?";
                url = NekoAPI.NEKO_GIF;
            }

            try {
                EmbedBuilder builder = new ImageAPI().getEmbed(url, title, color);
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
