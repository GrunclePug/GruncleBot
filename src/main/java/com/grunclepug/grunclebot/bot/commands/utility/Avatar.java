package com.grunclepug.grunclebot.bot.commands.utility;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Avatar Command
 * @author GrunclePug
 */
public class Avatar extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "pfp") || args[0].equalsIgnoreCase(Config.getPrefix() + "avatar")) {
            User user = event.getMember().getUser();
            if(args.length > 1) {
                if(event.getMessage().getMentionedMembers().size() > 0 && event.getMessage().getMentionedMembers().get(0) != null) {
                    // Mentioned User
                    user = event.getMessage().getMentionedMembers().get(0).getUser();
                } else if(event.getGuild().getMemberById(args[1]) != null) {
                    // User ID
                    user = event.getGuild().getMemberById(args[1]).getUser();
                } else {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Cannot find user `" + args[1] + "`").queue();
                }
            }

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(user.getName())
                    .setImage(user.getAvatarUrl())
                    .setColor(0xb83837);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
            BotLog.log(event);
        }
    }
}
