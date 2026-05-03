package gui;

import main.MainFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ResourceBundle;

public class ReportePanel extends JPanel {

    private MainFrame mainFrame;

    public ReportePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        ResourceBundle textos = mainFrame.getBundle();

        setLayout(new GridBagLayout());
        setBackground(new Color(175, 255, 100)); // Fondo verde lima

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Título
        JLabel lblTitulo = new JLabel(textos.getString("reporte.titulo"), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridy = 0;
        add(lblTitulo, gbc);

        // 2. Desplegable de tipo de error
        String[] tiposError = {
            textos.getString("reporte.tipoPrompt"),
            textos.getString("reporte.tipo1"),
            textos.getString("reporte.tipo2"),
            textos.getString("reporte.tipo3")
        };
        JComboBox<String> comboErrores = new JComboBox<>(tiposError);
        comboErrores.setPreferredSize(new Dimension(280, 40));
        comboErrores.setBackground(new Color(230, 230, 230)); // Gris claro
        gbc.gridy = 1;
        add(comboErrores, gbc);

        // 3. Área de texto para la descripción
        String placeholder = textos.getString("reporte.descPrompt");
        JTextArea txtDescripcion = new JTextArea(placeholder);
        txtDescripcion.setPreferredSize(new Dimension(280, 150));
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBackground(new Color(230, 230, 230)); // Gris claro
        txtDescripcion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(10, 10, 10, 10)
        ));

        // Lógica del placeholder para JTextArea
        txtDescripcion.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtDescripcion.getText().equals(placeholder)) {
                    txtDescripcion.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtDescripcion.getText().isEmpty()) {
                    txtDescripcion.setText(placeholder);
                }
            }
        });
        gbc.gridy = 2;
        add(txtDescripcion, gbc);

        // 4. Panel para los botones (Cancelar y Enviar)
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 20, 0));
        panelBotones.setOpaque(false);

        JButton btnCancelar = new JButton(textos.getString("reporte.cancelar"));
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1, true),
            new EmptyBorder(10, 10, 10, 10)
        ));

        JButton btnEnviar = new JButton(textos.getString("reporte.enviar"));
        btnEnviar.setBackground(new Color(25, 25, 112)); // Azul oscuro tipo Midnight Blue
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFocusPainted(false);
        btnEnviar.setBorder(new EmptyBorder(10, 10, 10, 10));

        panelBotones.add(btnCancelar);
        panelBotones.add(btnEnviar);

        gbc.gridy = 3;
        gbc.insets = new Insets(30, 20, 10, 20); // Más margen por arriba
        add(panelBotones, gbc);

        // --- LÓGICA DE NAVEGACIÓN ---
        btnCancelar.addActionListener(e -> {
            mainFrame.showView("LOGIN_ERROR"); // Vuelve a la pantalla de error
        });

        btnEnviar.addActionListener(e -> {
            // Aquí iría la lógica para enviar el reporte (ej. guardar en BD)
            mainFrame.showView("REPORTE_ENVIADO"); 
        });
    }
}