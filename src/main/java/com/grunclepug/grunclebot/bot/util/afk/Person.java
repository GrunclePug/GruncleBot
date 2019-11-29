package com.grunclepug.grunclebot.bot.util.afk;

public class Person
{
    private final String id;
    private boolean afk;
    private String content = "no reason provided";

    public Person(String id)
    {
        this.id = id;
    }

    public Person(String id, boolean afk, String content)
    {
        this(id);
        this.afk = afk;
        this.content = content.trim();
    }

    public String getId()
    {
        return this.id;
    }

    public Person setAfk(boolean afk)
    {
        this.afk = afk;
        return this;
    }

    public boolean isAfk()
    {
        return this.afk;
    }

    public Person setContent(String content)
    {
        if(content != null && content.trim().length() > 0)
        {
            this.content = content;
        }
        else
        {
            this.content = "no reason provided";
        }
        return this;
    }

    public String getContent()
    {
        return this.content;
    }
}
