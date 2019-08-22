package com.grunclepug.grunclebot.commands.general;

import com.grunclepug.grunclebot.core.Main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Help Command
 * @author grunclepug
 */
public class Help extends ListenerAdapter
{
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Help Command
        if(args[0].equalsIgnoreCase(Main.prefix + "help"))
        {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("GruncleBot Command Help")
                .setDescription("Bot created by @GrunclePug#7015")
                .setThumbnail("https://i.imgur.com/bfwjiDz.png")
                .addField("Commands",
                    "**help** | Brings you here" +
                            "\n**serverinfo** | See info on the current server" +
                            "\n**userinfo** | See info on a certain user" +
                            "\n**info** | See info on the bot" +
                            "\n**ping** | Checks the delay between you and the bot" +
                            "\n**invite** | gives you the link to invite the bot", false)
                .addField("Fun Commands",
                      "**pat** | give headpats to a user uwu" +
                            "\n**hug** | hug a user" +
                            "\n**poke** | poke a user" +
                            "\n**slap** | slap a user" +
                            "\n**roll** | roll a dice `" + Main.prefix + "roll <number of sides>`", false)
                .addField("Staff Commands",
                      "**purge** | Purge x messages, where x is 2-100" +
                            "\n**ban** | ban a user `" + Main.prefix + "ban <@user> [reason]`" +
                            "\n**kick** | kick a user `" + Main.prefix + "kick <@user> [reason]`", false)
                .addField("Image Commands | SFW",
                            "**neko** | Post a random neko pic" +
                            "\n**nekogif** | Post a random Neko gif", false)
                .addField("Image Commands | NSFW",
                      "\n**neko** | Post a random NSFW neko pic (requires NSFW channel)" +
                            "\n**nekogif** | Post a random NSFW neko gif (requires NSFW channel)" +
                            "\n**pussygif** | Post a random pussy gif (requires NSFW channel)" +
                            "\n**tiddy** | Post a random boob pic (requires NSFW channel)" +
                            "\n**tiddygif** | Post a random boob gif (requires NSFW channel)" +
                            "\n**spaghetti** | excuse me?", false)
                .setColor(0xFF00FF);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();
        }
    }
}
