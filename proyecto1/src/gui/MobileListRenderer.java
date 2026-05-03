package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MobileListRenderer extends DefaultListCellRenderer {
    
    private ImageIcon carpetaIcon;

    public MobileListRenderer() {
        // Carga aquí tu imagen de carpeta personalizada
        carpetaIcon = loadIcon("/resources/folder.PNG", 24, 20); 
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel p = new JPanel(new BorderLayout(15, 0));
        p.setOpaque(false);
        // Margen lateral interno para que no se pegue al borde del scroll
        p.setBorder(new EmptyBorder(5, 15, 5, 15)); 

        // Icono (Carpeta)
        JLabel iconLabel = new JLabel(carpetaIcon != null ? carpetaIcon : new JLabel("📁").getIcon());
        
        // Texto
        JLabel text = new JLabel(value.toString());
        text.setFont(new Font("SansSerif", isSelected ? Font.BOLD : Font.PLAIN, 15));
        text.setForeground(new Color(50, 50, 50));

        // Flecha Derecha
        JLabel arrow = new JLabel("›");
        arrow.setForeground(new Color(100, 100, 100));
        arrow.setFont(new Font("Monospaced", Font.BOLD, 22));

        p.add(iconLabel, BorderLayout.WEST);
        p.add(text, BorderLayout.CENTER);
        p.add(arrow, BorderLayout.EAST);

        if (isSelected) {
            p.setOpaque(true);
            p.setBackground(new Color(255, 255, 255, 120)); // Fondo blanco translúcido
        }
        return p;
    }

    private ImageIcon loadIcon(String path, int w, int h) {
        try {
            java.net.URL url = getClass().getResource(path);
            if (url != null) {
                return new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
            }
        } catch (Exception e) {}
        return null;
    }
}