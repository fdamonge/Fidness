package util;

/**
 * Interfaz que define la capacidad de exportar datos.
 * Demuestra: Polimorfismo a través de interfaces.
 */
public interface Exportable {
    
    /**
     * Exporta el contenido a un archivo en la ruta especificada.
     * @param ruta Ruta del archivo de destino.
     * @return true si la exportación fue exitosa.
     */
    boolean exportar(String ruta);
}
