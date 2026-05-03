package gui;

import main.MainFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ResourceBundle;

public class ProfesorDetalle extends JPanel {

    private MainFrame mainFrame;
    private final Color VERDE_FONDO = new Color(180, 255, 104);
    private final Color GRIS_TARJETA = new Color(220, 220, 220);
    private final Color BLANCO_BOTON = Color.WHITE;
    private final Color VERDE_TEXTO_VAL = new Color(50, 205, 50); // Verde intenso para "4.4/5"

    public ProfesorDetalle(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        ResourceBundle textos = mainFrame.getBundle();

        setBackground(VERDE_FONDO);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // Insets laterales generales para que no toque los bordes del móvil
        gbc.insets = new Insets(10, 25, 10, 25); 

        // --- 1. HEADER (Foto + Nombre + Departamento) ---
        JPanel header = new JPanel(new GridBagLayout());
        header.setOpaque(false);
        GridBagConstraints hGbc = new GridBagConstraints();

        // Icono de Usuario (grande)
        ImageIcon userIcon = loadScaledIcon(34, 34, "/resources/prof-user.PNG");
        JLabel userLabel = (userIcon != null) ? new JLabel(userIcon) : new JLabel("👤");
        hGbc.gridx = 0; hGbc.weightx = 0; hGbc.anchor = GridBagConstraints.NORTHWEST;
        hGbc.insets = new Insets(0, 0, 0, 15);
        header.add(userLabel, hGbc);

        // Nombre y Departamento
        JPanel textHeader = new JPanel(new GridLayout(2, 1));
        textHeader.setOpaque(false);
        JLabel name = new JLabel(textos.getString("profesor.nombre"));
        name.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel dept = new JLabel(textos.getString("profesor.departamento"));
        dept.setFont(new Font("SansSerif", Font.PLAIN, 12));
        textHeader.add(name);
        textHeader.add(dept);

        hGbc.gridx = 1; hGbc.weightx = 1.0; hGbc.fill = GridBagConstraints.HORIZONTAL;
        hGbc.insets = new Insets(0, 0, 0, 0);
        header.add(textHeader, hGbc);

        gbc.gridy = 0;
        // Subimos el bloque para acercar el nombre al margen superior
        gbc.insets = new Insets(44, 25, 15, 25);
        contentPanel.add(header, gbc);

        // --- 2. VALORACIÓN (Cápsula Blanca) ---
        JPanelRedondeado valCard = new JPanelRedondeado(25);
        valCard.setBackground(BLANCO_BOTON);
        valCard.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 8));

        JLabel val = new JLabel("<html>" + textos.getString("profesor.valoracion") + ": <b><font color='#32CD32'>4.4</font>/5</b></html>");
        val.setFont(new Font("SansSerif", Font.PLAIN, 12));
        val.setOpaque(false);
        valCard.add(val);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 60, 5, 60); // Insets laterales para que parezca una cápsula
        contentPanel.add(valCard, gbc);

        // --- 3. DATOS DE CONTACTO (Tarjeta Gris) ---
        // Componente personalizado para esquinas redondeadas
        JPanelRedondeado datos = new JPanelRedondeado(15);
        datos.setBackground(GRIS_TARJETA);
        datos.setLayout(new BoxLayout(datos, BoxLayout.Y_AXIS));
        datos.setBorder(new EmptyBorder(15, 14, 15, 14));

        String info = "<html>" + textos.getString("profesor.despacho") + ": " + textos.getString("profesor.despacho_valor") + "<br>"
               + textos.getString("profesor.correo") + ": " + textos.getString("profesor.correo_valor") + "<br>"
               + textos.getString("profesor.telefono") + ": " + textos.getString("profesor.telefono_valor") + "<br>"
               + textos.getString("profesor.tutorias") + ": " + textos.getString("profesor.tutorias_valor") + "</html>";
        
        JLabel lblInfo = new JLabel(info);
        lblInfo.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblInfo.setForeground(Color.DARK_GRAY);
        datos.add(lblInfo);

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 4, 15, 4);
        contentPanel.add(datos, gbc);

        // --- 4. ASIGNATURAS IMPARTIDAS (Tarjeta Gris con Título) ---
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 4, 15, 4);
        contentPanel.add(crearTarjetaGrisConTitulo(textos.getString("profesor.asignaturas_impartidas"), 
            new String[]{textos.getString("profesor.asig_algebra"), textos.getString("profesor.asig_calculo"), textos.getString("profesor.asig_fundamentos")}), gbc);

        // --- 5. CONSIDERACIONES (Tarjeta Gris con Título e Iconos) ---
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 4, 15, 4);
        contentPanel.add(crearTarjetaGrisConsideraciones(textos.getString("profesor.consideraciones"), textos), gbc);

        // --- 6. BOTÓN PUNTUAR (Cápsula Blanca con Sombra) ---
        BotonRedondeado btnPuntuar = new BotonRedondeado(textos.getString("profesor.puntuar"));
        btnPuntuar.setBackground(BLANCO_BOTON);
        btnPuntuar.setPreferredSize(new Dimension(140, 38));
        btnPuntuar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnPuntuar.addActionListener(e -> mainFrame.showView("VALORACION"));

        gbc.gridy = 5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(25, 0, 15, 0); // Separación y centrado
        contentPanel.add(btnPuntuar, gbc);

        // --- 7. NAVEGACIÓN INFERIOR (Inicio + Atrás) ---
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
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

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        add(bottom, BorderLayout.SOUTH);
    }

    // --- MÉTODOS DE UTILIDAD PARA CREAR TARJETAS ---

    private JPanel crearTarjetaGrisConTitulo(String titulo, String[] items) {
        JPanelRedondeado tarjeta = new JPanelRedondeado(15);
        tarjeta.setBackground(GRIS_TARJETA);
        tarjeta.setLayout(new BorderLayout());
        
        // Título de la tarjeta (con línea divisoria)
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitulo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK), // Línea negra abajo
            new EmptyBorder(10, 20, 10, 20)
        ));
        tarjeta.add(lblTitulo, BorderLayout.NORTH);

        // Lista de items
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(10, 20, 15, 20));

        for (String item : items) {
            JLabel lblItem = new JLabel(item);
            lblItem.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblItem.setBorder(new EmptyBorder(5, 0, 5, 0));
            content.add(lblItem);
        }
        tarjeta.add(content, BorderLayout.CENTER);
        
        return tarjeta;
    }

    private JPanel crearTarjetaGrisConsideraciones(String titulo, ResourceBundle textos) {
        JPanelRedondeado tarjeta = new JPanelRedondeado(15);
        tarjeta.setBackground(GRIS_TARJETA);
        tarjeta.setLayout(new BorderLayout());
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitulo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),
            new EmptyBorder(10, 20, 10, 20)
        ));
        tarjeta.add(lblTitulo, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(10, 20, 15, 20));

        String[] items = {
            textos.getString("profesor.considera_pasa_lista"),
            textos.getString("profesor.considera_explica_bien"),
            textos.getString("profesor.considera_revisa_practicas"),
            textos.getString("profesor.considera_hace_parciales")
        };
        ImageIcon check = loadScaledIcon(18, 18, "/resources/check.png"); // Un PNG de un círculo verde con check

        for (String item : items) {
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.setBorder(new EmptyBorder(4, 0, 4, 0));

            JLabel lblItem = new JLabel(item);
            lblItem.setFont(new Font("SansSerif", Font.PLAIN, 12));
            
            JLabel lblCheck = (check != null) ? new JLabel(check) : new JLabel("✓");

            row.add(lblItem, BorderLayout.WEST);
            row.add(lblCheck, BorderLayout.EAST);
            content.add(row);
        }
        tarjeta.add(content, BorderLayout.CENTER);
        
        return tarjeta;
    }

    private ImageIcon loadScaledIcon(int width, int height, String... paths) {
        for (String path : paths) {
            try {
                java.net.URL url = getClass().getResource(path);
                if (url != null) {
                    return new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                }
            } catch (Exception ignored) {}
        }
        return null;
    }
}