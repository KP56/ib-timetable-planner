package me.kp56.timetables.timetable;

import me.kp56.timetables.configuration.Config;
import me.kp56.timetables.students.Student;

import java.util.ArrayList;
import java.util.List;

public enum Subject {
    HIS_SL(4, Config.getInstance().getInteger("history_sl.groups")),
    HIS_HL(Config.getInstance().getBoolean("history.connected") ? 2 : 6, Config.getInstance().getBoolean("history.connected") ? List.of(HIS_SL) : List.of(), Config.getInstance().getInteger("history_hl.groups")),
    ECO_SL(4, Config.getInstance().getInteger("economics_sl.groups")),
    ECO_HL(Config.getInstance().getBoolean("economics.connected") ? 2 : 6, Config.getInstance().getBoolean("economics.connected") ? List.of(ECO_SL) : List.of(), Config.getInstance().getInteger("economics_hl.groups")),
    POL_N(2, Config.getInstance().getInteger("polish_n.groups")),
    POL_A_SL(4, Config.getInstance().getInteger("polish_a_sl.groups")),
    POL_A_HL(Config.getInstance().getBoolean("polish_a.connected") ? 2 : 6, Config.getInstance().getBoolean("polish_a.connected") ? List.of(POL_A_SL) : List.of(), Config.getInstance().getInteger("polish_a_hl.groups")),
    ENG_A_SL(4, Config.getInstance().getInteger("english_a_sl.groups")),
    ENG_A_HL(Config.getInstance().getBoolean("english_a.connected") ? 2 : 6, Config.getInstance().getBoolean("english_a.connected") ? List.of(ENG_A_SL) : List.of(), Config.getInstance().getInteger("english_a_hl.groups")),
    ENG_B_HL(6, Config.getInstance().getInteger("english_b.groups")),
    GER_B_SL(4, Config.getInstance().getInteger("german_b_sl.groups")),
    GER_B_HL(Config.getInstance().getBoolean("german_b.connected") ? 2 : 6, Config.getInstance().getBoolean("german_b.connected") ? List.of(GER_B_SL) : List.of(), Config.getInstance().getInteger("german_b_hl.groups")),
    SPA_B_SL(4, Config.getInstance().getInteger("spanish_b_sl.groups")),
    SPA_B_HL(Config.getInstance().getBoolean("spanish_b.connected") ? 2 : 6, Config.getInstance().getBoolean("spanish_b.connected") ? List.of(SPA_B_SL) : List.of(), Config.getInstance().getInteger("spanish_b_hl.groups")),
    FRE_B_SL(4, Config.getInstance().getInteger("french_b_sl.groups")),
    FRE_B_HL(Config.getInstance().getBoolean("french_b.connected") ? 2 : 6, Config.getInstance().getBoolean("french_b.connected") ? List.of(FRE_B_SL) : List.of(), Config.getInstance().getInteger("french_b_hl.groups")),
    BIO_SL(4, Config.getInstance().getInteger("biology_sl.groups")),
    BIO_HL(Config.getInstance().getBoolean("biology.connected") ? 2 : 6, Config.getInstance().getBoolean("biology.connected") ? List.of(BIO_SL) : List.of(), Config.getInstance().getInteger("biology_hl.groups")),
    PHY_SL(4, Config.getInstance().getInteger("physics_sl.groups")),
    PHY_HL(Config.getInstance().getBoolean("physics.connected") ? 2 : 6, Config.getInstance().getBoolean("physics.connected") ? List.of(PHY_SL) : List.of(), Config.getInstance().getInteger("physics_hl.groups")),
    CHE_SL(4, Config.getInstance().getInteger("chemistry_sl.groups")),
    CHE_HL(Config.getInstance().getBoolean("chemistry.connected") ? 2 : 6, Config.getInstance().getBoolean("chemistry.connected") ? List.of(CHE_SL) : List.of(), Config.getInstance().getInteger("chemistry_hl.groups")),
    COM_SL(4, Config.getInstance().getInteger("computer_science_sl.groups")),
    COM_HL(Config.getInstance().getBoolean("computer_science.connected") ? 2 : 6, Config.getInstance().getBoolean("computer_science.connected") ? List.of(COM_SL) : List.of(), Config.getInstance().getInteger("computer_science_hl.groups")),
    GEO_SL(4, Config.getInstance().getInteger("geography_sl.groups")),
    GEO_HL(Config.getInstance().getBoolean("geography.connected") ? 2 : 6, Config.getInstance().getBoolean("geography.connected") ? List.of(GEO_SL) : List.of(), Config.getInstance().getInteger("geography_hl.groups")),
    MAT_AA_SL(4, Config.getInstance().getInteger("math_aa_sl.groups")),
    MAT_AA_HL(Config.getInstance().getBoolean("math_aa.connected") ? 2 : 6, Config.getInstance().getBoolean("math_aa.connected") ? List.of(MAT_AA_SL) : List.of(), Config.getInstance().getInteger("math_aa_hl.groups")),
    MAT_AI_SL(4, Config.getInstance().getInteger("math_ai.groups")),
    TOK(2, Config.getInstance().getInteger("tok.groups")),
    Z_WYCH(1, 1),
    DSD(2, 1);

    public final int limit;
    public final int groups;
    public List<Subject> connectedTo;

    Subject(int limit, List<Subject> connectedTo, int groups) {
        this.limit = limit;
        this.connectedTo = connectedTo;
        this.groups = groups;
    }

    Subject(int limit, int groups) {
        this.limit = limit;
        this.groups = groups;
        this.connectedTo = new ArrayList<>();
    }

    public boolean disabled() {
        for (Student s : Student.getStudents()) {
            if (!s.isTeacher) {
                if (s.subjects.contains(this)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return super.toString().replace("_", " ");
    }
}
