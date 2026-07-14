package excepciones;

/**
 * Excepción lanzada cuando se ingresan datos inválidos.
 * Demuestra: Excepciones personalizadas.
 */
public class DatosInvalidosException extends Exception {
    
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}
