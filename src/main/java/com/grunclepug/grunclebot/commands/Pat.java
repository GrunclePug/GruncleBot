package com.grunclepug.grunclebot.commands;

import com.grunclepug.grunclebot.core.Main;
import com.grunclepug.grunclebot.core.NekoAPI;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Pat Command
 * @author grunclepug
 */
public class Pat extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String member = event.getMember().getNickname();
        String[] args = event.getMessage().getContentDisplay().split("\\s+");

        String user = null;

        if(args.length > 2)
        {
            for(int i = 1; i < args.length; i++)
            {
                user += (" " + args[i]);
            }
            user = user.substring(6);
        }
        else if(args.length > 1)
        {
            user = args[1].substring(1);
        }

        //Pat Command
        if(args[0].equalsIgnoreCase(Main.prefix + "pat"))
        {
            if(args.length < 2)
            {
                //Usage
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Specify a member to head pat")
                        .setDescription("Usage: '" + Main.prefix + "pat <@user>'")
                        .setColor(0xff3923);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
            else
            {
                String url = "https://nekos.life/api/v2/img/pat";
                String title = (user + ", you got a head pat from " + member);
                int color = 0x8904B1;
                EmbedBuilder builder = new NekoAPI().getEmbed(url, title, color);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(builder.build()).queue();
                builder.clear();
            }
        }
    }
}
