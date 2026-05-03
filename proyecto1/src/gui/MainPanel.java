package gui;

import main.MainFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ResourceBundle;

public class MainPanel extends JPanel {

    private MainFrame mainFrame;
    private final Color VERDE_FONDO = new Color(180,255,104);
    private final Color VERDE_BOTON = new Color(212,255,189); // Verde clarito solicitado
    private final Color GRIS_BUSCADOR = new Color(235, 230, 240);
    private final Color BLANCO_TRANSLUCIDO = new Color(255, 255, 255, 150);

    public MainPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        ResourceBundle textos = mainFrame.getBundle();

        setBackground(VERDE_FONDO);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 15, 5, 15);

        // --- 1. HEADER ---
        JPanel header = new JPanel(new BorderLayout(8, 0));
        header.setOpaque(false);

        ImageIcon logoIcon = loadScaledIcon(35, 35, "/resources/logo-ing-informtica.png");
        header.add(new JLabel(logoIcon != null ? logoIcon : new ImageIcon()), BorderLayout.WEST);

        // Título ajustado para evitar puntos suspensivos
        JLabel title = new JLabel(textos.getString("grado.informatica"));
        title.setFont(new Font("SansSerif", Font.BOLD, 13)); 
        header.add(title, BorderLayout.CENTER);

        // Notificaciones -> Acción: CONFIGURACION
        JButton notif = crearBotonIcono("/resources/notif.png", "/resources/notif.PNG", "🔔", 22);
        notif.addActionListener(e -> mainFrame.showView("CONFIGURACION"));
        header.add(notif, BorderLayout.EAST);

        gbc.gridy = 0;
        add(header, gbc);

        // --- 2. BUSCADOR Y SETTINGS ---
        JPanel searchRow = new JPanel(new BorderLayout(10, 0)); 
        searchRow.setOpaque(false);

        JTextField search = new JTextField("Buscar") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GRIS_BUSCADOR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        search.setOpaque(false);
        search.setBorder(new EmptyBorder(0, 15, 0, 5));
        search.setPreferredSize(new Dimension(150, 35)); 
        searchRow.add(search, BorderLayout.CENTER);

        // Settings -> Acción: CONFIGURACION
        JButton settings = crearBotonIcono("/resources/settings.png", "/resources/settings.PNG", "⚙", 24);
        settings.addActionListener(e -> mainFrame.showView("CONFIGURACION"));
        searchRow.add(settings, BorderLayout.EAST);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 15, 10, 15);
        add(searchRow, gbc);

        // --- 3. ETIQUETA ASIGNATURAS ---
        JLabel lbl = new JLabel(textos.getString("subjects.label"));
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 20, 5, 15); // Más margen izquierdo (20)
        add(lbl, gbc);

        // --- 4. TABS (Estilo imagen_187ca0.png) ---
        JPanel tabsContainer = new JPanel(new GridLayout(1, 5)) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(BLANCO_TRANSLUCIDO);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        tabsContainer.setOpaque(false);
        tabsContainer.setPreferredSize(new Dimension(280, 40));

        String[] años = {"Todo", "1º", "2º", "3º", "4º"};
        for (int i = 0; i < años.length; i++) {
            JButton b = new JButton(años[i]);
            b.setFocusPainted(false);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.setFont(new Font("SansSerif", i == 0 ? Font.BOLD : Font.PLAIN, 12));
            b.setForeground(i == 0 ? new Color(100, 100, 255) : Color.DARK_GRAY);
            
            if (i == 0) {
                b.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(100, 100, 255)));
            }
            // Aquí puedes añadir ActionListeners para filtrar la JList en el futuro
            tabsContainer.add(b);
        }
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 15, 5, 15);
        add(tabsContainer, gbc);

        // --- 5. LISTA ---
        String[] subjects = {
            textos.getString("subjects.calculo"),
            textos.getString("subjects.economia"),
            textos.getString("subjects.legislacion"),
            textos.getString("subjects.estadistica"),
            textos.getString("subjects.poo"),
            textos.getString("subjects.algebra_lineal")
        };
        JList<String> list = new JList<>(subjects);
        list.setCellRenderer(new MobileListRenderer());
        list.setBackground(VERDE_FONDO);
        list.setFixedCellHeight(50);

        // Acción mejorada
        list.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
                String seleccionado = list.getSelectedValue();

                if (textos.getString("subjects.algebra_lineal").equals(seleccionado)) {
                    // Te lleva al nuevo panel que has creado
                    mainFrame.showView("ASIGNATURAS"); 
                }
            }
        });

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scroll, gbc);

        // --- 6. NAVEGACIÓN INFERIOR ---
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottom.setOpaque(false);

        // Inicio -> Acción: MAIN_ESTUDIANTE
        BotonRedondeado inicio = new BotonRedondeado(textos.getString("config.home"));
        inicio.setBackground(VERDE_BOTON); // Fondo verde clarito
        inicio.setPreferredSize(new Dimension(110, 36));
        inicio.addActionListener(e -> mainFrame.showView("MAIN_ESTUDIANTE"));

        // Atrás -> Acción: goBack
        BotonRedondeado back = new BotonRedondeado(" ← ");
        back.setBackground(VERDE_BOTON); // Fondo verde clarito
        back.setPreferredSize(new Dimension(65, 36));
        back.addActionListener(e -> mainFrame.goBack());

        bottom.add(inicio);
        bottom.add(back);

        gbc.gridy = 5;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(bottom, gbc);
    }

    private JButton crearBotonIcono(String p1, String p2, String bck, int s) {
        JButton btn = new JButton();
        ImageIcon icon = loadScaledIcon(s, s, p1, p2);
        if (icon != null) btn.setIcon(icon); else btn.setText(bck);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private ImageIcon loadScaledIcon(int w, int h, String... paths) {
        for (String p : paths) {
            try {
                java.net.URL url = getClass().getResource(p);
                if (url != null) return new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
            } catch (Exception ignored) {}
        }
        return null;
    }
}