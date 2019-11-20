package com.grunclepug.grunclebot.commands.staff;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

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
        if(args[0].equalsIgnoreCase(Main.prefix + "purge"))
        {
            if(event.getMessage().getMember().hasPermission(Permission.MESSAGE_MANAGE))
            {
                if (args.length > 1)
                {
                    try
                    {
                        List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
                        event.getChannel().deleteMessages(messages).queue();

                        // Success
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("✅ Successfully purged " + args[1] + " messages.")
                                .setColor(0x22ff2a);

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
                            .setDescription("Usage: `" + Main.prefix + "purge [# of messages]`")
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
        }
    }
}
