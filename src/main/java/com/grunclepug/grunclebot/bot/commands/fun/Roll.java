package com.grunclepug.grunclebot.bot.commands.fun;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Dice Roll Command
 * @author GrunclePug
 */
public class Roll extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "roll")) {
            int sides = 6;
            if(args.length != 1) {
                sides = Integer.parseInt(args[1]);
            } // Else roll D6

            int roll = (int) (Math.random() * sides) + 1;

            EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription("<:d20:949866899951472700> " + event.getMember().getEffectiveName() + " rolled a D" + sides + " and got: " + roll + "!")
                    .setColor(0x58FA58);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
            builder.clear();

            BotLog.log(event);
        }
    }
}
