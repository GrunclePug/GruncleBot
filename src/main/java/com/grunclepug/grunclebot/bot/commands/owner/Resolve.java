package com.grunclepug.grunclebot.bot.commands.owner;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import com.grunclepug.grunclebot.bot.util.log.BugReport;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Date;

public class Resolve extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Config.getPrefix() + "resolve") && event.getAuthor().getId().equals("247916497803018242")) {
            if (args.length != 2) {
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Usage: g!resolve {message_id}").queue();
            } else {
                Message m = event.getJDA().getTextChannelById(BugReport.BUG_REPORT_CHANNEL).getMessageById(args[1]).complete();

                if (m.getReactions().size() > 0) {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Report has already been resolved!").queue();
                } else {
                    m.addReaction(event.getJDA().getEmoteById("666069987689103370")).queue();

                    User user = null;
                    for (MessageEmbed.Field field : m.getEmbeds().get(0).getFields()) {
                        if (field.getName().equals("User")) {
                            user = event.getJDA().getUserById(field.getValue().split("\\n")[1]);
                            break;
                        }
                    }

                    String issue = "issue not found";
                    for (MessageEmbed.Field field : m.getEmbeds().get(0).getFields()) {
                        if (field.getName().equals("Issue")) {
                            issue = field.getValue();
                            break;
                        }
                    }

                    Date date = new Date();
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("<:greenTick:666069987689103370> Your bug report has been resolved")
                            .setDescription("Thank you for you contribution, the bug you reported has been investigated and solved.")
                            .addField("Issue", issue, false)
                            .setColor(Color.GREEN)
                            .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Report has been marked as resolved.").queue();

                    if(user != null) {
                        user.openPrivateChannel().queue((channel) -> {
                            channel.sendTyping().queue();
                            channel.sendMessage(builder.build()).queue();
                        });
                    }
                    BotLog.log(event);
                }
            }
        }
    }
}
