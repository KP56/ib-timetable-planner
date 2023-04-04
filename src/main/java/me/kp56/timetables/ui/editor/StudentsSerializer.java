package me.kp56.timetables.ui.editor;

import me.kp56.timetables.timetable.Student;

import javax.swing.*;
import java.io.*;

public class StudentsSerializer {

    private StudentsSerializer instance;

    public StudentsSerializer getInstance() {
        if (instance == null) {
            instance = new StudentsSerializer();
        }
        return instance;
    }

    private JFileChooser fileChooser = new JFileChooser();

    private StudentsSerializer() {
    }


    public void saveAs() {
        int returnVal = fileChooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            save();
        }
    }

    public void save() {
        File file = fileChooser.getSelectedFile();
        if (file == null) {
            saveAs();
            return;
        }
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(fileChooser.getSelectedFile());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ) {
            for (Student student : Student.students) {
                objectOutputStream.writeObject(student);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Save file not found", "Failed to save", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void open() {
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try (
                    FileInputStream fileInputStream = new FileInputStream(fileChooser.getSelectedFile());
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ) {
                Student.students.clear();
                for (; ; ) {
                    Student.students.add((Student) objectInputStream.readObject());
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Failed to open file.", "Could not open", JOptionPane.ERROR_MESSAGE);
            } catch (EOFException e) {
                JOptionPane.showMessageDialog(null, "Loaded all students from file", "Loading completed", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
