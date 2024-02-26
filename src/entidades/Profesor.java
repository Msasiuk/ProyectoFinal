package entidades;

public class Profesor extends Persona{
    // Mismos métodos y atributos que la SuperClase.
    // Atributo compartido por toda la clase, cada vez que se llama a la clase se autosuma. No es instancia de objeto.
    private static int ultimoID = 1;

    public Profesor(String nombre, String apellido, int dni) { // Método constructor
        super(nombre, apellido, dni);
        ID = ultimoID;
        ultimoID++; // Se guarda el próximo valor de ID
    }

}
