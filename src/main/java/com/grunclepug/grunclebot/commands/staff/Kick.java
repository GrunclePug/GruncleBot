package com.grunclepug.grunclebot.commands.staff;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Kick Command
 * @author grunclepug
 */
public class Kick extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Kick Command
        if(args[0].equalsIgnoreCase(Main.prefix + "kick"))
        {
            Member member = event.getMessage().getMember();
            Member target = event.getMessage().getMentionedMembers().get(0);
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

            //Checks to see if user has permission to kick, is capable of kicking target, and bot is capable of kicking target
            if(member.hasPermission(Permission.KICK_MEMBERS) && member.canInteract(target) && bot.canInteract(target))
            {
                if (args.length > 1)
                {
                    try
                    {
                        String finalReason = reason;
                        target.getUser().openPrivateChannel().queue((channel) ->
                        {
                            channel.sendMessage("You have been kicked from: " + guild.getName() +
                                    "\nreason: " + finalReason).queue();
                        });

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("✅ Successfully kicked " + target.getEffectiveName())
                                .setDescription("reason: " + reason)
                                .setColor(0x22ff2a);

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(builder.build()).queue();
                        builder.clear();

                        guild.getController().kick(target, reason).queue();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace(System.err);
                    }
                }
                else
                {
                    // Usage
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Specify member and reason of kick")
                            .setDescription("Usage: `" + Main.prefix + "kick [user] [reason]`")
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
                        .setDescription("Either you are missing the 'Kick Members' permission\nor that member cannot be kicked")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
        }
    }
}
