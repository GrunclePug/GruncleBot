package com.grunclepug.grunclebot.commands.general;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Invite Command
 * @author grunclepug
 */
public class Invite extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // Invite Command
        if(args[0].equalsIgnoreCase(Main.prefix + "invite"))
        {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Invite GruncleBot to your server: <:grunclebot:574337320552103951>")
                .setDescription("[Click here](https://discordapp.com/api/oauth2/authorize?client_id=390942897463099393&permissions=2146958583&scope=bot)")
                .setColor(0x58FAF4);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();
        }
    }
}
