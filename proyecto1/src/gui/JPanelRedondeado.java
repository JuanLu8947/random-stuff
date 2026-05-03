package gui;

import javax.swing.*;
import java.awt.*;

public class JPanelRedondeado extends JPanel {
    private int radius;

    public JPanelRedondeado(int radius) {
        this.radius = radius;
        setOpaque(false); // Importante para que se vea el fondo de atrás en las esquinas
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
    }
}