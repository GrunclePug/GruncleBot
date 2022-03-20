package com.grunclepug.grunclebot.bot.commands.owner;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;
import com.grunclepug.grunclebot.bot.util.guild.FileInteraction;
import com.grunclepug.grunclebot.bot.util.guild.Guild;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Guild Log Command
 * @author GrunclePug
 */
public class GuildLog extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(!event.getAuthor().isBot()) {
            Date date = new Date();
            ArrayList<Guild> guilds = null;

            try {
                guilds = FileInteraction.readGuildFile();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }

            if(guilds != null) {
                for(int i = 0; i < guilds.size(); i++) {
                    if(guilds.get(i).getID().equals(event.getGuild().getId())) {
                        EmbedBuilder builder = new EmbedBuilder();

                        builder.setTitle("Guild Log: " + event.getGuild().getName())
                                .setDescription("ID: " + event.getGuild().getId())
                                .addField("Channel", event.getChannel().getName() + " (" + event.getChannel().getId() + ")", false)
                                .addField("Message Author", event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + " (" + event.getAuthor().getId() + ")", false)
                                .addField("Message", event.getMessage().getContentDisplay(), false)
                                .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/WQSW5lV.png");

                        Driver.jda.getTextChannelById(guilds.get(i).getLogChannel()).sendMessageEmbeds(builder.build()).queue();
                    }
                }
            }
        }

        //Guild Log Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "logguild") || args[0].equalsIgnoreCase(Config.getPrefix() + "guildlog")) {
            if(event.getAuthor().getId().equals("247916497803018242") || event.getAuthor().getId().equals("474703059981697025")) {
                if(args.length != 4) {
                    event.getChannel().sendMessage("Usage: " + Config.getPrefix() + "logguild add|remove {guild_id} {log_channel_id}").queue();
                } else {
                    try {
                        switch(args[1].trim().toUpperCase()) {
                            case "ADD":
                                FileInteraction.updateGuildFile(args[2].trim(), args[3].trim(), true);
                                break;
                            case "REMOVE":
                                FileInteraction.updateGuildFile(args[2].trim(), args[3].trim(), false);
                                break;
                        }
                    } catch(IOException e) {
                        e.printStackTrace(System.err);
                    }
                }
            }
            BotLog.log(event);
        }
    }
}
