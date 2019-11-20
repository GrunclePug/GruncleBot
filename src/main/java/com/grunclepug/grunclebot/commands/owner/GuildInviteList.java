package com.grunclepug.grunclebot.commands.owner;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

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
        if(args[0].equalsIgnoreCase(Main.prefix + "guildinvitelist") || args[0].equalsIgnoreCase(Main.prefix + "serverinvitelist"))
        {
            //Member issuing command
            Member member = event.getMember();
            ArrayList<Guild> guilds = new ArrayList<>(event.getJDA().getGuilds());
            ArrayList<String> formattedGuilds = new ArrayList<>();
            String guildList = "";
            String inv = "";

            for(int i = 0; i < guilds.size(); i++)
            {
                try
                {
                    inv = guilds.get(i).getInvites().complete().get(0).getCode();
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
        }
    }
}
