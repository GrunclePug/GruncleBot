package com.grunclepug.grunclebot.bot.commands.owner;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

/**
 * Guild List Command
 * @author grunclepug
 */
public class GuildInviteList extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Guild List Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "guildinvitelist") || args[0].equalsIgnoreCase(Config.getPrefix() + "serverinvitelist"))
        {
            //Member issuing command
            Member member = event.getMember();
            ArrayList<Guild> guilds = new ArrayList<>(event.getJDA().getGuilds());
            ArrayList<String> formattedGuilds = new ArrayList<>();
            String guildList = "";

            for(int i = 0; i < guilds.size(); i++)
            {
                String inv = "not available";
                try
                {
                    inv = guilds.get(i).retrieveInvites().complete().get(0).getCode();
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.err);
                }
                formattedGuilds.add(i, "\nGuild: **" + guilds.get(i).getName() + "**\nInvite: " + inv + "\nUsers: " + guilds.get(i).getMembers().size());
                guildList += formattedGuilds.get(i);
            }

            //Checks to see if user is bot owner (me)
            if(member.getUser().getId().equals("247916497803018242"))
            {
                if (args.length < 2)
                {
                    // Usage
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("GruncleBot Guild List")
                            .setDescription(guildList)
                            .addField("Bot Info:", "Guilds: " + guilds.size() + "\nUsers: " + event.getJDA().getUsers().size(), false)
                            .setColor(0xF9AF04);

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
                        .setDescription("You are not the owner of this bot!")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
