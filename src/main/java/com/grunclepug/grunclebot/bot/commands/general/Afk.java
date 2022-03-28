package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.afk.FileInteraction;
import com.grunclepug.grunclebot.bot.util.afk.User;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Afk command
 * @author GrunclePug
 */
public class Afk extends ListenerAdapter {
    public static final String AFK_LOG_FILE = "src/main/resources/afk_log.json";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        ArrayList<User> users = null;

        try {
            users = FileInteraction.readFile(AFK_LOG_FILE);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        if(users != null) {
            // Check if member comes back
            for(int i = 0; i < users.size(); i++) {
                if(users.get(i).getId().equals(event.getMessage().getAuthor().getId())) {
                    try {
                        FileInteraction.updateFile(AFK_LOG_FILE, event.getMessage().getAuthor().getId(), false, null);
                    } catch (IOException e) {
                        e.printStackTrace(System.err);
                    }
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(event.getMessage().getAuthor().getName() + ", you have been removed from the afk list!").queue(message -> {
                        message.delete().queueAfter(5000, TimeUnit.MILLISECONDS);
                    });
                    break;
                }
            }

            // Check if member gets pinged
            for(int i = 0; i < event.getMessage().getMentionedMembers().size(); i++) {
                for(int j = 0; j < users.size(); j++) {
                    if(event.getMessage().getMentionedMembers().get(i).getId().equals(users.get(j).getId())) {
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("**" + event.getMessage().getMentionedMembers().get(i).getEffectiveName() + "** is currently afk.")
                                .setDescription("**Reason:** " + users.get(j).getReason())
                                .setColor(0x000000);
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessageEmbeds(builder.build()).queue();
                    }
                }
            }
        }

        // Afk Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "afk")) {
            String reason = "";

            if(args.length > 1) {
                for(int i = 1; i < args.length; i++) {
                    reason += (" " + args[i]);
                }
                reason = reason.trim();
            } else {
                reason = User.DEFAULT_REASON;
            }

            try {
                FileInteraction.updateFile(AFK_LOG_FILE, event.getMessage().getAuthor().getId(), true, reason);
            } catch (IOException e) {
                e.printStackTrace();
            }

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("**" + event.getMessage().getAuthor().getName() + "**, you have been set to afk")
                    .setDescription("**Reason:** " + reason)
                    .setColor(0x000000);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
            builder.clear();

            BotLog.log(event);
        }
    }
}
