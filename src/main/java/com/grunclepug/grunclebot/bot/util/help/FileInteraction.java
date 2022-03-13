package com.grunclepug.grunclebot.bot.util.help;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * File interaction for command_info.json
 * @author GrunclePug
 */
public class FileInteraction {
    private static final String FILE_NAME = "src/main/resources/command_info.json";

    private FileInteraction() {}

    /**
     * Read file
     * @return command info arraylist
     * @throws IOException issue reading file
     */
    public static ArrayList<CommandInfo> readFile() throws IOException {
        ArrayList<CommandInfo> output = new ArrayList<>();
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(FILE_NAME));
        JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);

        if(jsonArray != null) {
            for(int i = 0; i < jsonArray.size(); i++) {
                JsonObject commandInfo = jsonArray.get(i).getAsJsonObject();
                output.add(new CommandInfo(
                        commandInfo.get("command").toString(),
                        commandInfo.get("category").toString(),
                        commandInfo.get("usage").toString(),
                        commandInfo.get("description").toString()));
            }
        }
        return output;
    }

    /**
     * Update file
     * @param commandInfos command info arraylist
     * @throws IOException issue writing to file
     */
    public static void updateFile(ArrayList<CommandInfo> commandInfos) throws IOException {
        File file = new File(FILE_NAME);
        file.delete();
        FileWriter writer = new FileWriter(FILE_NAME, false);
        Gson gson = new Gson();

        gson.toJson(commandInfos, writer);
        writer.flush();
    }
}
