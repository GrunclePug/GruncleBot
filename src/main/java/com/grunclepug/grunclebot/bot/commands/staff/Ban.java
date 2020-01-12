package com.grunclepug.grunclebot.bot.commands.staff;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Ban Command
 * @author grunclepug
 */
public class Ban extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Ban Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "ban"))
        {
            Member member = event.getMessage().getMember();
            Member target;
            Guild guild = event.getMessage().getGuild();
            Member bot = event.getGuild().getSelfMember();
            String reason = "No reason was provided";

            if(args.length > 3)
            {
                reason = "";
                for(int i = 2; i < args.length; i++)
                {
                    reason += (" " + args[i]);
                }
            }
            else if(args.length > 2)
            {
                reason = args[2];
            }

            if(args.length > 1)
            {
                target = event.getMessage().getMentionedMembers().get(0);
                //Checks to see if user has permission to ban, is capable of banning target, and bot is capable of banning target
                if(member.hasPermission(Permission.BAN_MEMBERS) && member.canInteract(target) && bot.canInteract(target))
                {
                    try
                    {
                        String finalReason = reason;
                        target.getUser().openPrivateChannel().queue((channel) ->
                        {
                            channel.sendMessage("You have been banned from: " + guild.getName() +
                                    "\nreason: " + finalReason).queue();
                        });

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("✅ Successfully banned " + target.getEffectiveName())
                                .setDescription("reason: " + reason)
                                .setColor(0x22ff2a);

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(builder.build()).queue();
                        builder.clear();

                        guild.getController().ban(target, 0, reason).queue();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace(System.err);
                    }
                }
                else
                {
                    // Lack in Perms
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                            .setDescription("Either you are missing the 'Ban Members' permission\nor that member cannot be banned")
                            .setColor(0xff3923);

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(builder.build()).queue();
                    builder.clear();
                }
            }
            else
            {
                // Usage
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Specify member and reason of ban")
                        .setDescription("Usage: `" + Config.getPrefix() + "ban {user} {reason}`")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
