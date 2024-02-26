package servicios;

import entidades.Alumno;

import javax.swing.*;
import java.util.ArrayList;

// Clase que tendrá una lista de Alumnos y podrá crear un alumno
public class ServicioAlumno extends Servicio<Alumno> {

    private Alumno alumno;

    // Atributo estatico para que no inicialice con cada nuevo llamado a la clase
    private static ArrayList<Alumno> listaAlumnos = new ArrayList<>();

    // Metodo que devuelve listaAlumnos cuando es llamado por la clase Servicio
    @Override
    protected ArrayList<Alumno> getLista() {
        return listaAlumnos;
    }

    // Metodo que devuelve la entidad cuando es llamado por la clase Servicio
    @Override
    protected Alumno getEntidad() {
        return alumno;
    }

    @Override
    protected String informacionEntidad(Alumno alumno) {
        StringBuilder impresion = new StringBuilder();
        ServicioMateria sm = new ServicioMateria(); // Se llama a SM para traer nombres de materias

        if (alumno.getListaMaterias().isEmpty()) { // Si no tiene materias
            impresion.append("Materias: N/A.\n");
        } else { // Si tiene materias
            impresion.append("Materias:\n");
            for (Integer IDMateria : alumno.getListaMaterias()) {
                impresion.append(" - " + sm.buscarPorID(IDMateria).getNombre() + "\n");
            }
        }
        return impresion.toString(); // Regreso del método
    }


    @Override
    public String mensajeListaVacia() {
        return "No hay alumnos registrados.";
    }

    //Metodo para crear un nuevo Alumno
    public void crear() {
        String apellido = pedirDato("apellido del Alumno:");
        if (apellido == null) {
            return; // Cancelar la operación si el apellido es null
        }
        String nombre = pedirDato("nombre del Alumno:");

        if (nombre == null) {
            return; // Cancelar la operación si el nombres es null
        } else {}

        int dni = obtenerDNI();
        if (dni == 0) {
            return; // Cancelar la operación si el DNI es 0
        }

        if (existeListado()) {
            while (existeDNI(dni)) {
                JOptionPane.showMessageDialog(null, "El DNI ya se encuentra registrado al alumno " + alumno.getNombre());
                dni = obtenerDNI();
                if (dni == 0) {
                    JOptionPane.showMessageDialog(null, "Operación cancelada.");
                    return; // Cancelar la operación si el nombre es null
                }
            }
        }

        alumno = new Alumno(nombre, apellido, dni);
        listaAlumnos.add(alumno);
        JOptionPane.showMessageDialog(null, "*  ALUMNO INGRESADO  *\n" + alumno);
    }

        // Devuelve un booleno dependiendo si el DNI ya existe en otro alumno
    private boolean existeDNI(int dni) {
        for (Alumno alumno : listaAlumnos) {
            if (alumno.getDni() == dni) {
                return true;
            }
        }
        return false;
    }

    // Valida el numero ingresado a la variable DNI
    public int obtenerDNI() {
        int dni;
        do {
            try {
                String dniStr = JOptionPane.showInputDialog("Ingrese el número de documento:");
                if (dniStr == null) {
                    return 0;
                }
                dni = Integer.parseInt(dniStr);
                if (dni <= 1000000 || dni > 99999999) {
                    throw new IllegalArgumentException("El documento debe encontrarse entre 1.000.000 y 99.999.999.");
                }
                return dni;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El documento debe ser un número entero.");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } while (true);
    }

    // Método para asignar materia a alumno
    public void asignarAMAteria() {
        if (existeListado()) { // Si existen alumnos a colocar materias
            try {
                int IDAlumno = Integer.parseInt(pedirDato("ID del alumno:"));
                int IDMateria = Integer.parseInt(pedirDato("ID de la materia:"));
                asignar(IDAlumno, IDMateria);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un valor numérico válido.");
            }
        } else { // Si listado de clase no tiene elementos
            JOptionPane.showMessageDialog(null, mensajeListaVacia());
        }
    }

    // Método para asignar materia a alumno
    public void asignar(Integer IDAlumno, Integer IDMateria) {
        ServicioMateria sm = new ServicioMateria();
        // Si Alumno != null y Materia != null
        if (buscarPorID(IDAlumno) != null && sm.buscarPorID(IDMateria) != null) {
            // Validar que la Materia no exista ya en la lista del Alumno
            if (!buscarPorID(IDAlumno).getListaMaterias().contains(IDMateria)) {
                buscarPorID(IDAlumno).getListaMaterias().add(IDMateria); // Agrega la materia al listadeo del profesor
                sm.buscarPorID(IDMateria).getListaAlumnos().add(IDAlumno); // Agrega
                JOptionPane.showMessageDialog(null, "Se asignó la materia " + sm.buscarPorID(IDMateria).getNombre() +
                        " al alumno " + buscarPorID(IDAlumno).getNombre());
            } else { // Si existe en la lista del alumno
                JOptionPane.showMessageDialog(null, "El alumno " + buscarPorID(IDAlumno).getNombre() + " ya tiene la materia " +
                        sm.buscarPorID(IDMateria).getNombre() + " asignada");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Verifique los ID proporcionados.");
        }
    }

    public void eliminar() {
        Integer ID = obtenerIDEliminar();

        if (ID != null) {
            for (Alumno a : listaAlumnos) {
                if (a != null && a.getID() == ID) {
                    // Eliminar la referencia de la materia en la lista de materias de los profesores
                    for (Integer materiaID : a.getListaMaterias()) {
                        ServicioMateria sm = new ServicioMateria();
                        if (sm.buscarPorID(materiaID) != null) {
                            sm.buscarPorID(materiaID).getListaAlumnos().remove(ID);
                        }
                    }

                    // Eliminar al alumno de la lista de alumnos
                    listaAlumnos.remove(a);
                    JOptionPane.showMessageDialog(null, "Alumno eliminado correctamente.");
                    return;
                }
            }
        }
    }

}
