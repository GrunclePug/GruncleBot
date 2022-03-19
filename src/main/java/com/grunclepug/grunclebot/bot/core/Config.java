package com.grunclepug.grunclebot.bot.core;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Config class for GruncleBot
 * reads config.json and provides configuration data for the bot
 * @author GrunclePug
 */
public class Config {
    private static final String FILE_NAME = "src/main/resources/config.json";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm");
    public static final DecimalFormat DF_ONE_DECIMAL_POINT = new DecimalFormat("0.0");
    public static final DecimalFormat DF_TWO_DECIMAL_POINT = new DecimalFormat("0.00");
    public static final DecimalFormat COMMA_NUMBER = new DecimalFormat("#,###");

    private static String token;
    private static String prefix;
    private static String suggestionChannel;
    private static String bugReportChannel;
    private static String botLogChannel;
    private static String guildLogChannel;

    private Config() {}

    /**
     * Reads the config file and loads data into memory
     */
    public static void readFile() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(FILE_NAME));
        Map<?, ?> configMap = gson.fromJson(reader, Map.class);

        for(Map.Entry<?, ?> entry : configMap.entrySet()) {
            switch(entry.getKey().toString()) {
                case "token":
                    token = entry.getValue().toString();
                    break;
                case "prefix":
                    prefix = entry.getValue().toString();
                    break;
                case "suggestionChannel":
                    suggestionChannel = entry.getValue().toString();
                    break;
                case "bugReportChannel":
                    bugReportChannel = entry.getValue().toString();
                    break;
                case "botLogChannel":
                    botLogChannel = entry.getValue().toString();
                    break;
                case "guildLogChannel":
                    guildLogChannel = entry.getValue().toString();
                    break;
            }
        }
    }

    /**
     * Provides the bot token
     * @return Bot token
     */
    public static String getToken() {
        return token;
    }

    /**
     * Provides the bot prefix
     * @return Bot prefix
     */
    public static String getPrefix() {
        return prefix;
    }

    /**
     * Provides the bot suggestion channel
     * @return Bot suggestion channel id
     */
    public static String getSuggestionChannel() {
        return suggestionChannel;
    }

    /**
     * Provides the bot bug report channel
     * @return Bot bug report channel id
     */
    public static String getBugReportChannel() {
        return bugReportChannel;
    }

    /**
     * Provides the bot log channel
     * @return Bot log channel id
     */
    public static String getBotLogChannel() {
        return botLogChannel;
    }

    /**
     * Provides the bot guild log channel
     * @return Bot guild log channel id
     */
    public static String getGuildLogChannel() {
        return guildLogChannel;
    }
}
