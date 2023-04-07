package me.kp56.timetables.ui.run;

import me.kp56.timetables.timetable.Runner;
import me.kp56.timetables.ui.ScreenSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class RunDialog extends JFrame implements ActionListener {
    private Runner runner;
    private JTextArea textArea = new JTextArea(100, 20);
    private Timer loggingTimer;
    private JButton stopButton;
    public RunDialog() throws InterruptedException {
        setName("Run");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(ScreenSize.getInstance().half());
        setLocationRelativeTo(null);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(new JScrollPane(textArea));
        JButton stopButton = new JButton("Stop");
        stopButton.setAlignmentX(CENTER_ALIGNMENT);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!runner.keep_running.get()) {
                    JFrame root = (JFrame) SwingUtilities.getRoot((Component) actionEvent.getSource());
                    root.dispatchEvent(new WindowEvent(root, WindowEvent.WINDOW_CLOSING));
                }
                runner.keep_running.set(false);
            }
        });
        contentPane.add(stopButton);
        setVisible(true);
        runner = new Runner();
        loggingTimer = new Timer(1000, this);
        loggingTimer.start();

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {


        if (!runner.keep_running.get()) {
            textArea.append("STOPPED\nPress Stop button again to exit");
            loggingTimer.stop();
            return;
        }

        for (AtomicInteger integer : runner.environmentGaps) {
            //Checking if any environment is yet to produce the first generation
            if (integer.get() == -1) {
                return;
            }
        }

        textArea.append("Environment performance (" + ((System.currentTimeMillis() - runner.beginning) / 1000) + "s):\n");
        for (int i = 0; i < runner.environments; i++) {
            textArea.append("- Environment #" + (i + 1) + ": " + runner.environmentGaps.get(i) + " Gaps/" + runner.fitnessValues.get(i) + " Fitness\n");
        }

        int bestGaps = Integer.MAX_VALUE;
        double bestFitness = 0;
        for (AtomicReference<Double> d : runner.fitnessValues) {
            bestFitness = Math.max(bestFitness, d.get());
        }
        for (AtomicInteger i : runner.environmentGaps) {
            bestGaps = Math.min(bestGaps, i.get());
        }

        textArea.append("Best performance: " + bestGaps + " Gaps/" + bestFitness + " Fitness\n");

    }

}

