package com.grunclepug.grunclebot.bot.commands.staff;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Purge Command
 * @author GrunclePug
 */
public class Purge extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "purge")) {
            if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                if(args.length > 1) {
                    try {
                        ArrayList<Message> messages = new ArrayList<>();
                        Member target = null;
                        int messageCount = 0;

                        if (args.length > 2) {
                            if (event.getMessage().getMentionedMembers().size() > 0 && event.getMessage().getMentionedMembers().get(0) != null) {
                                // Mentioned Member
                                target = event.getMessage().getMentionedMembers().get(0);
                            } else if (event.getGuild().getMemberById(args[1]) != null) {
                                target = event.getGuild().getMemberById(args[1]);
                            } else {
                                event.getChannel().sendTyping().queue();
                                event.getChannel().sendMessage("Cannot find user `" + args[1] + "`").queue();
                            }
                            messageCount = Integer.parseInt(args[2]);
                        } else {
                            messageCount = Integer.parseInt(args[1]);
                        }

                        if (target != null) {
                            ArrayList<Message> messageHistory = new ArrayList<>(event.getChannel().getHistory().retrievePast(100).complete());

                            int counter = 0;
                            ListIterator<Message> it = messageHistory.listIterator();
                            while(it.hasNext()) {
                                Message message = it.next();
                                if(message.getAuthor().getId().equals(target.getUser().getId())) {
                                    messages.add(message);
                                    counter++;
                                }
                                if(counter >= messageCount) {
                                    break;
                                }
                            }
                        } else {
                            ArrayList<Message> messageHistory = new ArrayList<>(event.getChannel().getHistory().retrievePast(messageCount).complete());
                            for(int i = 0; i < messageCount; i++) {
                                messages.add(messageHistory.get(i));
                            }
                        }

                        if(messages.size() > 0) {
                            event.getChannel().purgeMessages(messages);
                        }

                        EmbedBuilder builder = new EmbedBuilder();

                        if(target != null) {
                            builder.setTitle("✅ Successfully purged " + args[2] + " messages by " + target.getEffectiveName());
                        } else {
                            builder.setTitle("✅ Successfully purged " + args[1] + " messages.");
                        }
                        builder.setColor(0x22ff2a);

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessageEmbeds(builder.build()).queue();
                        builder.clear();
                    } catch(IllegalArgumentException e) {
                        if(e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
                            // Too many messages
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("\uD83D\uDED1 Too many messages selected")
                                    .setDescription("Between 1-100 messages may be deleted at a time.")
                                    .setColor(0xff3923);

                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessageEmbeds(builder.build()).queue();
                            builder.clear();
                        } else {
                            // Messages too old
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("\uD83D\uDED1 Selected messages are too old")
                                    .setDescription("Messages older than 2 weeks cannot be deleted.")
                                    .setColor(0xff3923);

                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessageEmbeds(builder.build()).queue();
                            builder.clear();
                        }
                    }
                }
            } else {
                // Lack in Perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                        .setDescription("The 'Manage Messages' permission is required to use this command.")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
