package vista;

import controlador.GestorEjercicios;
import controlador.GestorRutinas;
import modelo.Ejercicio;
import modelo.Persona;
import modelo.Rutina;
import util.ExportadorRutina;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Ventana para crear una nueva rutina seleccionando ejercicios.
 * Demuestra: Interfaz gráfica, Colecciones, Multihilos (exportación en hilo separado).
 */
public class VentanaCrearRutina extends JFrame {
    
    private JTextField txtNombre;
    private JList<String> listaEjercicios;
    private DefaultListModel<String> modeloLista;
    private JButton btnGuardar;
    private JButton btnExportar;
    private GestorEjercicios gestorEjercicios;
    private GestorRutinas gestorRutinas;
    private List<Ejercicio> ejerciciosDisponibles;
    private Persona usuarioActual;

    public VentanaCrearRutina(Persona usuario) {
        this.usuarioActual = usuario;
        this.gestorEjercicios = new GestorEjercicios();
        this.gestorRutinas = new GestorRutinas();
        inicializarComponentes();
        cargarEjercicios();
    }

    private void inicializarComponentes() {
        setTitle("FIDNESS - Crear Nueva Rutina");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior - nombre de rutina
        JPanel panelNombre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNombre.setBackground(new Color(45, 45, 45));
        JLabel lblNombre = new JLabel("Nombre de la rutina:");
        lblNombre.setForeground(Color.WHITE);
        panelNombre.add(lblNombre);
        txtNombre = new JTextField(20);
        panelNombre.add(txtNombre);
        panel.add(panelNombre, BorderLayout.NORTH);

        // Lista de ejercicios con selección múltiple
        JPanel panelLista = new JPanel(new BorderLayout(5, 5));
        panelLista.setBackground(new Color(45, 45, 45));
        JLabel lblSeleccione = new JLabel("Seleccione ejercicios (Ctrl+Click para varios):");
        lblSeleccione.setForeground(Color.WHITE);
        panelLista.add(lblSeleccione, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        listaEjercicios = new JList<>(modeloLista);
        listaEjercicios.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaEjercicios.setBackground(new Color(60, 60, 60));
        listaEjercicios.setForeground(Color.WHITE);
        panelLista.add(new JScrollPane(listaEjercicios), BorderLayout.CENTER);

        panel.add(panelLista, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(new Color(45, 45, 45));

        btnGuardar = new JButton("Guardar Rutina");
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        panelBotones.add(btnGuardar);

        btnExportar = new JButton("Guardar y Exportar TXT");
        btnExportar.setBackground(new Color(33, 150, 243));
        btnExportar.setForeground(Color.WHITE);
        panelBotones.add(btnExportar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(158, 158, 158));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);

        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);

        // Eventos
        btnGuardar.addActionListener(e -> guardarRutina(false));
        btnExportar.addActionListener(e -> guardarRutina(true));
    }

    private void cargarEjercicios() {
        ejerciciosDisponibles = gestorEjercicios.listarTodos();
        for (Ejercicio e : ejerciciosDisponibles) {
            modeloLista.addElement(e.getNombre() + " (" + e.getTipo().getNombre() + ")");
        }
    }

    private void guardarRutina(boolean exportar) {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre para la rutina.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int[] indicesSeleccionados = listaEjercicios.getSelectedIndices();
        if (indicesSeleccionados.length == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un ejercicio.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Crear la rutina
        Rutina rutina = gestorRutinas.crearRutina(usuarioActual.getUsername(), nombre);
        for (int indice : indicesSeleccionados) {
            rutina.agregarEjercicio(ejerciciosDisponibles.get(indice));
        }
        gestorRutinas.guardarDatos();

        JOptionPane.showMessageDialog(this,
            "Rutina '" + nombre + "' guardada con " + indicesSeleccionados.length + " ejercicios.",
            "Éxito", JOptionPane.INFORMATION_MESSAGE);

        if (exportar) {
            exportarRutina(rutina);
        }
    }

    /**
     * Exporta la rutina en un hilo separado.
     * Demuestra: Multihilos (Thread).
     */
    private void exportarRutina(Rutina rutina) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar rutina como...");
        fileChooser.setSelectedFile(new java.io.File(rutina.getNombre() + ".txt"));
        
        int resultado = fileChooser.showSaveDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            
            // Exportar en hilo separado para no bloquear la GUI
            Thread hiloExportacion = new Thread(new Runnable() {
                @Override
                public void run() {
                    ExportadorRutina exportador = new ExportadorRutina(rutina);
                    boolean exito = exportador.exportar(ruta);
                    
                    // Actualizar GUI desde el hilo de Swing
                    SwingUtilities.invokeLater(() -> {
                        if (exito) {
                            JOptionPane.showMessageDialog(VentanaCrearRutina.this,
                                "Rutina exportada exitosamente en:\n" + ruta,
                                "Exportación", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(VentanaCrearRutina.this,
                                "Error al exportar la rutina.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                }
            });
            hiloExportacion.start();
        }
    }
}
