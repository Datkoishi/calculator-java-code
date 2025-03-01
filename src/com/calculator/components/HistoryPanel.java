package com.calculator.components;

import javax.swing.*;
import java.awt.*;
import com.calculator.constants.Colors;
import com.calculator.utils.UIUtils;

public class HistoryPanel extends JPanel {
    private final JTextArea historyArea;

    public HistoryPanel() {
        setLayout(new BorderLayout());
        setBackground(Colors.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));

        historyArea = createHistoryArea();
        JScrollPane scrollPane = createScrollPane(historyArea);
        
        add(createHistoryLabel(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JTextArea createHistoryArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("SansSerif", Font.PLAIN, 14));
        area.setBackground(Colors.HISTORY_BACKGROUND);
        area.setForeground(Colors.TEXT_LIGHT);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    private JScrollPane createScrollPane(JTextArea area) {
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(340, 120));
        scrollPane.setBorder(UIUtils.createRoundedBorder(8, Colors.BUTTON_DARK));
        scrollPane.getVerticalScrollBar().setBackground(Colors.HISTORY_BACKGROUND);
        scrollPane.getVerticalScrollBar().setForeground(new Color(120, 120, 120));
        return scrollPane;
    }

    private JLabel createHistoryLabel() {
        JLabel label = new JLabel("History");
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(Colors.TEXT_SECONDARY);
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
        return label;
    }

    public void addToHistory(String calculation) {
        historyArea.append(calculation + "\n\n");
        historyArea.setCaretPosition(historyArea.getDocument().getLength());
    }

    public void clearHistory() {
        historyArea.setText("");
    }
}