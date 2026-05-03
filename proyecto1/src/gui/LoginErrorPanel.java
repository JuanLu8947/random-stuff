package gui;

import java.awt.*;
import java.util.ResourceBundle;
import javax.swing.*;
import main.MainFrame;

public class LoginErrorPanel extends JPanel {

    private MainFrame mainFrame;

    public LoginErrorPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        // Recuperamos el bundle configurado en el MainFrame
        ResourceBundle textos = mainFrame.getBundle();
        
        setLayout(new BorderLayout());
        setBackground(new Color(175, 255, 100)); // Fondo verde lima

        // --- PANEL CENTRAL ---
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Icono de Alerta
        JLabel iconLabel = new JLabel();
        try {
            ImageIcon alertIcon = new ImageIcon(getClass().getResource("/resources/acceso_denegado.png"));
            Image img = alertIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            iconLabel.setText("⚠️");
            iconLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        }
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        centerPanel.add(iconLabel, gbc);

        // 2. Título (Usando la clave LoginError.titulo)
        JLabel lblTitulo = new JLabel(textos.getString("LoginError.titulo"), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.RED);
        gbc.gridy = 1;
        centerPanel.add(lblTitulo, gbc);

        // 3. Mensaje Explicativo (Usando la clave LoginError.mensaje)
        JLabel lblMensaje = new JLabel(textos.getString("LoginError.mensaje"), SwingConstants.CENTER);
        gbc.gridy = 2;
        centerPanel.add(lblMensaje, gbc);

        // 4. Botón Reportar (Usando la clave LoginError.reportar)
        JButton btnReportar = new JButton(textos.getString("LoginError.reportar"));
        btnReportar.setForeground(Color.BLUE);
        btnReportar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnReportar.setBorderPainted(false);
        btnReportar.setContentAreaFilled(false);
        btnReportar.setFocusPainted(false);
        btnReportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 3;
        gbc.insets = new Insets(50, 10, 10, 10);
        centerPanel.add(btnReportar, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // --- PANEL INFERIOR (Botón de retroceso) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        bottomPanel.setOpaque(false);

        JButton btnVolver = new JButton("⬅"); 
        btnVolver.setFont(new Font("Arial", Font.BOLD, 18));
        btnVolver.setBackground(new Color(160, 255, 90)); 
        btnVolver.setForeground(Color.BLACK);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bottomPanel.add(btnVolver);
        add(bottomPanel, BorderLayout.SOUTH);

        // Lógica de navegación
        btnVolver.addActionListener(e -> {
            mainFrame.showView("LOGIN");
        });

        btnReportar.addActionListener(e -> {
            mainFrame.showView("REPORTE");
        });
    }
}