package vista;

import controlador.GestorUsuarios;
import excepciones.LoginException;
import modelo.Persona;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana de inicio de sesión.
 * Demuestra: Interfaz gráfica de usuario (Swing).
 */
public class VentanaLogin extends JFrame {
    
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JButton btnRegistrar;
    private GestorUsuarios gestorUsuarios;

    public VentanaLogin(GestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("FIDNESS - Inicio de Sesión");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(45, 45, 45));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel lblTitulo = new JLabel("FIDNESS GYM");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(76, 175, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        // Label usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblUsuario, gbc);

        // Campo usuario
        txtUsuario = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtUsuario, gbc);

        // Label contraseña
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lblPassword, gbc);

        // Campo contraseña
        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtPassword, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(45, 45, 45));

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBackground(new Color(76, 175, 80));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        panelBotones.add(btnIngresar);

        btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBackground(new Color(33, 150, 243));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        panelBotones.add(btnRegistrar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelBotones, gbc);

        add(panel);

        // Eventos
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarse();
            }
        });
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        try {
            Persona persona = gestorUsuarios.autenticar(usuario, password);
            JOptionPane.showMessageDialog(this, 
                "¡Bienvenido, " + persona.getUsername() + "!\nRol: " + persona.getRol(),
                "Login exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            // Abrir menú principal
            dispose();
            new VentanaMenuPrincipal(persona).setVisible(true);
            
        } catch (LoginException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                "Error de Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarse() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        try {
            gestorUsuarios.registrar(usuario, password);
            JOptionPane.showMessageDialog(this,
                "Usuario registrado exitosamente.\nAhora puede iniciar sesión.",
                "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
        } catch (excepciones.DatosInvalidosException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                "Error de Registro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
