package com.grunclepug.grunclebot.bot.util.autorole;

public class Guild {
    private final String id;
    private String role;
    private boolean active;

    public Guild(String id, String role, boolean active) {
        this.id = id.trim();
        this.role = role.trim();
        this.active = active;
    }

    public void setRole(String role) {
        if(role != null && role.trim().length() > 0) {
            this.role = role;
        }
    }

    public String getRole() {
        return this.role;
    }

    public String getId() {
        return this.id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }
}
