package excepciones;

/**
 * Excepción lanzada cuando las credenciales de login son inválidas.
 * Demuestra: Excepciones personalizadas.
 */
public class LoginException extends Exception {
    
    public LoginException(String mensaje) {
        super(mensaje);
    }
}
