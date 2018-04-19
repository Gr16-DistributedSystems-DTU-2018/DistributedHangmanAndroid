package io.inabsentia.superhangman.singleton;

import android.annotation.SuppressLint;
import android.app.Application;

@SuppressLint("Registered")
public class App extends Application {

    private String username;
    private String password;

    private static App instance;

    static {
        try {
            instance = new App();
        } catch (Exception e) {
            throw new RuntimeException("Failed to init App Singleton!");
        }
    }

    private App() {

    }

    public static synchronized App getInstance() {
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}