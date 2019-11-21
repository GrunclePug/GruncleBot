package com.grunclepug.grunclebot.commands.general;

import com.grunclepug.grunclebot.core.Main;

import com.sun.management.OperatingSystemMXBean;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

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
        if(args[0].equalsIgnoreCase(Main.prefix + "resources"))
        {
            Member bot = event.getGuild().getSelfMember();
            OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

            int cpuCoreCount = os.getAvailableProcessors();
            long systemMemory = os.getTotalPhysicalMemorySize() / 1024000;
            long memoryUsage = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024000;

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(bot.getEffectiveName() + " System info")
                    .setThumbnail(bot.getUser().getAvatarUrl())
                    .addField("・CPU", "Usage: " + Main.dfTwoDecimalPoint.format(os.getProcessCpuLoad() * 100) + "%" +
                            "\nCores: " + cpuCoreCount, false)
                    .addField("・Memory", "Total: " + Main.dfOneDecimalPoint.format(systemMemory) + "MB (" + Main.dfOneDecimalPoint.format((double) systemMemory / 1024) + "GB)" +
                            "\nUsage: " + Main.dfOneDecimalPoint.format(memoryUsage) + "MB (" + Main.dfOneDecimalPoint.format((double) memoryUsage / 1024) + "GB)", false)
                    .setFooter("Created by GrunclePug", "https://i.imgur.com/mK2zlbr.png")
                    .setColor(0xFF00FF);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();
        }
    }
}
