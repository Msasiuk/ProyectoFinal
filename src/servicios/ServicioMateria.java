package servicios;

import entidades.Materia;

import javax.swing.*;
import java.util.ArrayList;

// Clase que tendrá una lista de Materias y podrá crear una materia
public class ServicioMateria extends Servicio<Materia> {

    private Materia materia;

    // Atributo estatico para que no inicialice con cada nuevo llamado a la clase
    private static ArrayList<Materia> listaMaterias = new ArrayList<>();

    // Metodo que devuelve listaMaterias cuando es llamado por la clase Servicio
    @Override
    protected ArrayList<Materia> getLista() {
        return listaMaterias;
    }

    // Metodo que devuelve la entidad cuando es llamado por la clase Servicio
    @Override
    protected Materia getEntidad() {
        return materia;
    }

    @Override
    public String informacionEntidad(Materia materia) {
        StringBuilder impresion = new StringBuilder();
        ServicioAlumno sa = new ServicioAlumno(); // Se llama a SA para traer nombres de alumnos
        ServicioProfesor sp = new ServicioProfesor(); // Se llama a SM para traer nombre de profesor

        if (materia.getProfesor() == null) { // Si no tiene profesor
            impresion.append("Profesor: N/A.\n");
        } else { // Si tiene profesor
            impresion.append("Profesor: " + sp.buscarPorID(materia.getProfesor()).getNombre()+ "\n");
        }

        if (materia.getListaAlumnos().isEmpty()) { // Si no tiene alumnos
            impresion.append("Alumnos: N/A.\n");
        } else { // Si tiene alumnos
            impresion.append("Alumnos:\n");
            for (Integer IDAlumno : materia.getListaAlumnos()) {
                impresion.append(" - " + sa.buscarPorID(IDAlumno).getNombre() + "\n");
            }
        }
        return impresion.toString();
    }


    @Override
    public String mensajeListaVacia() {
        return "No hay materias registradas.";
    }

    //Metodo para crear una nueva Materia
    public void crear() {
        String nombre = pedirDato("nombre de la Materia:");
        if (nombre == null) {
            return; // Cancelar la operación si el nombre es null
        }

        if (existeListado()) {
            while (existeNombre(nombre)) {
                JOptionPane.showMessageDialog(null, "Ya existe la materia " + nombre);
                nombre = pedirDato(" nombre de Materia:");
                if (nombre == null) {
                    return; // Cancelar la operación si el nombre es null
                }
            }
        }

        materia = new Materia(nombre);
        listaMaterias.add(materia);
        JOptionPane.showMessageDialog(null, "*  MATERIA CREADA  *\n" + materia);
    }

    public void eliminar() {
        Integer ID = obtenerIDEliminar();

        // Valida que el ID != null y materia != null
        if (ID != null && buscarPorID(ID) != null) {
            ServicioProfesor sp = new ServicioProfesor();
            ServicioAlumno sa = new ServicioAlumno();

            for (Materia m : listaMaterias) { // Recorre listaMaterias
                // Si materia != null y su ID es == al ID pedido
                if (m != null && m.getID() == ID) {
                    // Eliminar la referencia del profesor
                    if (sp.buscarPorID(m.getProfesor()) != null) { // Busca al profesor de acuerdo a su ID y luego borra la materia de su lista
                        sp.buscarPorID(m.getProfesor()).getListaMaterias().remove(ID);
                    }

                    // Recorre el listado de alumnos
                    Integer IDAlumnos = sa.getLista().size();
                    for(int i = 0; i <IDAlumnos; i++){
                        //Si el alumno contiene la materia, la elimina
                        if(sa.getLista().get(i).getListaMaterias().contains(m.getID())){
                            sa.getLista().get(i).getListaMaterias().remove(m.getID());
                        }
                    }

                    // Eliminar la materia de la lista de materias
                    listaMaterias.remove(m);
                    JOptionPane.showMessageDialog(null, "Materia eliminada correctamente.");
                    return;
                }
            }
            //JOptionPane.showMessageDialog(null, "No se encontró una materia con el ID especificado.");
        }
    }

}