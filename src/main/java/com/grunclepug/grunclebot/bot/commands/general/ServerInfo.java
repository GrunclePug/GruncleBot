package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Server Info Command
 * @author GrunclePug
 */
public class ServerInfo extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "serverinfo") || args[0].equalsIgnoreCase(Config.getPrefix() + "guildinfo")) {
            Date date = new Date();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Server: " + event.getGuild().getName())
                    .setThumbnail(event.getGuild().getIconUrl())
                    .addField("・Owner", event.getGuild().getOwner().getAsMention(), false)
                    .addField("・Date Created", event.getGuild().getTimeCreated().format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm")), false)
                    .addField("・Members", "" + event.getGuild().getMembers().size(), false)
                    .addField("・Roles", "" + event.getGuild().getRoles().size(), false)
                    .addField("・Text Channels", "" + event.getGuild().getTextChannels().size(), false)
                    .addField("・Voice Channels", "" + event.getGuild().getVoiceChannels().size(), false)
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/WQSW5lV.png")
                    .setColor(0x00FF00);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
            builder.clear();

            BotLog.log(event);
        }
    }
}
