package gui;

import main.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class FelicitacionMadre extends JPanel {

    private final MainFrame mainFrame;

    public FelicitacionMadre(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        // Esta clase ahora es un componente JPanel para poder añadirse al CardLayout
        setLayout(new BorderLayout());
        // Más ancho solicitado por el usuario
        setPreferredSize(new Dimension(900, 600));

        // Añadir el panel principal con diseño personalizado
        add(new PanelTarjeta(), BorderLayout.CENTER);
    }

    // Clase interna para el panel con fondo degradado y diseño
    class PanelTarjeta extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fondo Degradado (Rosa suave a Blanco)
            GradientPaint gp = new GradientPaint(0, 0, new Color(255, 182, 193), 0, getHeight(), Color.WHITE);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Dibujar algunos corazones decorativos de fondo
            g2d.setColor(new Color(255, 105, 180, 50)); // Rosa fuerte con transparencia
            drawHeart(g2d, 50, 50, 30);
            drawHeart(g2d, 400, 100, 50);
            drawHeart(g2d, 350, 450, 40);
        }

        // Método auxiliar para dibujar un corazón simple
        private void drawHeart(Graphics2D g2d, int x, int y, int size) {
            g2d.fillOval(x, y, size, size);
            g2d.fillOval(x + size / 2, y, size, size);
            int[] xPoints = {x, x + size, x + size * 2 / 2 - (size/20), x + size / 2}; // Ajuste manual
            int[] yPoints = {y + size / 2, y + size / 2, y + size, y + size}; // Ajuste manual
            g2d.fillPolygon(xPoints, yPoints, 4);
            // Para simplicidad en este ejemplo, usamos círculos solapados.
        }

        public PanelTarjeta() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            // Espaciado inicial
            add(Box.createVerticalStrut(50));

            // Etiqueta de Título
            JLabel lblTitulo = new JLabel("¡Gracias por todo,Mamá!");
            lblTitulo.setFont(new Font("Serif", Font.BOLD, 22));
            lblTitulo.setForeground(new Color(199, 21, 133)); // Magenta oscuro
            lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(lblTitulo);

            add(Box.createVerticalStrut(30));

            // Icono o Imagen (Simulada con un emoji grande en un JLabel)
            JLabel lblIcono = new JLabel("❤️");
            lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
            lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(lblIcono);

            add(Box.createVerticalStrut(30));

            // Mensaje principal
String mensaje = "<html><div style='text-align: center;'>"
               + "Eres el corazón de nuestra familia.<br>"
               + "Tu amor infinito y tu fuerza nos guían cada día.<br><br>"
               + "<b>¡Que tengas un día maravilloso!</b>"
               + "</div></html>";

JLabel lblMensaje = new JLabel(mensaje);
lblMensaje.setFont(new Font("Arial", Font.ITALIC, 18));

// --- ESTO ES LO QUE SOLUCIONA TU PROBLEMA ---
lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra el componente en el BoxLayout
lblMensaje.setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto dentro del componente
lblMensaje.setMaximumSize(new Dimension(Integer.MAX_VALUE, lblMensaje.getPreferredSize().height)); 
// --------------------------------------------

add(lblMensaje);

            add(Box.createVerticalGlue());

            // Botón de interacción
            JButton btnAmor = new JButton("Haz clic para enviar un abrazo");
            btnAmor.setFont(new Font("Arial", Font.BOLD, 14));
            btnAmor.setBackground(new Color(255, 105, 180));
            btnAmor.setForeground(Color.WHITE);
            btnAmor.setFocusPainted(false);
            btnAmor.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btnAmor.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            btnAmor.addActionListener(e -> {
                JOptionPane.showMessageDialog(this,
                        "¡Abrazo virtual enviado con éxito! \nTe quiero mucho, Mamá.",
                        "Feliz Día de la Madre",
                        JOptionPane.PLAIN_MESSAGE);
                // Tras pulsar OK, navegar a la pantalla de login si existe el MainFrame
                if (mainFrame != null) {
                    mainFrame.showView("LOGIN");
                }
            });
            
            add(btnAmor);
            add(Box.createVerticalStrut(50));
        }
    }

    public static void main(String[] args) {
        // Permite ejecutar la tarjeta de forma independiente para pruebas locales
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("¡Feliz Día de la Madre!");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(new FelicitacionMadre(null));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}