package com.grunclepug.grunclebot.bot.commands.osu;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.api.OsuAPI;
import com.grunclepug.grunclebot.bot.util.osu.FileInteraction;
import com.grunclepug.grunclebot.bot.util.osu.User;
import com.oopsjpeg.osu4j.GameMode;
import com.oopsjpeg.osu4j.exception.OsuAPIException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Osu Top Plays Command
 * @author GrunclePug
 */
public class OsuTop extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "osutop")) {
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
                    gameMode = OsuAPI.STANDARD;
                    break;
                case "MANIA":
                    gameMode = OsuAPI.MANIA;
                    break;
                case "TAIKO":
                    gameMode = OsuAPI.TAIKO;
                    break;
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
                        builder = OsuAPI.getTopPlays(userID, gameMode);
                    } else {
                        builder = OsuAPI.getTopPlays(user, gameMode);
                    }

                } catch (OsuAPIException e) {
                    e.printStackTrace(System.err);
                } catch(MalformedURLException e) {
                    e.printStackTrace(System.err);
                }

                if(builder != null) {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessageEmbeds(builder.build()).queue();
                }
            }

        }
    }
}
