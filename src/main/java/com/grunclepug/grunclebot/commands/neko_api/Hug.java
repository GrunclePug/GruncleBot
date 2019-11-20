package com.grunclepug.grunclebot.commands.neko_api;

import com.grunclepug.grunclebot.core.Main;
import com.grunclepug.grunclebot.core.NekoAPI;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Hug Command
 * @author grunclepug
 */
public class Hug extends ListenerAdapter
{
    private static final String URL = "https://nekos.life/api/v2/img/hug";

    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentDisplay().split("\\s+");

        //Hug Command
        if(args[0].equalsIgnoreCase(Main.prefix + "hug"))
        {
            Member member = event.getMessage().getMember();
            String title = "owo hugs";
            int color = 0x8904B1;

            if(args.length > 1)
            {
                Member target = event.getMessage().getMentionedMembers().get(0);
                title = target.getEffectiveName() + ", you got hugs from " + member.getEffectiveName();
            }
            EmbedBuilder builder = new NekoAPI().getEmbed(URL, title, color);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();
        }
    }
}
