package com.grunclepug.grunclebot.commands.neko_api;

import com.grunclepug.grunclebot.core.Main;
import com.grunclepug.grunclebot.core.NekoAPI;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Pussy Gif Command
 * @author grunclepug
 */
public class PussyGif extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Pussy Gif Command
        if (args[0].equalsIgnoreCase(Main.prefix + "pussygif"))
        {
            if(event.getChannel().isNSFW())
            {
                String url = "https://nekos.life/api/v2/img/pussy";
                String title = "*slurp*";
                int color = 0x8904B1;
                EmbedBuilder builder = new NekoAPI().getEmbed(url, title, color);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
            else
            {
                //SFW Channel Error
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("\uD83D\uDED1 NSFW Content")
                        .setDescription("Please enter a NSFW channel to view this content.")
                        .setColor(0xff3923);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
        }
    }
}
