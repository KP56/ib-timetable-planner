package me.kp56.timetables.ui;

import java.awt.*;

public final class ScreenSize {
    private static ScreenSize instance;

    public Dimension getDimension() {
        return dimension;
    }

    private Dimension dimension;

    private ScreenSize() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        dimension = tk.getScreenSize();
    }

    public static ScreenSize getInstance() {
        if (instance == null) {
            instance = new ScreenSize();
        }

        return instance;
    }

    public Dimension half() {
        return new Dimension((int) dimension.getWidth() / 2, (int) dimension.getHeight() / 2);
    }

    public int getWidth() {
        return (int) dimension.getWidth();
    }

    public int getHeight() {
        return (int) dimension.getHeight();
    }

}
