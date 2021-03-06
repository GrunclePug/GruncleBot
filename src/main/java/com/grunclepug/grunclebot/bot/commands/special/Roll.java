package com.grunclepug.grunclebot.bot.commands.special;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Dice Roll Command
 * @author grunclepug
 */
public class Roll extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Dice Roll Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "roll"))
        {
            Member member = event.getMessage().getMember();
            int sides = Integer.parseInt(args[1]);
            int roll = (int) (Math.random() * sides) + 1;

            EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription("<:d20:646717144717066240> " + member.getEffectiveName() + " rolled a D" + sides + " and got: " + roll + "!")
                    .setColor(0x58FA58);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();

            BotLog.log(event);
        }
    }
}
