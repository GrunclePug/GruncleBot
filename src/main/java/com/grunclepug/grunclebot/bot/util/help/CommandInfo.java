package com.grunclepug.grunclebot.bot.util.help;

/**
 * Represent a commands info
 * @author GrunclePug
 */
public class CommandInfo {
    private String command;
    private String category;
    private String usage;
    private String description;

    /**
     * Constructor
     * @param command command name
     * @param category command category
     * @param usage command usage
     * @param description command description
     */
    public CommandInfo(String command, String category, String usage, String description) {
        this.command = command.trim();
        this.setCategory(category);
        this.setUsage(usage);
        this.setDescription(description);
    }

    /**
     * Set category
     * @param category command category
     */
    public void setCategory(String category) {
        this.category = category.trim();
    }

    /**
     * Set usage
     * @param usage command usage
     */
    public void setUsage(String usage) {
        this.usage = usage.trim();
    }

    /**
     * Set description
     * @param description command description
     */
    public void setDescription(String description) {
        this.description = description.trim();
    }

    /**
     * Get command name
     * @return command name
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * Get category
     * @return category
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Get usage
     * @return usage
     */
    public String getUsage() {
        return this.usage;
    }

    /**
     * Get description
     * @return description
     */
    public String getDescription() {
        return this.description;
    }
}
