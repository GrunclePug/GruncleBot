package com.grunclepug.grunclebot.bot.commands.general;

import com.grunclepug.grunclebot.bot.core.Config;
import com.grunclepug.grunclebot.bot.core.Driver;

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
        if(args[0].equalsIgnoreCase(Config.getPrefix() + "help"))
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
                            "\n**resources** | Check bot system resource usage" +
                            "\n**ping** | Checks the delay between you and the bot" +
                            "\n**afk** | set yourself as afk `" + Config.getPrefix() + "afk {reason}`" +
                            "\n**invite** | gives you the link to invite the bot", false)
                .addField("Utility Commands",
                      "**enlarge** | enlarge an emote (custom emotes only)" +
                            "\n**avatar** | get a users avatar", false)
                .addField("Fun Commands",
                      "**pat** | give headpats to a user uwu" +
                            "\n**hug** | hug a user" +
                            "\n**cuddle** | cuddle a user" +
                            "\n**poke** | poke a user" +
                            "\n**slap** | slap a user" +
                            "\n**roll** | roll a dice `" + Config.getPrefix() + "roll {number of sides}`", false)
                .addField("Music Commands",
                      "**play** | play song '" + Config.getPrefix() + "play {url}" +
                            "\n**skip** | skip song" +
                            "\n**stop** | stop music", false)
                .addField("Staff Commands",
                      "**purge** | Purge x messages, where x is 2-100" +
                            "\n**ban** | ban a user `" + Config.getPrefix() + "ban {@user} {reason}`" +
                            "\n**kick** | kick a user `" + Config.getPrefix() + "kick {@user} {reason}`" +
                            "\n**role** | add/remove role from user `" + Config.getPrefix() + "role add {role} {@user}`" +
                            "\n**massrole** | add/remove role from all users `" + Config.getPrefix() + "massrole add {role}`", false)
                .addField("Image Commands | SFW",
                      "**neko** | Post a random neko pic" +
                            "\n**nekogif** | Post a random neko gif" +
                            "\n**kitsune** | Post a random kitsune pic" +
                            "\n**lampmeme** | Post a random lamp meme" +
                            "\n**jojomeme** | Post a random jojo meme", false)
                .addField("Image Commands | NSFW",
                      "\n**neko** | Post a random NSFW neko pic" +
                            "\n**nekogif** | Post a random NSFW neko gif" +
                            "\n**kitsune** | Post a random NSFW kitsune pic" +
                            "\n**kuni** | Post a random NSFW pussy lick gif" +
                            "\n**pussygif** | Post a random pussy gif" +
                            "\n**tiddy** | Post a random boob pic" +
                            "\n**tiddygif** | Post a random boob gif" +
                            "\n**hentai** | Post a random hentai pic" +
                            "\n**hentaigif** | Post a random hentai gif" +
                            "\n**cum** | Post a random cum pic" +
                            "\n**cumgif** | Post a random cum gif" +
                            "\n**yuri** | Post a random yuri pic" +
                            "\n**spaghetti** | excuse me?", false)
                .setColor(0xFF00FF);

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(builder.build()).queue();
            builder.clear();
        }
    }
}
