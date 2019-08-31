package com.grunclepug.grunclebot.commands.general;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Info Command
 * @author grunclepug
 */
public class Info extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // Info Command
        if(args[0].equalsIgnoreCase(Main.prefix + "info"))
        {
            Member selfMember = event.getGuild().getSelfMember();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(selfMember.getEffectiveName() + " Information")
                .setThumbnail(selfMember.getUser().getAvatarUrl())
                .addField("・Description", "Multistage aerobic capacity test that progressively gets more difficult as it continues.", false)
                .addField("・Date Created", "May 03 2019", false)
                .addField("・Honorable Mentions", "<@!316636759901470720> (Bullfrog098)", false)
                .addField("・Website", "https://grunclepug.com", false)
                .setFooter("Created by GrunclePug", "https://i.imgur.com/mK2zlbr.png")
                .setColor(0xFF00FF);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();
        }
    }
    //https://i.imgur.com/bfwjiDz.png
}
