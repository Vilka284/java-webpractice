package com.andrii.writer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;

public class WriterNIO implements Writer {

    private Path path;

    WriterNIO(String usersFile) {

        this.path = Paths.get(usersFile);
    }

    @Override
    public HashSet<String> readUsers() {
        try {
            return new HashSet<>(Files.readAllLines(path));
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return new HashSet<>();
    }

    @Override
    public boolean writeUsers(String[] newUser) {
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
            Files.write(path, userData, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
