package me.kp56.timetables.configuration;

import java.util.HashMap;
import java.util.Map;

public class Config {
    private static Config config = new Config();

    private  Map<String, Boolean> onOffSettings = new HashMap<>();
    private Map<String, Integer> valueSettings = new HashMap<>();

    private Config() {
        //just for now
        valueSettings.put("genetic.generation_size", 500);
        valueSettings.put("genetic.environments", -1);
        valueSettings.put("genetic.environment_timeout", 120);
        valueSettings.put("genetic.fix_tries", 5);

        valueSettings.put("maximum_daily_subjects", 2);
        valueSettings.put("maximum_daily_lessons", 11);

        valueSettings.put("keep_track_of", 5);

        onOffSettings.put("history.connected", true);
        onOffSettings.put("math_aa.connected", false);
        onOffSettings.put("economics.connected", false);
        onOffSettings.put("polish_a.connected", true);
        onOffSettings.put("english_a.connected", false);
        onOffSettings.put("german_b.connected", false);
        onOffSettings.put("spanish_b.connected", false);
        onOffSettings.put("biology.connected", false);
        onOffSettings.put("chemistry.connected", true);
        onOffSettings.put("physics.connected", false);
        onOffSettings.put("computer_science.connected", true);
        onOffSettings.put("french_b.connected", false);
        onOffSettings.put("geography.connected", false);

        onOffSettings.put("history_hl.disabled", false);
        onOffSettings.put("history_sl.disabled", false);
        onOffSettings.put("math_aa_hl.disabled", false);
        onOffSettings.put("math_aa_sl.disabled", false);
        onOffSettings.put("math_ai_sl.disabled", false);
        onOffSettings.put("economics_hl.disabled", false);
        onOffSettings.put("economics_sl.disabled", false);
        onOffSettings.put("polish_a_hl.disabled", false);
        onOffSettings.put("polish_a_sl.disabled", false);
        onOffSettings.put("english_b_hl.disabled", false);
        onOffSettings.put("english_a_hl.disabled", false);
        onOffSettings.put("english_a_sl.disabled", false);
        onOffSettings.put("german_b_hl.disabled", false);
        onOffSettings.put("german_b_sl.disabled", true);
        onOffSettings.put("spanish_b_hl.disabled", false);
        onOffSettings.put("spanish_b_sl.disabled", true);
        onOffSettings.put("biology_hl.disabled", false);
        onOffSettings.put("biology_sl.disabled", false);
        onOffSettings.put("chemistry_hl.disabled", false);
        onOffSettings.put("chemistry_sl.disabled", false);
        onOffSettings.put("physics_hl.disabled", false);
        onOffSettings.put("physics_sl.disabled", true);
        onOffSettings.put("computer_science_hl.disabled", false);
        onOffSettings.put("computer_science_sl.disabled", false);
        onOffSettings.put("french_b_sl.disabled", false);
        onOffSettings.put("french_b_hl.disabled", true);
        onOffSettings.put("geography_sl.disabled", true);
        onOffSettings.put("geography_hl.disabled", false);
        onOffSettings.put("dsd.disabled", false);
        onOffSettings.put("tok.disabled", false);
        onOffSettings.put("z_wych.disabled", false);
        onOffSettings.put("polish_no_examination.disabled", false);
    }

    public Boolean getBoolean(String id) {
        return onOffSettings.get(id);
    }

    public int getInteger(String id) {
        return valueSettings.get(id);
    }

    public static Config getInstance() {
        return config;
    }
}
