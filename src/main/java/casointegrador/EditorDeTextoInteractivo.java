package casointegrador;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


class PortadaInicio extends JFrame {

    public PortadaInicio() {
        super("Portada de Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Editor de Texto Interactivo");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        JButton startButton = new JButton("Iniciar Editor de Texto");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirEditorDeTexto();
            }
        });
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(startButton);

        add(panel);
        setVisible(true);
    }
    private void abrirEditorDeTexto() {
        EditorDeTextoInteractivo editor = new EditorDeTextoInteractivo();
        dispose(); // Cierra la portada de inicio cuando se abre el editor de texto
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PortadaInicio::new);
    }
}

public class EditorDeTextoInteractivo extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private File archivoActual;
    private List<Contacto> listaContactos;
    private List<EditorDeTextoInteractivo> ventanas;
    public EditorDeTextoInteractivo() {
        super("Editor de Texto Interactivo");

        textArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        fileChooser = new JFileChooser();
        archivoActual = null;
        listaContactos = new ArrayList<>();
        ventanas = new ArrayList<>();

        JButton guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(e -> guardarDocumento());

        JButton listarButton = new JButton("Listar Documentos");
        listarButton.addActionListener(e -> mostrarDocumentos());

        JButton compararButton = new JButton("Comparar Archivos");
        compararButton.addActionListener(e -> compararArchivos());

        JButton contarPalabrasButton = new JButton("Contar Palabras");
        contarPalabrasButton.addActionListener(e -> contarPalabras());

        JButton buscarPalabraButton = new JButton("Buscar Palabra");
        buscarPalabraButton.addActionListener(e -> buscarPalabra());

        JButton gestionarContactosButton = new JButton("Gestionar Contactos");
        gestionarContactosButton.addActionListener(e -> gestionarContactos());

        JButton verContactosButton = new JButton("Ver Contactos");
        verContactosButton.addActionListener(e -> verContactos());

        JButton abrirVentanaButton = new JButton("Abrir Nueva Ventana");
        abrirVentanaButton.addActionListener(e -> abrirNuevaVentana());

        JButton dibujarButton = new JButton("Dibujar");
        dibujarButton.addActionListener(e -> dibujar());

        JButton validarEmailButton = new JButton("Validar Email");
        validarEmailButton.addActionListener(e -> validarEmail());

        JButton cambiarTamanoTextoButton = new JButton("Cambiar Tamaño del Texto");
        cambiarTamanoTextoButton.addActionListener(e -> cambiarTamanoTexto());

        JButton cambiarColorTextoButton = new JButton("Cambiar Color del Texto");
        cambiarColorTextoButton.addActionListener(e -> cambiarColorTexto());

        JButton estadisticasTextoButton = new JButton("Estadísticas de Texto");
        estadisticasTextoButton.addActionListener(e -> estadisticasTexto());

        JButton imprimirButton = new JButton("Imprimir");
        imprimirButton.addActionListener(e -> imprimir());

        JButton verTodasLasFuncionesButton = new JButton("Ver Todas las Funciones");
        verTodasLasFuncionesButton.addActionListener(e -> verTodasLasFunciones());
        JMenuBar menuBar = new JMenuBar();
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem guardarMenuItem = new JMenuItem("Guardar");
        guardarMenuItem.addActionListener(e -> guardarDocumento());
        archivoMenu.add(guardarMenuItem);
        JMenuItem abrirMenuItem = new JMenuItem("Abrir");
        abrirMenuItem.addActionListener(e -> mostrarDocumentos());
        archivoMenu.add(abrirMenuItem);
        JMenuItem imprimirMenuItem = new JMenuItem("Imprimir");
        imprimirMenuItem.addActionListener(e -> imprimir());
        archivoMenu.add(imprimirMenuItem);
        menuBar.add(archivoMenu);
        setJMenuBar(menuBar);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.add(guardarButton);
        buttonPanel.add(listarButton);
        buttonPanel.add(compararButton);
        buttonPanel.add(contarPalabrasButton);
        buttonPanel.add(buscarPalabraButton);
        buttonPanel.add(gestionarContactosButton);
        buttonPanel.add(verContactosButton);
        buttonPanel.add(abrirVentanaButton);
        buttonPanel.add(dibujarButton);
        buttonPanel.add(validarEmailButton);
        buttonPanel.add(cambiarTamanoTextoButton);
        buttonPanel.add(cambiarColorTextoButton);
        buttonPanel.add(estadisticasTextoButton);
        buttonPanel.add(imprimirButton);
        buttonPanel.add(verTodasLasFuncionesButton);
