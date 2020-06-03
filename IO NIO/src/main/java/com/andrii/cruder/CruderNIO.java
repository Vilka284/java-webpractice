package com.andrii.cruder;

import com.andrii.user.User;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashSet;


public class CruderNIO implements Cruder {

    private HashSet<User> userData;
    private String usersFile;

    public CruderNIO(String usersFile) throws IOException {

        // create file if not exist
        File newFile = new File(usersFile);
        newFile.createNewFile();
        this.usersFile = usersFile;
        userData = new HashSet<>();
    }


    @Override
    public HashSet<User> readUsers() {

        RandomAccessFile serFile = null;
        FileChannel fileChannel = null;
        byte[] bytes = null;

        try {
            serFile = new RandomAccessFile(usersFile, "rw");
            fileChannel = serFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) serFile.length());
            FileLock fileLock = fileChannel.lock();
            fileChannel.read(buffer);
            fileLock.release();
            bytes = buffer.array();
            buffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(fileChannel);
            closeStream(serFile);
        }

        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;

        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            userData.clear();
            while (true) {
                User u = (User) ois.readObject();
                userData.add(u);
            }
        } catch (EOFException eof) {
            System.out.println("Reached end of file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeStream(ois);
            closeStream(bis);
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
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(bos);
            for (User us :
                    userData) {
                oos.writeObject(us);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeStream(oos);
        }

        RandomAccessFile serFile = null;
        FileChannel fileChannel = null;

        try {
            byte[] source = bos.toByteArray();
            serFile = new RandomAccessFile(usersFile, "rw");
            fileChannel = serFile.getChannel();
            ByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, source.length);
            FileLock fileLock = fileChannel.lock();
            buffer.put(source);
            fileLock.release();
            buffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeStream(bos);
            closeStream(fileChannel);
            closeStream(serFile);
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
