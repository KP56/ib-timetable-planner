package me.kp56.timetables;

import me.kp56.timetables.configuration.Config;
import me.kp56.timetables.timetable.Runner;
import me.kp56.timetables.timetable.Student;
import me.kp56.timetables.timetable.Timetable;
import me.kp56.timetables.ui.UI;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    private static Config config = Config.getInstance();
    private static File workingDir = new File(System.getProperty("user.dir"));

    public static void main(String[] args) {
        UI ui = new UI();
    }
}
