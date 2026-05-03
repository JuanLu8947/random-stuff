package main;

import gui.LoginPanel;
import gui.FelicitacionMadre;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Deque;
import java.util.ArrayDeque;
import javax.swing.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ResourceBundle bundle;
    private Deque<String> history = new ArrayDeque<>();
    private String currentView = null;

    public MainFrame() {
        // 1. Establecer el idioma por defecto (Español de España)
        Locale.setDefault(new Locale("es", "ES"));
        
        // 2. Cargar el Bundle. Busca "Bundle_es.properties" en la carpeta "bundle"
        bundle = ResourceBundle.getBundle("bundle.Bundle", Locale.getDefault());

        setTitle("UCO-Reviews - Interfaz");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Usamos CardLayout para apilar las pantallas y mostrar solo una
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        buildViews();

        add(mainPanel);
    }

    private void buildViews() {
        mainPanel.removeAll();

        // Añadimos las pantallas al contenedor.
        mainPanel.add(new LoginPanel(this), "LOGIN");
        mainPanel.add(new FelicitacionMadre(this), "FELICITACION_MADRE");

        mainPanel.revalidate();
        mainPanel.repaint();
        // Mostrar la vista inicial sin añadir entrada al historial
        showView("LOGIN", false);
    }

    // Método que usan los paneles para pedirle al MainFrame que cambie de vista
    public void showView(String viewName) {
        showView(viewName, true);
    }

    public void showView(String viewName, boolean addToHistory) {
        if (addToHistory && currentView != null && !currentView.equals(viewName)) {
            history.push(currentView);
        }
        cardLayout.show(mainPanel, viewName);
        currentView = viewName;
    }

    public void goBack() {
        if (history.isEmpty()) return;
        String previous = history.pop();
        showView(previous, false);
    }

    public static void main(String[] args) {
        // Evita crear ventanas Swing en entornos sin servidor gráfico.
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("Entorno sin interfaz grafica (headless): no se puede abrir la UI Swing.");
            System.err.println("Ejecuta esta aplicacion en tu equipo local con escritorio o con X11/Xvfb.");
            return;
        }

        // Iniciar la aplicación
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

    // Método para que los paneles accedan al diccionario de textos
    public ResourceBundle getBundle() {
        return bundle;
    }

    public void changeLanguage(Locale locale, String viewToShow) {
        Locale.setDefault(locale);
        bundle = ResourceBundle.getBundle("bundle.Bundle", locale);
        buildViews();
        // Mostrar la vista solicitada sin añadir al historial (cambio de idioma no debe crear entrada)
        showView(viewToShow, false);
    }
}