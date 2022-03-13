package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

/**
 * Bot Info Command
 * @author GrunclePug
 */
public class Info extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Config.getPrefix() + "info")) {
            ArrayList<Guild> guilds = new ArrayList<>(event.getJDA().getGuilds());
            ArrayList<User> users = new ArrayList<>(event.getJDA().getUsers());
            Member bot = event.getGuild().getSelfMember();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(bot.getEffectiveName() + " Information")
                    .setThumbnail(bot.getUser().getAvatarUrl())
                    .addField("・About", "Multi-purpose open source Discord bot written in Java", false)
                    .addField("・Description", "Multistage aerobic capacity test that progressively gets more difficult as it continues.", false)
                    .addField("・Date Created", "May 03 2019", false)
                    .addField("・Honorable Mentions", "<@!390909003984666624> (Bullfrog098)", false)
                    .addField("・Guilds", "" + guilds.size(), false)
                    .addField("・Users", "" + users.size(), false)
                    .addField("・Website", "https://grunclepug.com", false)
                    .setFooter("Created by GrunclePug", "https://i.imgur.com/mK2zlbr.png")
                    .setColor(0xFF00FF);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
            builder.clear();

            BotLog.log(event);
        }
    }
}
