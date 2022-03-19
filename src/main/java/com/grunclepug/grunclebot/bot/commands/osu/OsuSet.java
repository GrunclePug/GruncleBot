package com.grunclepug.grunclebot.bot.commands.osu;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.api.OsuAPI;
import com.grunclepug.grunclebot.bot.util.osu.FileInteraction;
import com.grunclepug.grunclebot.bot.util.osu.User;
import com.oopsjpeg.osu4j.exception.OsuAPIException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Osu Set User Command for Logging
 * @author GrunclePug
 */
public class OsuSet extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "osuset")) {
            ArrayList<User> users = null;
            String osuUser = "";
            String osuID = null;

            System.out.println("Command issued");

            try {
                System.out.println("Read file");
                users = FileInteraction.readFile();
            } catch(IOException e) {
                e.printStackTrace(System.err);
            }

            if(args.length > 1) {
                for(int i = 1; i < args.length; i++) {
                    osuUser += " " + args[i];
                }

                try {
                    System.out.println("Get user ID for:" + osuUser);
                    osuID = OsuAPI.getUserID(osuUser.trim());
                } catch (OsuAPIException e) {
                    e.printStackTrace(System.err);
                }

                if(osuID != null) {
                    System.out.println("ID not null");
                    try {
                        FileInteraction.updateFile(event.getMember().getUser().getId(), osuID);
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Success! your osu username has been set to: " + osuUser).queue();
                    } catch (IOException e) {
                        e.printStackTrace(System.err);
                    }
                }
            } else {
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Usage: " + Config.getPrefix() + "osuset {osu username}").queue();
            }
        }
    }
}
