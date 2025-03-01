package com.calculator.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.calculator.constants.Colors;

public class CalculatorButton extends JButton {
    private Timer animationTimer;
    private float animationProgress;
    private boolean isAnimating;
    private final Color backgroundColor;
    private static final int ARC_SIZE = 20;

    public CalculatorButton(String text, Color bgColor, Color fgColor) {
        super(text);
        this.backgroundColor = bgColor;
        setFont(new Font("SansSerif", Font.BOLD, 24));
        setForeground(fgColor);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setupAnimation();
        setupListeners();
    }

    private void setupAnimation() {
        animationTimer = new Timer(10, e -> {
            animationProgress += 0.1f;
            if (animationProgress >= 1.0f) {
                animationProgress = 0;
                isAnimating = false;
                animationTimer.stop();
            }
            repaint();
        });
    }

    private void setupListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                isAnimating = true;
                animationProgress = 0;
                animationTimer.start();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (isAnimating) {
            paintAnimatedButton(g2);
        } else {
            paintNormalButton(g2);
        }
        
        paintButtonText(g2);
        g2.dispose();
    }

    private void paintAnimatedButton(Graphics2D g2) {
        float scale = 0.95f + 0.05f * (1 - animationProgress);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int scaledWidth = (int)(getWidth() * scale);
        int scaledHeight = (int)(getHeight() * scale);
        int x = centerX - scaledWidth / 2;
        int y = centerY - scaledHeight / 2;
        
        g2.setColor(backgroundColor);
        g2.fillRoundRect(x, y, scaledWidth, scaledHeight, ARC_SIZE, ARC_SIZE);
        
        Color glowColor = new Color(
            backgroundColor.getRed(), 
            backgroundColor.getGreen(), 
            backgroundColor.getBlue(), 
            50 + (int)(50 * (1 - animationProgress))
        );
        g2.setColor(glowColor);
        g2.fillRoundRect(-5, -5, getWidth() + 10, getHeight() + 10, ARC_SIZE + 10, ARC_SIZE + 10);
    }

    private void paintNormalButton(Graphics2D g2) {
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_SIZE, ARC_SIZE);
        
        // Gradient effect
        GradientPaint gp = new GradientPaint(
            0, 0, new Color(255, 255, 255, 30),
            0, getHeight(), new Color(0, 0, 0, 30)
        );
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_SIZE, ARC_SIZE);
        
        // Border
        g2.setColor(new Color(255, 255, 255, 30));
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC_SIZE, ARC_SIZE);
    }

    private void paintButtonText(Graphics2D g2) {
        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(getText(), x, y);
    }
}