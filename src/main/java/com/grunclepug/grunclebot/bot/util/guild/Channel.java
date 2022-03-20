package com.grunclepug.grunclebot.bot.util.guild;

/**
 * Models a Channel
 * @author GrunclePug
 */
public class Channel {
    private String ID;
    String logChannel;
    private boolean log;

    /**
     * Create channel object
     * @param ID id
     */
    public Channel(String ID, String logChannel, boolean log) {
        this.ID = ID.trim();
        this.logChannel = logChannel;
        this.setLog(log);
    }

    /**
     * Set log bool
     * @param log log?
     */
    public void setLog(boolean log) {
        this.log = log;
    }

    /**
     * get log bool
     * @return log?
     */
    public boolean getLog() {
        return this.log;
    }

    /**
     * Get channel id
     * @return id
     */
    public String getID() {
        return this.ID;
    }

    /**
     * Get log channel id
     * @return id
     */
    public String getLogChannel() {
        return this.logChannel;
    }
}
