package com.grunclepug.grunclebot.bot.util.guild;

/**
 * Models a guild
 * @author GrunclePug
 */
public class Guild {
    private String ID;
    private String logChannel;
    private boolean log;

    /**
     * Create guild object
     * @param ID id
     */
    public Guild(String ID, String logChannel, boolean log) {
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
     * Get guild id
     * @return id
     */
    public String getID() {
        return this.ID;
    }

    /**
     * Get guild log channel id
     * @return id
     */
    public String getLogChannel() {
        return this.logChannel;
    }
}
