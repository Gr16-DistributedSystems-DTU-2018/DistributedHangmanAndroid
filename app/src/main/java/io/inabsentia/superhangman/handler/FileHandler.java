package io.inabsentia.superhangman.handler;


public class FileHandler {

    private static FileHandler instance;

    private FileHandler() {

    }

    static {
        try {
            instance = new FileHandler();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized FileHandler getInstance() {
        return instance;
    }

}