package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;
import com.grunclepug.grunclebot.bot.util.LineCount;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

/**
 * Bot System Resources Command
 * @author GrunclePug
 */
public class Resources extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "resources")) {
            OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            Member bot = event.getGuild().getSelfMember();

            int cpuCoreCount = os.getAvailableProcessors();
            long systemMemory = os.getTotalPhysicalMemorySize() / 1024000;
            long memoryUsage = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024000;

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(bot.getEffectiveName() + " System info")
                    .setThumbnail(bot.getUser().getAvatarUrl())
                    .addField("・CPU", "Usage: " + Config.DF_TWO_DECIMAL_POINT.format(os.getProcessCpuLoad() * 100) + "%" +
                            "\nCores: " + cpuCoreCount +
                            "\nThreads: " + cpuCoreCount * 2, false)
                    .addField("・Memory", "Total: " + Config.DF_ONE_DECIMAL_POINT.format(systemMemory) + "MB (" + Config.DF_ONE_DECIMAL_POINT.format((double) systemMemory / 1024) + "GB)" +
                            "\nUsage: " + Config.DF_ONE_DECIMAL_POINT.format(memoryUsage) + "MB (" + Config.DF_TWO_DECIMAL_POINT.format((double) memoryUsage / 1024) + "GB)", false)
                    .addField("・Line Count", "" + LineCount.getLineCount(), false)
                    .setFooter("Created by GrunclePug", "https://i.imgur.com/mK2zlbr.png")
                    .setColor(0xFF00FF);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
            builder.clear();

            BotLog.log(event);
        }
    }
}
