package com.grunclepug.grunclebot.bot.util.autorole;

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
 * File Interaction for AutoRole config
 * @author GrunclePug
 */
public class FileInteraction {
    public static final String AUTOROLE_FILE_NAME = "src/main/resources/autorole.json";

    private FileInteraction() {}

    /**
     * Read AutoRole guild config
     * @return ArrayList of Guild configs for AutoRole settings
     * @throws IOException
     */
    public static ArrayList<Guild> readAutoRoleFile() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(AUTOROLE_FILE_NAME));
        ArrayList<Guild> guilds = gson.fromJson(reader, new TypeToken<List<Guild>>(){}.getType());

        return guilds;
    }

    /**
     * Update AutoRole config entry
     * @param roleId Requested Role for assignment
     * @param guildId Guild for AutoRole
     * @param active Is this AutoRole entry enabled
     * @throws IOException Thrown if file is failed to read, this may be caused by insufficient permissions or a missing file
     */
    public static void updateAutoRoleFile(String roleId, String guildId, boolean active) throws IOException {
        // Load guild AutoRole list and wipe file to prevent corruption upon writing
        ArrayList<Guild> guilds = readAutoRoleFile();
        ArrayList<String> ids = new ArrayList<>();
        File file = new File(AUTOROLE_FILE_NAME);
        file.delete();
        FileWriter writer = new FileWriter(AUTOROLE_FILE_NAME, false);
        Gson gson = new Gson();

        // Prepare output Arraylist if file was empty, add existing entries to buffer
        if(guilds == null) {
            guilds = new ArrayList<>();
        } else {
            for(int i = 0; i < guilds.size(); i++) {
                ids.add(guilds.get(i).getId());
            }
        }

        // Determine if command issued was to enable or disable an AutoRole entry
        if(ids.contains(guildId)) {
            for(int i = 0; i < guilds.size(); i++) {
                // Apply change to existing entry
                if(guilds.get(i).getId().equals(guildId)) {
                    guilds.get(i).setActive(active);
                }
                // If entry was marked as disabled, remove it from the arraylist
                if(!guilds.get(i).isActive()) {
                    guilds.remove(guilds.get(i));
                }
            }
        } else {
            // Entry not found in file, adding to output arraylist to be written to file
            guilds.add(new Guild(guildId, roleId, active));
        }

        // Write new config list to json file
        gson.toJson(guilds, writer);
        writer.flush();
    }
}
