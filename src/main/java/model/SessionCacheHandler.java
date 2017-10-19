package model;

import exception.NoCachedSessionException;
import model.api.dtos.AuthenticatedUser;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class manages the cached sessions.
 */
public class SessionCacheHandler {

    private static final String FILE_NAME = "data.ser";

    public static void save(AuthenticatedUser authenticatedUser) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(FILE_NAME));
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(authenticatedUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AuthenticatedUser load() {
        try (FileInputStream fileInputStream = new FileInputStream(new File(FILE_NAME));
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            return (AuthenticatedUser) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new NoCachedSessionException();
        } catch (IOException e) {
            throw new NoCachedSessionException();
        }
    }

    public static void clearCache() {
        try {
            Path path = FileSystems.getDefault().getPath(FILE_NAME);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
