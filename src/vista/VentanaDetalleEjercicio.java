package vista;

import modelo.Ejercicio;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana que muestra el detalle de un ejercicio.
 * Demuestra: Interfaz gráfica (Swing).
 */
public class VentanaDetalleEjercicio extends JFrame {

    public VentanaDetalleEjercicio(Ejercicio ejercicio) {
        inicializarComponentes(ejercicio);
    }

    private void inicializarComponentes(Ejercicio ejercicio) {
        setTitle("FIDNESS - Detalle: " + ejercicio.getNombre());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel de información
        JPanel panelInfo = new JPanel(new GridLayout(2, 1, 5, 5));
        panelInfo.setBackground(new Color(45, 45, 45));

        JLabel lblNombre = new JLabel("Ejercicio: " + ejercicio.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 18));
        lblNombre.setForeground(new Color(76, 175, 80));
        panelInfo.add(lblNombre);

        JLabel lblTipo = new JLabel("Tipo: " + ejercicio.getTipo().getNombre());
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTipo.setForeground(Color.LIGHT_GRAY);
        panelInfo.add(lblTipo);

        panel.add(panelInfo, BorderLayout.NORTH);

        // Descripción
        JLabel lblDescTitulo = new JLabel("Cómo se ejecuta:");
        lblDescTitulo.setForeground(Color.WHITE);
        lblDescTitulo.setFont(new Font("Arial", Font.BOLD, 14));

        JTextArea txtDescripcion = new JTextArea(ejercicio.getDescripcion());
        txtDescripcion.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 13));
        txtDescripcion.setBackground(new Color(60, 60, 60));
        txtDescripcion.setForeground(Color.WHITE);
        txtDescripcion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelCentro = new JPanel(new BorderLayout(5, 5));
        panelCentro.setBackground(new Color(45, 45, 45));
        panelCentro.add(lblDescTitulo, BorderLayout.NORTH);
        panelCentro.add(new JScrollPane(txtDescripcion), BorderLayout.CENTER);

        panel.add(panelCentro, BorderLayout.CENTER);

        // Botón volver
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(45, 45, 45));
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(158, 158, 158));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(e -> dispose());
        panelInferior.add(btnVolver);

        panel.add(panelInferior, BorderLayout.SOUTH);

        add(panel);
    }
}
