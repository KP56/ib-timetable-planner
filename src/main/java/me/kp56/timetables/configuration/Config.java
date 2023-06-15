package me.kp56.timetables.configuration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private static Config config = new Config();

    private Map<String, Boolean> booleanSettings = new HashMap<>();

    public Map<String, Integer> getValueSettings() {
        return valueSettings;
    }

    public void setValueSettings(Map<String, Integer> valueSettings) {
        this.valueSettings = valueSettings;
    }

    private Map<String, Integer> valueSettings = new HashMap<>();

    public Map<String, Boolean> getBooleanSettings() {
        return booleanSettings;
    }

    public void setBooleanSettings(Map<String, Boolean> booleanSettings) {
        this.booleanSettings = booleanSettings;
    }

    private Config() {
        valueSettings.put("genetic.generation_size", 50);
        valueSettings.put("genetic.environments", -1);
        valueSettings.put("genetic.environment_timeout", 30);
        valueSettings.put("genetic.fix_tries", 5);

        valueSettings.put("maximum_daily_subjects", 2);

        valueSettings.put("keep_track_of", 10);

        valueSettings.put("fuzzylogic.rate", 50);

        booleanSettings.put("history.connected", false);
        booleanSettings.put("math_aa.connected", false);
        booleanSettings.put("economics.connected", false);
        booleanSettings.put("polish_a.connected", false);
        booleanSettings.put("english_a.connected", false);
        booleanSettings.put("german_b.connected", false);
        booleanSettings.put("spanish_b.connected", false);
        booleanSettings.put("biology.connected", false);
        booleanSettings.put("chemistry.connected", false);
        booleanSettings.put("physics.connected", false);
        booleanSettings.put("computer_science.connected", false);
        booleanSettings.put("french_b.connected", false);
        booleanSettings.put("geography.connected", false);

        load();
    }

    public Boolean getBoolean(String id) {
        return booleanSettings.get(id);
    }
    public void set(String id, Boolean value) { booleanSettings.put(id, value); }

    public int getInteger(String id) {
        return valueSettings.get(id);
    }
    public void set(String id, Integer value) { valueSettings.put(id, value); }

    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream("settings");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(valueSettings);
            oos.writeObject(booleanSettings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        if (Files.exists(Path.of("settings"))) {
            try {
                FileInputStream fos = new FileInputStream("settings");
                ObjectInputStream oos = new ObjectInputStream(fos);
                valueSettings = (Map<String, Integer>) oos.readObject();
                booleanSettings = (Map<String, Boolean>) oos.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isInteger(String id) {
        if (valueSettings.containsKey(id)) {
            return true;
        } else {
            return false;
        }
    }

    public  boolean isBoolean(String id) {
        if (booleanSettings.containsKey(id)) {
            return true;
        } else {
            return false;
        }
    }

    public static Config getInstance() {
        return config;
    }
}
