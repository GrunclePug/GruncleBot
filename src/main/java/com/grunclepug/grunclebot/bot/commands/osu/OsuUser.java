package com.grunclepug.grunclebot.bot.commands.osu;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.api.OsuAPI;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import com.grunclepug.grunclebot.bot.util.osu.FileInteraction;
import com.grunclepug.grunclebot.bot.util.osu.User;
import com.oopsjpeg.osu4j.GameMode;
import com.oopsjpeg.osu4j.exception.OsuAPIException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Osu User command
 * @author GrunclePug
 */
public class OsuUser extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "osu")) {
            String userID = null;
            String user = "";
            String mode = "";
            ArrayList<User> users = null;

            if(args.length < 3) {
                try {
                    users = FileInteraction.readFile();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }

                if(users != null) {
                    ArrayList<String> userIDs = new ArrayList<>();
                    for(int i = 0; i < users.size(); i++) {
                        userIDs.add(users.get(i).getUserID());
                    }

                    if(userIDs.contains(event.getMember().getUser().getId())) {
                        for(int i = 0; i < users.size(); i++) {
                            if(users.get(i).getUserID().equalsIgnoreCase(event.getMember().getUser().getId())) {
                                userID = users.get(i).getOsuID();
                            }
                        }
                    }
                }
            } else {
                if(args.length > 2) {
                    user = "";
                    for (int i = 2; i < args.length; i++) {
                        user += " " + args[i];
                    }
                    user = user.trim();
                }
            }

            if(args.length > 1) {
                mode = args[1].trim();
            }

            GameMode gameMode = null;
            switch(mode.toUpperCase()) {
                case "STANDARD":
                case "STD":
                case "S":
                    gameMode = OsuAPI.STANDARD;
                    break;
                case "M":
                case "MANIA":
                    gameMode = OsuAPI.MANIA;
                    break;
                case "T":
                case "TAIKO":
                    gameMode = OsuAPI.TAIKO;
                    break;
                case "C":
                case "CATCH":
                case "CTB":
                    gameMode = OsuAPI.CATCH;
                    break;
                default:
                    gameMode = OsuAPI.STANDARD;
                    break;
            }

            if(gameMode != null) {
                EmbedBuilder builder = null;
                try {
                    if(userID != null) {
                        builder = OsuAPI.getUser(userID, gameMode);
                    } else if(user != null && user.length() > 0) {
                        builder = OsuAPI.getUser(user, gameMode);
                    } else {
                        // Usage
                        builder = new EmbedBuilder();
                        builder.setTitle("You need to set your osu username first!")
                                .setDescription("Usage: `" + Config.getPrefix() + "osuset {osu username}`")
                                .setColor(0xff3923);
                    }

                } catch (OsuAPIException e) {
                    e.printStackTrace(System.err);
                }

                if(builder != null) {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessageEmbeds(builder.build()).queue();
                }
            }
            BotLog.log(event);
        }
    }
}
