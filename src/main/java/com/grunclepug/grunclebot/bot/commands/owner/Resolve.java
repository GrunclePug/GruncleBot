package com.grunclepug.grunclebot.bot.commands.owner;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Date;

/**
 * Resolve Bug Report Command
 * @author GrunclePug
 */
public class Resolve extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "resolve")) {
            // Verify Identity
            if(event.getMember().getId().equals("247916497803018242") || event.getMember().getId().equals("474703059981697025")) {
                if(args.length != 2) {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Usage: " + Config.getPrefix() + "resolve {message_id}").queue();
                } else {
                    Message report = event.getJDA().getTextChannelById(Config.getBugReportChannel()).retrieveMessageById(args[1]).complete();

                    if(report.getReactions().size() > 0) {
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Report has already been resolved!").queue();
                    } else {
                        report.addReaction(event.getJDA().getEmoteById("934590255590420552")).queue();

                        User user = null;
                        String issue = "Issue not found";
                        for(MessageEmbed.Field field : report.getEmbeds().get(0).getFields()) {
                            switch(field.getName()) {
                                case "User":
                                    user = event.getJDA().getUserById(field.getValue().split("\\n")[1]);
                                    break;
                                case "Issue":
                                    issue = field.getValue();
                                    break;
                            }
                        }

                        Date date = new Date();
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("<:greenTick:934590255590420552> Your bug report has been resolved")
                                .setDescription("Thank you for you contribution, the bug you reported has been investigated and solved.")
                                .addField("Issue", issue, false)
                                .setColor(Color.GREEN)
                                .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("Report has been marked as resolved.").queue();

                        if(user != null) {
                            user.openPrivateChannel().queue((channel) -> {
                                channel.sendTyping().queue();
                                channel.sendMessageEmbeds(builder.build()).queue();
                            });
                        }
                    }
                }
            } else {
                // Lack in Perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                        .setDescription("You are not the owner of this bot!")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
