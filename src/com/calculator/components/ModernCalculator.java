package com.calculator.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import com.calculator.constants.Colors;
import com.calculator.constants.Symbols;
import com.calculator.utils.UIUtils;

public class ModernCalculator extends JFrame {
    private final CalculatorDisplay display;
    private final HistoryPanel historyPanel;
    private final StringBuilder currentInput;
    private double result;
    private String lastOperator;
    private boolean startNewInput;
    private boolean isInFractionMode;
    private boolean isInSqrtMode;
    private Point dragPoint;

    public ModernCalculator() {
        currentInput = new StringBuilder();
        result = 0;
        lastOperator = "";
        startNewInput = true;
        isInFractionMode = false;
        isInSqrtMode = false;

        setupWindow();
        
        display = new CalculatorDisplay();
        historyPanel = new HistoryPanel();
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Colors.BACKGROUND_COLOR);
        
        mainPanel.add(new TitleBar(this), BorderLayout.NORTH);
        mainPanel.add(createDisplayAndHistoryPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
        enableWindowDrag();
    }

    private void setupWindow() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 380, 700, 20, 20));
        getContentPane().setBackground(Colors.BACKGROUND_COLOR);
    }

    private JPanel createDisplayAndHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Colors.BACKGROUND_COLOR);
        panel.add(display, BorderLayout.NORTH);
        panel.add(historyPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(6, 4, 12, 12));
        buttonPanel.setBackground(Colors.BACKGROUND_COLOR);

        String[][] buttons = {
            {"C", Symbols.BACKSPACE, "xⁿ", Symbols.DIVIDE_SYMBOL},
            {"7", "8", "9", Symbols.MULTIPLY_SYMBOL},
            {"4", "5", "6", "-"},
            {"1", "2", "3", "+"},
            {"0", ".", Symbols.SQRT_SYMBOL, "="},
            {"%", "(", ")", Symbols.FRACTION_SYMBOL}
        };

        for (String[] row : buttons) {
            for (String label : row) {
                Color buttonColor = getButtonColor(label);
                Color textColor = Colors.TEXT_LIGHT;
                
                CalculatorButton button = new CalculatorButton(label, buttonColor, textColor);
                button.addActionListener(e -> handleButtonClick(label));
                buttonPanel.add(button);
            }
        }

        return buttonPanel;
    }
        private Color getButtonColor(String label) {
        if (("C" + Symbols.BACKSPACE + "xⁿ" + Symbols.SQRT_SYMBOL + "()" + 
             Symbols.FRACTION_SYMBOL + "%").indexOf(label) >= 0) {
            return Colors.BUTTON_FUNCTION;
        } else if ((Symbols.DIVIDE_SYMBOL + Symbols.MULTIPLY_SYMBOL + "-+=").contains(label)) {
            return Colors.BUTTON_OPERATOR;
        } else if (Character.isDigit(label.charAt(0)) || label.equals(".")) {
            return Colors.BUTTON_NUMBER;
        } else if (label.equals("=")) {
            return Colors.BUTTON_EQUAL;
        } else {
            return Colors.BUTTON_DARK;
        }
    }

    private void handleButtonClick(String value) {
        switch (value) {
            case "C":
                clearAll();
                break;
            case "⌫":
                backspace();
                break;
            case "=":
                calculate();
                break;
            case "÷":
                handleOperator("/");
                break;
            case "×":
                handleOperator("*");
                break;
            case "xⁿ":
                handleOperator("^");
                break;
            case "+":
            case "-":
            case "%":
                handleOperator(value);
                break;
            case ".":
                appendDecimal();
                break;
            case "(":
            case ")":
                appendParenthesis(value);
                break;
            default:
                if (value.equals(Symbols.SQRT_SYMBOL)) {
                    handleSquareRoot();
                } else if (value.equals(Symbols.FRACTION_SYMBOL)) {
                    handleFraction();
                } else if (Character.isDigit(value.charAt(0))) {
                    appendDigit(value);
                }
                break;
        }
    }

    private void handleSquareRoot() {
        if (!isInSqrtMode) {
            isInSqrtMode = true;
            currentInput.setLength(0);
            currentInput.append(Symbols.SQRT_SYMBOL);
            startNewInput = false;
            updateDisplay();
        }
    }

    private void handleFraction() {
        if (!isInFractionMode && !currentInput.toString().contains(Symbols.FRACTION_SYMBOL)) {
            isInFractionMode = true;
            if (currentInput.length() == 0) {
                currentInput.append("0");
            }
            currentInput.append(Symbols.FRACTION_SYMBOL);
            updateDisplay();
        }
    }

    private void handleOperator(String operator) {
        if (currentInput.length() > 0) {
            if (!lastOperator.isEmpty()) {
                calculate();
            } else {
                try {
                    result = Double.parseDouble(currentInput.toString());
                } catch (NumberFormatException e) {
                    displayError("Invalid input");
                    return;
                }
            }
        }
        lastOperator = operator;
        startNewInput = true;
        
        String displayOperator = operator;
        switch (operator) {
            case "*": displayOperator = Symbols.MULTIPLY_SYMBOL; break;
            case "/": displayOperator = Symbols.DIVIDE_SYMBOL; break;
            case "^": displayOperator = "ⁿ"; break;
        }
        display.setExpression(UIUtils.formatResult(result) + " " + displayOperator + " ");
    }

    private void calculate() {
        if (isInFractionMode) {
            calculateFraction();
            return;
        }
        if (isInSqrtMode) {
            calculateSquareRoot();
            return;
        }
        
        if (lastOperator.isEmpty()) return;

        try {
            double currentValue = Double.parseDouble(currentInput.toString());
            String expression = UIUtils.formatResult(result);
            
            switch (lastOperator) {
                case "+": 
                    expression += " + " + UIUtils.formatResult(currentValue);
                    result += currentValue;
                    break;
                case "-": 
                    expression += " - " + UIUtils.formatResult(currentValue);
                    result -= currentValue;
                    break;
                case "*": 
                    expression += " × " + UIUtils.formatResult(currentValue);
                    result *= currentValue;
                    break;
                case "/":
                    if (currentValue == 0) throw new ArithmeticException("Cannot divide by zero");
                    expression += " ÷ " + UIUtils.formatResult(currentValue);
                    result /= currentValue;
                    break;
                case "%": 
                    expression += " % " + UIUtils.formatResult(currentValue);
                    result = result * currentValue / 100;
                    break;
                case "^": 
                    expression += "ⁿ" + UIUtils.formatResult(currentValue);
                    result = Math.pow(result, currentValue);
                    break;
            }
            
            expression += " = " + UIUtils.formatResult(result);
            historyPanel.addToHistory(expression);
            display.setExpression("");
            currentInput.setLength(0);
            currentInput.append(UIUtils.formatResult(result));
            updateDisplay();
            lastOperator = "";
            startNewInput = true;
            
        } catch (NumberFormatException e) {
            displayError("Invalid input");
        } catch (ArithmeticException e) {
            displayError(e.getMessage());
        }
    }

    private void calculateFraction() {
        String input = currentInput.toString();
        String[] parts = input.split(Symbols.FRACTION_SYMBOL);
        if (parts.length != 2) {
            displayError("Invalid fraction format");
            return;
        }
        
        try {
            double numerator = Double.parseDouble(parts[0]);
            double denominator = Double.parseDouble(parts[1]);
            
            if (denominator == 0) {
                throw new ArithmeticException("Cannot divide by zero");
            }
            
            result = numerator / denominator;
            String expression = parts[0] + Symbols.FRACTION_SYMBOL + parts[1] + " = " + UIUtils.formatResult(result);
            historyPanel.addToHistory(expression);
            
            currentInput.setLength(0);
            currentInput.append(UIUtils.formatResult(result));
            updateDisplay();
            isInFractionMode = false;
            startNewInput = true;
            
        } catch (NumberFormatException e) {
            displayError("Invalid fraction numbers");
        } catch (ArithmeticException e) {
            displayError(e.getMessage());
        }
    }

    private void calculateSquareRoot() {
        String input = currentInput.toString().substring(1);
        try {
            double value = Double.parseDouble(input);
            if (value < 0) {
                throw new ArithmeticException("Cannot calculate square root of negative number");
            }
            
            result = Math.sqrt(value);
            String expression = Symbols.SQRT_SYMBOL + UIUtils.formatResult(value) + " = " + UIUtils.formatResult(result);
            historyPanel.addToHistory(expression);
            
            currentInput.setLength(0);
            currentInput.append(UIUtils.formatResult(result));
            updateDisplay();
            isInSqrtMode = false;
            startNewInput = true;
            
        } catch (NumberFormatException e) {
            displayError("Invalid number for square root");
        } catch (ArithmeticException e) {
            displayError(e.getMessage());
        }
    }

    private void appendDigit(String digit) {
        if (startNewInput) {
            currentInput.setLength(0);
            startNewInput = false;
        }
        currentInput.append(digit);
        updateDisplay();
        
        if (!lastOperator.isEmpty()) {
            String displayOperator = lastOperator;
            switch (lastOperator) {
                case "*": displayOperator = Symbols.MULTIPLY_SYMBOL; break;
                case "/": displayOperator = Symbols.DIVIDE_SYMBOL; break;
                case "^": displayOperator = "ⁿ"; break;
            }
            display.setExpression(UIUtils.formatResult(result) + " " + displayOperator + " " + currentInput);
        }
    }

    private void appendDecimal() {
        if (startNewInput) {
            currentInput.setLength(0);
            currentInput.append("0");
            startNewInput = false;
        }
        if (!currentInput.toString().contains(".")) {
            currentInput.append(".");
        }
        updateDisplay();
    }

    private void appendParenthesis(String paren) {
        if (startNewInput) {
            currentInput.setLength(0);
            startNewInput = false;
        }
        currentInput.append(paren);
        updateDisplay();
    }

    private void clearAll() {
        currentInput.setLength(0);
        currentInput.append("0");
        result = 0;
        lastOperator = "";
        startNewInput = true;
        isInFractionMode = false;
        isInSqrtMode = false;
        display.setExpression("");
        updateDisplay();
    }

    private void backspace() {
        if (currentInput.length() > 0) {
            currentInput.deleteCharAt(currentInput.length() - 1);
            if (currentInput.length() == 0) {
                currentInput.append("0");
                startNewInput = true;
            }
            updateDisplay();
            
            if (!lastOperator.isEmpty()) {
                String displayOperator = lastOperator;
                switch (lastOperator) {
                    case "*": displayOperator = Symbols.MULTIPLY_SYMBOL; break;
                    case "/": displayOperator = Symbols.DIVIDE_SYMBOL; break;
                    case "^": displayOperator = "ⁿ"; break;
                }
                display.setExpression(UIUtils.formatResult(result) + " " + displayOperator + " " + currentInput);
            }
        }
    }

    private void updateDisplay() {
        display.setDisplay(currentInput.toString());
    }

    private void displayError(String message) {
        JDialog errorDialog = new JDialog(this, "Error", true);
        errorDialog.setSize(300, 150);
        errorDialog.setLocationRelativeTo(this);
        errorDialog.setUndecorated(true);
        errorDialog.setShape(new RoundRectangle2D.Double(0, 0, 300, 150, 15, 15));
        
        JPanel errorPanel = new JPanel(new BorderLayout(10, 10));
        errorPanel.setBackground(Colors.BACKGROUND_COLOR);
        errorPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel errorLabel = new JLabel(message, JLabel.CENTER);
        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        errorLabel.setForeground(Colors.TEXT_LIGHT);
        
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        okButton.setBackground(Colors.BUTTON_EQUAL);
        okButton.setForeground(Colors.TEXT_LIGHT);
        okButton.setBorderPainted(false);
        okButton.setFocusPainted(false);
        okButton.addActionListener(e -> errorDialog.dispose());
        
        errorPanel.add(errorLabel, BorderLayout.CENTER);
        errorPanel.add(okButton, BorderLayout.SOUTH);
        
        errorDialog.add(errorPanel);
        errorDialog.setVisible(true);
        
        clearAll();
    }

    private void enableWindowDrag() {
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (dragPoint != null) {
                    Point currentLocation = getLocation();
                    setLocation(
                        currentLocation.x + e.getX() - dragPoint.x,
                        currentLocation.y + e.getY() - dragPoint.y
                    );
                }
            }
        });

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                dragPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                dragPoint = null;
            }
        });
    }
}