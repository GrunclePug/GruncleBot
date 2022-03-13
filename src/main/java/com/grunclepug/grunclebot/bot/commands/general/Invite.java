package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Bot Invite Command
 * @author GrunclePug
 */
public class Invite extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "invite")) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Invite " + Driver.jda.getSelfUser().getName() + " to your server: <:GruncleBot:934591292976017438>")
                    .setDescription("[Click here](https://discordapp.com/api/oauth2/authorize?client_id=" + Driver.jda.getSelfUser().getId() + "&permissions=2146958583&scope=bot)")
                    .setColor(0x58FAF4);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
            builder.clear();

            BotLog.log(event);
        }
    }
}
