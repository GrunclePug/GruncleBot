package com.grunclepug.grunclebot.bot.commands.fun;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Random;

public class RandomCase extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "randomcase"))
        {
            Random rand = new Random();
            String content = event.getMessage().getContentRaw().substring(args[0].length()).toLowerCase().trim();
            char[] chars = content.toCharArray();

            for(int i = 0; i < content.length(); i++)
            {
                if(rand.nextBoolean())
                {
                    chars[i] = Character.toUpperCase(chars[i]);
                }
            }
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(String.copyValueOf(chars)).queue();

            BotLog.log(event);
        }
    }
}
