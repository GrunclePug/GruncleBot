package com.grunclepug.grunclebot.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Config Class to read the config.txt
 * @author  BCIT_summercamp_2019
 * @version Stage 4
 */
public class Config
{
    //Variables
    private static final String fileName = "config.txt";
    public static String token;
    public static String prefix;

    /**
     * Constructor
     */
    public Config()
    {
        readFile();
    }

    /**
     * Method to read the file
     */
    public static void readFile()
    {
        try
        {
            BufferedReader br;
            br = new BufferedReader(new FileReader(fileName));
            token = br.readLine().substring(6);
            prefix = br.readLine().substring(7);
            br.close();
        }

        //Handle Exceptions
        catch (FileNotFoundException fnf)
        {
            System.out.println("File not found");
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    /**
     * Accessor for token
     * @return The bot token
     */
    public static String getToken()
    {
        return token;
    }

    /**
     * Accessor for prefix
     * @return The bot prefix
     */
    public static String getPrefix()
    {
        return prefix;
    }
}