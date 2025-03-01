package com.calculator.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import com.calculator.constants.Colors;
import com.calculator.utils.MathRenderer;

public class CalculatorDisplay extends JPanel {
    private final JTextField expressionField;
    private final JTextField displayField;

    public CalculatorDisplay() {
        setLayout(new GridLayout(2, 1, 5, 5));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        expressionField = createExpressionField();
        displayField = createDisplayField();

        add(expressionField);
        add(displayField);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Colors.DISPLAY_BACKGROUND);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        g2.dispose();
    }

    private JTextField createExpressionField() {
        JTextField field = new JTextField("") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(Colors.DISPLAY_BACKGROUND);
                g2.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        field.setFont(new Font("SansSerif", Font.PLAIN, 24));
        field.setHorizontalAlignment(JTextField.RIGHT);
        field.setEditable(false);
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        field.setBackground(Colors.DISPLAY_BACKGROUND);
        field.setForeground(Colors.TEXT_SECONDARY);
        return field;
    }

    private JTextField createDisplayField() {
        JTextField field = new JTextField("0") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(Colors.DISPLAY_BACKGROUND);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                String text = getText();
                FontMetrics fm = g2.getFontMetrics(getFont());
                
                g2.setColor(getForeground());
                MathRenderer.drawMathExpression(g2, text, getWidth() - 10, (getHeight() + fm.getAscent()) / 2);
                
                g2.dispose();
            }
        };
        field.setFont(new Font("SansSerif", Font.BOLD, 48));
        field.setHorizontalAlignment(JTextField.RIGHT);
        field.setEditable(false);
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        field.setBackground(Colors.DISPLAY_BACKGROUND);
        field.setForeground(Colors.TEXT_LIGHT);
        return field;
    }

    public void setExpression(String text) {
        expressionField.setText(text);
    }

    public void setDisplay(String text) {
        displayField.setText(text);
        displayField.repaint();
    }

    public String getDisplayText() {
        return displayField.getText();
    }
}