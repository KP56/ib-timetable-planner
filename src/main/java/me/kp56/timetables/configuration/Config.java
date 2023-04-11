package me.kp56.timetables.configuration;

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
        valueSettings.put("genetic.environment_timeout", 120);
        valueSettings.put("genetic.fix_tries", 5);

        valueSettings.put("maximum_daily_subjects", 2);
        valueSettings.put("maximum_daily_lessons", 11);

        valueSettings.put("keep_track_of", 5);

        booleanSettings.put("fuzzylogic.enabled", false); //Need to create one more form with a question related to groups of gaps in order for it to be better

        booleanSettings.put("history.connected", true);
        booleanSettings.put("math_aa.connected", false);
        booleanSettings.put("economics.connected", false);
        booleanSettings.put("polish_a.connected", true);
        booleanSettings.put("english_a.connected", false);
        booleanSettings.put("german_b.connected", false);
        booleanSettings.put("spanish_b.connected", false);
        booleanSettings.put("biology.connected", false);
        booleanSettings.put("chemistry.connected", true);
        booleanSettings.put("physics.connected", false);
        booleanSettings.put("computer_science.connected", true);
        booleanSettings.put("french_b.connected", false);
        booleanSettings.put("geography.connected", false);

        booleanSettings.put("history_hl.disabled", false);
        booleanSettings.put("history_sl.disabled", false);
        booleanSettings.put("math_aa_hl.disabled", false);
        booleanSettings.put("math_aa_sl.disabled", false);
        booleanSettings.put("math_ai_sl.disabled", false);
        booleanSettings.put("economics_hl.disabled", false);
        booleanSettings.put("economics_sl.disabled", false);
        booleanSettings.put("polish_a_hl.disabled", false);
        booleanSettings.put("polish_a_sl.disabled", false);
        booleanSettings.put("english_b_hl.disabled", false);
        booleanSettings.put("english_a_hl.disabled", false);
        booleanSettings.put("english_a_sl.disabled", false);
        booleanSettings.put("german_b_hl.disabled", false);
        booleanSettings.put("german_b_sl.disabled", true);
        booleanSettings.put("spanish_b_hl.disabled", false);
        booleanSettings.put("spanish_b_sl.disabled", true);
        booleanSettings.put("biology_hl.disabled", false);
        booleanSettings.put("biology_sl.disabled", false);
        booleanSettings.put("chemistry_hl.disabled", false);
        booleanSettings.put("chemistry_sl.disabled", false);
        booleanSettings.put("physics_hl.disabled", false);
        booleanSettings.put("physics_sl.disabled", true);
        booleanSettings.put("computer_science_hl.disabled", false);
        booleanSettings.put("computer_science_sl.disabled", false);
        booleanSettings.put("french_b_sl.disabled", false);
        booleanSettings.put("french_b_hl.disabled", true);
        booleanSettings.put("geography_sl.disabled", true);
        booleanSettings.put("geography_hl.disabled", false);
        booleanSettings.put("dsd.disabled", false);
        booleanSettings.put("tok.disabled", false);
        booleanSettings.put("z_wych.disabled", false);
        booleanSettings.put("polish_no_examination.disabled", false);
    }

    public Boolean getBoolean(String id) {
        return booleanSettings.get(id);
    }
    public void set(String id, Boolean value) { booleanSettings.put(id, value); }

    public int getInteger(String id) {
        return valueSettings.get(id);
    }
    public void set(String id, Integer value) { valueSettings.put(id, value); }



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
