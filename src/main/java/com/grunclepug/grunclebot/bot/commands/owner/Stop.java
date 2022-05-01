package com.grunclepug.grunclebot.bot.commands.owner;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Shutdown Command
 * @author grunclepug
 */
public class Stop extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "stop") || args[0].equalsIgnoreCase(Config.getPrefix() + "shutdown")) {
            if(event.getMessage().getAuthor().getId().equals("247916497803018242") || event.getMessage().getAuthor().getId().equals(Driver.jda.getSelfUser().getId())) {
                BotLog.log(event);
                System.exit(0);
            }
        }
    }
}
