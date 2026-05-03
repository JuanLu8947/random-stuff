package gui;

import main.MainFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ResourceBundle;

public class ValoracionEnviada extends JPanel {

    private MainFrame mainFrame;

    public ValoracionEnviada(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        ResourceBundle textos = mainFrame.getBundle();

        setLayout(new GridBagLayout());
        setBackground(new Color(175, 255, 100)); // Fondo verde lima

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // 1. Título
        JLabel lblMensaje = new JLabel(textos.getString("valoracionEnviada.mensaje"), SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 0;
        add(lblMensaje, gbc);

        // 2. Icono (Check verde)
        JLabel lblIcono = new JLabel();
        try {
            // Intenta cargar un icono real si lo tienes en resources
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/check_verde.png"));
            Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            lblIcono.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            // Si no hay imagen, dibujamos un check enorme de texto
            lblIcono.setText("✔");
            lblIcono.setFont(new Font("Arial", Font.BOLD, 150));
            lblIcono.setForeground(new Color(34, 139, 34)); // Verde bosque
        }
        lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(40, 20, 60, 20); // Separación grande
        add(lblIcono, gbc);

        // 3. Botón Volver
        JButton btnVolver = new JButton(textos.getString("valoracionEnviada.volver"));
        btnVolver.setBackground(new Color(25, 25, 112)); // Azul oscuro
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(new EmptyBorder(12, 40, 12, 40));
        
        // Centrar el botón limitando su tamaño
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setOpaque(false);
        panelBoton.add(btnVolver);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 20, 20, 20);
        add(panelBoton, gbc);

        // --- LÓGICA DE NAVEGACIÓN ---
        btnVolver.addActionListener(e -> {
            mainFrame.showView("MAIN_ESTUDIANTE"); // Te devuelve al mainPanel
        });
    }
}