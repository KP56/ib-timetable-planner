package me.kp56.timetables.ui;

import me.kp56.timetables.Main;
import me.kp56.timetables.timetable.Student;
import me.kp56.timetables.timetable.Timetable;
import me.kp56.timetables.ui.editor.StudentEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI extends JFrame {
    public UI() {
        this.setTitle("Timetable creator 3000");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(ScreenSize.getInstance().getWidth(), ScreenSize.getInstance().getHeight());
        getContentPane().add(new StudentEditor());

        this.setVisible(true);


    }

}
