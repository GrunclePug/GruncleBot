package com.grunclepug.grunclebot.bot.util.afk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grunclepug.grunclebot.bot.commands.general.Afk;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles file interaction for the afk log
 * @author GrunclePug
 */
public class FileInteraction {
    private FileInteraction() {}

    /**
     * Read afk log file and load users into memory
     * @param fileName afk log file
     * @return arraylist of afk users
     * @throws IOException issue with reading file
     */
    public static ArrayList<User> readFile(String fileName) throws IOException {
        Gson gson = new Gson();
        // Reader reader = Files.newBufferedReader(Paths.get(Afk.AFK_LOG_FILE)); Old way, having encoding issues
        Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Paths.get(Afk.AFK_LOG_FILE).toString()),"utf-8"));
        ArrayList<User> users = gson.fromJson(reader, new TypeToken<List<User>>(){}.getType());

        return users;
    }

    /**
     * Updates the afk log
     * @param fileName afk log file
     * @param id user id
     * @param afk isAFK
     * @param reason reason for afk
     * @throws IOException issue with reading file
     */
    public static void updateFile(String fileName, String id, boolean afk, String reason) throws IOException {
        ArrayList<User> users = readFile(fileName);
        ArrayList<String> ids = new ArrayList<>();
        File file = new File(fileName);
        file.delete();
        FileWriter writer = new FileWriter(fileName, false);
        Gson gson = new Gson();

        if(users == null) {
            users = new ArrayList<>();
        } else {
            for(int i = 0; i < users.size(); i++) {
                ids.add(users.get(i).getId());
            }
        }

        if(ids.contains(id)) {
            for(int i = 0; i < users.size(); i++) {
                if(users.get(i).getId().equals(id)) {
                    users.get(i).setAfk(afk).setReason(reason);
                }
                if(!users.get(i).isAfk()) {
                    users.remove(users.get(i));
                }
            }
        } else {
            users.add(new User(id, afk, reason));
        }

        gson.toJson(users, writer);
        writer.flush();
    }
}
