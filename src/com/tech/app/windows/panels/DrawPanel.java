package com.tech.app.windows.panels;

import com.tech.app.models.Model;
import com.tech.app.models.Place;

import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {

    private final JFrame frame;
    private final Model model;

    public DrawPanel(JFrame frame, Model model) {
        this.frame = frame;
        this.model = model;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // clear
        
        //test
        g.setColor(Color.BLACK);
        g.setFont(new Font("serif", Font.BOLD, 60));
        g.drawString("hello", getWidth() / 2 - g.getFontMetrics().stringWidth("hello") / 2,
                getHeight() / 2 + g.getFontMetrics().getHeight() / 2);

        for (Place p:model.placeVector) {
            System.out.println("Nom "+p.getName()+"X "+p.getX()+"Y " + p.getY());
            paintPlace(g,p.getName(),p.getX(),p.getY());
        }
        
    }
    
    public void addPlace(int x, int y){
        System.out.println("Je suis dans la fn addPLace");
        System.out.println(x + " " + y);


        model.addPlace(new Place("p0", x, y));
        repaint();
    }
    
    public void paintPlace(Graphics g, String label, int x, int y){
        System.out.println("Je suis dans la fn paintPLace");

        g.setColor(Color.BLACK);
        g.drawOval(x,y,20,20);
        g.setFont(new Font("serif", Font.BOLD, 60));
        g.drawString(label,x+10 ,y+10);
    }


    public void applyPanel() {
        this.frame.add(this);
        this.frame.setVisible(true);
    }



}
