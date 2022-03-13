package com.grunclepug.grunclebot.bot.commands.neko_api.misc;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.api.CustomAPI;
import com.grunclepug.grunclebot.bot.util.api.NekoAPI;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

/**
 * Why Command
 * @author GrunclePug
 */
public class Why extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "why")) {
            String title = "placeholder";
            int color = 0x8904B1;

            try {
                EmbedBuilder builder = new CustomAPI().getEmbed(NekoAPI.WHY, "why", title, color);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build().getDescription()).queue();
                builder.clear();
            } catch(IOException e) {
                // Embed failed to get json data
                e.printStackTrace(System.err);
            }
            BotLog.log(event);
        }
    }
}
