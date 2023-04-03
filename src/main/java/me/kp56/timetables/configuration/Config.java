package me.kp56.timetables.configuration;

import java.util.HashMap;
import java.util.Map;

public class Config {
    private static Config config = new Config();

    private Map<String, Object> contents = new HashMap<>();

    private Config() {
        //just for now
        contents.put("genetic.generation_size", 500);
        contents.put("genetic.environments", -1);
        contents.put("genetic.environment_timeout", 120);
        contents.put("genetic.fix_tries", 5);

        contents.put("maximum_daily_subjects", 2);
        contents.put("maximum_daily_lessons", 11);

        contents.put("keep_track_of", 5);

        contents.put("history.connected", true);
        contents.put("math_aa.connected", false);
        contents.put("economics.connected", false);
        contents.put("polish_a.connected", true);
        contents.put("english_a.connected", false);
        contents.put("german_b.connected", false);
        contents.put("spanish_b.connected", false);
        contents.put("biology.connected", false);
        contents.put("chemistry.connected", true);
        contents.put("physics.connected", false);
        contents.put("computer_science.connected", true);
        contents.put("french_b.connected", false);
        contents.put("geography.connected", false);

        contents.put("history_hl.disabled", false);
        contents.put("history_sl.disabled", false);
        contents.put("math_aa_hl.disabled", false);
        contents.put("math_aa_sl.disabled", false);
        contents.put("math_ai_sl.disabled", false);
        contents.put("economics_hl.disabled", false);
        contents.put("economics_sl.disabled", false);
        contents.put("polish_a_hl.disabled", false);
        contents.put("polish_a_sl.disabled", false);
        contents.put("english_b_hl.disabled", false);
        contents.put("english_a_hl.disabled", false);
        contents.put("english_a_sl.disabled", false);
        contents.put("german_b_hl.disabled", false);
        contents.put("german_b_sl.disabled", true);
        contents.put("spanish_b_hl.disabled", false);
        contents.put("spanish_b_sl.disabled", true);
        contents.put("biology_hl.disabled", false);
        contents.put("biology_sl.disabled", false);
        contents.put("chemistry_hl.disabled", false);
        contents.put("chemistry_sl.disabled", false);
        contents.put("physics_hl.disabled", false);
        contents.put("physics_sl.disabled", true);
        contents.put("computer_science_hl.disabled", false);
        contents.put("computer_science_sl.disabled", false);
        contents.put("french_b_sl.disabled", false);
        contents.put("french_b_hl.disabled", true);
        contents.put("geography_sl.disabled", true);
        contents.put("geography_hl.disabled", false);
        contents.put("dsd.disabled", false);
        contents.put("tok.disabled", false);
        contents.put("z_wych.disabled", false);
        contents.put("polish_no_examination.disabled", false);
    }

    public Object getToken(String id) {
        return contents.get(id);
    }

    public String getString(String id) {
        return (String) getToken(id);
    }

    public boolean getBoolean(String id) {
        return (Boolean) getToken(id);
    }

    public int getInteger(String id) {
        return (Integer) getToken(id);
    }

    public double getDouble(String id) {
        return (Double) getToken(id);
    }

    public static Config getInstance() {
        return config;
    }
}
