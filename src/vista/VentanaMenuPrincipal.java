package vista;

import modelo.Persona;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana del menú principal que muestra opciones según el rol del usuario.
 * Demuestra: Interfaz gráfica, Polimorfismo (opciones según tipo de Persona).
 */
public class VentanaMenuPrincipal extends JFrame {
    
    private Persona usuarioActual;

    public VentanaMenuPrincipal(Persona usuarioActual) {
        this.usuarioActual = usuarioActual;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("FIDNESS - Menú Principal");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(45, 45, 45));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título de bienvenida
        JLabel lblBienvenida = new JLabel("Bienvenido, " + usuarioActual.getUsername());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        lblBienvenida.setForeground(new Color(76, 175, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(lblBienvenida, gbc);

        JLabel lblRol = new JLabel("Rol: " + usuarioActual.getRol());
        lblRol.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = 1;
        panel.add(lblRol, gbc);

        // Botones del menú
        int fila = 2;

        JButton btnConsultar = crearBoton("Consultar Ejercicios");
        gbc.gridy = fila++;
        panel.add(btnConsultar, gbc);

        JButton btnCrearRutina = crearBoton("Crear Nueva Rutina");
        gbc.gridy = fila++;
        panel.add(btnCrearRutina, gbc);

        JButton btnMisRutinas = crearBoton("Mis Rutinas");
        gbc.gridy = fila++;
        panel.add(btnMisRutinas, gbc);

        // Botón de admin solo si tiene permiso (Polimorfismo)
        JButton btnAdmin = null;
        if (usuarioActual.tienePermisoAdmin()) {
            btnAdmin = crearBoton("Administrar Ejercicios");
            btnAdmin.setBackground(new Color(255, 152, 0));
            gbc.gridy = fila++;
            panel.add(btnAdmin, gbc);
        }

        JButton btnCerrar = crearBoton("Cerrar Sesión");
        btnCerrar.setBackground(new Color(244, 67, 54));
        gbc.gridy = fila++;
        panel.add(btnCerrar, gbc);

        add(panel);

        // Eventos
        btnConsultar.addActionListener(e -> {
            new VentanaEjercicios(usuarioActual).setVisible(true);
        });

        btnCrearRutina.addActionListener(e -> {
            new VentanaCrearRutina(usuarioActual).setVisible(true);
        });

        btnMisRutinas.addActionListener(e -> {
            new VentanaMisRutinas(usuarioActual).setVisible(true);
        });

        if (btnAdmin != null) {
            btnAdmin.addActionListener(e -> {
                new VentanaAdminEjercicios().setVisible(true);
            });
        }

        btnCerrar.addActionListener(e -> {
            dispose();
            new VentanaLogin(new controlador.GestorUsuarios()).setVisible(true);
        });
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(76, 175, 80));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.PLAIN, 14));
        boton.setPreferredSize(new Dimension(250, 40));
        return boton;
    }
}
