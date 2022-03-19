package com.grunclepug.grunclebot.bot.util.osu;

/**
 * Models osu user
 * @author GrunclePug
 */
public class User {
    private String userID;
    private String osuID;

    /**
     * Contructor for osu user
     * @param userID user id
     * @param osuID osu id
     */
    public User(String userID, String osuID) {
        this.userID = userID.trim();
        this.setOsuID(osuID);
    }

    /**
     * set osu id
     * @param osuID osu id
     */
    public void setOsuID(String osuID) {
        this.osuID = osuID.trim();
    }

    /**
     * get user id
     * @return user id
     */
    public String getUserID() {
        return this.userID;
    }

    /**
     * get osu id
     * @return osu id
     */
    public String getOsuID() {
        return this.osuID;
    }
}
