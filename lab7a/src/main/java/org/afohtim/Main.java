package org.afohtim;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            SwingUtilities.invokeLater(() -> new GameWindow("Duck Hunt"));
        } catch (Exception e) {}
    }
}
