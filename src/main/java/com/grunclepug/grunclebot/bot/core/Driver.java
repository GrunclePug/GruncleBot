package com.grunclepug.grunclebot.bot.core;

import com.grunclepug.grunclebot.bot.commands.fun.RandomCase;
import com.grunclepug.grunclebot.bot.commands.general.*;
import com.grunclepug.grunclebot.bot.commands.myanimelist.SearchCharacter;
import com.grunclepug.grunclebot.bot.commands.neko_api.both.Holo;
import com.grunclepug.grunclebot.bot.commands.neko_api.both.Kitsune;
import com.grunclepug.grunclebot.bot.commands.neko_api.both.Neko;
import com.grunclepug.grunclebot.bot.commands.neko_api.both.NekoGif;
import com.grunclepug.grunclebot.bot.commands.neko_api.misc.*;
import com.grunclepug.grunclebot.bot.commands.neko_api.nsfw.*;
import com.grunclepug.grunclebot.bot.commands.neko_api.sfw.*;
import com.grunclepug.grunclebot.bot.commands.neko_api.sfw.Cat;
import com.grunclepug.grunclebot.bot.commands.neko_api.sfw.Dog;
import com.grunclepug.grunclebot.bot.commands.neko_api.sfw.Goose;
import com.grunclepug.grunclebot.bot.commands.osu.OsuSet;
import com.grunclepug.grunclebot.bot.commands.osu.OsuTop;
import com.grunclepug.grunclebot.bot.commands.osu.OsuUser;
import com.grunclepug.grunclebot.bot.commands.owner.*;
import com.grunclepug.grunclebot.bot.commands.fun.Roll;
import com.grunclepug.grunclebot.bot.commands.special.reddit.JoJoMeme;
import com.grunclepug.grunclebot.bot.commands.special.reddit.KOn;
import com.grunclepug.grunclebot.bot.commands.special.reddit.LampMeme;
import com.grunclepug.grunclebot.bot.commands.special.reddit.Spaghetti;
import com.grunclepug.grunclebot.bot.commands.staff.*;
import com.grunclepug.grunclebot.bot.commands.utility.Avatar;
import com.grunclepug.grunclebot.bot.commands.utility.Enlarge;
import com.grunclepug.grunclebot.bot.commands.utility.RandomColor;
import com.grunclepug.grunclebot.bot.commands.utility.StealEmote;
import com.grunclepug.grunclebot.bot.util.help.CommandInfoLoader;
import com.grunclepug.grunclebot.bot.util.log.BotLog;
import com.grunclepug.grunclebot.bot.util.log.BugReport;
import com.grunclepug.grunclebot.bot.util.log.Suggest;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.IOException;

/**
 * Driver for GruncleBot
 * @author GrunclePug
 */
public class Driver {
    public static JDA jda;

