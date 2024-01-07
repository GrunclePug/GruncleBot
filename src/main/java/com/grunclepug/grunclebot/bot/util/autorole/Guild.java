package com.grunclepug.grunclebot.bot.util.autorole;

/**
 * Representation of AutoRole Guild configuration as an object
 * This class is used to store the data read from the AutoRole config file
 * @author GrunclePug
 */
public class Guild {
    // Instance Variables
    private final String id;
    private String role;
    private boolean active;

    /**
     * Constructor for Guild object
     * @param id Guild associated with AutoRole config
     * @param role Role to be automatically added upon user join
     * @param active Mark this rule as active or inactive
     */
    public Guild(String id, String role, boolean active) {
        this.id = id.trim();
        this.role = role.trim();
        this.active = active;
    }

    /**
     * Mutator for Assigned Role
     * @param role Role to be Assigned to user on join
     */
    public void setRole(String role) {
        if(role != null && role.trim().length() > 0) {
            this.role = role;
        }
    }

    /**
     * Accessor for Assigned Role
     * @return Role to be Assigned to user on join
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Accessor for Guild associated to AutoRole rule
     * @return Guild ID of associated Guild
     */
    public String getId() {
        return this.id;
    }

    /**
     * Mutator for marking rule as active or inactive
     * @param active Status of AutoRole rule
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Accessor for AutoRole Active status
     * @return Active status for AutoRole rule
     */
    public boolean isActive() {
        return this.active;
    }
}
