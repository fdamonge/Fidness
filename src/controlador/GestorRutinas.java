package controlador;

import modelo.Rutina;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador que gestiona las rutinas de los usuarios.
 * Demuestra: Colecciones (HashMap, ArrayList), Serialización.
 */
public class GestorRutinas {
    
    // Map: username -> lista de rutinas del usuario
    private Map<String, List<Rutina>> rutinas;
    private int siguienteId;
    private static final String ARCHIVO_DATOS = "datos/rutinas.dat";

    public GestorRutinas() {
        rutinas = new HashMap<>();
        siguienteId = 1;
        cargarDatos();
    }

    /**
     * Crea una nueva rutina para un usuario.
     */
    public Rutina crearRutina(String username, String nombre) {
        Rutina rutina = new Rutina(siguienteId++, nombre, username);
        
        rutinas.computeIfAbsent(username, k -> new ArrayList<>()).add(rutina);
        guardarDatos();
        
        return rutina;
    }

    /**
     * Obtiene todas las rutinas de un usuario.
     */
    public List<Rutina> obtenerRutinas(String username) {
        return rutinas.getOrDefault(username, new ArrayList<>());
    }

    /**
     * Elimina una rutina de un usuario.
     */
    public boolean eliminarRutina(String username, int idRutina) {
        List<Rutina> listaRutinas = rutinas.get(username);
        if (listaRutinas != null) {
            boolean eliminado = listaRutinas.removeIf(r -> r.getId() == idRutina);
            if (eliminado) {
                guardarDatos();
            }
            return eliminado;
        }
        return false;
    }

    public void guardarDatos() {
        try {
            File directorio = new File("datos");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
                oos.writeObject(rutinas);
                oos.writeInt(siguienteId);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar rutinas: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                rutinas = (Map<String, List<Rutina>>) ois.readObject();
                siguienteId = ois.readInt();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar rutinas: " + e.getMessage());
                rutinas = new HashMap<>();
            }
        }
    }
}
