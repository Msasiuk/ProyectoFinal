package entidades;

public class Alumno extends Persona {
    // Mismos métodos y atributos que la SuperClase. Ademas un Alumno puede tener de 0 a muchas materias
    // Atributo compartido por toda la clase, cada vez que se llama a la clase se autosuma. No es instancia de objeto.
    private static int ultimoID = 1;

    public Alumno(String nombres, String apellido, int dni) { // Método constructor
        super(nombres, apellido, dni);
        ID = ultimoID;
        ultimoID++; // Se guarda el próximo valor de ID
    }

}
