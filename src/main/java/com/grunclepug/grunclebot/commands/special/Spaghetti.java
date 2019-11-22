package com.grunclepug.grunclebot.commands.special;

import com.grunclepug.grunclebot.core.Main;

import com.grunclepug.grunclebot.resources.reddit.RedditAPI;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Spaghetti Hentai Command
 * @author grunclepug
 */
public class Spaghetti extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Spaghetti Hentai Command
        if (args[0].equalsIgnoreCase(Main.prefix + "spaghetti"))
        {
            if(event.getChannel().isNSFW())
            {
                if(event.getMessage().getMember().hasPermission(Permission.MESSAGE_ATTACH_FILES))
                {
                    String url = "https://reddit.com/r/spaghettihentai/random.json";
                    String title = "SSSOOOO LLEEEWWWWDDDDD!!";
                    int color = 0xF9DB72;
                    EmbedBuilder builder = new RedditAPI().getEmbed(url, title, color);

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(builder.build()).queue();
                    builder.clear();
                }
                else
                {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("Invalid perms. `Attach Files`").queue();
                }
            }
            else
            {
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("This is not a NSFW channel.").queue();
            }
        }
    }
}
