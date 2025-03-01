package com.calculator.utils;

import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Font;
import com.calculator.constants.Symbols;

public class MathRenderer {
    public static void drawMathExpression(Graphics2D g2, String expression, int x, int y) {
        if (expression.contains(Symbols.SQRT_SYMBOL)) {
            drawSquareRoot(g2, expression, x, y);
        } else if (expression.contains(Symbols.FRACTION_SYMBOL)) {
            drawFraction(g2, expression, x, y);
        } else if (expression.contains(Symbols.POWER_SYMBOL)) {
            drawPower(g2, expression, x, y);
        } else {
            g2.drawString(expression, x - g2.getFontMetrics().stringWidth(expression), y);
        }
    }

    public static void drawPower(Graphics2D g2, String expression, int x, int y) {
        String[] parts = expression.split("\\" + Symbols.POWER_SYMBOL);
        if (parts.length == 2) {
            String base = parts[0];
            String exponent = parts[1];

            // Lưu font và metrics hiện tại
            Font originalFont = g2.getFont();
            FontMetrics originalMetrics = g2.getFontMetrics();

            // Tính toán kích thước cho số cơ sở
            int baseWidth = originalMetrics.stringWidth(base);
            
            // Tạo font nhỏ hơn cho số mũ (60% kích thước gốc)
            Font exponentFont = originalFont.deriveFont(originalFont.getSize() * 0.6f);
            g2.setFont(exponentFont);
            FontMetrics exponentMetrics = g2.getFontMetrics();
            
            // Tính toán kích thước và vị trí cho số mũ
            int exponentWidth = exponentMetrics.stringWidth(exponent);
            int exponentHeight = exponentMetrics.getAscent();
            int exponentY = y - originalMetrics.getAscent() + exponentHeight;

            // Vẽ số cơ sở
            g2.setFont(originalFont);
            g2.drawString(base, x - baseWidth - exponentWidth, y);

            // Vẽ số mũ
            g2.setFont(exponentFont);
            g2.drawString(exponent, x - exponentWidth, exponentY);

            // Khôi phục font gốc
            g2.setFont(originalFont);
        }
    }

    public static void drawSquareRoot(Graphics2D g2, String expression, int x, int y) {
        FontMetrics fm = g2.getFontMetrics();
        String[] parts = expression.split(Symbols.SQRT_SYMBOL);
        if (parts.length > 1) {
            String number = parts[1];
            int numberWidth = fm.stringWidth(number);
            int sqrtSymbolWidth = fm.stringWidth(Symbols.SQRT_SYMBOL);
            
            // Vẽ dấu căn
            g2.drawString(Symbols.SQRT_SYMBOL, x - numberWidth - sqrtSymbolWidth, y);
            
            // Vẽ đường ngang trên số
            int lineY = y - fm.getAscent() + 5;
            g2.drawLine(x - numberWidth, lineY, x, lineY);
            
            // Vẽ số trong căn
            g2.drawString(number, x - numberWidth, y);
        }
    }

    public static void drawFraction(Graphics2D g2, String expression, int x, int y) {
        FontMetrics fm = g2.getFontMetrics();
        String[] parts = expression.split(Symbols.FRACTION_SYMBOL);
        if (parts.length == 2) {
            String numerator = parts[0];
            String denominator = parts[1];
            
            // Tính toán kích thước
            int numWidth = fm.stringWidth(numerator);
            int denWidth = fm.stringWidth(denominator);
            int maxWidth = Math.max(numWidth, denWidth);
            
            // Vẽ tử số
            g2.drawString(numerator, x - maxWidth + (maxWidth - numWidth) / 2, y - fm.getHeight() / 2);
            
            // Vẽ đường phân số
            g2.drawLine(x - maxWidth, y, x, y);
            
            // Vẽ mẫu số
            g2.drawString(denominator, x - maxWidth + (maxWidth - denWidth) / 2, y + fm.getHeight() / 2);
        }
    }
}