package entidades;

// Se crea para que el genérico T tenga el método getID, getNombre
public interface Identificable {
    Integer getID();

    // Depende si es una persona trae el nombre con apellido, si es la materia trae el nombre
    String getNombre();
}
