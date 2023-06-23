package me.kp56.timetables.timetable;

import me.kp56.timetables.configuration.Config;
import me.kp56.timetables.students.Student;

import java.util.ArrayList;
import java.util.List;

public enum Subject {
    HIS_SL(4),
    HIS_HL(Config.getInstance().getBoolean("history.connected") ? 2 : 6, Config.getInstance().getBoolean("history.connected") ? List.of(HIS_SL) : List.of()),
    ECO_SL(4),
    ECO_HL(Config.getInstance().getBoolean("economics.connected") ? 2 : 6, Config.getInstance().getBoolean("economics.connected") ? List.of(ECO_SL) : List.of()),
    POL_N(2),
    POL_A_SL(4),
    POL_A_HL(Config.getInstance().getBoolean("polish_a.connected") ? 2 : 6, Config.getInstance().getBoolean("polish_a.connected") ? List.of(POL_A_SL) : List.of()),
    ENG_A_SL(4),
    ENG_A_HL(Config.getInstance().getBoolean("english_a.connected") ? 2 : 6, Config.getInstance().getBoolean("english_a.connected") ? List.of(ENG_A_SL) : List.of()),
    ENG_B_HL(6),
    GER_B_SL(4),
    GER_B_HL(Config.getInstance().getBoolean("german_b.connected") ? 2 : 6, Config.getInstance().getBoolean("german_b.connected") ? List.of(GER_B_SL) : List.of()),
    SPA_B_SL(4),
    SPA_B_HL(Config.getInstance().getBoolean("spanish_b.connected") ? 2 : 6, Config.getInstance().getBoolean("spanish_b.connected") ? List.of(SPA_B_SL) : List.of()),
    FRE_B_SL(4),
    FRE_B_HL(Config.getInstance().getBoolean("french_b.connected") ? 2 : 6, Config.getInstance().getBoolean("french_b.connected") ? List.of(FRE_B_SL) : List.of()),
    BIO_SL(4),
    BIO_HL(Config.getInstance().getBoolean("biology.connected") ? 2 : 6, Config.getInstance().getBoolean("biology.connected") ? List.of(BIO_SL) : List.of()),
    PHY_SL(4),
    PHY_HL(Config.getInstance().getBoolean("physics.connected") ? 2 : 6, Config.getInstance().getBoolean("physics.connected") ? List.of(PHY_SL) : List.of()),
    CHE_SL(4),
    CHE_HL(Config.getInstance().getBoolean("chemistry.connected") ? 2 : 6, Config.getInstance().getBoolean("chemistry.connected") ? List.of(CHE_SL) : List.of()),
    COM_SL(4),
    COM_HL(Config.getInstance().getBoolean("computer_science.connected") ? 2 : 6, Config.getInstance().getBoolean("computer_science.connected") ? List.of(COM_SL) : List.of()),
    GEO_SL(4),
    GEO_HL(Config.getInstance().getBoolean("geography.connected") ? 2 : 6, Config.getInstance().getBoolean("geography.connected") ? List.of(GEO_SL) : List.of()),
    MAT_AA_SL(4),
    MAT_AA_HL(Config.getInstance().getBoolean("math_aa.connected") ? 2 : 6, Config.getInstance().getBoolean("math_aa.connected") ? List.of(MAT_AA_SL) : List.of()),
    MAT_AI_SL(4),
    TOK(2),
    HIS_SL_2(4),
    HIS_HL_2(Config.getInstance().getBoolean("history.connected") ? 2 : 6, Config.getInstance().getBoolean("history.connected") ? List.of(HIS_SL) : List.of()),
    ECO_SL_2(4),
    ECO_HL_2(Config.getInstance().getBoolean("economics.connected") ? 2 : 6, Config.getInstance().getBoolean("economics.connected") ? List.of(ECO_SL) : List.of()),
    POL_N_2(2),
    POL_A_SL_2(4),
    POL_A_HL_2(Config.getInstance().getBoolean("polish_a.connected") ? 2 : 6, Config.getInstance().getBoolean("polish_a.connected") ? List.of(POL_A_SL) : List.of()),
    ENG_A_SL_2(4),
    ENG_A_HL_2(Config.getInstance().getBoolean("english_a.connected") ? 2 : 6, Config.getInstance().getBoolean("english_a.connected") ? List.of(ENG_A_SL) : List.of()),
    ENG_B_HL_2(6),
    GER_B_SL_2(4),
    GER_B_HL_2(Config.getInstance().getBoolean("german_b.connected") ? 2 : 6, Config.getInstance().getBoolean("german_b.connected") ? List.of(GER_B_SL) : List.of()),
    SPA_B_SL_2(4),
    SPA_B_HL_2(Config.getInstance().getBoolean("spanish_b.connected") ? 2 : 6, Config.getInstance().getBoolean("spanish_b.connected") ? List.of(SPA_B_SL) : List.of()),
    FRE_B_SL_2(4),
    FRE_B_HL_2(Config.getInstance().getBoolean("french_b.connected") ? 2 : 6, Config.getInstance().getBoolean("french_b.connected") ? List.of(FRE_B_SL) : List.of()),
    BIO_SL_2(4),
    BIO_HL_2(Config.getInstance().getBoolean("biology.connected") ? 2 : 6, Config.getInstance().getBoolean("biology.connected") ? List.of(BIO_SL) : List.of()),
    PHY_SL_2(4),
    PHY_HL_2(Config.getInstance().getBoolean("physics.connected") ? 2 : 6, Config.getInstance().getBoolean("physics.connected") ? List.of(PHY_SL) : List.of()),
    CHE_SL_2(4),
    CHE_HL_2(Config.getInstance().getBoolean("chemistry.connected") ? 2 : 6, Config.getInstance().getBoolean("chemistry.connected") ? List.of(CHE_SL) : List.of()),
    COM_SL_2(4),
    COM_HL_2(Config.getInstance().getBoolean("computer_science.connected") ? 2 : 6, Config.getInstance().getBoolean("computer_science.connected") ? List.of(COM_SL) : List.of()),
    GEO_SL_2(4),
    GEO_HL_2(Config.getInstance().getBoolean("geography.connected") ? 2 : 6, Config.getInstance().getBoolean("geography.connected") ? List.of(GEO_SL) : List.of()),
    MAT_AA_SL_2(4),
    MAT_AA_HL_2(Config.getInstance().getBoolean("math_aa.connected") ? 2 : 6, Config.getInstance().getBoolean("math_aa.connected") ? List.of(MAT_AA_SL) : List.of()),
    MAT_AI_SL_2(4),
    TOK_2(2),
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

    public boolean disabled() {
        for (Student s : Student.students) {
            if (s.subjects.contains(this)) {
                return false;
            }
        }

        return true;
    }

    public String toSimplifiedString() {
        return super.toString().replace("_", " ");
    }

    @Override
    public String toString() {
        String s = super.toString();
        boolean existsSecond = false;
        try {
            Subject.valueOf(s + "_2");
            existsSecond = true;
        } catch (IllegalArgumentException ignored) {

        }

        if (s.contains("2")) {
            s = s.substring(0, s.length() - 1) + "GR_2";
        } else if (existsSecond) {
            s += "_GR_1";
        }

        return s.replace("_", " ");
    }
}
