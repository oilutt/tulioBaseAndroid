package oilutt.baseproject.app;

import android.app.Application;

import oilutt.baseproject.utils.PreferencesManager;

public class App extends Application {

    private static App instance;
    public static PreferencesManager preferencesManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    public static App getInstance() {
        return instance;
    }

    private void init() {
        initPreferences();
    }

    private void initPreferences() {
        PreferencesManager.initializeInstance(this);
        preferencesManager = PreferencesManager.getInstance();
    }
}
