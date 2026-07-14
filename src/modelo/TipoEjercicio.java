package modelo;

/**
 * Enumeración que representa los tipos de ejercicio por grupo muscular.
 */
public enum TipoEjercicio {
    PIERNA("Pierna"),
    ESPALDA("Espalda"),
    BRAZO("Brazo"),
    PECHO("Pecho"),
    HOMBRO("Hombro"),
    ABDOMEN("Abdomen");

    private final String nombre;

    TipoEjercicio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
