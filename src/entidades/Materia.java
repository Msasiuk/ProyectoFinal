package entidades;

import java.util.ArrayList;

//Clase Objeto tendra características y métodos generales
public class Materia implements Identificable {
    private Integer ID;

    // Atributo compartido por toda la clase, cada vez que se llama a la clase se autosuma. No es instancia de objeto.
    private static int ultimoID = 1;
    private String nombre;

    // Atributos
    private ArrayList<Integer> listaAlumnos = new ArrayList<>();
    private Integer profesor;

    public Materia(String nombre) {
        this.ID = ultimoID;
        ultimoID++;
        this.nombre = nombre;
    }

    public Integer getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getProfesor() {
        return profesor;
    }

    public ArrayList<Integer> getListaAlumnos() {
        return listaAlumnos;
    }

    public void setProfesor(Integer IDProfesor) {
        profesor = IDProfesor;
    }

    @Override
    public String toString() {
        return "(ID: " + ID + ")  " + nombre;
    }
}
