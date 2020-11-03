package com.grunclepug.grunclebot.bot.util.log;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BotLog extends ListenerAdapter
{
    public static final String BOT_LOG_CHANNEL = "665793550990180392";
    public static final String GUILDS_LOG_CHANNEL = "665826957300072448";
    private static EmbedBuilder builder = new EmbedBuilder();

    public static void log(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        Date date = new Date();

        builder.setTitle("__Command: " + args[0].substring(Config.getPrefix().length()) + "__")
                .addField("User", event.getAuthor().getAsMention() + "\n" + event.getAuthor().getId(), true)
                .addField("Guild", event.getGuild().getName() + "\n" + event.getGuild().getId(), true)
                .addField("Channel", event.getChannel().getAsMention() + "\n" + event.getChannel().getId(), true)
                .addField("Command issued", event.getMessage().getContentDisplay(), false)
                .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");

        Driver.jda.getTextChannelById(BOT_LOG_CHANNEL).sendMessage(builder.build()).queue();
    }

    public void onGenericGuild(GenericGuildEvent event)
    {
        Date date = new Date();
        builder.clear();

        // Bot join/leave
        if(event instanceof GuildJoinEvent)
        {
            GuildJoinEvent e = (GuildJoinEvent) event;

            builder.setTitle("__Bot Added__")
                    .addField("Guild", e.getGuild().getName() + "\n" + e.getGuild().getId(), true)
                    .addField("Members", "" + e.getGuild().getMembers().size(), true)
                    .setColor(Color.GREEN.darker())
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");
        }
        else if(event instanceof GuildLeaveEvent)
        {
            GuildLeaveEvent e = (GuildLeaveEvent) event;

            builder.setTitle("__Bot Removed__")
                    .addField("Guild", e.getGuild().getName() + "\n" + e.getGuild().getId(), true)
                    .addField("Members", "" + e.getGuild().getMembers().size(), true)
                    .setColor(Color.RED.darker())
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");
        }

        // Member join/leave
        else if(event instanceof GuildMemberJoinEvent)
        {
            GuildMemberJoinEvent e = (GuildMemberJoinEvent) event;

            builder.setTitle("__Member Joined__")
                    .addField("User", e.getUser().getAsMention() + "\n" + e.getUser().getId(), true)
                    .addField("Account Age", e.getUser().getTimeCreated().format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm")), true)
                    .addField("Guild", e.getGuild().getName() + "\n" + e.getGuild().getId(), true)
                    .addField("Join Date", Config.DATE_FORMAT.format(date), false)
                    .addField("Members", "" + e.getGuild().getMembers().size(), true)
                    .setColor(Color.GREEN)
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");
        }
        else if(event instanceof GuildMemberLeaveEvent)
        {
            GuildMemberLeaveEvent e = (GuildMemberLeaveEvent) event;

            builder.setTitle("__Member Left__")
                    .addField("User", e.getUser().getAsMention() + "\n" + e.getUser().getId(), true)
                    .addField("Guild", e.getGuild().getName() + "\n" + e.getGuild().getId(), true)
                    .addField("Leave Date", Config.DATE_FORMAT.format(date), false)
                    .addField("Members", "" + e.getGuild().getMembers().size(), true)
                    .setColor(Color.RED)
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");
        }

        // Member ban/unban
        else if(event instanceof GuildBanEvent)
        {
            GuildBanEvent e = (GuildBanEvent) event;

            builder.setTitle("__Member Banned__")
                    .addField("User", e.getUser().getAsMention() + "\n" + e.getUser().getId(), true)
                    .addField("Guild", e.getGuild().getName() + "\n" + e.getGuild().getId(), true)
                    .addField("Reason", e.getGuild().retrieveBan(e.getUser()).complete().getReason(), false)
                    .setColor(Color.RED.brighter())
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");
        }
        else if(event instanceof GuildUnbanEvent)
        {
            GuildUnbanEvent e = (GuildUnbanEvent) event;

            builder.setTitle("__Member Unbanned__")
                    .addField("User", e.getUser().getAsMention() + "\n" + e.getUser().getId(), true)
                    .addField("Guild", e.getGuild().getName() + "\n" + e.getGuild().getId(), true)
                    .setColor(Color.ORANGE.brighter())
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");
        }

        // Member role add/remove
        else if(event instanceof GuildMemberRoleAddEvent)
        {
            GuildMemberRoleAddEvent e = (GuildMemberRoleAddEvent) event;

            builder.setTitle("__Role Added__")
                    .addField("User", e.getUser().getAsMention() + "\n" + e.getUser().getId(), true)
                    .addField("Guild", e.getGuild().getName() + "\n" + e.getGuild().getId(), true)
                    .addField("Role", e.getRoles().get(0).getName() + "\n" + e.getRoles().get(0).getId(), true)
                    .setColor(Color.BLUE.darker())
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");
        }
        else if(event instanceof GuildMemberRoleRemoveEvent)
        {
            GuildMemberRoleRemoveEvent e = (GuildMemberRoleRemoveEvent) event;

            builder.setTitle("__Role Removed__")
                    .addField("User", e.getUser().getAsMention() + "\n" + e.getUser().getId(), true)
                    .addField("Guild", e.getGuild().getName() + "\n" + e.getGuild().getId(), true)
                    .addField("Role", e.getRoles().get(0).getName() + "\n" + e.getRoles().get(0).getId(), true)
                    .setColor(Color.BLUE.darker())
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");
        }

        // Message update/delete
        else if(event instanceof GuildMessageUpdateEvent)
        {
            GuildMessageUpdateEvent e = (GuildMessageUpdateEvent) event;

            builder.setTitle("__Message Update__")
                    .addField("User", e.getAuthor().getAsMention() + "\n" + e.getAuthor().getId(), true)
                    .addField("Guild", e.getGuild().getName() + "\n" + e.getGuild().getId(), true)
                    .addField("Channel", e.getChannel().getAsMention() + "\n" + e.getChannel().getId(), true)
                    .addField("New Content", e.getMessage().getContentDisplay(), false)
                    .setColor(Color.YELLOW)
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");
        }
        else if(event instanceof GuildMessageDeleteEvent)
        {
            GuildMessageDeleteEvent e = (GuildMessageDeleteEvent) event;

            builder.setTitle("__Message Delete__")
                    .addField("Guild", e.getGuild().getName() + "\n" + e.getGuild().getId(), true)
                    .addField("Channel", e.getChannel().getAsMention() + "\n" + e.getChannel().getId(), true)
                    .setColor(Color.YELLOW)
                    .setFooter(Config.DATE_FORMAT.format(date), "https://i.imgur.com/mK2zlbr.png");
        }

        try
        {
            Driver.jda.getTextChannelById(GUILDS_LOG_CHANNEL).sendMessage(builder.build()).queue();
        }
        catch(Exception e)
        {
            // Ignore if embed is empty
        }
    }
}
