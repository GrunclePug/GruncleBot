package com.grunclepug.grunclebot.core;

import javax.security.auth.login.LoginException;

import com.grunclepug.grunclebot.commands.general.*;
import com.grunclepug.grunclebot.commands.neko_api.*;
import com.grunclepug.grunclebot.commands.staff.*;
import com.grunclepug.grunclebot.commands.special.*;
import com.grunclepug.grunclebot.commands.owner.*;
import com.grunclepug.grunclebot.resources.music.Music;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

/**
 * Main class for GruncleBot
 * @author grunclepug
 */
public class Main
{
    private static Config c = new Config();
    public static JDA jda;
    public static String prefix = c.getPrefix();

    /**
     * Main Method
     * @param args Arguments from terminal
     * @throws LoginException Login Exception
     */
    public static void main(String[] args) throws LoginException
    {
        jda = new JDABuilder(AccountType.BOT).setToken(c.getToken()).build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setGame(Game.watching(c.getPrefix() + "help | GrunclePug#7015"));

        //Owner
        jda.addEventListener(new GuildList());

        //General Purpose
        jda.addEventListener(new Help());
        jda.addEventListener(new Info());
        jda.addEventListener(new ServerInfo());
        jda.addEventListener(new UserInfo());
        jda.addEventListener(new Ping());
        jda.addEventListener(new Invite());

        //Music
        jda.addEventListener(new Music());

        //Staff
        jda.addEventListener(new Purge());
        jda.addEventListener(new Kick());
        jda.addEventListener(new Ban());
        jda.addEventListener(new Role());
        jda.addEventListener(new MassRole());

        //Neko API
        jda.addEventListener(new Neko());
        jda.addEventListener(new NekoGif());
        jda.addEventListener(new Kitsune());
        jda.addEventListener(new Kuni());
        jda.addEventListener(new PussyGif());
        jda.addEventListener(new Tiddy());
        jda.addEventListener(new TiddyGif());
        jda.addEventListener(new Hentai());
        jda.addEventListener(new HentaiGif());
        jda.addEventListener(new Cum());
        jda.addEventListener(new CumGif());
        jda.addEventListener(new Yuri());

        jda.addEventListener(new Pat());
        jda.addEventListener(new Slap());
        jda.addEventListener(new Hug());
        jda.addEventListener(new Cuddle());
        jda.addEventListener(new Poke());

        //Special
        jda.addEventListener(new Spaghetti());
        jda.addEventListener(new Roll());
    }
}