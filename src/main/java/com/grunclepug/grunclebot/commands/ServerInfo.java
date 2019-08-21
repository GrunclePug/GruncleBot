package com.grunclepug.grunclebot.commands;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
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
        if(args[0].equalsIgnoreCase(Main.prefix + "serverinfo"))
        {
            String guildName = event.getGuild().getName();
            String guildOwner = event.getGuild().getOwner().getAsMention();
            String guildCreationDate = event.getGuild().getCreationTime().toLocalDateTime().toString().replace("T", " At ");
            String _guildCreationDate = guildCreationDate.substring(0, guildCreationDate.indexOf("."));
            String guildIcon = event.getGuild().getIconUrl();
            String currentTime = LocalDateTime.now().toString().replace("T", " At ");
            String _currentTime = currentTime.substring(0, currentTime.indexOf("."));

            int guildMembers = event.getGuild().getMembers().size();
            int guildRoles = event.getGuild().getRoles().size();
            int guildTextChannels = event.getGuild().getTextChannels().size();
            int guildVoiceChannels = event.getGuild().getVoiceChannels().size();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Server: " + guildName)
                .setThumbnail(guildIcon)
                .addField("・Owner", guildOwner, true)
                .addField("・Date Created", _guildCreationDate, true)
                .addField("・Members", "" + guildMembers, true)
                .addField("・Roles", "" + guildRoles, true)
                .addField("・Text Channels", "" + guildTextChannels, true)
                .addField("・Voice Channels", "" + guildVoiceChannels, true)
                .setFooter(_currentTime, "https://i.imgur.com/WQSW5lV.png")
                .setColor(0x00FF00);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();
        }
    }
}
