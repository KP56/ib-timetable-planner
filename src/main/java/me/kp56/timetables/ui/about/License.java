package me.kp56.timetables.ui.about;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class License extends JFrame {
    public License() {
        setTitle("License");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextArea licenseTextArea = new JTextArea();
        licenseTextArea.setEditable(false);
        try (InputStream licenseInputStream = getClass().getClassLoader().getResourceAsStream("license.txt")) {
            String license = new String(licenseInputStream.readAllBytes(), StandardCharsets.UTF_8);
            licenseTextArea.setText(license);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getContentPane().add(licenseTextArea);
        pack();
        setVisible(true);
    }

}
