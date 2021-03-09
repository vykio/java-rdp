package com.tech.app.windows.panels;

import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {

    private final JFrame frame;

    public DrawPanel(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // clear
        g.setColor(Color.BLACK);
        g.setFont(new Font("serif", Font.BOLD, 60));
        g.drawString("hello", getWidth() / 2 - g.getFontMetrics().stringWidth("hello") / 2,
                getHeight() / 2 + g.getFontMetrics().getHeight() / 2);
    }

    public void applyPanel() {
        this.frame.add(this);
        this.frame.setVisible(true);
    }



}
