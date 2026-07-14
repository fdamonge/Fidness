package util;

import modelo.Ejercicio;
import modelo.Rutina;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Clase que exporta una rutina a archivo de texto.
 * Demuestra: Polimorfismo (implementa interfaz Exportable), manejo de excepciones.
 */
public class ExportadorRutina implements Exportable {
    
    private Rutina rutina;

    public ExportadorRutina(Rutina rutina) {
        this.rutina = rutina;
    }

    @Override
    public boolean exportar(String ruta) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ruta))) {
            writer.println("================================================");
            writer.println("         FIDNESS - Rutina de Ejercicios");
            writer.println("================================================");
            writer.println();
            writer.println("Nombre de la rutina: " + rutina.getNombre());
            writer.println("Creada por: " + rutina.getUsernameCreador());
            writer.println("Total de ejercicios: " + rutina.getEjercicios().size());
            writer.println();
            writer.println("------------------------------------------------");
            
            int contador = 1;
            for (Ejercicio ejercicio : rutina.getEjercicios()) {
                writer.println();
                writer.println(contador + ". " + ejercicio.getNombre());
                writer.println("   Tipo: " + ejercicio.getTipo().getNombre());
                writer.println("   Ejecución:");
                writer.println("   " + ejercicio.getDescripcion());
                writer.println();
                writer.println("------------------------------------------------");
                contador++;
            }
            
            writer.println();
            writer.println("¡Buena rutina! - Fidness Gym");
            
            return true;
        } catch (IOException e) {
            System.err.println("Error al exportar rutina: " + e.getMessage());
            return false;
        }
    }
}
