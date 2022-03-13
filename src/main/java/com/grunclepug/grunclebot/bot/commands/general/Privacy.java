package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Privacy Policy Command
 * @author GrunclePug
 */
public class Privacy extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "privacy")) {
            String message = "What data does this bot store?\n" +
                    "-User IDs\n" +
                    "Why do we need the data, and why do we use this data?\n" +
                    "\n" +
                    "A) User IDs are used to keep track of data for bug reports, commands used, afk status, and suggestions\n" +
                    "\n" +
                    "No info saved is public or shared anywhere besides discord\n" +
                    "\n" +
                    "How can users get data removed, or how can users contact the bot owner? They can join the support server or use my discord handle GrunclePug#7015";

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(message).queue();

            BotLog.log(event);
        }
    }
}
