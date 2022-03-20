package com.grunclepug.grunclebot.bot.util.guild;

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

public class FileInteraction {
    public static final String GUILD_LOG_FILE_NAME = "src/main/resources/guild_log.json";
    public static final String CHANNEL_LOG_FILE_NAME = "src/main/resources/channel_log.json";

    private FileInteraction() {}

    public static ArrayList<Channel> readChannelFile() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(CHANNEL_LOG_FILE_NAME));
        ArrayList<Channel> channels = gson.fromJson(reader, new TypeToken<List<Channel>>(){}.getType());

        return channels;
    }

    public static void updateChannelFile(String id, String logChannel, boolean log) throws IOException {
        ArrayList<Channel> channels = readChannelFile();
        ArrayList<String> ids = new ArrayList<>();
        File file = new File(CHANNEL_LOG_FILE_NAME);
        file.delete();
        FileWriter writer = new FileWriter(CHANNEL_LOG_FILE_NAME, false);
        Gson gson = new Gson();

        if(channels == null) {
            channels = new ArrayList<>();
        } else {
            for(int i = 0; i < channels.size(); i++) {
                ids.add(channels.get(i).getID());
            }
        }

        if(ids.contains(id)) {
            for(int i = 0; i < channels.size(); i++) {
                if(channels.get(i).getID().equals(id)) {
                    channels.get(i).setLog(log);
                }
                if(!channels.get(i).getLog()) {
                    channels.remove(channels.get(i));
                }
            }
        } else {
            channels.add(new Channel(id, logChannel, log));
        }

        gson.toJson(channels, writer);
        writer.flush();
    }

    public static ArrayList<Guild> readGuildFile() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(GUILD_LOG_FILE_NAME));
        ArrayList<Guild> guilds = gson.fromJson(reader, new TypeToken<List<Guild>>(){}.getType());

        return guilds;
    }

    public static void updateGuildFile(String id, String logChannel, boolean log) throws IOException {
        ArrayList<Guild> guilds = readGuildFile();
        ArrayList<String> ids = new ArrayList<>();
        File file = new File(GUILD_LOG_FILE_NAME);
        file.delete();
        FileWriter writer = new FileWriter(GUILD_LOG_FILE_NAME, false);
        Gson gson = new Gson();

        if(guilds == null) {
            guilds = new ArrayList<>();
        } else {
            for(int i = 0; i < guilds.size(); i++) {
                ids.add(guilds.get(i).getID());
            }
        }

        if(ids.contains(id)) {
            for(int i = 0; i < guilds.size(); i++) {
                if(guilds.get(i).getID().equals(id)) {
                    guilds.get(i).setLog(log);
                }
                if(!guilds.get(i).getLog()) {
                    guilds.remove(guilds.get(i));
                }
            }
        } else {
            guilds.add(new Guild(id, logChannel, log));
        }

        gson.toJson(guilds, writer);
        writer.flush();
    }
}
