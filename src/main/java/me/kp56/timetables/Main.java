package me.kp56.timetables;

import me.kp56.timetables.timetable.Student;
import me.kp56.timetables.timetable.Subject;
import me.kp56.timetables.ui.UI;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Student.students.add(new Student("Avcioglu Aylin", List.of(Subject.MAT_AA_SL, Subject.ENG_A_HL, Subject.ECO_SL, Subject.BIO_HL, Subject.CHE_HL, Subject.POL_N, Subject.DSD, Subject.FRE_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Bartkowska Aleksandra", List.of(Subject.GEO_HL, Subject.ENG_A_HL, Subject.ECO_HL, Subject.CHE_SL, Subject.MAT_AI_SL, Subject.POL_N, Subject.DSD, Subject.FRE_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Bryl Zofia", List.of(Subject.MAT_AA_SL, Subject.POL_A_SL, Subject.ENG_B_HL, Subject.SPA_B_HL, Subject.BIO_HL, Subject.CHE_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Chmielewski Oskar", List.of(Subject.MAT_AA_HL, Subject.PHY_HL, Subject.POL_A_SL, Subject.ENG_B_HL, Subject.ECO_SL, Subject.CHE_SL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Dolata Antoni", List.of(Subject.MAT_AA_HL, Subject.POL_A_SL, Subject.ENG_B_HL, Subject.ECO_HL, Subject.GER_B_HL, Subject.CHE_SL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Drgas Kalina", List.of(Subject.POL_A_SL, Subject.ENG_B_HL, Subject.BIO_HL, Subject.CHE_HL, Subject.MAT_AI_SL, Subject.DSD, Subject.FRE_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Flicińska Zuzanna", List.of(Subject.MAT_AA_SL, Subject.POL_A_HL, Subject.ENG_A_HL, Subject.SPA_B_HL, Subject.BIO_SL, Subject.HIS_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Fliegner Szymon", List.of(Subject.MAT_AA_HL, Subject.PHY_HL, Subject.ENG_A_SL, Subject.ECO_SL, Subject.GER_B_HL, Subject.CHE_HL, Subject.POL_N, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Grzesiak Kornelia", List.of(Subject.MAT_AA_SL, Subject.ENG_A_HL, Subject.ECO_SL, Subject.GER_B_HL, Subject.BIO_HL, Subject.CHE_HL, Subject.POL_N, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Grzeszczyk Maja", List.of(Subject.MAT_AA_SL, Subject.ENG_A_SL, Subject.ECO_SL, Subject.GER_B_HL, Subject.BIO_HL, Subject.CHE_HL, Subject.POL_N, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Jeleń Zuzanna", List.of(Subject.MAT_AA_SL, Subject.POL_A_SL, Subject.ENG_A_HL, Subject.SPA_B_HL, Subject.BIO_HL, Subject.CHE_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Kasperkiewicz Marianna", List.of(Subject.POL_A_SL, Subject.ENG_A_HL, Subject.BIO_HL, Subject.CHE_HL, Subject.MAT_AI_SL, Subject.DSD, Subject.FRE_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Kraiz Malek", List.of(Subject.MAT_AA_SL, Subject.POL_A_SL, Subject.ENG_B_HL, Subject.ECO_SL, Subject.BIO_HL, Subject.CHE_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Kubaszewska Franciszka", List.of(Subject.MAT_AA_SL, Subject.POL_A_SL, Subject.ENG_A_HL, Subject.SPA_B_HL, Subject.BIO_HL, Subject.CHE_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Kubecki Mikołaj", List.of(Subject.MAT_AA_SL, Subject.POL_A_SL, Subject.ENG_A_SL, Subject.ECO_HL, Subject.BIO_HL, Subject.CHE_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Kusiak Marta", List.of(Subject.MAT_AA_HL, Subject.POL_A_SL, Subject.ENG_B_HL, Subject.ECO_SL, Subject.GER_B_HL, Subject.CHE_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Lisek Karina", List.of(Subject.MAT_AA_SL, Subject.PHY_HL, Subject.ENG_A_HL, Subject.COM_HL, Subject.HIS_HL, Subject.POL_N, Subject.DSD, Subject.FRE_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Marek Adam", List.of(Subject.MAT_AA_HL, Subject.POL_A_SL, Subject.ENG_B_HL, Subject.SPA_B_HL, Subject.ECO_SL, Subject.COM_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Matuszczak Maciej", List.of(Subject.MAT_AA_HL, Subject.ENG_A_SL, Subject.ECO_SL, Subject.GER_B_HL, Subject.BIO_HL, Subject.CHE_HL, Subject.POL_N, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Michalczuk Emilia", List.of(Subject.POL_A_SL, Subject.ENG_A_HL, Subject.SPA_B_HL, Subject.BIO_SL, Subject.HIS_HL, Subject.MAT_AI_SL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Paszkowska Zuzanna", List.of(Subject.ENG_A_HL, Subject.SPA_B_HL, Subject.ECO_SL, Subject.GER_B_HL, Subject.BIO_SL, Subject.MAT_AI_SL, Subject.POL_N, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Piotrowska Milena", List.of(Subject.POL_A_SL, Subject.ENG_A_HL, Subject.ECO_HL, Subject.COM_SL, Subject.HIS_HL, Subject.MAT_AI_SL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Raźnikiewicz Dawid", List.of(Subject.MAT_AA_SL, Subject.ENG_A_HL, Subject.ECO_HL, Subject.COM_SL, Subject.GER_B_HL, Subject.HIS_HL, Subject.POL_N, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Reshetnyk Diana", List.of(Subject.MAT_AA_SL, Subject.POL_A_SL, Subject.GEO_HL, Subject.ENG_B_HL, Subject.ECO_HL, Subject.COM_SL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Sadowczyk Milena", List.of(Subject.GEO_HL, Subject.ENG_A_HL, Subject.BIO_HL, Subject.CHE_HL, Subject.MAT_AI_SL, Subject.POL_N, Subject.DSD, Subject.FRE_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Seite-Jastrzębska Adrianna", List.of(Subject.POL_A_HL, Subject.ENG_A_HL, Subject.GER_B_HL, Subject.BIO_SL, Subject.HIS_HL, Subject.MAT_AI_SL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Verschelden Julia", List.of(Subject.MAT_AA_SL, Subject.PHY_HL, Subject.ENG_A_HL, Subject.COM_SL, Subject.HIS_HL, Subject.POL_N, Subject.DSD, Subject.FRE_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Ziółkowski Hubert", List.of(Subject.MAT_AA_SL, Subject.PHY_HL, Subject.POL_A_SL, Subject.ENG_B_HL, Subject.BIO_HL, Subject.CHE_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));

        /*Student.students.add(new Student("Adamczak Aleksandra",List.of(Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_SL,Subject.BIOLOGY_HL,Subject.CHEMISTRY_HL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Andruszkiewicz Patrycja",List.of(Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_SL,Subject.GERMAN_B_HL,Subject.CHEMISTRY_HL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Balcerkiewicz Tomasz",List.of(Subject.MATH_AA_SL,Subject.PHYSICS_HL,Subject.POLISH_A_HL,Subject.ENGLISH_A_HL,Subject.HISTORY_HL,Subject.CHEMISTRY_HL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Baran Zuzanna",List.of(Subject.MATH_AA_SL,Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_SL,Subject.BIOLOGY_HL,Subject.CHEMISTRY_HL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Baworowska Maja",List.of(Subject.POLISH_A_SL,Subject.GEOGRAPHY_SL,Subject.ENGLISH_B_HL,Subject.BIOLOGY_HL,Subject.MATH_AI_SL,Subject.FRENCH_B_HL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Bober Bartłomiej",List.of(Subject.MATH_AA_SL,Subject.POLISH_A_HL,Subject.ENGLISH_A_HL,Subject.ECONOMICS_SL,Subject.COMPUTER_SCIENCE_SL,Subject.CHEMISTRY_HL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Buchwald Jakub",List.of(Subject.POLISH_A_SL,Subject.ENGLISH_A_HL,Subject.ECONOMICS_HL,Subject.COMPUTER_SCIENCE_SL,Subject.GERMAN_B_HL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Gierlik Wiktoria",List.of(Subject.MATH_AA_SL,Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_HL,Subject.GERMAN_B_HL,Subject.BIOLOGY_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Hoch Helena Maria",List.of(Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.SPANISH_B_HL,Subject.BIOLOGY_HL,Subject.CHEMISTRY_HL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Kearney Mateusz",List.of(Subject.POLISH_A_SL,Subject.GEOGRAPHY_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_HL,Subject.HISTORY_HL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Kłonowski Franciszek",List.of(Subject.MATH_AA_HL,Subject.PHYSICS_HL,Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_SL,Subject.CHEMISTRY_HL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Krysztofowicz Julia",List.of(Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.GERMAN_B_HL,Subject.BIOLOGY_HL,Subject.HISTORY_HL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Łasecka Anastazja",List.of(Subject.POLISH_A_SL,Subject.ENGLISH_A_HL,Subject.ECONOMICS_HL,Subject.COMPUTER_SCIENCE_SL,Subject.MATH_AI_SL,Subject.FRENCH_B_HL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Machniewski Jeremi",List.of(Subject.MATH_AA_HL,Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_SL,Subject.COMPUTER_SCIENCE_HL,Subject.FRENCH_B_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Malinowska Alicja",List.of(Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_HL,Subject.BIOLOGY_SL,Subject.MATH_AI_SL,Subject.FRENCH_B_HL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Mielnik Ewa",List.of(Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.SPANISH_B_HL,Subject.ECONOMICS_HL,Subject.COMPUTER_SCIENCE_SL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Mikołajczak Mateusz",List.of(Subject.MATH_AA_HL,Subject.PHYSICS_HL,Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_HL,Subject.COMPUTER_SCIENCE_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Muras Wojciech",List.of(Subject.MATH_AA_SL,Subject.PHYSICS_HL,Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_SL,Subject.COMPUTER_SCIENCE_HL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Najmanowicz Zuzanna",List.of(Subject.POLISH_A_HL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_HL,Subject.BIOLOGY_SL,Subject.MATH_AI_SL,Subject.FRENCH_B_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Ograbek Pola",List.of(Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.GERMAN_B_HL,Subject.BIOLOGY_HL,Subject.CHEMISTRY_HL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Oporowska Nina",List.of(Subject.GEOGRAPHY_SL,Subject.ENGLISH_A_HL,Subject.SPANISH_B_HL,Subject.BIOLOGY_HL,Subject.CHEMISTRY_HL,Subject.MATH_AI_SL,Subject.POLISH_NO_EXAMINATION,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Podskarbi Magdalena",List.of(Subject.POLISH_A_SL,Subject.GEOGRAPHY_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_HL,Subject.GERMAN_B_HL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Siwek Natalia",List.of(Subject.MATH_AA_HL,Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_SL,Subject.HISTORY_HL,Subject.CHEMISTRY_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Smagała Marcjanna",List.of(Subject.MATH_AA_HL,Subject.POLISH_A_SL,Subject.ENGLISH_A_HL,Subject.ECONOMICS_SL,Subject.BIOLOGY_HL,Subject.CHEMISTRY_HL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Smyczyński Paweł",List.of(Subject.POLISH_A_HL,Subject.ENGLISH_B_HL,Subject.GERMAN_B_HL,Subject.BIOLOGY_SL,Subject.HISTORY_HL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Sokólska Julia",List.of(Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_HL,Subject.HISTORY_HL,Subject.CHEMISTRY_SL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Sokół Alicja",List.of(Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_SL,Subject.BIOLOGY_HL,Subject.CHEMISTRY_HL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Szudrowicz Marcin",List.of(Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.GERMAN_B_HL,Subject.BIOLOGY_SL,Subject.HISTORY_HL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Wałęsa Giulia",List.of(Subject.GEOGRAPHY_SL,Subject.ENGLISH_A_HL,Subject.SPANISH_B_HL,Subject.ECONOMICS_HL,Subject.BIOLOGY_SL,Subject.MATH_AI_SL,Subject.POLISH_NO_EXAMINATION,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Węgrowski Jakub",List.of(Subject.MATH_AA_SL,Subject.PHYSICS_HL,Subject.POLISH_A_SL,Subject.ENGLISH_B_HL,Subject.ECONOMICS_SL,Subject.CHEMISTRY_HL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Wiśniewska Barbara",List.of(Subject.POLISH_A_SL,Subject.GEOGRAPHY_SL,Subject.ENGLISH_B_HL,Subject.BIOLOGY_HL,Subject.CHEMISTRY_HL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Wypych Stanisław",List.of(Subject.POLISH_A_SL,Subject.ENGLISH_A_HL,Subject.ECONOMICS_SL,Subject.COMPUTER_SCIENCE_HL,Subject.HISTORY_HL,Subject.MATH_AI_SL,Subject.TOK,Subject.Z_WYCH)));
        Student.students.add(new Student("Żakowska Julia",List.of(Subject.GEOGRAPHY_SL,Subject.ENGLISH_A_HL,Subject.BIOLOGY_SL,Subject.HISTORY_HL,Subject.MATH_AI_SL,Subject.POLISH_NO_EXAMINATION,Subject.FRENCH_B_HL,Subject.TOK,Subject.Z_WYCH)));*/

        UI ui = new UI();
    }
}
