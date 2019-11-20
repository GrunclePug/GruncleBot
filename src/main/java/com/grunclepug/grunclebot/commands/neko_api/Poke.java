package com.grunclepug.grunclebot.commands.neko_api;

import com.grunclepug.grunclebot.core.Main;
import com.grunclepug.grunclebot.core.NekoAPI;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Poke Command
 * @author grunclepug
 */
public class Poke extends ListenerAdapter
{
    private static final String URL = "https://nekos.life/api/v2/img/poke";

    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentDisplay().split("\\s+");

        //Poke Command
        if(args[0].equalsIgnoreCase(Main.prefix + "poke"))
        {
            Member member = event.getMessage().getMember();
            String title = "owo pokes";
            int color = 0x8904B1;

            if(args.length > 1)
            {
                Member target = event.getMessage().getMentionedMembers().get(0);
                title = target.getEffectiveName() + ", you got poked by " + member.getEffectiveName();
            }
            EmbedBuilder builder = new NekoAPI().getEmbed(URL, title, color);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();
        }
    }
}
