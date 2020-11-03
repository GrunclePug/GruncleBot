package com.grunclepug.grunclebot.bot.commands.fun;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class RandomCase extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "randomcase"))
        {
            if(!args[1].toLowerCase().startsWith(Config.getPrefix())) {
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
            } else {
                // Lack in Perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 Nice try")
                        .setDescription("You cannot issue a command inside of this command!")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }

            BotLog.log(event);
        }
    }
}
