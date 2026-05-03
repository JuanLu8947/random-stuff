package gui;

import javax.swing.*;

import gui.ConfiguracionPanel.RoundButton;
import gui.ConfiguracionPanel.TogglePill;
import gui.ConfiguracionPanel.ToggleSwitch;
import main.MainFrame;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConfiguracionPanel extends JPanel {

    private final MainFrame mainFrame;

    public ConfiguracionPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        // Usamos BorderLayout para separar el contenido del footer
        setLayout(new BorderLayout());
        setBackground(new Color(212, 250, 187)); // Color verde más claro como la foto

        ResourceBundle bundle = mainFrame.getBundle();

        // 1. PANEL CENTRAL (Contenido con layout null)
        JPanel contentPanel = new JPanel(null);
        contentPanel.setOpaque(false);
        
        // ===== TITULO =====
        JLabel title = new JLabel(bundle.getString("config.title"));
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(25, 30, 300, 40);
        contentPanel.add(title);

        ImageIcon rawUserIcon = null;
        try {
            rawUserIcon = new ImageIcon(getClass().getResource("/resources/logo_user.png"));
        } catch (Exception ex) {
            // fallback to text if resource not found
        }
        JLabel user;
        if (rawUserIcon != null) {
            Image img = rawUserIcon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
            user = new JLabel(new ImageIcon(img));
        } else {
            user = new JLabel(bundle.getString("config.user"));
            user.setFont(new Font("Serif", Font.PLAIN, 30));
        }
        user.setBounds(310, 30, 40, 40);
        contentPanel.add(user);

        JSeparator sep = new JSeparator();
        sep.setForeground(Color.BLACK);
        sep.setBounds(20, 80, 340, 2);
        contentPanel.add(sep);

        // ===== SECCIONES =====
        int startX = 40, startY = 90;
        contentPanel.add(createLabel(bundle.getString("config.account_settings"), startX, startY + 20, true));
        
        contentPanel.add(createLabel(bundle.getString("config.language"), startX, startY + 90, true));
        contentPanel.add(new TogglePill(190, startY + 85));

        contentPanel.add(createLabel(bundle.getString("config.notifications"), startX, startY + 180, true));
        // dejar un pequeño margen entre el switch y el borde derecho
        contentPanel.add(new ToggleSwitch(280, startY + 175));

        contentPanel.add(createLabel(bundle.getString("config.change_password"), startX, startY + 260, true));
        contentPanel.add(createLabel(bundle.getString("config.clear_cache"), startX, startY + 340, true));

        // ===== LOGOUT (Cerrar Sesión) =====
        JLabel logoutLabel = createLabelCentered(bundle.getString("config.logout"), startX, startY + 410, new Color(180, 40, 70));
        logoutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                mainFrame.showView("LOGIN");
            }
        });
        contentPanel.add(logoutLabel);

        add(contentPanel, BorderLayout.CENTER);

        // 2. PANEL FOOTER (Botones abajo)
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        centerPanel.setOpaque(false);
        RoundButton homeBtn = new RoundButton(bundle.getString("config.home"), 100, 40);
        homeBtn.addActionListener(e -> mainFrame.showView("MAIN_ESTUDIANTE"));
        centerPanel.add(homeBtn);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 20));
        rightPanel.setOpaque(false);
        RoundButton backBtn = new RoundButton(bundle.getString("config.back"), 60, 40); // Usando flecha unicode
        backBtn.addActionListener(e -> mainFrame.goBack());
        rightPanel.add(backBtn);

        footerPanel.add(centerPanel, BorderLayout.CENTER);
        footerPanel.add(rightPanel, BorderLayout.EAST);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text, int x, int y, boolean bold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", bold ? Font.BOLD : Font.PLAIN, 18));
        // aumentar ancho para evitar truncamientos
        label.setBounds(x, y, 300, 25);
        return label;
    }

    private JLabel createLabelCentered(String text, int x, int y, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(color);
        // ocupar todo el ancho del panel
        label.setBounds(x, y, 300, 25);
        return label;
    }

    // ================== CLASES INTERNAS (Sin cambios mayores, solo ajustes de tamaño) ==================
    
    class TogglePill extends JComponent {
        boolean left;
        public TogglePill(int x, int y) {
            left = Locale.getDefault().getLanguage().equals("es");
            setBounds(x, y, 160, 45);
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    left = !left;
                    repaint();

                    Locale selectedLocale = left ? new Locale("es", "ES") : Locale.ENGLISH;
                    Timer timer = new Timer(130, ev -> mainFrame.changeLanguage(selectedLocale, "CONFIGURACION"));
                    timer.setRepeats(false);
                    timer.start();
                }
            });
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(110, 125, 95));
            g2.fillRoundRect(0, 0, 150, 40, 40, 40);
            g2.setColor(new Color(135, 150, 120));
            if (left) g2.fillRoundRect(3, 3, 72, 34, 34, 34);
            else g2.fillRoundRect(75, 3, 72, 34, 34, 34);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            g2.drawString("Español", 18, 25);
            g2.drawString("English", 90, 25);
        }
    }

    class ToggleSwitch extends JComponent {
        boolean on = true;
        public ToggleSwitch(int x, int y) {
            setBounds(x, y, 55, 30);
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    on = !on; repaint();
                }
            });
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(on ? new Color(45, 35, 99) : Color.GRAY);
            g2.fillRoundRect(0, 0, 50, 28, 28, 28);
            g2.setColor(Color.WHITE);
            int circleX = on ? 24 : 3;
            g2.fillOval(circleX, 3, 22, 22);
        }
    }

    class RoundButton extends JButton {
        public RoundButton(String text, int width, int height) {
            super(text);
            setPreferredSize(new Dimension(width, height));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(185, 250, 130));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            g2.setColor(Color.BLACK);
            FontMetrics fm = g2.getFontMetrics();
            int tx = (getWidth() - fm.stringWidth(getText())) / 2;
            int ty = (getHeight() + fm.getAscent()) / 2 - 4;
            g2.drawString(getText(), tx, ty);
        }
    }
}