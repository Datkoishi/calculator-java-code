package com.calculator.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.calculator.constants.Colors;

public class TitleBar extends JPanel {
    private final JFrame parent;

    public TitleBar(JFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Colors.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
        
        setupComponents();
    }

    private void setupComponents() {
        add(createControlButtons(), BorderLayout.WEST);
        add(createTitle(), BorderLayout.CENTER);
    }

    private JPanel createControlButtons() {
        JPanel controlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        controlButtons.setBackground(Colors.BACKGROUND_COLOR);
        
        JButton closeButton = createCircleButton(new Color(255, 95, 87));
        JButton minimizeButton = createCircleButton(new Color(255, 189, 46));
        JButton maximizeButton = createCircleButton(new Color(40, 200, 64));
        
        closeButton.addActionListener(e -> System.exit(0));
        minimizeButton.addActionListener(e -> parent.setState(JFrame.ICONIFIED));
        
        controlButtons.add(closeButton);
        controlButtons.add(minimizeButton);
        controlButtons.add(maximizeButton);
        
        return controlButtons;
    }

    private JLabel createTitle() {
        JLabel titleLabel = new JLabel("Calculator", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(Colors.TEXT_LIGHT);
        return titleLabel;
    }

    private JButton createCircleButton(Color color) {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(color);
                g2.fillOval(0, 0, getWidth(), getHeight());
                
                g2.setColor(new Color(0, 0, 0, 40));
                g2.setStroke(new BasicStroke(1f));
                g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
                
                g2.dispose();
            }
        };
        
        button.setPreferredSize(new Dimension(14, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        
        return button;
    }
}