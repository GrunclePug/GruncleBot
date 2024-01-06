package com.grunclepug.grunclebot.bot.util.autorole;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grunclepug.grunclebot.bot.util.autorole.Guild;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileInteraction {
    public static final String AUTOROLE_FILE_NAME = "src/main/resources/autorole.json";

    private FileInteraction() {}

    public static ArrayList<com.grunclepug.grunclebot.bot.util.autorole.Guild> readAutoRoleFile() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(AUTOROLE_FILE_NAME));
        ArrayList<com.grunclepug.grunclebot.bot.util.autorole.Guild> guilds = gson.fromJson(reader, new TypeToken<List<com.grunclepug.grunclebot.bot.util.autorole.Guild>>(){}.getType());

        return guilds;
    }

    public static void updateAutoRoleFile(String roleId, String guildId, boolean active) throws IOException {
        ArrayList<com.grunclepug.grunclebot.bot.util.autorole.Guild> guilds = readAutoRoleFile();
        ArrayList<String> ids = new ArrayList<>();
        File file = new File(AUTOROLE_FILE_NAME);
        file.delete();
        FileWriter writer = new FileWriter(AUTOROLE_FILE_NAME, false);
        Gson gson = new Gson();

        if(guilds == null) {
            guilds = new ArrayList<>();
        } else {
            for(int i = 0; i < guilds.size(); i++) {
                ids.add(guilds.get(i).getId());
            }
        }

        if(ids.contains(guildId)) {
            for(int i = 0; i < guilds.size(); i++) {
                if(guilds.get(i).getId().equals(guildId)) {
                    guilds.get(i).setActive(active);
                }
                if(!guilds.get(i).isActive()) {
                    guilds.remove(guilds.get(i));
                }
            }
        } else {
            guilds.add(new Guild(guildId, roleId, active));
        }

        gson.toJson(guilds, writer);
        writer.flush();
    }
}
