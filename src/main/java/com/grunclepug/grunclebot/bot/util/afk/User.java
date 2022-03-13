package com.grunclepug.grunclebot.bot.util.afk;

/**
 * Models a user
 * @author GrunclePug
 */
public class User {
    public static final String DEFAULT_REASON = "no reason provided.";
    private final String id;
    private boolean afk;
    private String reason;

    /**
     * Main Constructor
     * @param id user id
     */
    public User(String id) {
        this.id = id;
    }

    /**
     * Detailed Constructor
     * @param id user id
     * @param afk is user afk?
     * @param reason reason for afk
     */
    public User(String id, boolean afk, String reason) {
        this(id);
        this.setAfk(afk);
        this.setReason(reason);
    }

    /**
     * Accessor for user id
     * @return user id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Accessor for user afk status
     * @return user afk status
     */
    public boolean isAfk() {
        return this.afk;
    }

    /**
     * Accessor for afk reason
     * @return afk reason
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * Sets user afk status
     * @param afk afk status
     * @return this
     */
    public User setAfk(boolean afk) {
        this.afk = afk;
        return this;
    }

    /**
     * Sets user afk reason
     * @param reason afk reason
     * @return this
     */
    public User setReason(String reason) {
        if(reason != null && reason.trim().length() > 0) {
            this.reason = reason.trim();
        } else {
            this.reason = DEFAULT_REASON;
        }
        return this;
    }
}
