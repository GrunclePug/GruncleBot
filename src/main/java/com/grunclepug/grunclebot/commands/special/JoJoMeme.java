package com.grunclepug.grunclebot.commands.special;

import com.grunclepug.grunclebot.core.Main;

import com.grunclepug.grunclebot.resources.reddit.RedditAPI;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * JoJo Meme Command
 * @author grunclepug
 */
public class JoJoMeme extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //JoJo Meme Command
        if (args[0].equalsIgnoreCase(Main.prefix + "jojomeme"))
        {
            if(event.getMessage().getMember().hasPermission(Permission.MESSAGE_ATTACH_FILES))
            {
                String url = "https://reddit.com/r/ShitPostCrusaders/random.json";
                String title = "ZA WARUDO";
                int color = 0x3b2cbc;
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
    }
}
