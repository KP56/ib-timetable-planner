package me.kp56.timetables.timetable;

import me.kp56.timetables.configuration.Config;

import java.util.ArrayList;
import java.util.List;

public enum Subject {
    HISTORY_SL(4),
    HISTORY_HL(Config.getInstance().getBoolean("history.connected") ? 2 : 6, Config.getInstance().getBoolean("history.connected") ? List.of(HISTORY_SL) : List.of()),
    ECONOMICS_SL(4),
    ECONOMICS_HL(Config.getInstance().getBoolean("economics.connected") ? 2 : 6, Config.getInstance().getBoolean("economics.connected") ? List.of(ECONOMICS_SL) : List.of()),
    POLISH_NO_EXAMINATION(2),
    POLISH_A_SL(4),
    POLISH_A_HL(Config.getInstance().getBoolean("polish_a.connected") ? 2 : 6, Config.getInstance().getBoolean("polish_a.connected") ? List.of(POLISH_A_SL) : List.of()),
    ENGLISH_A_SL(4),
    ENGLISH_A_HL(Config.getInstance().getBoolean("english_a.connected") ? 2 : 6, Config.getInstance().getBoolean("english_a.connected") ? List.of(ENGLISH_A_SL) : List.of()),
    ENGLISH_B_HL(6),
    GERMAN_B_SL(4),
    GERMAN_B_HL(Config.getInstance().getBoolean("german_b.connected") ? 2 : 6, Config.getInstance().getBoolean("german_b.connected") ? List.of(GERMAN_B_SL) : List.of()),
    SPANISH_B_SL(4),
    SPANISH_B_HL(Config.getInstance().getBoolean("spanish_b.connected") ? 2 : 6, Config.getInstance().getBoolean("spanish_b.connected") ? List.of(SPANISH_B_SL) : List.of()),
    FRENCH_B_SL(4),
    FRENCH_B_HL(Config.getInstance().getBoolean("french_b.connected") ? 2 : 6, Config.getInstance().getBoolean("french_b.connected") ? List.of(FRENCH_B_SL) : List.of()),
    BIOLOGY_SL(4),
    BIOLOGY_HL(Config.getInstance().getBoolean("biology.connected") ? 2 : 6, Config.getInstance().getBoolean("biology.connected") ? List.of(BIOLOGY_SL) : List.of()),
    PHYSICS_SL(4),
    PHYSICS_HL(Config.getInstance().getBoolean("physics.connected") ? 2 : 6, Config.getInstance().getBoolean("physics.connected") ? List.of(PHYSICS_SL) : List.of()),
    CHEMISTRY_SL(4),
    CHEMISTRY_HL(Config.getInstance().getBoolean("chemistry.connected") ? 2 : 6, Config.getInstance().getBoolean("chemistry.connected") ? List.of(CHEMISTRY_SL) : List.of()),
    COMPUTER_SCIENCE_SL(4),
    COMPUTER_SCIENCE_HL(Config.getInstance().getBoolean("computer_science.connected") ? 2 : 6, Config.getInstance().getBoolean("computer_science.connected") ? List.of(COMPUTER_SCIENCE_SL) : List.of()),
    GEOGRAPHY_SL(4),
    GEOGRAPHY_HL(Config.getInstance().getBoolean("geography.connected") ? 2 : 6, Config.getInstance().getBoolean("geography.connected") ? List.of(GEOGRAPHY_SL) : List.of()),
    MATH_AA_SL(4),
    MATH_AA_HL(Config.getInstance().getBoolean("math_aa.connected") ? 2 : 6, Config.getInstance().getBoolean("math_aa.connected") ? List.of(MATH_AA_SL) : List.of()),
    MATH_AI_SL(4),
    TOK(2),
    Z_WYCH(1),
    DSD(2);

    public final int limit;
    public List<Subject> connectedTo;

    Subject(int limit, List<Subject> connectedTo) {
        this.limit = limit;
        this.connectedTo = connectedTo;
    }

    Subject(int limit) {
        this.limit = limit;
        this.connectedTo = new ArrayList<>();
    }
}
