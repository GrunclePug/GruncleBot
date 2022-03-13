package com.grunclepug.grunclebot.bot.commands.utility;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Enlarge Command
 * @author GrunclePug
 */
public class Enlarge extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "enlarge")) {
            if(args.length > 1 && event.getMessage().getEmotes().size() > 0) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle(event.getMessage().getEmotes().get(0).getName())
                        .setImage(event.getMessage().getEmotes().get(0).getImageUrl())
                        .setColor(0x8904B1);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                builder.clear();
            } else {
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Usage: " + Config.getPrefix() + "enlarge {emote} (custom emotes only)").queue();
            }
            BotLog.log(event);
        }
    }
}
