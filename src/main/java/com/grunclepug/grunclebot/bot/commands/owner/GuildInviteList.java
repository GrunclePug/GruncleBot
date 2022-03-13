package com.grunclepug.grunclebot.bot.commands.owner;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

/**
 * Guild Invite List Command
 * @author GrunclePug
 */
public class GuildInviteList extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "guildinvitelist")) {
            // Verify Identity
            if(event.getMember().getId().equals("247916497803018242") || event.getMember().getId().equals("474703059981697025")) {
                ArrayList<Guild> guilds = new ArrayList<>(event.getJDA().getGuilds());
                ArrayList<String> formattedGuilds = new ArrayList<>();

                String guildList = "";
                for(int i = 0; i < guilds.size(); i++) {
                    String invite = "not available";
                    try {
                        invite = guilds.get(i).retrieveInvites().complete().get(0).getCode();
                    } catch(Exception e) {
                        e.printStackTrace(System.err);
                    }

                    formattedGuilds.add(i, "\nGuild: **" + guilds.get(i).getName() + "**\nInvite: " + invite + "\nUsers: " + guilds.get(i).getMembers().size());
                    guildList += formattedGuilds.get(i);
                }

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("GruncleBot Guild List")
                        .setDescription(guildList)
                        .addField("Bot Info:", "Guilds: " + guilds.size() + "\nUsers: " + event.getJDA().getUsers().size(), false)
                        .setColor(0xF9AF04);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                builder.clear();
            } else {
                // Lack in Perms
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 You lack the required permissions")
                        .setDescription("You are not the owner of this bot!")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                builder.clear();
            }
            BotLog.log(event);
        }
    }
}
