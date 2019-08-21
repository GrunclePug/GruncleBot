package com.grunclepug.grunclebot.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class creates and parses an Arraylist and displays a file
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

        // handle exceptions
        catch (FileNotFoundException fnf)
        {
            System.out.println("File not found");
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public static String getToken()
    {
        return token;
    }

    public static String getPrefix()
    {
        return prefix;
    }
}