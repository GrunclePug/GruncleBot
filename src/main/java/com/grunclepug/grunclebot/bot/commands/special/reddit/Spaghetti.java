package com.grunclepug.grunclebot.bot.commands.special.reddit;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.errors.SFWChannelError;
import com.grunclepug.grunclebot.bot.util.api.ImageAPI;
import com.grunclepug.grunclebot.bot.util.api.RedditAPI;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Spaghetti Hentai Command
 * @author GrunclePug
 */
public class Spaghetti extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "spaghetti")) {
            if(event.getTextChannel().isNSFW()) {
                String title = "SSSOOOO LLEEEWWWWDDDDD!!";
                int color = 0xF9DB72;

                EmbedBuilder builder = new ImageAPI().getRedditEmbed(RedditAPI.SPAGHETTI, title, color);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
            } else {
                new SFWChannelError(event);
            }
            BotLog.log(event);
        }
    }
}
