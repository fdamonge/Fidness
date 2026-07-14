package controlador;

import excepciones.DatosInvalidosException;
import modelo.Ejercicio;
import modelo.TipoEjercicio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador que gestiona los ejercicios del sistema.
 * Demuestra: Colecciones (ArrayList), Serialización, Excepciones.
 */
public class GestorEjercicios {
    
    private List<Ejercicio> ejercicios;
    private int siguienteId;
    private static final String ARCHIVO_DATOS = "datos/ejercicios.dat";

    public GestorEjercicios() {
        ejercicios = new ArrayList<>();
        siguienteId = 1;
        cargarDatos();
        
        // Si no hay ejercicios, cargar datos de ejemplo
        if (ejercicios.isEmpty()) {
            cargarEjemplos();
            guardarDatos();
        }
    }

    /**
     * Agrega un nuevo ejercicio al sistema.
     */
    public void agregar(String nombre, TipoEjercicio tipo, String descripcion) throws DatosInvalidosException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre del ejercicio no puede estar vacío.");
        }
        if (tipo == null) {
            throw new DatosInvalidosException("Debe seleccionar un tipo de ejercicio.");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new DatosInvalidosException("La descripción no puede estar vacía.");
        }
        
        Ejercicio ejercicio = new Ejercicio(siguienteId++, nombre.trim(), tipo, descripcion.trim());
        ejercicios.add(ejercicio);
        guardarDatos();
    }

    /**
     * Elimina un ejercicio por su ID.
     */
    public boolean eliminar(int id) {
        boolean eliminado = ejercicios.removeIf(e -> e.getId() == id);
        if (eliminado) {
            guardarDatos();
        }
        return eliminado;
    }

    /**
     * Filtra ejercicios por tipo muscular.
     * Demuestra: Colecciones con filtrado.
     */
    public List<Ejercicio> buscarPorTipo(TipoEjercicio tipo) {
        List<Ejercicio> resultado = new ArrayList<>();
        for (Ejercicio e : ejercicios) {
            if (e.getTipo() == tipo) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    public Ejercicio buscarPorId(int id) {
        for (Ejercicio e : ejercicios) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public List<Ejercicio> listarTodos() {
        return new ArrayList<>(ejercicios);
    }

    /**
     * Carga ejercicios de ejemplo para demostración.
     */
    private void cargarEjemplos() {
        ejercicios.add(new Ejercicio(siguienteId++, "Sentadilla", TipoEjercicio.PIERNA,
            "1. Coloque la barra sobre los trapecios.\n2. Pies al ancho de hombros.\n3. Descienda flexionando rodillas hasta muslos paralelos.\n4. Empuje hacia arriba controladamente."));
        ejercicios.add(new Ejercicio(siguienteId++, "Peso Muerto", TipoEjercicio.PIERNA,
            "1. Pies al ancho de caderas frente a la barra.\n2. Agarre la barra con manos al ancho de hombros.\n3. Levante manteniendo espalda recta.\n4. Extienda caderas y rodillas simultáneamente."));
        ejercicios.add(new Ejercicio(siguienteId++, "Press Banca", TipoEjercicio.PECHO,
            "1. Acuéstese en el banco con pies firmes en el suelo.\n2. Agarre la barra al ancho de hombros.\n3. Baje la barra al pecho controladamente.\n4. Empuje hacia arriba hasta extensión completa."));
        ejercicios.add(new Ejercicio(siguienteId++, "Curl Bíceps", TipoEjercicio.BRAZO,
            "1. De pie, sostenga mancuernas a los costados.\n2. Flexione los codos llevando el peso hacia los hombros.\n3. Mantenga los codos pegados al cuerpo.\n4. Baje controladamente a la posición inicial."));
        ejercicios.add(new Ejercicio(siguienteId++, "Remo con Barra", TipoEjercicio.ESPALDA,
            "1. Incline el torso a 45 grados.\n2. Agarre la barra con manos al ancho de hombros.\n3. Tire la barra hacia el abdomen.\n4. Baje controladamente sin mover el torso."));
        ejercicios.add(new Ejercicio(siguienteId++, "Press Militar", TipoEjercicio.HOMBRO,
            "1. De pie o sentado, sostenga la barra a la altura de los hombros.\n2. Empuje la barra hacia arriba sobre la cabeza.\n3. Extienda completamente los brazos.\n4. Baje controladamente a los hombros."));
        ejercicios.add(new Ejercicio(siguienteId++, "Plancha", TipoEjercicio.ABDOMEN,
            "1. Apoye antebrazos y puntas de los pies en el suelo.\n2. Mantenga el cuerpo recto como una tabla.\n3. Active el abdomen y glúteos.\n4. Mantenga la posición por 30-60 segundos."));
    }

    @SuppressWarnings("unchecked")
    public void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                ejercicios = (List<Ejercicio>) ois.readObject();
                // Calcular siguiente ID
                siguienteId = ejercicios.stream()
                    .mapToInt(Ejercicio::getId)
                    .max()
                    .orElse(0) + 1;
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar ejercicios: " + e.getMessage());
                ejercicios = new ArrayList<>();
            }
        }
    }

    public void guardarDatos() {
        try {
            File directorio = new File("datos");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
                oos.writeObject(ejercicios);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar ejercicios: " + e.getMessage());
        }
    }
}
