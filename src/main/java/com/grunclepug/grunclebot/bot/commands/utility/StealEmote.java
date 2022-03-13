package com.grunclepug.grunclebot.bot.commands.utility;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.apache.commons.io.FileUtils;
import java.io.*;
import java.net.URL;

/**
 * Steal Emote Command
 * @author GrunclePug
 */
public class StealEmote extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "stealemote") || args[0].equalsIgnoreCase(Config.getPrefix() + "steal") || args[0].equalsIgnoreCase(Config.getPrefix() + "yoink")) {
            if(event.getMember().hasPermission(Permission.MANAGE_EMOTES_AND_STICKERS)) {
                if(args.length > 1) {
                    Emote emote = event.getMessage().getEmotes().get(0);
                    File file = new File("emote" + emote.getImageUrl().substring(emote.getImageUrl().length() - 4));
                    String emoteName = "";
                    try {
                        FileUtils.copyURLToFile(new URL(emote.getImageUrl()), file);

                        if(args.length > 2) {
                            emoteName = args[2];
                        } else {
                            emoteName = emote.getName();
                        }
                        event.getGuild().createEmote(emoteName, Icon.from(file)).queue();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Emote has been added with the name `" + emoteName + "`").queue();
                } else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Usage: " + Config.getPrefix() + "steal {emote} {new name (optional}").queue();
                }
            } else {
                // Lack in Perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                        .setDescription("This command requires you to have the 'Manage Emotes' permission")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
