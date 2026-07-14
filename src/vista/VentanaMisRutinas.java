package vista;

import controlador.GestorRutinas;
import modelo.Ejercicio;
import modelo.Persona;
import modelo.Rutina;
import util.ExportadorRutina;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Ventana para ver las rutinas del usuario.
 * Demuestra: Interfaz gráfica, Colecciones, Multihilos.
 */
public class VentanaMisRutinas extends JFrame {
    
    private JList<String> listaRutinas;
    private DefaultListModel<String> modeloLista;
    private JTextArea txtDetalle;
    private GestorRutinas gestorRutinas;
    private List<Rutina> rutinasUsuario;
    private Persona usuarioActual;

    public VentanaMisRutinas(Persona usuario) {
        this.usuarioActual = usuario;
        this.gestorRutinas = new GestorRutinas();
        inicializarComponentes();
        cargarRutinas();
    }

    private void inicializarComponentes() {
        setTitle("FIDNESS - Mis Rutinas");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título
        JLabel lblTitulo = new JLabel("Mis Rutinas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(76, 175, 80));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Panel central dividido
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(200);

        // Lista de rutinas (izquierda)
        modeloLista = new DefaultListModel<>();
        listaRutinas = new JList<>(modeloLista);
        listaRutinas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaRutinas.setBackground(new Color(60, 60, 60));
        listaRutinas.setForeground(Color.WHITE);
        splitPane.setLeftComponent(new JScrollPane(listaRutinas));

        // Detalle de rutina (derecha)
        txtDetalle = new JTextArea();
        txtDetalle.setEditable(false);
        txtDetalle.setLineWrap(true);
        txtDetalle.setWrapStyleWord(true);
        txtDetalle.setBackground(new Color(60, 60, 60));
        txtDetalle.setForeground(Color.WHITE);
        txtDetalle.setFont(new Font("Monospaced", Font.PLAIN, 12));
        splitPane.setRightComponent(new JScrollPane(txtDetalle));

        panel.add(splitPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(new Color(45, 45, 45));

        JButton btnExportar = new JButton("Exportar TXT");
        btnExportar.setBackground(new Color(33, 150, 243));
        btnExportar.setForeground(Color.WHITE);
        panelBotones.add(btnExportar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(244, 67, 54));
        btnEliminar.setForeground(Color.WHITE);
        panelBotones.add(btnEliminar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(158, 158, 158));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(e -> dispose());
        panelBotones.add(btnVolver);

        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);

        // Eventos
        listaRutinas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetalle();
            }
        });

        btnExportar.addActionListener(e -> exportarSeleccionada());
        btnEliminar.addActionListener(e -> eliminarSeleccionada());
    }

    private void cargarRutinas() {
        modeloLista.clear();
        rutinasUsuario = gestorRutinas.obtenerRutinas(usuarioActual.getUsername());
        for (Rutina r : rutinasUsuario) {
            modeloLista.addElement(r.getNombre());
        }
        
        if (rutinasUsuario.isEmpty()) {
            txtDetalle.setText("No tienes rutinas creadas.\n\nVe a 'Crear Nueva Rutina' para empezar.");
        }
    }

    private void mostrarDetalle() {
        int indice = listaRutinas.getSelectedIndex();
        if (indice >= 0 && indice < rutinasUsuario.size()) {
            Rutina rutina = rutinasUsuario.get(indice);
            StringBuilder sb = new StringBuilder();
            sb.append("Rutina: ").append(rutina.getNombre()).append("\n");
            sb.append("Ejercicios: ").append(rutina.getEjercicios().size()).append("\n");
            sb.append("─────────────────────────\n\n");
            
            int i = 1;
            for (Ejercicio e : rutina.getEjercicios()) {
                sb.append(i).append(". ").append(e.getNombre()).append("\n");
                sb.append("   Tipo: ").append(e.getTipo().getNombre()).append("\n");
                sb.append("   ").append(e.getDescripcion()).append("\n\n");
                i++;
            }
            txtDetalle.setText(sb.toString());
            txtDetalle.setCaretPosition(0);
        }
    }

    private void exportarSeleccionada() {
        int indice = listaRutinas.getSelectedIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una rutina.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Rutina rutina = rutinasUsuario.get(indice);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File(rutina.getNombre() + ".txt"));
        
        int resultado = fileChooser.showSaveDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            
            // Multihilo para exportación
            Thread hilo = new Thread(() -> {
                ExportadorRutina exportador = new ExportadorRutina(rutina);
                boolean exito = exportador.exportar(ruta);
                SwingUtilities.invokeLater(() -> {
                    if (exito) {
                        JOptionPane.showMessageDialog(this, "Exportado en: " + ruta,
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al exportar.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            });
            hilo.start();
        }
    }

    private void eliminarSeleccionada() {
        int indice = listaRutinas.getSelectedIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una rutina.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Rutina rutina = rutinasUsuario.get(indice);
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Eliminar la rutina '" + rutina.getNombre() + "'?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            gestorRutinas.eliminarRutina(usuarioActual.getUsername(), rutina.getId());
            cargarRutinas();
            txtDetalle.setText("");
        }
    }
}
