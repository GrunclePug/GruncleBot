package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.LocalDateTime;

/**
 * Server Info Command
 */
public class ServerInfo extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Server Info Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + ("serverinfo")) || args[0].equalsIgnoreCase(Config.getPrefix() + "guildinfo"))
        {
            Guild guild = event.getGuild();
            String guildCreationDate = event.getGuild().getCreationTime().toLocalDateTime().toString().replace("T", " At ");
            String formattedGuildCreationDate = guildCreationDate.substring(0, guildCreationDate.indexOf("."));
            String currentTime = LocalDateTime.now().toString().replace("T", " At ");
            String formattedCurrentTime = currentTime.substring(0, currentTime.indexOf("."));

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Server: " + guild.getName())
                .setThumbnail(guild.getIconUrl())
                .addField("・Owner", guild.getOwner().getAsMention(), false)
                .addField("・Date Created", formattedGuildCreationDate, false)
                .addField("・Members", "" + guild.getMembers().size(), false)
                .addField("・Roles", "" + guild.getRoles().size(), false)
                .addField("・Text Channels", "" + guild.getTextChannels().size(), false)
                .addField("・Voice Channels", "" + guild.getVoiceChannels().size(), false)
                .setFooter(formattedCurrentTime, "https://i.imgur.com/WQSW5lV.png")
                .setColor(0x00FF00);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();
        }
    }
}
