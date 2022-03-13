package com.grunclepug.grunclebot.bot.commands.staff;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Ban Command
 * @author GrunclePug
 */
public class Ban extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "ban")) {
            String reason = "No reason was provided.";
            Member target;

            if(args.length > 2) {
                reason = "";
                for(int i = 0; i < args.length; i++) {
                    reason += " " + args[i];
                }
            }

            if(args.length > 1) {
                target = event.getMessage().getMentionedMembers().get(0);

                // Checks to see if user has permission to ban, is capable of banning target, and bot is capable of banning target
                if(event.getMember().hasPermission(Permission.BAN_MEMBERS) && event.getMember().canInteract(target) && event.getGuild().getSelfMember().canInteract(target)) {
                    try {
                        String finalReason = reason;
                        target.getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessage("You have been banned from: " + event.getGuild().getName() +
                                    "\nreason: " + finalReason).queue();
                        });

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("✅ Successfully banned " + target.getEffectiveName())
                                .setDescription("reason: " + reason)
                                .setColor(0x22ff2a);

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessageEmbeds(builder.build()).queue();

                        event.getGuild().ban(target, 0, reason).queue();
                    } catch(Exception e) {
                        e.printStackTrace(System.err);
                    }
                } else {
                    // Lack in Perms
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                            .setDescription("Either you are missing the 'Ban Members' permission\nor that member cannot be banned")
                            .setColor(0xff3923);

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessageEmbeds(builder.build()).queue();
                    builder.clear();
                }
            }
            else {
                // Usage
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Specify member and reason of ban")
                        .setDescription("Usage: `" + Config.getPrefix() + "ban {user} {reason}`")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
