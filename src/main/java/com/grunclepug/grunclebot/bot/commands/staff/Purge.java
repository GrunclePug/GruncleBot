package com.grunclepug.grunclebot.bot.commands.staff;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Purge Command
 * @author grunclepug
 */
public class Purge extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // Purge Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "purge"))
        {
            if(event.getMessage().getMember().hasPermission(Permission.MESSAGE_MANAGE))
            {
                if (args.length > 1)
                {
                    try
                    {
                        Member target = null;
                        List<Message> messages = new ArrayList<>();
                        if(args.length > 2 && event.getMessage().getMentionedMembers().get(0) != null) {
                            target = event.getMessage().getMentionedMembers().get(0);
                        }

                        if(target != null) {

                            List<Message> history = event.getChannel().getHistory().retrievePast(100).complete();
                            for(Message m : history) {
                                if(m.getAuthor().getId().equals(target.getUser().getId())) {
                                    messages.add(m);
                                    if(messages.size() == Integer.parseInt(args[2])) {
                                        break;
                                    }
                                }
                            }
                        } else {
                            messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
                        }

                        event.getChannel().deleteMessages(messages).queue();

                        // Success
                        EmbedBuilder builder = new EmbedBuilder();

                        if(target != null) {
                            builder.setTitle("✅ Successfully purged " + args[2] + " messages by " + target.getEffectiveName());
                        } else {
                            builder.setTitle("✅ Successfully purged " + args[1] + " messages.");
                        }
                        builder.setColor(0x22ff2a);

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(builder.build()).queue();
                        builder.clear();
                    }
                    catch(IllegalArgumentException e)
                    {
                        if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval"))
                        {
                            // Too many messages
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("\uD83D\uDED1 Too many messages selected")
                                    .setDescription("Between 1-100 messages may be deleted at a time.")
                                    .setColor(0xff3923);

                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            builder.clear();
                        }
                        else
                        {
                            // Messages too old
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setTitle("\uD83D\uDED1 Selected messages are too old")
                                    .setDescription("Messages older than 2 weeks cannot be deleted.")
                                    .setColor(0xff3923);

                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(builder.build()).queue();
                            builder.clear();
                        }
                    }
                }
                else
                {
                    // Usage
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Specify amount of messages to delete")
                            .setDescription("Usage: `" + Config.getPrefix() + "purge [# of messages]`")
                            .setColor(0xff3923);

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(builder.build()).queue();
                    builder.clear();
                }
            }
            else
            {
                // Lack in Perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                        .setDescription("The 'Manage Messages' permission is required to use this command.")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
