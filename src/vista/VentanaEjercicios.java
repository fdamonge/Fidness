package vista;

import controlador.GestorEjercicios;
import modelo.Ejercicio;
import modelo.Persona;
import modelo.TipoEjercicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para consultar ejercicios con filtro por tipo.
 * Demuestra: Interfaz gráfica (JTable, JComboBox), Colecciones.
 */
public class VentanaEjercicios extends JFrame {
    
    private JComboBox<String> cmbTipo;
    private JTable tablaEjercicios;
    private DefaultTableModel modeloTabla;
    private JButton btnBuscar;
    private JButton btnVerDetalle;
    private GestorEjercicios gestorEjercicios;
    private List<Ejercicio> ejerciciosMostrados;

    public VentanaEjercicios(Persona usuario) {
        gestorEjercicios = new GestorEjercicios();
        inicializarComponentes();
        cargarEjercicios(null);
    }

    private void inicializarComponentes() {
        setTitle("FIDNESS - Consultar Ejercicios");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior con filtro
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.setBackground(new Color(45, 45, 45));

        JLabel lblFiltro = new JLabel("Filtrar por tipo:");
        lblFiltro.setForeground(Color.WHITE);
        panelFiltro.add(lblFiltro);

        cmbTipo = new JComboBox<>();
        cmbTipo.addItem("Todos");
        for (TipoEjercicio tipo : TipoEjercicio.values()) {
            cmbTipo.addItem(tipo.getNombre());
        }
        panelFiltro.add(cmbTipo);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(76, 175, 80));
        btnBuscar.setForeground(Color.WHITE);
        panelFiltro.add(btnBuscar);

        panel.add(panelFiltro, BorderLayout.NORTH);

        // Tabla de ejercicios
        String[] columnas = {"ID", "Nombre", "Tipo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEjercicios = new JTable(modeloTabla);
        tablaEjercicios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaEjercicios);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(45, 45, 45));

        btnVerDetalle = new JButton("Ver Detalle");
        btnVerDetalle.setBackground(new Color(33, 150, 243));
        btnVerDetalle.setForeground(Color.WHITE);
        panelInferior.add(btnVerDetalle);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(158, 158, 158));
        btnVolver.setForeground(Color.WHITE);
        panelInferior.add(btnVolver);

        panel.add(panelInferior, BorderLayout.SOUTH);

        add(panel);

        // Eventos
        btnBuscar.addActionListener(e -> {
            String seleccion = (String) cmbTipo.getSelectedItem();
            if ("Todos".equals(seleccion)) {
                cargarEjercicios(null);
            } else {
                for (TipoEjercicio tipo : TipoEjercicio.values()) {
                    if (tipo.getNombre().equals(seleccion)) {
                        cargarEjercicios(tipo);
                        break;
                    }
                }
            }
        });

        btnVerDetalle.addActionListener(e -> {
            int fila = tablaEjercicios.getSelectedRow();
            if (fila >= 0 && fila < ejerciciosMostrados.size()) {
                Ejercicio ejercicio = ejerciciosMostrados.get(fila);
                new VentanaDetalleEjercicio(ejercicio).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un ejercicio de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnVolver.addActionListener(e -> dispose());
    }

    private void cargarEjercicios(TipoEjercicio tipo) {
        modeloTabla.setRowCount(0);
        
        if (tipo == null) {
            ejerciciosMostrados = gestorEjercicios.listarTodos();
        } else {
            ejerciciosMostrados = gestorEjercicios.buscarPorTipo(tipo);
        }

        for (Ejercicio e : ejerciciosMostrados) {
            modeloTabla.addRow(new Object[]{e.getId(), e.getNombre(), e.getTipo().getNombre()});
        }
    }
}
