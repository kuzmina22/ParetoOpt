package ru.kuzmina.paretoOpt.form;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SwingConsole {
    public static void run(final JFrame f, final int width, final int height) {
        SwingUtilities.invokeLater(() -> {
            f.setTitle(!f.getTitle().isEmpty() ? f.getTitle() : f.getClass().getSimpleName());
            f.setSize(width, height);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        });
    }
}
