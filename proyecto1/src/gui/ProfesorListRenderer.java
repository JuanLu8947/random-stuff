package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProfesorListRenderer extends DefaultListCellRenderer {
    
    private ImageIcon userIcon;

    public ProfesorListRenderer() {
        // Asegúrate de tener un icono de usuario o persona en tus recursos
        userIcon = loadIcon("/resources/prof.PNG", 20, 20); 
    }

    @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel p = new JPanel(new BorderLayout(6, 0)); // Espacio reducido entre icono y texto
        p.setOpaque(false);
        
        // Reducimos el padding izquierdo para pegar más al borde
        p.setBorder(new EmptyBorder(4, 0, 4, 8));
            
        // Icono de la izquierda (Persona)
        JLabel iconLabel = new JLabel(userIcon != null ? userIcon : new JLabel("👤").getIcon());
        iconLabel.setBorder(new EmptyBorder(0, 4, 0, 8));
        
        // Nombre del profesor
        JLabel text = new JLabel(value.toString());
        text.setBorder(new EmptyBorder(0, 0, 0, 0));
        text.setFont(new Font("SansSerif", Font.PLAIN, 15));

        // Flecha de la derecha (pequeña y gris)
        JLabel arrow = new JLabel("‣");
        arrow.setForeground(new Color(80, 80, 80));
        arrow.setFont(new Font("Serif", Font.BOLD, 18));

        p.add(iconLabel, BorderLayout.WEST);
        p.add(text, BorderLayout.CENTER);
        p.add(arrow, BorderLayout.EAST);

        if (isSelected) {
            p.setOpaque(true);
            p.setBackground(new Color(255, 255, 255, 100));
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