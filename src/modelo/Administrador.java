package modelo;

/**
 * Clase que representa un administrador del sistema.
 * Demuestra: Herencia (extiende Persona), Polimorfismo (implementa métodos abstractos).
 */
public class Administrador extends Persona {
    private static final long serialVersionUID = 1L;

    public Administrador(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRol() {
        return "Administrador";
    }

    @Override
    public boolean tienePermisoAdmin() {
        return true;
    }
}
