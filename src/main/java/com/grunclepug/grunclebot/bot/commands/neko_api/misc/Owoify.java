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
 * Owoify Command
 * @author GrunclePug
 */
public class Owoify extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "owoify")) {
            String title = "placeholder";
            int color = 0x8904B1;

            if(args.length > 1) {
                String input = "";
                for(int i = 1; i < args.length; i++) {
                    input += " " + args[i];
                }

                try {
                    EmbedBuilder builder = new CustomAPI().getEmbed(NekoAPI.OWOIFY + "?text=" + input.trim(), "owo", title, color);
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(builder.build().getDescription()).queue();
                    builder.clear();
                } catch(IOException e) {
                    // Embed failed to get json data
                    e.printStackTrace(System.err);
                }
            } else {
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("You must provide input!").queue();
            }
            BotLog.log(event);
        }
    }
}
