package com.grunclepug.grunclebot.core;

import javax.security.auth.login.LoginException;

import com.grunclepug.grunclebot.commands.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;


public class Main
{
    public static JDA jda;
    public static String prefix = "g!";

    public static void main(String[] args) throws LoginException
    {
        jda = new JDABuilder(AccountType.BOT).setToken(FileFunctions.getToken()).build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setGame(Game.watching("g!help | GrunclePug#7015"));

        jda.addEventListener(new Info());
        jda.addEventListener(new Ping());
        jda.addEventListener(new Purge());
        jda.addEventListener(new Invite());
        jda.addEventListener(new Neko());
        jda.addEventListener(new NekoGif());
        jda.addEventListener(new Help());
        jda.addEventListener(new Spaghetti());
        jda.addEventListener(new PussyGif());
        jda.addEventListener(new Boobs());
        jda.addEventListener(new Serverinfo());
        jda.addEventListener(new Userinfo());
        jda.addEventListener(new Pat());
        jda.addEventListener(new Slap());
        jda.addEventListener(new Ban());
        jda.addEventListener(new Hug());
        jda.addEventListener(new Test());
        jda.addEventListener(new Poke());
        jda.addEventListener(new Kick());
        jda.addEventListener(new Roll());
    }
}