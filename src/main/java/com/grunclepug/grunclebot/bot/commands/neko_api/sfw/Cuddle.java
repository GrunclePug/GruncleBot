package com.grunclepug.grunclebot.bot.commands.neko_api.sfw;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.api.ImageAPI;
import com.grunclepug.grunclebot.bot.util.api.NekoAPI;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

/**
 * Cuddle Command
 * @author GrunclePug
 */
public class Cuddle extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "cuddle")) {
            String title = "owo cuddles";
            int color = 0x8904B1;

            if(args.length > 1) {
                Member target = event.getMessage().getMentionedMembers().get(0);
                title = target.getEffectiveName() + ", you got cuddles from " + event.getMember().getEffectiveName();
            }

            try {
                EmbedBuilder builder = new ImageAPI().getEmbed(NekoAPI.CUDDLE, title, color);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                builder.clear();
            } catch(IOException e) {
                // Embed failed to get json data
                e.printStackTrace(System.err);
            }
            BotLog.log(event);
        }
    }
}
