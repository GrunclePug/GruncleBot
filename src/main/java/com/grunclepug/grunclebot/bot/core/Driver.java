package com.grunclepug.grunclebot.bot.core;

import javax.security.auth.login.LoginException;

import com.grunclepug.grunclebot.bot.commands.fun.RandomCase;
import com.grunclepug.grunclebot.bot.commands.owner.*;
import com.grunclepug.grunclebot.bot.commands.general.*;
import com.grunclepug.grunclebot.bot.commands.utility.*;
import com.grunclepug.grunclebot.bot.commands.staff.*;
import com.grunclepug.grunclebot.bot.commands.neko_api.*;
import com.grunclepug.grunclebot.bot.commands.special.*;
import com.grunclepug.grunclebot.bot.util.log.*;
import com.grunclepug.grunclebot.bot.util.music.Music;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import java.text.DecimalFormat;

/**
 * Main class for GruncleBot
 * @author grunclepug
 */
public class Driver
{
    public static JDA jda;
    public static DecimalFormat dfOneDecimalPoint = new DecimalFormat("0.0");
    public static DecimalFormat dfTwoDecimalPoint = new DecimalFormat("0.00");

    /**
     * Main Method
     * @param args Arguments from terminal
     */
    public static void main(String[] args)
    {
        try
        {
            new Driver().setup();
        }
        catch(LoginException e)
        {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Setup
     * @throws LoginException
     */
    private void setup() throws LoginException
    {
        Config.readFile();

        jda = new JDABuilder(AccountType.BOT).setToken(Config.getToken()).build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setGame(Game.watching(Config.getPrefix() + "help | GrunclePug#7015"));

        //Log
        jda.addEventListener(new BotLog());
        jda.addEventListener(new BugReport());
        jda.addEventListener(new Suggest());

        //Owner
        jda.addEventListener(new GuildList());
        jda.addEventListener(new GuildInviteList());
        jda.addEventListener(new Resolve());
        jda.addEventListener(new GenerateInvite());

        //General Purpose
        jda.addEventListener(new Help());
        jda.addEventListener(new Info());
        jda.addEventListener(new ServerInfo());
        jda.addEventListener(new UserInfo());
        jda.addEventListener(new Resources());
        jda.addEventListener(new Ping());
        jda.addEventListener(new Invite());
        jda.addEventListener(new Afk());

        //Fun
        jda.addEventListener(new RandomCase());

        //Utility
        jda.addEventListener(new RandomColor());
        jda.addEventListener(new Avatar());
        jda.addEventListener(new Enlarge());

        //Music
        jda.addEventListener(new Music());

        //Staff
        jda.addEventListener(new Purge());
        jda.addEventListener(new Kick());
        jda.addEventListener(new Ban());
        jda.addEventListener(new Role());
        jda.addEventListener(new MassRole());
        jda.addEventListener(new RoleColor());

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
        jda.addEventListener(new Feet());

        jda.addEventListener(new Pat());
        jda.addEventListener(new Slap());
        jda.addEventListener(new Hug());
        jda.addEventListener(new Cuddle());
        jda.addEventListener(new Poke());

        //Special
        jda.addEventListener(new Spaghetti());
        jda.addEventListener(new LampMeme());
        jda.addEventListener(new JoJoMeme());
        jda.addEventListener(new Roll());
    }
}