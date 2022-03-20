package com.grunclepug.grunclebot.bot.util.log;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Date;

/**
 * Bug report command to log bug reports submitted by users
 * @author GrunclePug
 */
public class BugReport extends ListenerAdapter {
    private EmbedBuilder builder = new EmbedBuilder();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "bugreport") || args[0].equalsIgnoreCase(Config.getPrefix() + "reportbug")) {
            Date date = new Date();
            builder.clear();

            builder.setTitle("Bug Report")
                    .addField("User", event.getAuthor().getAsMention() + "\n" + event.getAuthor().getId(), true)
                    .addField("Guild", event.getGuild().getName() + "\n" + event.getGuild().getId(), true)
                    .addField("Channel", event.getChannel().getAsMention() + "\n" + event.getChannel().getId(), true)
                    .addField("Issue", event.getMessage().getContentDisplay().substring(args[0].length()).trim(), false)
                    .setColor(Color.CYAN)
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("<:greenTick:934590255590420552> Bug Report filed.").queue();
            Driver.jda.getTextChannelById(Config.getBugReportChannel()).sendMessageEmbeds(builder.build()).queue();

            BotLog.log(event);
        }
    }
}
