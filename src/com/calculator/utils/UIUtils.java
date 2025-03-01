package com.calculator.utils;

import java.awt.*;
import javax.swing.border.Border;

public class UIUtils {
    public static Border createRoundedBorder(int radius, Color color) {
        return new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(1, 1, 1, 1);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };
    }

    public static String formatResult(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        }
        return String.format("%.8g", value);
    }
}