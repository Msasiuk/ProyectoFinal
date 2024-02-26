package entidades;

import java.util.ArrayList;

/* Superclase de Alumno y Profesor que tendrá características y métodos generales a ellas dos.
 Se declara abstracta porque no se necesita instancia de ella
 */
public abstract class Persona implements Identificable {
    protected String nombre;
    protected String apellido;
    protected int dni;
    protected Integer ID;

    // ArrayList no permite int como argumento, pero no se puede usar Array ya que no se conoce el tamaño que tendrá
    protected ArrayList<Integer> listaMaterias = new ArrayList<>();

    //Constructor de persona, debe tener obligatoriamente estos atributos
    public Persona(String nombre, String apellido, int dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    // Getters, no se implementa el cambio de Nombre, Apellido o DNI por ello no es necesario codificar Setters.
    public int getDni() {
        return dni;
    }

    // Implementación de Interfaz, devuelve ID
    public Integer getID() {
        return ID;
    }

    // Método para obtener la lista de materias asignadas, valida si la lista es nula y devuelve una lista vacía en ese caso
    public ArrayList<Integer> getListaMaterias() {
        return listaMaterias != null ? listaMaterias : new ArrayList<>();
    }

    // Implementación de Interfaz, devuelve nombre completo
    public String getNombre(){
        return apellido + ", " + nombre;
    }

    @Override
    public String toString() {
        return "(ID: " + ID + ")  " + apellido + ", " + nombre;
    }
}
