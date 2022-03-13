package com.grunclepug.grunclebot.bot.commands.owner;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Generate Invite Command
 * @author GrunclePug
 */
public class GenerateInvite extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "generateinvite")) {
            // Verify Identity
            if(event.getMember().getId().equals("247916497803018242") || event.getMember().getId().equals("474703059981697025")) {
                Guild guild = event.getJDA().getGuildById(args[1]);

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Guild: " + guild.getName())
                        .setDescription("id: " + guild.getId())
                        .addField("invite:", guild.retrieveInvites().complete().get(0).getUrl(), false)
                        .setColor(0xF9AF04);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
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
