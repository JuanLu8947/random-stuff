package gui;

import main.MainFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ResourceBundle;

public class RegistroPanel extends JPanel {

    private MainFrame mainFrame;

    public RegistroPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        ResourceBundle textos = mainFrame.getBundle();

        setLayout(new BorderLayout());
        setBackground(new Color(175, 255, 100)); // Fondo verde lima

        // --- PANEL CENTRAL (Formulario) ---
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 20, 8, 20);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Texto Bienvenida
        JLabel lblBienvenida = new JLabel(textos.getString("registro.bienvenida"), SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        centerPanel.add(lblBienvenida, gbc);
        gbc.insets = new Insets(8, 20, 8, 20); // Reset insets

        // 2. Campos de texto con Placeholders
        JTextField txtCorreo = crearCampo(textos.getString("registro.correo"), false);
        gbc.gridy = 1; centerPanel.add(txtCorreo, gbc);

        JPasswordField txtPass = (JPasswordField) crearCampo(textos.getString("registro.pass"), true);
        gbc.gridy = 2; centerPanel.add(txtPass, gbc);

        JPasswordField txtConfirmPass = (JPasswordField) crearCampo(textos.getString("registro.confirmPass"), true);
        gbc.gridy = 3; centerPanel.add(txtConfirmPass, gbc);

        JTextField txtNombre = crearCampo(textos.getString("registro.nombre"), false);
        gbc.gridy = 4; centerPanel.add(txtNombre, gbc);

        JTextField txtApellidos = crearCampo(textos.getString("registro.apellidos"), false);
        gbc.gridy = 5; centerPanel.add(txtApellidos, gbc);

        // 3. Desplegable (JComboBox)
        String[] grados = {
            textos.getString("registro.gradoPrompt"),
            textos.getString("grado.informatica"),
            textos.getString("grado.derecho"),
            textos.getString("grado.medicina"),
            textos.getString("grado.veterinaria")
        };
        JComboBox<String> comboGrado = new JComboBox<>(grados);
        comboGrado.setPreferredSize(new Dimension(250, 40));
        comboGrado.setBackground(Color.WHITE);
        gbc.gridy = 6;
        centerPanel.add(comboGrado, gbc);

        // 4. Checkbox Términos
        JCheckBox checkTerminos = new JCheckBox(textos.getString("registro.terminos"));
        checkTerminos.setOpaque(false);
        checkTerminos.setFont(new Font("Arial", Font.BOLD, 11));
        gbc.gridy = 7;
        centerPanel.add(checkTerminos, gbc);

        // 5. Botón Registrar
        JButton btnRegistrar = new JButton(textos.getString("registro.boton"));
        btnRegistrar.setBackground(Color.WHITE);
        btnRegistrar.setForeground(Color.BLACK);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            new EmptyBorder(10, 20, 10, 20)
        ));
        gbc.gridy = 8;
        gbc.insets = new Insets(30, 60, 20, 60);
        centerPanel.add(btnRegistrar, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // --- PANEL INFERIOR (Botón Atrás) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        bottomPanel.setOpaque(false);
        JButton btnAtras = new JButton("⬅");
        btnAtras.setFont(new Font("Arial", Font.BOLD, 18));
        btnAtras.setBackground(new Color(160, 255, 90));
        btnAtras.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bottomPanel.add(btnAtras);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- LÓGICA ---
        btnAtras.addActionListener(e -> mainFrame.showView("LOGIN"));
        
        btnRegistrar.addActionListener(e -> {
            if(checkTerminos.isSelected()) {
                System.out.println("Registro completado. Navegando a pantalla principal...");
                // mainFrame.showView("PRINCIPAL"); 
            } else {
                JOptionPane.showMessageDialog(this, "Debe aceptar los términos");
            }
        });
    }

    // Método auxiliar para crear campos con placeholder (reutilizando tu lógica)
    private JTextField crearCampo(String placeholder, boolean esPassword) {
        JTextField campo = esPassword ? new JPasswordField(placeholder) : new JTextField(placeholder);
        campo.setPreferredSize(new Dimension(250, 40));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE), 
            new EmptyBorder(5, 10, 5, 10)
        ));

        if (esPassword) ((JPasswordField) campo).setEchoChar((char) 0);

        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    if (esPassword) ((JPasswordField) campo).setEchoChar('•');
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    if (esPassword) ((JPasswordField) campo).setEchoChar((char) 0);
                }
            }
        });
        return campo;
    }
}