    /**
     * Main Method
     * @param args Console arguments
     */
    public static void main(String[] args) {
        try {
            new Driver().setup();
        } catch(Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Setup
     * @throws LoginException if bot has issues connecting
     * @throws IOException if bot has issues finding config.json
     */
    private void setup() throws LoginException, IOException {
        Config.readFile();

        jda = JDABuilder.createDefault(Config.getToken())
                //.enableCache(CacheFlag.ACTIVITY)
                //.setMemberCachePolicy(MemberCachePolicy.ALL)
                //.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_INVITES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .build();

        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.watching(Config.getPrefix() + "help | GrunclePug#7015"));

        CommandInfoLoader.loadCommands();

        // LOG
        jda.addEventListener(new BotLog());
        jda.addEventListener(new BugReport());
        jda.addEventListener(new Suggest());

        // OWNER
        jda.addEventListener(new Broadcast());
        jda.addEventListener(new GenerateInvite());
        jda.addEventListener(new GuildInviteList());
        jda.addEventListener(new GuildList());
        jda.addEventListener(new Resolve());
        jda.addEventListener(new ChannelLog());
        jda.addEventListener(new GuildLog());
        jda.addEventListener(new GetImage());
        jda.addEventListener(new GetImages());
        jda.addEventListener(new Stop());

        // GENERAL PURPOSE
        jda.addEventListener(new Afk());
        jda.addEventListener(new Help());
        jda.addEventListener(new Info());
        jda.addEventListener(new Invite());
        jda.addEventListener(new Ping());
        jda.addEventListener(new Resources());
        jda.addEventListener(new ServerInfo());
        jda.addEventListener(new UserInfo());
        jda.addEventListener(new Privacy());

        // OSU
        jda.addEventListener(new OsuSet());
        jda.addEventListener(new OsuUser());
        jda.addEventListener(new OsuTop());

        // FUN
        jda.addEventListener(new RandomCase());
        jda.addEventListener(new SearchCharacter());

        // UTILITY
        jda.addEventListener(new StealEmote());
        jda.addEventListener(new Avatar());
        jda.addEventListener(new Enlarge());
        jda.addEventListener(new RandomColor());

        // STAFF
        jda.addEventListener(new Ban());
        jda.addEventListener(new Kick());
        jda.addEventListener(new MassRole());
        jda.addEventListener(new Purge());
        jda.addEventListener(new Role());
        jda.addEventListener(new RoleColor());

        // NEKO API
        jda.addEventListener(new Cat());
        jda.addEventListener(new Dog());
        jda.addEventListener(new Goose());

        jda.addEventListener(new Holo());
        jda.addEventListener(new Kitsune());
        jda.addEventListener(new Neko());
        jda.addEventListener(new NekoGif());

        jda.addEventListener(new EightBall());
        jda.addEventListener(new Fact());
        jda.addEventListener(new Name());
        jda.addEventListener(new Owoify());
        jda.addEventListener(new Spoiler());
        jda.addEventListener(new Waifu());
        jda.addEventListener(new Why());

        jda.addEventListener(new Anal());
        jda.addEventListener(new Blowjob());
        jda.addEventListener(new BlowjobGif());
        jda.addEventListener(new BoobGif());
        jda.addEventListener(new Boobs());
        jda.addEventListener(new Classic());
        jda.addEventListener(new Cum());
        jda.addEventListener(new CumGif());
        jda.addEventListener(new Ero());
        jda.addEventListener(new EroFeet());
        jda.addEventListener(new EroHolo());
        jda.addEventListener(new EroKitsune());
        jda.addEventListener(new EroNeko());
        jda.addEventListener(new EroYuri());
        jda.addEventListener(new Feet());
        jda.addEventListener(new FeetGif());
        jda.addEventListener(new Femdom());
        jda.addEventListener(new Futa());
        jda.addEventListener(new Hentai());
        jda.addEventListener(new HentaiGif());
        jda.addEventListener(new Kuni());
        jda.addEventListener(new Lesbian());
        jda.addEventListener(new Orgasm());
        jda.addEventListener(new Pussy());
        jda.addEventListener(new PussyGif());
        jda.addEventListener(new PussyWank());
        jda.addEventListener(new SoloGirl());
        jda.addEventListener(new SoloGirlGif());
        jda.addEventListener(new Spank());
        jda.addEventListener(new Trap());
        jda.addEventListener(new Yuri());

        jda.addEventListener(new Baka());
        jda.addEventListener(new Cuddle());
        jda.addEventListener(new Feed());
        jda.addEventListener(new Hug());
        jda.addEventListener(new Kiss());
        jda.addEventListener(new Pat());
        jda.addEventListener(new Poke());
        jda.addEventListener(new Slap());
        jda.addEventListener(new Smug());
        jda.addEventListener(new Tickle());

        // SPECIAL
        jda.addEventListener(new JoJoMeme());
        jda.addEventListener(new LampMeme());
        jda.addEventListener(new Spaghetti());
        jda.addEventListener(new Roll());
        jda.addEventListener(new KOn());
    }
}
