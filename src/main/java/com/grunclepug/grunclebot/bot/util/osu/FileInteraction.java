package com.grunclepug.grunclebot.bot.util.osu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * File Interaction for osu users log
 * @author GrunclePug
 */
public class FileInteraction {
    private static final String FILE_NAME = "src/main/resources/osu_users.json";

    private FileInteraction() {}

    /**
     * read osu user log
     * @return arraylist of users
     * @throws IOException issue reading file
     */
    public static ArrayList<User> readFile() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(FILE_NAME));
        ArrayList<User> users = gson.fromJson(reader, new TypeToken<List<User>>(){}.getType());

        return users;
    }

    /**
     * update osu user log
     * @param userID user id
     * @param osuID osu id
     * @throws IOException issue writing to file
     */
    public static void updateFile(String userID, String osuID) throws IOException {
        ArrayList<User> users = readFile();
        ArrayList<String> userIDs = new ArrayList<>();
        File file = new File(FILE_NAME);
        file.delete();
        FileWriter writer = new FileWriter(FILE_NAME, false);
        Gson gson = new Gson();

        if(users == null) {
            users = new ArrayList<>();
        } else {
            for(int i = 0; i < users.size(); i++) {
                userIDs.add(users.get(i).getUserID());
            }
        }

        if(userIDs.contains(userID)) {
            for(int i = 0; i < users.size(); i++) {
                if(users.get(i).getUserID().equalsIgnoreCase(userID)) {
                    users.get(i).setOsuID(osuID);
                }
            }
        } else {
            System.out.println("add new user osu id " + osuID);
            users.add(new User(userID, osuID));
        }

        gson.toJson(users, writer);
        writer.flush();
    }
}
