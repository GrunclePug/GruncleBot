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
public class Token
{
    //Variables
    private static final String fileName = "token.txt";
    private static String token;

    /**
     * Constructor
     */
    public Token()
    {

    }

    /**
     * Method to read the file
     */
    public static String getToken()
    {
        try
        {
            // create a Buffered Reader object instance with a FileReader
            BufferedReader br;
            br = new BufferedReader(new FileReader(fileName));
            // read the first line from the text file
            token = br.readLine();
            System.out.println("token: " + token);
            // close file stream
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

        return token;
    }
}