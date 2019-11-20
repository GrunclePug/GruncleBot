package com.grunclepug.grunclebot.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileInteraction
{
    private FileInteraction()
    {
    }

    /**
     * Method to read the file
     * @param fileName the name of the input file
     */
    public static ArrayList<Person> readFile(String fileName)
    {
        ArrayList<Person> people = new ArrayList<>();

        try
        {
            BufferedReader br;
            if((fileName!= null) && (fileName.length() > 0))
            {
                br = new BufferedReader(new FileReader(fileName));
                String fileRead = br.readLine();

                //Loop until all lines are read
                while (fileRead != null)
                {
                    String[] tokenize = fileRead.split("\\|");

                    if(tokenize.length == 3)
                    {
                        String tempUserID = "";
                        boolean tempAfk = false;
                        String tempContent = "";

                        if(tokenize[0] != null && tokenize[0].trim().length() > 0)
                        {
                            tempUserID = tokenize[0];
                        }
                        if(tokenize[0] != null && tokenize[0].trim().length() > 0)
                        {
                            tempAfk = Boolean.parseBoolean(tokenize[1]);
                        }
                        if(tokenize[0] != null && tokenize[0].trim().length() > 0)
                        {
                            tempContent = tokenize[2];
                        }

                        people.add(new Person(tempUserID, tempAfk, tempContent));
                    }
                    //Read next line before looping
                    fileRead = br.readLine();
                }
                //Close file stream
                br.close();
            }
            else
            {
                System.out.println("Please enter a valid file name.");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
        }
        return people;
    }

    public static void updateFile(String fileName, String userID, boolean afk, String content)
    {
        ArrayList<Person> people = readFile(fileName);
        ArrayList<String> formattedPeople = new ArrayList<>();
        ArrayList<String> userIDs = new ArrayList<>();
        String newContent = "";

        for(Person p : people)
        {
            userIDs.add(p.getId());
        }

        if(userIDs.contains(userID))
        {
            for(int i = 0; i < people.size(); i++)
            {
                if(people.get(i).getId().equals(userID))
                {
                    people.get(i).setAfk(afk).setContent(content);
                }
                if(people.get(i).isAfk())
                {
                    formattedPeople.add(people.get(i).getId() + "|" + people.get(i).isAfk() + "|" + people.get(i).getContent() + "\n");
                    newContent += formattedPeople.get(formattedPeople.size() - 1);
                }
            }
        }
        else
        {
            if(people.size() > 0)
            {
                for(int i = 0; i < people.size(); i++)
                {
                    formattedPeople.add(i, people.get(i).getId() + "|" + people.get(i).isAfk() + "|" + people.get(i).getContent() + "\n");
                    newContent += formattedPeople.get(i);
                }
            }
            formattedPeople.add(userID + "|" + afk + "|" + content + "\n");
            newContent += formattedPeople.get(formattedPeople.size() - 1);
        }

        try
        {
            FileWriter fw = new FileWriter(new File(fileName), false);
            fw.write(newContent);
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
        }
    }
}
