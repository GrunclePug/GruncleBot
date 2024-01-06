package com.grunclepug.grunclebot.bot.util.help;

import com.grunclepug.grunclebot.bot.core.Config;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Create and load all command info
 * @author GrunclePug
 */
public class CommandInfoLoader {
    private CommandInfoLoader() {}

    /**
     * Load all command info
     * @throws IOException issue reading file
     */
    public static void loadCommands() throws IOException {
        ArrayList<CommandInfo> commandInfos = new ArrayList<>();

        // General
        commandInfos.add(new CommandInfo("help", "General", Config.getPrefix() + "help", "Displays command help"));
        commandInfos.add(new CommandInfo("afk", "General", Config.getPrefix() + "afk {reason (optional)}", "Set yourself as afk"));
        commandInfos.add(new CommandInfo("info", "General", Config.getPrefix() + "info", "Get bot info"));
        commandInfos.add(new CommandInfo("invite", "General", Config.getPrefix() + "invite", "Get an invite for the bot"));
        commandInfos.add(new CommandInfo("ping", "General", Config.getPrefix() + "ping", "Get bot/discord latency"));
        commandInfos.add(new CommandInfo("resources", "General", Config.getPrefix() + "resources", "Get bot system resources"));
        commandInfos.add(new CommandInfo("serverinfo", "General", Config.getPrefix() + "serverinfo", "Get server info"));
        commandInfos.add(new CommandInfo("userinfo", "General", Config.getPrefix() + "userinfo {@user (optional)", "Get user info"));

        // Utility
        commandInfos.add(new CommandInfo("avatar", "Utility", Config.getPrefix() + "avatar {@user (optional)", "Get user avatar"));
        commandInfos.add(new CommandInfo("enlarge", "Utility", Config.getPrefix() + "enlarge {emote}", "Enlarge an emote (custom emotes only)"));
        commandInfos.add(new CommandInfo("randomcolor", "Utility", Config.getPrefix() + "randomcolor", "Generate a random color"));
        commandInfos.add(new CommandInfo("steal", "Utility", Config.getPrefix() + "steal {emote}", "Steal an emote and add to this server"));

        // Staff
        commandInfos.add(new CommandInfo("ban", "Staff", Config.getPrefix() + "ban {@user} {reason (optional)}", "Ban user"));
        commandInfos.add(new CommandInfo("kick", "Staff", Config.getPrefix() + "kick {@user} {reason (optional)}", "Kick user"));
        commandInfos.add(new CommandInfo("massrole", "Staff", Config.getPrefix() + "massrole add/remove {@role}", "Add or remove role from everyone"));
        commandInfos.add(new CommandInfo("purge", "Staff", Config.getPrefix() + "purge {@user (optional)} {# of messages (max 100)}", "Purge x messages"));
        commandInfos.add(new CommandInfo("role", "Staff", Config.getPrefix() + "role add/remove {@role} {@user}", "Add or remove role from user"));
        commandInfos.add(new CommandInfo("rolecolor", "Staff", Config.getPrefix() + "rolecolor {@role} {color code}", "Change role color"));
        commandInfos.add(new CommandInfo("autorole", "Staff", Config.getPrefix() + "autorole add/remove {@role}", "Auto add role on member join"));

        // Fun
        commandInfos.add(new CommandInfo("randomcase", "Fun", Config.getPrefix() + "randomcase {text}", "Return input randomly cased"));
        commandInfos.add(new CommandInfo("character", "Fun", Config.getPrefix() + "character {name}", "Search for a character on MyAnimeList"));
        commandInfos.add(new CommandInfo("8ball", "Fun", Config.getPrefix() + "8ball {question}", "Ask the magical 8 ball a question"));
        commandInfos.add(new CommandInfo("fact", "Fun", Config.getPrefix() + "fact", "Get a random fact"));
        commandInfos.add(new CommandInfo("name", "Fun", Config.getPrefix() + "name", "Generate a random name"));
        commandInfos.add(new CommandInfo("owoify", "Fun", Config.getPrefix() + "owoify {text}", "Owoify your input text"));
        commandInfos.add(new CommandInfo("spoiler", "Fun", Config.getPrefix() + "spoiler {text}", "Make each letter a spoiler"));
        commandInfos.add(new CommandInfo("why", "Fun", Config.getPrefix() + "why", "Why does this command even exist?"));
        commandInfos.add(new CommandInfo("roll", "Fun", Config.getPrefix() + "roll {sides (optional)}", "Roll a die (default is 6)"));

        // Osu
        commandInfos.add(new CommandInfo("osuset", "Osu", Config.getPrefix() + "osuset {osu username}", "Set your osu username"));
        commandInfos.add(new CommandInfo("osu", "Osu", Config.getPrefix() + "osu {mode} {osu user}", "Get osu profile"));
        commandInfos.add(new CommandInfo("osu", "Osu", Config.getPrefix() + "osu {mode (default: standard)}", "Get your osu profile if you have it set"));
        commandInfos.add(new CommandInfo("osutop", "Osu", Config.getPrefix() + "osutop {mode} {osu user}", "Get osu top plays"));
        commandInfos.add(new CommandInfo("osutop", "Osu", Config.getPrefix() + "osutop {mode (default: standard)}", "Get your osu top plays if you have username set"));

        // Special
        commandInfos.add(new CommandInfo("jojomeme", "Special", Config.getPrefix() + "jojomeme", "Post a Jojo's meme"));
        commandInfos.add(new CommandInfo("kon", "Special", Config.getPrefix() + "kon", "Post a random K-On! photo from reddit"));
        commandInfos.add(new CommandInfo("lampmeme", "Special", Config.getPrefix() + "lampmeme", "Post a moth/lamp meme"));
        commandInfos.add(new CommandInfo("spaghetti", "Special", Config.getPrefix() + "spaghetti", "Excuse me?"));

        // SFW Images
        commandInfos.add(new CommandInfo("holo", "SFW Images", Config.getPrefix() + "holo", "Post a holo image"));
        commandInfos.add(new CommandInfo("kitsune", "SFW Images", Config.getPrefix() + "kitsune", "Post a fox girl image"));
        commandInfos.add(new CommandInfo("neko", "SFW Images", Config.getPrefix() + "neko", "Post a cat girl image"));
        commandInfos.add(new CommandInfo("nekogif", "SFW Images", Config.getPrefix() + "nekogif", "Post a cat girl gif"));
        commandInfos.add(new CommandInfo("baka", "SFW Images", Config.getPrefix() + "baka", "BAKA BAKA BAKA!"));
        commandInfos.add(new CommandInfo("cuddle", "SFW Images", Config.getPrefix() + "cuddle {@user (optional)}", "Cuddle a user"));
        commandInfos.add(new CommandInfo("feed", "SFW Images", Config.getPrefix() + "feed {@user (optional)}", "Feed a user"));
        commandInfos.add(new CommandInfo("hug", "SFW Images", Config.getPrefix() + "hug {@user (optional)}", "Hug a user"));
        commandInfos.add(new CommandInfo("kiss", "SFW Images", Config.getPrefix() + "kiss {@user (optional)}", "Kiss a user"));
        commandInfos.add(new CommandInfo("pat", "SFW Images", Config.getPrefix() + "pat {@user (optional)}", "Headpat a user"));
        commandInfos.add(new CommandInfo("poke", "SFW Images", Config.getPrefix() + "poke {@user (optional)}", "Poke a user"));
        commandInfos.add(new CommandInfo("slap", "SFW Images", Config.getPrefix() + "slap {@user (optional)}", "Slap a user"));
        commandInfos.add(new CommandInfo("smug", "SFW Images", Config.getPrefix() + "smug", ":3"));
        commandInfos.add(new CommandInfo("tickle", "SFW Images", Config.getPrefix() + "tickle {@user (optional)}", "Tickle a user"));
        commandInfos.add(new CommandInfo("waifu", "SFW Images", Config.getPrefix() + "waifu", "Post a random waifu"));
        commandInfos.add(new CommandInfo("cat", "SFW Images", Config.getPrefix() + "cat", "Post a cat image"));
        commandInfos.add(new CommandInfo("dog", "SFW Images", Config.getPrefix() + "dog", "Post a dog image"));
        commandInfos.add(new CommandInfo("goose", "SFW Images", Config.getPrefix() + "goose", "Post a goose image"));

        // NSFW Images
        commandInfos.add(new CommandInfo("holo", "NSFW Images", Config.getPrefix() + "holo", "Post a holo image"));
        commandInfos.add(new CommandInfo("kitsune", "NSFW Images", Config.getPrefix() + "kitsune", "Post a fox girl image"));
        commandInfos.add(new CommandInfo("neko", "NSFW Images", Config.getPrefix() + "neko", "Post a cat girl image"));
        commandInfos.add(new CommandInfo("nekogif", "NSFW Images", Config.getPrefix() + "nekogif", "Post a cat girl gif"));
        commandInfos.add(new CommandInfo("anal", "NSFW Images", Config.getPrefix() + "anal", "Post an anal image"));
        commandInfos.add(new CommandInfo("blowjob", "NSFW Images", Config.getPrefix() + "blowjob", "Post a blowjob image"));
        commandInfos.add(new CommandInfo("blowjobgif", "NSFW Images", Config.getPrefix() + "blowjobgif", "Post a blowjob gif"));
        commandInfos.add(new CommandInfo("tiddy", "NSFW Images", Config.getPrefix() + "tiddy", "Post a tiddy image"));
        commandInfos.add(new CommandInfo("tiddygif", "NSFW Images", Config.getPrefix() + "tiddygif", "Post a tiddy gif"));
        commandInfos.add(new CommandInfo("classic", "NSFW Images", Config.getPrefix() + "classic", "Post a classic hentai image"));
        commandInfos.add(new CommandInfo("cum", "NSFW Images", Config.getPrefix() + "cum", "Post a cum image"));
        commandInfos.add(new CommandInfo("cumgif", "NSFW Images", Config.getPrefix() + "cumgif", "Post a cum gif"));
        commandInfos.add(new CommandInfo("ero", "NSFW Images", Config.getPrefix() + "ero", "Post an erotic image"));
        commandInfos.add(new CommandInfo("erofeet", "NSFW Images", Config.getPrefix() + "erofeet", "Post an erotic feet image"));
        commandInfos.add(new CommandInfo("eroholo", "NSFW Images", Config.getPrefix() + "eroholo", "Post an erotic holo image"));
        commandInfos.add(new CommandInfo("erokitsune", "NSFW Images", Config.getPrefix() + "erokitsune", "Post an erotic fox girl image"));
        commandInfos.add(new CommandInfo("eroneko", "NSFW Images", Config.getPrefix() + "eroneko", "Post an erotic cat girl image"));
        commandInfos.add(new CommandInfo("eroyuri", "NSFW Images", Config.getPrefix() + "eroyuri", "Post an erotic yuri image"));

        // NSFW Images 2
        commandInfos.add(new CommandInfo("feet", "NSFW Images 2", Config.getPrefix() + "feet", "Post a feet image"));
        commandInfos.add(new CommandInfo("feetgif", "NSFW Images 2", Config.getPrefix() + "feetgif", "Post a feet gif"));
        commandInfos.add(new CommandInfo("femdom", "NSFW Images 2", Config.getPrefix() + "femdom", "Post a femdom image"));
        commandInfos.add(new CommandInfo("futa", "NSFW Images 2", Config.getPrefix() + "futa", "Post a futa image"));
        commandInfos.add(new CommandInfo("hentai", "NSFW Images 2", Config.getPrefix() + "hentai", "Post a generic hentai image"));
        commandInfos.add(new CommandInfo("hentaigif", "NSFW Images 2", Config.getPrefix() + "hentaigif", "Post a generic hentai gif"));
        commandInfos.add(new CommandInfo("kuni", "NSFW Images 2", Config.getPrefix() + "kuni", "Post a pussy lick image"));
        commandInfos.add(new CommandInfo("lesbian", "NSFW Images 2", Config.getPrefix() + "lesbian", "Post a lesbian image"));
        commandInfos.add(new CommandInfo("orgasm", "NSFW Images 2", Config.getPrefix() + "orgasm", "Post an orgrasm image"));
        commandInfos.add(new CommandInfo("pussy", "NSFW Images 2", Config.getPrefix() + "pussy", "Post a pussy image"));
        commandInfos.add(new CommandInfo("pussygif", "NSFW Images 2", Config.getPrefix() + "pussygif", "Post a pussy gif"));
        commandInfos.add(new CommandInfo("pussywank", "NSFW Images 2", Config.getPrefix() + "pussywank", "Post a pussy wank image"));
        commandInfos.add(new CommandInfo("sologirl", "NSFW Images 2", Config.getPrefix() + "sologirl", "Post a solo girl image"));
        commandInfos.add(new CommandInfo("sologirlgif", "NSFW Images 2", Config.getPrefix() + "sologirlgif", "Post a solo girl gif"));
        commandInfos.add(new CommandInfo("spank", "NSFW Images 2", Config.getPrefix() + "spank {@user (optional)}", "Spank a user"));
        commandInfos.add(new CommandInfo("trap", "NSFW Images 2", Config.getPrefix() + "trap", "Post a trap image"));
        commandInfos.add(new CommandInfo("yuri", "NSFW Images 2", Config.getPrefix() + "yuri", "Post a yuri image"));

        // Bot
        commandInfos.add(new CommandInfo("bugreport", "Bot", Config.getPrefix() + "bugreport {bug}", "Report a bug"));
        commandInfos.add(new CommandInfo("suggest", "Bot", Config.getPrefix() + "suggest {suggestion}", "Suggest a feature/change"));
        commandInfos.add(new CommandInfo("privacy", "Bot", Config.getPrefix() + "privacy", "View the privacy policy"));

        FileInteraction.updateFile(commandInfos);
    }
}
