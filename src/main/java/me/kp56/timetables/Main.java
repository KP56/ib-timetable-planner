package me.kp56.timetables;

import me.kp56.timetables.timetable.Student;
import me.kp56.timetables.timetable.Subject;
import me.kp56.timetables.ui.UI;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Student.students.add(new Student("Avcioglu Aylin", List.of(Subject.MATH_AA_SL, Subject.ENGLISH_A_HL, Subject.ECONOMICS_SL, Subject.BIOLOGY_HL, Subject.CHEMISTRY_HL, Subject.POLISH_NO_EXAMINATION, Subject.DSD, Subject.FRENCH_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Bartkowska Aleksandra", List.of(Subject.GEOGRAPHY_HL, Subject.ENGLISH_A_HL, Subject.ECONOMICS_HL, Subject.CHEMISTRY_SL, Subject.MATH_AI_SL, Subject.POLISH_NO_EXAMINATION, Subject.DSD, Subject.FRENCH_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Bryl Zofia", List.of(Subject.MATH_AA_SL, Subject.POLISH_A_SL, Subject.ENGLISH_B_HL, Subject.SPANISH_B_HL, Subject.BIOLOGY_HL, Subject.CHEMISTRY_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Chmielewski Oskar", List.of(Subject.MATH_AA_HL, Subject.PHYSICS_HL, Subject.POLISH_A_SL, Subject.ENGLISH_B_HL, Subject.ECONOMICS_SL, Subject.CHEMISTRY_SL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Dolata Antoni", List.of(Subject.MATH_AA_HL, Subject.POLISH_A_SL, Subject.ENGLISH_B_HL, Subject.ECONOMICS_HL, Subject.GERMAN_B_HL, Subject.CHEMISTRY_SL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Drgas Kalina", List.of(Subject.POLISH_A_SL, Subject.ENGLISH_B_HL, Subject.BIOLOGY_HL, Subject.CHEMISTRY_HL, Subject.MATH_AI_SL, Subject.DSD, Subject.FRENCH_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Flicińska Zuzanna", List.of(Subject.MATH_AA_SL, Subject.POLISH_A_HL, Subject.ENGLISH_A_HL, Subject.SPANISH_B_HL, Subject.BIOLOGY_SL, Subject.HISTORY_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Fliegner Szymon", List.of(Subject.MATH_AA_HL, Subject.PHYSICS_HL, Subject.ENGLISH_A_SL, Subject.ECONOMICS_SL, Subject.GERMAN_B_HL, Subject.CHEMISTRY_HL, Subject.POLISH_NO_EXAMINATION, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Grzesiak Kornelia", List.of(Subject.MATH_AA_SL, Subject.ENGLISH_A_HL, Subject.ECONOMICS_SL, Subject.GERMAN_B_HL, Subject.BIOLOGY_HL, Subject.CHEMISTRY_HL, Subject.POLISH_NO_EXAMINATION, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Grzeszczyk Maja", List.of(Subject.MATH_AA_SL, Subject.ENGLISH_A_SL, Subject.ECONOMICS_SL, Subject.GERMAN_B_HL, Subject.BIOLOGY_HL, Subject.CHEMISTRY_HL, Subject.POLISH_NO_EXAMINATION, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Jeleń Zuzanna", List.of(Subject.MATH_AA_SL, Subject.POLISH_A_SL, Subject.ENGLISH_A_HL, Subject.SPANISH_B_HL, Subject.BIOLOGY_HL, Subject.CHEMISTRY_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Kasperkiewicz Marianna", List.of(Subject.POLISH_A_SL, Subject.ENGLISH_A_HL, Subject.BIOLOGY_HL, Subject.CHEMISTRY_HL, Subject.MATH_AI_SL, Subject.DSD, Subject.FRENCH_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Kraiz Malek", List.of(Subject.MATH_AA_SL, Subject.POLISH_A_SL, Subject.ENGLISH_B_HL, Subject.ECONOMICS_SL, Subject.BIOLOGY_HL, Subject.CHEMISTRY_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Kubaszewska Franciszka", List.of(Subject.MATH_AA_SL, Subject.POLISH_A_SL, Subject.ENGLISH_A_HL, Subject.SPANISH_B_HL, Subject.BIOLOGY_HL, Subject.CHEMISTRY_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Kubecki Mikołaj", List.of(Subject.MATH_AA_SL, Subject.POLISH_A_SL, Subject.ENGLISH_A_SL, Subject.ECONOMICS_HL, Subject.BIOLOGY_HL, Subject.CHEMISTRY_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Kusiak Marta", List.of(Subject.MATH_AA_HL, Subject.POLISH_A_SL, Subject.ENGLISH_B_HL, Subject.ECONOMICS_SL, Subject.GERMAN_B_HL, Subject.CHEMISTRY_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Lisek Karina", List.of(Subject.MATH_AA_SL, Subject.PHYSICS_HL, Subject.ENGLISH_A_HL, Subject.COMPUTER_SCIENCE_HL, Subject.HISTORY_HL, Subject.POLISH_NO_EXAMINATION, Subject.DSD, Subject.FRENCH_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Marek Adam", List.of(Subject.MATH_AA_HL, Subject.POLISH_A_SL, Subject.ENGLISH_B_HL, Subject.SPANISH_B_HL, Subject.ECONOMICS_SL, Subject.COMPUTER_SCIENCE_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Matuszczak Maciej", List.of(Subject.MATH_AA_HL, Subject.ENGLISH_A_SL, Subject.ECONOMICS_SL, Subject.GERMAN_B_HL, Subject.BIOLOGY_HL, Subject.CHEMISTRY_HL, Subject.POLISH_NO_EXAMINATION, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Michalczuk Emilia", List.of(Subject.POLISH_A_SL, Subject.ENGLISH_A_HL, Subject.SPANISH_B_HL, Subject.BIOLOGY_SL, Subject.HISTORY_HL, Subject.MATH_AI_SL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Paszkowska Zuzanna", List.of(Subject.ENGLISH_A_HL, Subject.SPANISH_B_HL, Subject.ECONOMICS_SL, Subject.GERMAN_B_HL, Subject.BIOLOGY_SL, Subject.MATH_AI_SL, Subject.POLISH_NO_EXAMINATION, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Piotrowska Milena", List.of(Subject.POLISH_A_SL, Subject.ENGLISH_A_HL, Subject.ECONOMICS_HL, Subject.COMPUTER_SCIENCE_SL, Subject.HISTORY_HL, Subject.MATH_AI_SL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Raźnikiewicz Dawid", List.of(Subject.MATH_AA_SL, Subject.ENGLISH_A_HL, Subject.ECONOMICS_HL, Subject.COMPUTER_SCIENCE_SL, Subject.GERMAN_B_HL, Subject.HISTORY_HL, Subject.POLISH_NO_EXAMINATION, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Reshetnyk Diana", List.of(Subject.MATH_AA_SL, Subject.POLISH_A_SL, Subject.GEOGRAPHY_HL, Subject.ENGLISH_B_HL, Subject.ECONOMICS_HL, Subject.COMPUTER_SCIENCE_SL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Sadowczyk Milena", List.of(Subject.GEOGRAPHY_HL, Subject.ENGLISH_A_HL, Subject.BIOLOGY_HL, Subject.CHEMISTRY_HL, Subject.MATH_AI_SL, Subject.POLISH_NO_EXAMINATION, Subject.DSD, Subject.FRENCH_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Seite-Jastrzębska Adrianna", List.of(Subject.POLISH_A_HL, Subject.ENGLISH_A_HL, Subject.GERMAN_B_HL, Subject.BIOLOGY_SL, Subject.HISTORY_HL, Subject.MATH_AI_SL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Verschelden Julia", List.of(Subject.MATH_AA_SL, Subject.PHYSICS_HL, Subject.ENGLISH_A_HL, Subject.COMPUTER_SCIENCE_SL, Subject.HISTORY_HL, Subject.POLISH_NO_EXAMINATION, Subject.DSD, Subject.FRENCH_B_SL, Subject.TOK, Subject.Z_WYCH)));
        Student.students.add(new Student("Ziółkowski Hubert", List.of(Subject.MATH_AA_SL, Subject.PHYSICS_HL, Subject.POLISH_A_SL, Subject.ENGLISH_B_HL, Subject.BIOLOGY_HL, Subject.CHEMISTRY_HL, Subject.DSD, Subject.TOK, Subject.Z_WYCH)));
        
        UI ui = new UI();
    }
}
