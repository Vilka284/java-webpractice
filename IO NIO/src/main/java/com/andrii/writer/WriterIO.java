package com.andrii.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class WriterIO implements Writer {

    private File usersFile;

    public WriterIO(String usersFile) throws IOException {

        this.usersFile = new File(usersFile);
        this.usersFile.createNewFile();
    }

    @Override
    public HashSet<String> readUsers() {

        /*
            Using hashset to avoid duplicates
         */
        HashSet<String> userData = new HashSet<>();
        try {
            Scanner reader = new Scanner(usersFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                userData.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return userData;
    }

    @Override
    public boolean writeUsers(String[] newUser) {

        /*
            replaceAll regex remove redundant brackets after toString cast
         */
        HashSet<String> userData = new HashSet<>();
        userData.add(Arrays.toString(newUser).
                replaceAll("[\\[\\](){}]", "").
                replaceAll(" ", ""));

        userData.addAll(readUsers());
        return writeToFile(userData);
    }

    @Override
    public boolean deleteUsers(String deleteParam) {

        HashSet<String> userData;
        userData = readUsers();
        for (String st :
                userData) {
            if (st.toLowerCase().contains(deleteParam.toLowerCase())) {
                userData.remove(st);
                return writeToFile(userData);
            }
        }
        return false;
    }

    @Override
    public boolean updateUsers(String data, String[] newUser) {

        HashSet<String> userData;
        userData = readUsers();
        String[] occasions = (String[]) userData.stream().
                                filter(data::contains).
                                toArray();
        for (String st :
                userData) {
            if (st.toLowerCase().contains(occasions[0].toLowerCase())) {
                userData.remove(st);
                userData.add(Arrays.toString(newUser).replaceAll("[\\[\\](){}]", ""));
                return writeToFile(userData);
            }
        }
        return false;
    }


    private boolean writeToFile(HashSet<String> userData) {

        try {
            FileWriter writer = new FileWriter(usersFile);
            for (String st :
                    userData) {
                writer.write(st + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

