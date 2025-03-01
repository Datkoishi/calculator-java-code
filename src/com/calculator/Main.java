package com.calculator;

import com.calculator.components.ModernCalculator;
import javax.swing.*;
import java.awt.Font;

public class Main {
    public static void main(String[] args) {
        try {
            System.setProperty("apple.awt.application.appearance", "system");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("ProgressBar.arc", 10);
            UIManager.put("TextComponent.arc", 10);
            
            Font sansSerifFont = new Font("SansSerif", Font.PLAIN, 12);
            UIManager.put("Label.font", sansSerifFont);
            UIManager.put("Button.font", sansSerifFont);
            UIManager.put("TextField.font", sansSerifFont);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            try {
                ModernCalculator calculator = new ModernCalculator();
                calculator.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                    null,
                    "Error starting application: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}