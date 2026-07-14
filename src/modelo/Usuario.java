package modelo;

/**
 * Clase que representa un usuario cliente del gimnasio.
 * Demuestra: Herencia (extiende Persona), Polimorfismo (implementa métodos abstractos).
 */
public class Usuario extends Persona {
    private static final long serialVersionUID = 1L;

    public Usuario(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRol() {
        return "Cliente";
    }

    @Override
    public boolean tienePermisoAdmin() {
        return false;
    }
}
