package vista;

import controlador.GestorEjercicios;
import excepciones.DatosInvalidosException;
import modelo.Ejercicio;
import modelo.TipoEjercicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana de administración para agregar y eliminar ejercicios.
 * Demuestra: Interfaz gráfica, Excepciones, Colecciones.
 */
public class VentanaAdminEjercicios extends JFrame {
    
    private JTextField txtNombre;
    private JComboBox<TipoEjercicio> cmbTipo;
    private JTextArea txtDescripcion;
    private JTable tablaEjercicios;
    private DefaultTableModel modeloTabla;
    private GestorEjercicios gestorEjercicios;

    public VentanaAdminEjercicios() {
        gestorEjercicios = new GestorEjercicios();
        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        setTitle("FIDNESS - Administrar Ejercicios");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de formulario (arriba)
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(new Color(55, 55, 55));
        panelForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(76, 175, 80)), 
            "Agregar Ejercicio", 0, 0, null, new Color(76, 175, 80)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(lblNombre, gbc);
        
        txtNombre = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0;
        panelForm.add(txtNombre, gbc);

        // Tipo
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(lblTipo, gbc);
        
        cmbTipo = new JComboBox<>(TipoEjercicio.values());
        gbc.gridx = 1; gbc.gridy = 1;
        panelForm.add(cmbTipo, gbc);

        // Descripción
        JLabel lblDesc = new JLabel("Descripción:");
        lblDesc.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panelForm.add(lblDesc, gbc);
        
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        gbc.gridx = 1; gbc.gridy = 2;
        panelForm.add(new JScrollPane(txtDescripcion), gbc);

        // Botón agregar
        JButton btnAgregar = new JButton("Agregar Ejercicio");
        btnAgregar.setBackground(new Color(76, 175, 80));
        btnAgregar.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panelForm.add(btnAgregar, gbc);

        panel.add(panelForm, BorderLayout.NORTH);

        // Tabla de ejercicios existentes
        String[] columnas = {"ID", "Nombre", "Tipo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEjercicios = new JTable(modeloTabla);
        tablaEjercicios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(tablaEjercicios), BorderLayout.CENTER);

        // Panel inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(45, 45, 45));

        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        btnEliminar.setBackground(new Color(244, 67, 54));
        btnEliminar.setForeground(Color.WHITE);
        panelInferior.add(btnEliminar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(158, 158, 158));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(e -> dispose());
        panelInferior.add(btnVolver);

        panel.add(panelInferior, BorderLayout.SOUTH);

        add(panel);

        // Eventos
        btnAgregar.addActionListener(e -> agregarEjercicio());
        btnEliminar.addActionListener(e -> eliminarEjercicio());
    }

    private void agregarEjercicio() {
        try {
            String nombre = txtNombre.getText();
            TipoEjercicio tipo = (TipoEjercicio) cmbTipo.getSelectedItem();
            String descripcion = txtDescripcion.getText();

            gestorEjercicios.agregar(nombre, tipo, descripcion);
            
            JOptionPane.showMessageDialog(this, "Ejercicio agregado exitosamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar campos
            txtNombre.setText("");
            txtDescripcion.setText("");
            cargarTabla();
            
        } catch (DatosInvalidosException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEjercicio() {
        int fila = tablaEjercicios.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un ejercicio.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Eliminar el ejercicio seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            gestorEjercicios.eliminar(id);
            cargarTabla();
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Ejercicio> ejercicios = gestorEjercicios.listarTodos();
        for (Ejercicio e : ejercicios) {
            modeloTabla.addRow(new Object[]{e.getId(), e.getNombre(), e.getTipo().getNombre()});
        }
    }
}
