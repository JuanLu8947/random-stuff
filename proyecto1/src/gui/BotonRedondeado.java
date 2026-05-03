package gui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

public class BotonRedondeado extends JButton {

    public BotonRedondeado(String label) {
        super(label);
        // Quitamos todo el estilo por defecto de Java (el look gris metálico)
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        
        // Fuente más moderna y parecida a la imagen
        setFont(new Font("SansSerif", Font.BOLD, 13));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        
        // 1. Suavizado de bordes (Crucial para que no se vea pixelado)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 2. Lógica de color (Efecto de clic)
        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker()); // Se oscurece un poco al pulsar
        } else if (getModel().isRollover()) {
            g2.setColor(getBackground().brighter()); // Se ilumina al pasar el ratón
        } else {
            g2.setColor(getBackground());
        }

        // 3. Dibujar el fondo redondeado
        // Para que sea una "cápsula" perfecta, el radio (25) debe ser similar a la altura
        int arc = getHeight(); 
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));

        // 4. Dibujar el texto original del botón
        super.paintComponent(g);
        
        g2.dispose();
    }
}