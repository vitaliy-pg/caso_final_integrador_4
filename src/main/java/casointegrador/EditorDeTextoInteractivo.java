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

        JLabel mousePositionLabel = new JLabel("Posición del Ratón:");
        add(mousePositionLabel, BorderLayout.NORTH);

        textArea.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePositionLabel.setText("Posición del Ratón: (" + e.getX() + ", " + e.getY() + ")");
            }
        });

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int value = e.getValue();
                int maximum = verticalScrollBar.getMaximum() - verticalScrollBar.getVisibleAmount();
                double percentage = (double) value / maximum * 100;
                setTitle("Editor de Texto Interactivo - " + String.format("%.2f", percentage) + "%");
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void guardarDocumento() {
        int resultado;
        if (archivoActual == null) {
            resultado = fileChooser.showSaveDialog(this);
        } else {
            resultado = JFileChooser.APPROVE_OPTION;
        }

        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                if (archivoActual == null) {
                    archivoActual = fileChooser.getSelectedFile();
                }
                FileWriter escritor = new FileWriter(archivoActual);
                escritor.write(textArea.getText());
                escritor.close();
                JOptionPane.showMessageDialog(this, "Documento guardado correctamente.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar el documento.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void mostrarDocumentos() {
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            abrirDocumento(archivoSeleccionado);
        }
    }

    private void abrirDocumento(File archivo) {
        try {
            FileReader lector = new FileReader(archivo);
            BufferedReader bufferedReader = new BufferedReader(lector);
            String linea;
            StringBuilder contenido = new StringBuilder();
            while ((linea = bufferedReader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
            textArea.setText(contenido.toString());
            bufferedReader.close();
            archivoActual = archivo;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al abrir el documento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void compararArchivos() {
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setDialogTitle("Seleccione dos archivos para comparar");
        fileChooser.setApproveButtonText("Comparar");
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File[] archivos = fileChooser.getSelectedFiles();
            if (archivos.length == 2) {
                try {
                    Scanner scanner1 = new Scanner(archivos[0]);
                    Scanner scanner2 = new Scanner(archivos[1]);
                    StringBuilder diff = new StringBuilder();
                    while (scanner1.hasNext() && scanner2.hasNext()) {
                        String linea1 = scanner1.nextLine();
                        String linea2 = scanner2.nextLine();
                        if (!linea1.equals(linea2)) {
                            diff.append("Diferencia en archivo 1: ").append(linea1).append("\n");
                            diff.append("Diferencia en archivo 2: ").append(linea2).append("\n");
                        }
                    }
                    if (!scanner1.hasNext() && scanner2.hasNext()) {
                        diff.append("Archivo 2 tiene más líneas que el archivo 1");
                    } else if (scanner1.hasNext() && !scanner2.hasNext()) {
                        diff.append("Archivo 1 tiene más líneas que el archivo 2");
                    }
                    if (diff.length() == 0) {
                        JOptionPane.showMessageDialog(this, "Los archivos son iguales.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Diferencias encontradas:\n" + diff.toString(), "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                    scanner1.close();
                    scanner2.close();
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(this, "Error al comparar archivos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione exactamente dos archivos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void contarPalabras() {
        String texto = textArea.getText();
        int cantidadPalabras = texto.split("\\s+").length;
        JOptionPane.showMessageDialog(this, "El documento tiene " + cantidadPalabras + " palabras.", "Conteo de Palabras", JOptionPane.INFORMATION_MESSAGE);
    }
    private void buscarPalabra() {
        String palabra = JOptionPane.showInputDialog(this, "Ingrese la palabra a buscar:");

        if (palabra != null && !palabra.isEmpty()) {
            String texto = textArea.getText();
            int contador = 0;
            int indice = texto.indexOf(palabra);
            while (indice != -1) {
                contador++;
                indice = texto.indexOf(palabra, indice + 1);
            }
            JOptionPane.showMessageDialog(this, "La palabra '" + palabra + "' aparece " + contador + " veces en el documento.", "Búsqueda de Palabra", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void gestionarContactos() {
        JPanel panel = new JPanel(new GridLayout(0, 3));

        JTextField nombreField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JTextField telefonoField = new JTextField(10);

        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Añadir Contacto", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            String email = emailField.getText();
            String telefono = telefonoField.getText();
            Contacto nuevoContacto = new Contacto(nombre, email, telefono);
            listaContactos.add(nuevoContacto);
            JOptionPane.showMessageDialog(this, "Contacto agregado correctamente.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void verContactos() {
        if (listaContactos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay contactos registrados.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder contactosStr = new StringBuilder();
            for (Contacto contacto : listaContactos) {
                contactosStr.append(contacto.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, contactosStr.toString(), "Contactos Registrados", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void abrirNuevaVentana() {
        EditorDeTextoInteractivo nuevaVentana = new EditorDeTextoInteractivo();
        ventanas.add(nuevaVentana);
    }
