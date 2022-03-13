package com.grunclepug.grunclebot.bot.commands.special.reddit;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.api.ImageAPI;
import com.grunclepug.grunclebot.bot.util.api.RedditAPI;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * JoJo Meme Command
 * @author GrunclePug
 */
public class JoJoMeme extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "jojomeme")) {
            if(event.getMember().hasPermission(Permission.MESSAGE_ATTACH_FILES)) {
                String title = "ZA WARUDO";
                int color = 0x3b2cbc;

                EmbedBuilder builder = new ImageAPI().getRedditEmbed(RedditAPI.JOJO, title, color);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
            } else {
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Invalid perms. `Attach Files`").queue();
            }
            BotLog.log(event);
        }
    }
}
