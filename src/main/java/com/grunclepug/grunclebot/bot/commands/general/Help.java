package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;
import com.grunclepug.grunclebot.bot.util.help.CommandInfo;
import com.grunclepug.grunclebot.bot.util.help.FileInteraction;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Help Command
 * @author GrunclePug
 */
public class Help extends ListenerAdapter {
    private static String title = Driver.jda.getSelfUser().getName() + " Command Help";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "help")) {
            ArrayList<CommandInfo> commandInfos = null;
            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle(title)
                    .setColor(0xFF00FF)
                    .setDescription("Bot created by @GrunclePug#7015")
                    .setThumbnail("https://i.imgur.com/bfwjiDz.png");

            try {
                commandInfos = FileInteraction.readFile();
            } catch(IOException e) {
                e.printStackTrace(System.err);
            }

            ArrayList<String> categories = new ArrayList<>();
            for(int i = 0; i < commandInfos.size(); i++) {
                if(categories != null && categories.size() > 0) {
                    if(!categories.contains(commandInfos.get(i).getCategory().replace("\"", ""))) {
                        categories.add(commandInfos.get(i).getCategory().replace("\"", ""));
                    }
                } else {
                    categories.add(commandInfos.get(i).getCategory().replace("\"", ""));
                }
            }

            ArrayList<MessageEmbed.Field> fields = getPage(categories.get(0), commandInfos);
            for(int i = 0; i < fields.size(); i++) {
                builder.addField(fields.get(i));
            }

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessageEmbeds(builder.build()).queue(message -> {
                message.addReaction("⬅").queue();
                message.addReaction("➡").queue();
            });
            BotLog.log(event);
        }
    }

    /**
     * Create and return help page
     * @param category category of page
     * @param commandInfos command info arraylist
     * @return help page
     */
    public ArrayList<MessageEmbed.Field> getPage(String category, ArrayList<CommandInfo> commandInfos) {
        ArrayList<MessageEmbed.Field> page = new ArrayList<>();

        if(commandInfos != null) {
            MessageEmbed.Field categoryTitle = new MessageEmbed.Field(category.toUpperCase(), "", false);
            MessageEmbed.Field command = null;

            page.add(categoryTitle);
            for(int j = 0; j < commandInfos.size(); j++) {
                if(commandInfos.get(j).getCategory().replace("\"", "").equalsIgnoreCase(category)) {
                    command = new MessageEmbed.Field(commandInfos.get(j).getCommand().replace("\"", ""),
                            commandInfos.get(j).getDescription().replace("\"", "") +
                                    "\nUsage: " + commandInfos.get(j).getUsage().replace("\"", ""), true);
                    page.add(command);
                }
            }
        }
        return page;
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        Message message = event.retrieveMessage().complete();

        // Check if message is from bot
        if(message.getAuthor().getId().equals(Driver.jda.getSelfUser().getId())) {
            // Check if message is help command
            if(message.getEmbeds().size() > 0 && message.getEmbeds().get(0).getTitle().equals(title)) {
                // Check if reaction is from bot
                if(!event.getMember().getUser().isBot()) {
                    if(event.getReactionEmote().getEmoji().equalsIgnoreCase("⬅")) {
                        event.getReaction().removeReaction(event.getUser()).queue();
                        updateMessage(message, "LEFT");
                    } else if(event.getReactionEmote().getEmoji().equalsIgnoreCase("➡")) {
                        event.getReaction().removeReaction(event.getUser()).queue();
                        updateMessage(message, "RIGHT");
                    }
                }
            }
        }
    }

    /**
     * Update message with new page
     * @param message help command
     * @param action action to do
     */
    public void updateMessage(Message message, String action) {
        ArrayList<CommandInfo> commandInfos = null;
        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(title)
                .setColor(0xFF00FF)
                .setDescription("Bot created by @GrunclePug#7015")
                .setThumbnail("https://i.imgur.com/bfwjiDz.png");

        try {
            commandInfos = FileInteraction.readFile();
        } catch(IOException e) {
            e.printStackTrace(System.err);
        }

        ArrayList<String> categories = new ArrayList<>();
        for(int i = 0; i < commandInfos.size(); i++) {
            if(categories != null && categories.size() > 0) {
                if(!categories.contains(commandInfos.get(i).getCategory().replace("\"", ""))) {
                    categories.add(commandInfos.get(i).getCategory().replace("\"", ""));
                }
            } else {
                categories.add(commandInfos.get(i).getCategory().replace("\"", ""));
            }
        }

        String page = message.getEmbeds().get(0).getFields().get(0).getName();
        String nextPage = null;
        String previousPage = null;

        for(int i = 0; i < categories.size(); i++) {
            if(categories.get(i).equalsIgnoreCase(page)) {
                if(categories.get(i).equalsIgnoreCase(categories.get(0))) {
                    previousPage = categories.get(categories.size() - 1);
                    nextPage = categories.get(i + 1);
                } else if(categories.get(i).equalsIgnoreCase(categories.get(categories.size() - 1))) {
                    previousPage = categories.get(i - 1);
                    nextPage = categories.get(0);
                } else {
                    previousPage = categories.get(i - 1);
                    nextPage = categories.get(i + 1);
                }
            }
        }

        ArrayList<MessageEmbed.Field> fields = null;

        switch(action) {
            case "LEFT":
                fields = getPage(previousPage, commandInfos);
                break;
            case "RIGHT":
                fields = getPage(nextPage, commandInfos);
                break;
        }

        for(int i = 0; i < fields.size(); i++) {
            builder.addField(fields.get(i));
        }

        message.editMessageEmbeds(builder.build()).queue();
    }
}
