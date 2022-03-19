package com.grunclepug.grunclebot.bot.util.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.grunclepug.grunclebot.bot.core.Config;
import com.oopsjpeg.osu4j.GameMod;
import com.oopsjpeg.osu4j.GameMode;
import com.oopsjpeg.osu4j.OsuScore;
import com.oopsjpeg.osu4j.OsuUser;
import com.oopsjpeg.osu4j.backend.EndpointUsers;
import com.oopsjpeg.osu4j.backend.Osu;
import com.oopsjpeg.osu4j.exception.OsuAPIException;
import net.dv8tion.jda.api.EmbedBuilder;

import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Handle calls to Osu API
 * @author GrunclePug
 */
public class OsuAPI {
    private static final String FILE_NAME = "src/main/resources/osu_api_key.json";
    public static final GameMode STANDARD = GameMode.STANDARD;
    public static final GameMode MANIA = GameMode.MANIA;
    public static final GameMode TAIKO = GameMode.TAIKO;
    public static final GameMode CATCH = GameMode.CATCH_THE_BEAT;

    private static String token;

    private OsuAPI() {}

    /**
     * Authenticate to API
     * @return Osu api object
     * @throws IOException issue loading token from file
     */
    public static Osu authenticate() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(FILE_NAME));
        token = gson.fromJson(reader, JsonObject.class).get("token").toString().replace("\"", "");

        return Osu.getAPI(token);
    }

    /**
     * Get osu user
     * @param user osu user
     * @param mode game mode
     * @return osu user embed
     * @throws OsuAPIException issue getting user
     */
    public static EmbedBuilder getUser(String user, GameMode mode) throws OsuAPIException {
        EmbedBuilder builder = new EmbedBuilder();
        int userID = 0;
        OsuUser osuUser = null;
        Osu osu = null;
        try {
            osu = authenticate();
        } catch(IOException e) {
            e.printStackTrace(System.err);
        }

        if(osu != null) {
            try {
                userID = Integer.parseInt(user);
            } catch(NumberFormatException e) {
                // ignore
            }

            if(userID != 0) {
                osuUser = osu.users.query(new EndpointUsers.ArgumentsBuilder(userID).setMode(mode).build());
            } else {
                osuUser = osu.users.query(new EndpointUsers.ArgumentsBuilder(user.trim()).setMode(mode).build());
            }
            builder.setTitle("<:osu:952758267581460550> " + osuUser.getMode().getName() + " Profile: " + osuUser.getUsername())
                    .setColor(0xff64ac)
                    .setThumbnail("https://a.ppy.sh/" + osuUser.getID())
                    .addField("・Stats", Config.COMMA_NUMBER.format(osuUser.getPP()) + "PP ・ " + Config.DF_TWO_DECIMAL_POINT.format(osuUser.getAccuracy()) + "% Acc\n#" + Config.COMMA_NUMBER.format(osuUser.getRank()) + " (#" + Config.COMMA_NUMBER.format(osuUser.getCountryRank()) + " :flag_" + osuUser.getCountry().toString().toLowerCase() + ":)", false)
                    .addField("・Play Count", Config.COMMA_NUMBER.format(osuUser.getPlayCount()), false)
                    .addField("・Ranked Score", Config.COMMA_NUMBER.format(osuUser.getRankedScore()), false)
                    .addField("・Total Score", Config.COMMA_NUMBER.format(osuUser.getTotalScore()) +
                            "\n<:osuA:952693566902247486>: " + Config.COMMA_NUMBER.format(osuUser.getCountRankA()) +
                            ", <:osuS:952693685357797429>: " + Config.COMMA_NUMBER.format(osuUser.getCountRankS()) +
                            ", <:osuSm:952693824336068658>: " + Config.COMMA_NUMBER.format(osuUser.getCountRankSH()) +
                            ", <:osuSS:952693773933092926>: " + Config.COMMA_NUMBER.format(osuUser.getCountRankSS()) +
                            ", <:osuSSm:952693881844142150>: " + Config.COMMA_NUMBER.format(osuUser.getCountRankSSH()), false)
                    .addField("・Total Hits", Config.COMMA_NUMBER.format(osuUser.getTotalHits()) + " (" + Config.COMMA_NUMBER.format(osuUser.getTotalHits() / osuUser.getPlayCount()) + " Hits/play)" +
                            "\n<:osu300:952669072573730818>: " + Config.COMMA_NUMBER.format(osuUser.getHit300()) +
                            ", <:osu100:952669177716559903>: " + Config.COMMA_NUMBER.format(osuUser.getHit100()) +
                            ", <:osu50:952669215196852264>: " + Config.COMMA_NUMBER.format(osuUser.getHit50()), false);
        }
        return builder;
    }

    /**
     * Get user top plays
     * @param user osu user
     * @param mode game mode
     * @return top plays embed
     * @throws OsuAPIException issue getting top plays
     * @throws MalformedURLException invalid url
     */
    public static EmbedBuilder getTopPlays(String user, GameMode mode) throws OsuAPIException, MalformedURLException {
        EmbedBuilder builder = new EmbedBuilder();
        int userID = 0;
        OsuUser osuUser = null;
        Osu osu = null;
        try {
            osu = authenticate();
        } catch(IOException e) {
            e.printStackTrace(System.err);
        }

        if(osu != null) {
            try {
                userID = Integer.parseInt(user);
            } catch(NumberFormatException e) {
                // ignore
            }

            if(userID != 0) {
                osuUser = osu.users.query(new EndpointUsers.ArgumentsBuilder(userID).setMode(mode).build());
            } else {
                osuUser = osu.users.query(new EndpointUsers.ArgumentsBuilder(user.trim()).setMode(mode).build());
            }

            ArrayList<OsuScore> topScores = new ArrayList<>(osuUser.getTopScores(5).get());
            builder.setTitle("<:osu:952758267581460550> " + mode.getName() + " Top Plays: " + osuUser.getUsername())
                    .setColor(0xff64ac)
                    .setThumbnail("https://a.ppy.sh/" + osuUser.getID());

            for(int i = 0; i < topScores.size(); i++) {
                GameMod[] mods = topScores.get(i).getEnabledMods();
                String formattedMods = "";
                for(int j = 0; j < mods.length; j++) {
                    if(mods[j] != null) {
                        String mod = "";

                        switch(mods[j].name()) {
                            case "NO_FAIL":
                                mod = "NF";
                                break;
                            case "EASY":
                                mod = "EZ";
                                break;
                            case "HIDDEN":
                                mod = "HD";
                                break;
                            case "HARD_ROCK":
                                mod = "HR";
                                break;
                            case "SUDDEN_DEATH":
                                mod = "SD";
                                break;
                            case "DOUBLE_TIME":
                                mod = "DT";
                                break;
                            case "RELAX":
                                mod = "RL";
                                break;
                            case "HALF_TIME":
                                mod = "HT";
                                break;
                            case "NIGHTCORE":
                                mod = "NC";
                                break;
                            case "FLASHLIGHT":
                                mod = "FL";
                                break;
                            case "AUTOPLAY":
                                mod = "AT";
                                break;
                            case "SPUNOUT":
                                mod = "SO";
                                break;
                            case "AUTOPILOT":
                                mod = "AP";
                                break;
                            case "PERFECT":
                                mod = "PF";
                                break;
                            case "KEY_1":
                                mod = "1K";
                                break;
                            case "KEY_2":
                                mod = "2K";
                                break;
                            case "KEY_3":
                                mod = "3K";
                                break;
                            case "KEY_4":
                                mod = "4K";
                                break;
                            case "KEY_5":
                                mod = "5K";
                                break;
                            case "KEY_6":
                                mod = "6K";
                                break;
                            case "KEY_7":
                                mod = "7K";
                                break;
                            case "KEY_8":
                                mod = "8K";
                                break;
                            case "KEY_9":
                                mod = "9K";
                                break;
                            default:
                                mod = "";
                                break;
                        }
                        formattedMods += ", " + mod;
                    }
                }

                String rank = "";
                switch(topScores.get(i).getRank()) {
                    case "SSH":
                        rank = "<:osuSSm:952693881844142150>";
                        break;
                    case "SS":
                        rank = "<:osuSS:952693773933092926>";
                        break;
                    case "SH":
                        rank = "<:osuSm:952693824336068658>";
                        break;
                    case "S":
                        rank = "<:osuS:952693685357797429>";
                        break;
                    case "A":
                        rank = "<:osuA:952693566902247486>";
                        break;
                    case "B":
                        rank = "<:osuB:952693623626039308>";
                        break;
                    case "C":
                        rank = "<:osuC:952693921983655936>";
                        break;
                    case "D":
                        rank = "<:osuD:952693961309421568>";
                        break;
                    case "F":
                        rank = "<:osuF:952693999020413009>";
                        break;
                }

                if(formattedMods.length() < 1) {
                    formattedMods = "+No Mod";
                } else {
                    formattedMods = "+" + formattedMods.substring(1).trim();
                }
                builder.addField((i + 1) + ". " + topScores.get(i).getBeatmap().get().getTitle() + " [" + topScores.get(i).getBeatmap().get().getVersion() + "] " + formattedMods + " [" + Config.DF_TWO_DECIMAL_POINT.format(topScores.get(i).getBeatmap().get().getDifficulty()) + "☆]",
                        "[Map Link](" + topScores.get(i).getBeatmap().get().getURL().toString() + ")" +
                        "\n" + rank + " ・ " + Config.COMMA_NUMBER.format(topScores.get(i).getPp()) + "PP ・ " + Config.COMMA_NUMBER.format(topScores.get(i).getScore()) + " score" +
                        "\n<:osu300:952669072573730818>: " + Config.COMMA_NUMBER.format(topScores.get(i).getHit300()) +
                        ", <:osu100:952669177716559903>: " + Config.COMMA_NUMBER.format(topScores.get(i).getHit100()) +
                        ", <:osu50:952669215196852264>: " + Config.COMMA_NUMBER.format(topScores.get(i).getHit50()) +
                        ", <:osuMiss:952706759875784734>: " + Config.COMMA_NUMBER.format(topScores.get(i).getMisses()) +
                        "\nCombo: " + Config.COMMA_NUMBER.format(topScores.get(i).getMaxCombo()) + "/" + Config.COMMA_NUMBER.format(topScores.get(i).getBeatmap().get().getMaxCombo()), false);
            }
        }
        return builder;
    }

    /**
     * Get osu user id
     * @param user osu user
     * @return user id
     * @throws OsuAPIException issue getting user
     */
    public static String getUserID(String user) throws OsuAPIException {
        String id = "";
        Osu osu = null;
        try {
            osu = authenticate();
        } catch(IOException e) {
            e.printStackTrace(System.err);
        }

        if(osu != null) {
            OsuUser osuUser = osu.users.query(new EndpointUsers.ArgumentsBuilder(user.trim()).setMode(STANDARD).build());
            id = "" + osuUser.getID();
        }
        return id;
    }
}
