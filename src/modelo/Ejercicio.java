package modelo;

import java.io.Serializable;

/**
 * Clase que representa un ejercicio de gimnasio.
 * Demuestra: Clases y objetos, Serialización.
 */
public class Ejercicio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String nombre;
    private TipoEjercicio tipo;
    private String descripcion;

    public Ejercicio(int id, String nombre, TipoEjercicio tipo, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoEjercicio getTipo() {
        return tipo;
    }

    public void setTipo(TipoEjercicio tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo.getNombre() + ")";
    }
}
