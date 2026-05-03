package gui;

import main.MainFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ResourceBundle;

public class Asignaturas extends JPanel {

    private MainFrame mainFrame;
    private final Color VERDE_FONDO = new Color(172, 255, 100);
    private final Color VERDE_BOTON = new Color(220, 255, 180);

    public Asignaturas(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        ResourceBundle textos = mainFrame.getBundle();

        setBackground(VERDE_FONDO);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 25);

        // --- 1. TÍTULO DE LA ASIGNATURA ---
        // En la imagen aparece centrado y arriba (debajo de la "isla" del móvil)
        JLabel title = new JLabel(textos.getString("asignaturas.titulo"), SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        gbc.gridy = 0;
        gbc.insets = new Insets(40, 20, 20, 20); // Más margen superior para la "isla"
        add(title, gbc);

        // --- 2. ETIQUETA "PROFESORES" Y LISTA (panel izquierdo alineado) ---
        String[] profes = {textos.getString("profesor.nombre"), textos.getString("profesor.nombre_2")};
        JList<String> list = new JList<>(profes);
        // Usamos un renderizador similar al de asignaturas pero con icono de persona
        list.setCellRenderer(new ProfesorListRenderer());
        list.setBackground(VERDE_FONDO);
        list.setFixedCellHeight(55);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                String seleccionado = list.getSelectedValue();
                if (textos.getString("profesor.nombre").equals(seleccionado)) {
                    mainFrame.showView("PROFESOR_DETALLE");
                }
            }
        });

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        // Creamos un panel vertical alineado a la izquierda que contendrá la etiqueta y la lista
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        leftPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel lblProf = new JLabel(textos.getString("asignaturas.profesores"));
        lblProf.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblProf.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblProf.setBorder(new EmptyBorder(0, 2, 8, 0));
        leftPanel.add(lblProf);

        // Ajustamos el tamaño del scroll para que no añada márgenes extra
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(260, 180));
        leftPanel.add(scroll);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0); // Sin insets para pegar al borde del área
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(leftPanel, gbc);

        // --- 4. NAVEGACIÓN INFERIOR ---
        // FlowLayout con alineación a la derecha para que los botones queden como en la foto
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        bottom.setOpaque(false);

        BotonRedondeado inicio = new BotonRedondeado(textos.getString("config.home"));
        inicio.setBackground(VERDE_BOTON);
        inicio.setPreferredSize(new Dimension(100, 38));
        inicio.addActionListener(e -> mainFrame.showView("MAIN_ESTUDIANTE"));

        // El botón de atrás es circular en la imagen
        BotonRedondeado back = new BotonRedondeado(" ← ");
        back.setBackground(VERDE_BOTON);
        back.setPreferredSize(new Dimension(55, 38));
        back.addActionListener(e -> mainFrame.goBack());

        bottom.add(inicio);
        bottom.add(back);

        gbc.gridy = 3;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // Reducimos el margen inferior para acercar los botones al borde
        gbc.insets = new Insets(0, 5, 6, 15);
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(bottom, gbc);
    }
}