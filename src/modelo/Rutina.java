package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una rutina de ejercicios creada por un usuario.
 * Demuestra: Clases y objetos, Colecciones (ArrayList), Serialización.
 */
public class Rutina implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String nombre;
    private String usernameCreador;
    private List<Ejercicio> ejercicios;

    public Rutina(int id, String nombre, String usernameCreador) {
        this.id = id;
        this.nombre = nombre;
        this.usernameCreador = usernameCreador;
        this.ejercicios = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsernameCreador() {
        return usernameCreador;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void agregarEjercicio(Ejercicio ejercicio) {
        ejercicios.add(ejercicio);
    }

    public void removerEjercicio(Ejercicio ejercicio) {
        ejercicios.remove(ejercicio);
    }

    @Override
    public String toString() {
        return nombre + " (" + ejercicios.size() + " ejercicios)";
    }
}
