package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.util.afk.FileInteraction;

import com.grunclepug.grunclebot.bot.util.afk.Person;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

/**
 * Afk Command
 * @author grunclepug
 */
public class Afk extends ListenerAdapter
{
    private static final String FILE = "src/main/resources/afkLog.txt";

    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        ArrayList<Person> people = FileInteraction.readFile(FILE);

        // Check if member comes back
        for(int i = 0; i < people.size(); i++)
        {
            if(people.get(i).getId().equals(event.getMessage().getAuthor().getId()))
            {
                FileInteraction.updateFile(FILE, event.getMessage().getAuthor().getId(), false, null);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(event.getMessage().getMember().getEffectiveName() + ", you have been removed from the afk list!").queue();
                break;
            }
        }

        // Check if member gets pinged
        for(int i = 0; i < event.getMessage().getMentionedMembers().size(); i++)
        {
            for(int j = 0; j < people.size(); j++)
            {
                if(event.getMessage().getMentionedMembers().get(i).getUser().getId().equals(people.get(j).getId()))
                {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("**" + event.getMessage().getMentionedMembers().get(i).getEffectiveName() + "** is currently afk.")
                            .setDescription("**Reason:** " + people.get(j).getContent())
                            .setColor(0x000000);
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(builder.build()).queue();
                }
            }
        }

        // Afk Command
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "afk"))
        {
            Member member = event.getMessage().getMember();
            String reason = "";

            if(args.length > 1)
            {
                for(int i = 1; i < args.length; i++)
                {
                    reason += (" " + args[i]);
                }
                reason = reason.trim();
            }
            else
            {
                reason = "no reason provided.";
            }

            FileInteraction.updateFile(FILE, member.getUser().getId(), true, reason);

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("**" + member.getEffectiveName() + "**, you have been set to afk")
                    .setDescription("**Reason:** " + reason)
                    .setColor(0x000000);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();

            BotLog.log(event);
        }
    }
}
