package com.grunclepug.grunclebot.bot.commands.myanimelist;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.api.JikanAPI;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class SearchCharacter extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "character")) {
            String title = "";
            String query = "";
            for(int i = 1; i < args.length; i++) {
                query += " " + args[i];
            }
            try {
                EmbedBuilder builder = JikanAPI.getEmbed(JikanAPI.SEARCH_CHARACTER, query.trim(), 0x2596be);

                for(MessageEmbed.Field field : builder.build().getFields()) {
                    if(field.getName().equalsIgnoreCase("NAME")) {
                        title = field.getValue();
                    }
                }
                builder.setTitle(title);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
            BotLog.log(event);
        }
    }
}
