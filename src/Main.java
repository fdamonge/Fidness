import controlador.GestorUsuarios;
import vista.VentanaLogin;

import javax.swing.*;

/**
 * Clase principal que inicia la aplicación Fidness.
 */
public class Main {
    
    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, usar el Look and Feel por defecto
        }

        // Iniciar la aplicación en el hilo de Swing (buena práctica multihilo)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GestorUsuarios gestorUsuarios = new GestorUsuarios();
                VentanaLogin ventanaLogin = new VentanaLogin(gestorUsuarios);
                ventanaLogin.setVisible(true);
            }
        });
    }
}
