package com.grunclepug.grunclebot.bot.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Read project line count from json file
 * @author GrunclePug
 */
public class LineCount {
    private static final String LINE_COUNT_FILE = "src/main/resources/line_count.json";

    private LineCount() {}

    public static int getLineCount() {
        int lineCount = 0;
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(LINE_COUNT_FILE));
            lineCount = gson.fromJson(reader, JsonObject.class).get("line_count").getAsInt();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return lineCount;
    }
}
