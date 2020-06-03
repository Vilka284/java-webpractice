package com.andrii.cruder;

import com.andrii.user.User;

import java.io.*;
import java.util.HashSet;


public class CruderIO implements Cruder {

    private HashSet<User> userData;
    private String usersFile;

    public CruderIO(String usersFile) throws IOException {

        // create file if not exist
        File newFile = new File(usersFile);
        newFile.createNewFile();
        this.usersFile = usersFile;
        userData = new HashSet<>();
    }

    @Override
    public HashSet<User> readUsers() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(usersFile));
            userData.clear();
            while (true) {
                User u = (User) ois.readObject();
                System.out.println(u.getName());
                userData.add(u);
            }
        } catch (EOFException eof) {
            System.out.println("Reached end of file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeStream(ois);
        }
        return userData;
    }

    @Override
    public boolean writeUsers(User u) {
        userData = readUsers();
        userData.add(u);
        return writeLines();
    }


    @Override
    public boolean deleteUsers(User u) {
        userData.remove(u);
        return writeLines();
    }


    @Override
    public boolean updateUsers(User u, User newU) {
        userData.remove(u);
        userData.add(newU);
        return writeLines();
    }

    public User findUserByName(String name) {
        userData = readUsers();
        for (User u :
                userData) {
            if (u.getName().equals(name)) {
                return u;
            }
        }
        return null;
    }

    private boolean writeLines() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(usersFile));
            for (User us :
                    userData) {
                oos.writeObject(us);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            closeStream(oos);
        }
        return true;
    }

    private void closeStream(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





