package me.kp56.timetables.ui.run;

import me.kp56.timetables.run.Runner;
import me.kp56.timetables.ui.ScreenSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class RunDialog extends JFrame implements ActionListener {
    private Runner runner;
    private JTextArea textArea = new JTextArea(100, 20);
    private Timer loggingTimer;
    private JButton stopButton;
    public RunDialog(String studentClass) throws InterruptedException {
        setName("Run");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(ScreenSize.getInstance().half());
        setLocationRelativeTo(null);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(new JScrollPane(textArea));
        JButton stopButton = new JButton("Stop");
        stopButton.setAlignmentX(CENTER_ALIGNMENT);
        stopButton.addActionListener(actionEvent -> {
            if (!runner.keep_running.get()) {
                JFrame root = (JFrame) SwingUtilities.getRoot((Component) actionEvent.getSource());
                root.dispatchEvent(new WindowEvent(root, WindowEvent.WINDOW_CLOSING));
            }
            runner.keep_running.set(false);
        });
        contentPane.add(stopButton);
        setVisible(true);
        runner = new Runner(studentClass);
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

//        @KP56 here u have the old logging loop instead of printing to stdout just use textArea.append()
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
        for (int i = 0; i < runner.environments; i++) {
            double fitness = runner.fitnessValues.get(i).get();
            int gaps = runner.environmentGaps.get(i).get();
            if (fitness > bestFitness) {
                bestFitness = fitness;
                bestGaps = gaps;
            }
        }

        textArea.append("Best performance: " + bestGaps + " Gaps/" + bestFitness + " Fitness\n");
    }

}

