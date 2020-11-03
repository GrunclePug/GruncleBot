package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

import com.grunclepug.grunclebot.bot.util.log.BotLog;
import com.sun.management.OperatingSystemMXBean;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.management.ManagementFactory;

/**
 * Resources Command
 * @author grunclepug
 */
public class Resources extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // Resources Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "resources"))
        {
            Member bot = event.getGuild().getSelfMember();
            OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

            int cpuCoreCount = os.getAvailableProcessors();
            long systemMemory = os.getTotalPhysicalMemorySize() / 1024000;
            long memoryUsage = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024000;

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(bot.getEffectiveName() + " System info")
                    .setThumbnail(bot.getUser().getAvatarUrl())
                    .addField("・CPU", "Usage: " + Driver.dfTwoDecimalPoint.format(os.getProcessCpuLoad() * 100) + "%" +
                            "\nCores: " + cpuCoreCount, false)
                    .addField("・Memory", "Total: " + Driver.dfOneDecimalPoint.format(systemMemory) + "MB (" + Driver.dfOneDecimalPoint.format((double) systemMemory / 1024) + "GB)" +
                            "\nUsage: " + Driver.dfOneDecimalPoint.format(memoryUsage) + "MB (" + Driver.dfOneDecimalPoint.format((double) memoryUsage / 1024) + "GB)", false)
                    .setFooter("Created by GrunclePug", "https://i.imgur.com/mK2zlbr.png")
                    .setColor(0xFF00FF);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();

            BotLog.log(event);
        }
    }
}
