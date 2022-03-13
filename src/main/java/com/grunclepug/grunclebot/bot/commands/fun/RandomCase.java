package com.grunclepug.grunclebot.bot.commands.fun;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Locale;
import java.util.Random;

/**
 * RandomCase Command
 * Displays a string using a random mixture of upper and lower case
 * @author GrunclePug
 */
public class RandomCase extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "randomcase")) {
            if(!args[1].toLowerCase(Locale.ROOT).startsWith(Config.getPrefix())) {
                String content = event.getMessage().getContentRaw().substring(args[0].length()).toLowerCase().trim();
                char[] chars = content.toCharArray();
                Random random = new Random();

                for(int i = 0; i < content.length(); i++) {
                    if(random.nextBoolean()) {
                        chars[i] = Character.toUpperCase(chars[i]);
                    }
                }
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(String.copyValueOf(chars)).queue();
            } else {
                // Lack in perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 Nice try")
                        .setDescription("You cannot issue a command inside of this command!")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
