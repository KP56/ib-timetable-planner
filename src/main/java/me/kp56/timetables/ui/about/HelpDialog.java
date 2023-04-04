package me.kp56.timetables.ui.about;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HelpDialog extends JFrame {
    public HelpDialog(String resource, String title) {
        setTitle(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource)) {
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            textArea.setText(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JPanel textAreaWrapper = new JPanel();
        textAreaWrapper.add(textArea);
        textAreaWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        getContentPane().add(textAreaWrapper);
        pack();
    }

}
