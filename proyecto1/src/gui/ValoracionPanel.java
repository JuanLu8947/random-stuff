package gui;

import main.MainFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ResourceBundle;

public class ValoracionPanel extends JPanel {

    private MainFrame mainFrame;
    private final Color VERDE_FONDO = new Color(180, 255, 104);
    private final Color GRIS_CLARITO = new Color(220, 220, 220);
    private final Color AZUL_OSCURO = new Color(30, 30, 80);
    private final Color AMARILLO_ESTRELLA = new Color(255, 200, 0);
    private final Color GRIS_ESTRELLA = new Color(190, 190, 190);

    public ValoracionPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        ResourceBundle textos = mainFrame.getBundle();

        setBackground(VERDE_FONDO);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 16, 5, 16);

        // --- 1. TÍTULO ---
        JLabel title = new JLabel(textos.getString("valoracion.titulo"), SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 17));
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 12, 5, 12); // Margen para la "isla"
        contentPanel.add(title, gbc);

        // --- 2. SELECTOR DE ASIGNATURA ---
        String[] asigs = {
            textos.getString("valoracion.asignatura_placeholder"),
            textos.getString("profesor.asig_algebra"),
            textos.getString("profesor.asig_calculo")
        };
        JComboBox<String> combo = new JComboBox<>(asigs);
        combo.setPreferredSize(new Dimension(160, 35));
        combo.setBackground(GRIS_CLARITO);
        // Quitamos el borde por defecto para que parezca más moderno
        combo.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 14, 5, 14);
        contentPanel.add(combo, gbc);

        // --- 3. ESTRELLAS ---
        JLabel lblPregunta = new JLabel(textos.getString("valoracion.pregunta"), SwingConstants.CENTER);
        lblPregunta.setFont(new Font("SansSerif", Font.BOLD, 13));
        
        gbc.gridy = 2;
        contentPanel.add(lblPregunta, gbc);

        JPanel estrellasIconos = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        estrellasIconos.setOpaque(false);
        ButtonGroup starGroup = new ButtonGroup();
        final int[] rating = {0};
        for(int i=0; i<5; i++) {
            final int starValue = i + 1;
            JToggleButton estrella = new JToggleButton("☆");
            estrella.setFont(new Font("Serif", Font.PLAIN, 30));
            estrella.setFocusPainted(false);
            estrella.setBorderPainted(false);
            estrella.setContentAreaFilled(false);
            estrella.setOpaque(false);
            estrella.setForeground(GRIS_ESTRELLA);
            estrella.addActionListener(e -> {
                rating[0] = starValue;
                updateStars(starGroup, estrellasIconos, rating[0]);
            });
            starGroup.add(estrella);
            estrellasIconos.add(estrella);
        }
        gbc.gridy = 3;
        contentPanel.add(estrellasIconos, gbc);

        // --- 4. ASPECTOS DESTACADOS (Cuadro Blanco) ---
        JLabel lblAspectos = new JLabel(textos.getString("valoracion.aspectos"));
        lblAspectos.setFont(new Font("SansSerif", Font.BOLD, 13));
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 12, 5, 12);
        contentPanel.add(lblAspectos, gbc);

        JPanelRedondeado cuadroBlanco = new JPanelRedondeado(20);
        cuadroBlanco.setBackground(Color.WHITE);
        cuadroBlanco.setLayout(new GridLayout(0, 1, 0, 5));
        cuadroBlanco.setBorder(new EmptyBorder(15, 14, 15, 14));

        String[] aspectos = {
            textos.getString("profesor.considera_pasa_lista"),
            textos.getString("valoracion.aspecto_lee_pdf"),
            textos.getString("valoracion.aspecto_examen_dificil"),
            textos.getString("valoracion.aspecto_asistencia_obligatoria"),
            textos.getString("valoracion.aspecto_examen_test"),
            textos.getString("valoracion.aspecto_muy_practico"),
            textos.getString("valoracion.aspecto_buen_material")
        };
        
        for (String asp : aspectos) {
            JCheckBox cb = new JCheckBox(asp);
            cb.setOpaque(false);
            cb.setHorizontalTextPosition(SwingConstants.LEFT);
            // Esto hace que el check aparezca a la derecha
            JPanel p = new JPanel(new BorderLayout());
            p.setOpaque(false);
            p.add(new JLabel(asp), BorderLayout.WEST);
            p.add(new JCheckBox(), BorderLayout.EAST);
            cuadroBlanco.add(p);
        }

        gbc.gridy = 5;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(cuadroBlanco, gbc);

        // --- 5. COMENTARIOS (Cuadro Gris) ---
        JTextArea txtComentario = new JTextArea(textos.getString("valoracion.comentario_placeholder"));
        txtComentario.setFont(new Font("SansSerif", Font.ITALIC, 12));
        txtComentario.setForeground(Color.GRAY);
        txtComentario.setBackground(GRIS_CLARITO);
        txtComentario.setBorder(new EmptyBorder(10, 10, 5, 10));
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);

        JPanelRedondeado wrapComentario = new JPanelRedondeado(20);
        wrapComentario.setBackground(GRIS_CLARITO);
        wrapComentario.setLayout(new BorderLayout());
        wrapComentario.add(txtComentario);

        gbc.gridy = 6;
        gbc.insets = new Insets(5, 12, 5, 12);
        contentPanel.add(wrapComentario, gbc);

        // --- 6. BOTÓN ENVIAR ---
        JLabel lblAnonimo = new JLabel(textos.getString("valoracion.anonimo"), SwingConstants.CENTER);
        lblAnonimo.setFont(new Font("SansSerif", Font.BOLD, 12));
        gbc.gridy = 7;
        gbc.weighty = 0;
        contentPanel.add(lblAnonimo, gbc);

        BotonRedondeado btnEnviar = new BotonRedondeado(textos.getString("valoracion.enviar"));
        btnEnviar.setBackground(AZUL_OSCURO);
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setPreferredSize(new Dimension(105, 38));
        // Al pulsar enviar mostramos la pantalla de confirmación
        btnEnviar.addActionListener(e -> mainFrame.showView("VALORACION_ENVIADA"));
        
        JPanel btnWrap = new JPanel();
        btnWrap.setOpaque(false);
        btnWrap.add(btnEnviar);
        
        gbc.gridy = 8;
        contentPanel.add(btnWrap, gbc);

        // --- 7. NAVEGACIÓN INFERIOR ---
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottom.setOpaque(false);
        
        BotonRedondeado inicio = new BotonRedondeado(textos.getString("config.home"));
        inicio.setBackground(new Color(230, 255, 210)); // Verde clarito
        inicio.setPreferredSize(new Dimension(100, 36));
        inicio.addActionListener(e -> mainFrame.showView("MAIN_ESTUDIANTE"));

        
        BotonRedondeado back = new BotonRedondeado(" ← ");
        back.setBackground(new Color(230, 255, 210)); // Verde clarito
        back.setPreferredSize(new Dimension(65, 36));
        back.addActionListener(e -> mainFrame.goBack());

        bottom.add(inicio);
        bottom.add(back);

        gbc.gridy = 9;
        gbc.insets = new Insets(10, 0, 8, 0);
        contentPanel.add(bottom, gbc);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void updateStars(ButtonGroup starGroup, JPanel estrellasIconos, int rating) {
        int index = 1;
        for (java.util.Enumeration<AbstractButton> buttons = starGroup.getElements(); buttons.hasMoreElements(); index++) {
            AbstractButton button = buttons.nextElement();
            if (index <= rating) {
                button.setText("★");
                button.setForeground(AMARILLO_ESTRELLA);
                button.setSelected(true);
            } else {
                button.setText("☆");
                button.setForeground(GRIS_ESTRELLA);
                button.setSelected(false);
            }
        }
        estrellasIconos.revalidate();
        estrellasIconos.repaint();
    }
}