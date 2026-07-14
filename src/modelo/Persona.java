package modelo;

import java.io.Serializable;

/**
 * Clase abstracta que representa una persona en el sistema.
 * Demuestra: Clase abstracta, Herencia, Serialización.
 */
public abstract class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String username;
    protected String password;

    public Persona(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Método abstracto - cada tipo de persona define su rol.
     * Demuestra: Polimorfismo.
     */
    public abstract String getRol();

    /**
     * Método abstracto - cada tipo de persona define sus permisos.
     */
    public abstract boolean tienePermisoAdmin();

    @Override
    public String toString() {
        return username + " (" + getRol() + ")";
    }
}
