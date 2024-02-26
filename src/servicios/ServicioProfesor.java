package servicios;

import entidades.Profesor;

import javax.swing.*;
import java.util.ArrayList;

// Clase que tendrá una lista de Profesores y podrá crear un profesor
public class ServicioProfesor extends Servicio<Profesor> {

    private Profesor profesor;

    // Atributo estatico para que no inicialice con cada nuevo llamado a la clase
    private static ArrayList<Profesor> listaProfesores = new ArrayList<>();

    // Metodo que devuelve el listaProfesores cuando es llamado por la clase Servicio
    @Override
    protected ArrayList<Profesor> getLista() {
        return listaProfesores;
    }

    // Metodo que devuelve la entidad cuando es llamado por la clase Servicio
    @Override
    protected Profesor getEntidad(){
        return profesor;
    }

    @Override
    public String informacionEntidad(Profesor profesor) {
        StringBuilder impresion = new StringBuilder();
        ServicioMateria sm = new ServicioMateria(); // Se llama a SM para traer nombres de materias

        if (profesor.getListaMaterias().isEmpty()) { // Si no tiene materias
            impresion.append("Materias: N/A\n");
        } else { // Si tiene materias
            impresion.append("Materias: \n");
            for (Integer IDMateria : profesor.getListaMaterias()) {
                impresion.append(" - " + sm.buscarPorID(IDMateria).getNombre() + "\n");
            }
        }
        return impresion.toString(); // Regreso en String al método
    }

    @Override
    public String mensajeListaVacia() {
        return "No hay profesores registrados.";
    }

    //Metodo para crear un nuevo Profesor
    public void crear() {
        String apellido = pedirDato("apellido del Profesor:");
        if (apellido == null) {
            return; // Cancelar la operación si el apellido es null
        }
        String nombre = pedirDato("nombre del Profesor:");

        if (nombre == null) {
            return; // Cancelar la operación si el nombres es null
        }

        int dni = obtenerDNI();
        if (dni == 0) {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
            return; // Cancelar la operación si el DNI es 0
        }

        if (existeListado()) {
            while (existeDNI(dni)) {
                JOptionPane.showMessageDialog(null, "El DNI ya se encuentra registrado al profesor " + profesor.getNombre());
                dni = obtenerDNI();
                if (dni == 0) {
                    JOptionPane.showMessageDialog(null, "Operación cancelada.");
                    return; // Cancelar la operación si el nombre es null
                }
            }
        }
        profesor = new Profesor(nombre, apellido, dni);
        listaProfesores.add(profesor);
        JOptionPane.showMessageDialog(null, "*  PROFESOR INGRESADO  *\n" + profesor);
    }



    // Devuelve un booleno dependiendo// Devuelve un booleno dependiendo si el DNI ya existe en otro profesor
    private boolean existeDNI(int dni) {
        for (Profesor profesor : listaProfesores) {
            if (profesor.getDni() == dni) {
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

    // Método para asignar materia a profesor
    public void asignarAMAteria() {
        if (existeListado()) { // Si existen profesores a colocar materias
            try {
                Integer IDProfesor = Integer.parseInt(pedirDato("ID de profesor:"));
                Integer IDMateria = Integer.parseInt(pedirDato("ID de la materia:"));
                asignar(IDProfesor, IDMateria);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un valor numérico válido.");
            }
        } else { // Si listado de clase no tiene elementos
            JOptionPane.showMessageDialog(null, mensajeListaVacia());
        }
    }

    // Método para asignar materia a profesor
    private void asignar(Integer IDProfesor, Integer IDMateria) {
        ServicioMateria sm = new ServicioMateria();
        // Si Profesor != null y Materia != null
        if (buscarPorID(IDProfesor) != null && sm.buscarPorID(IDMateria) != null) {
            // Si Materia tiene profesor muestra el profesor asignado
            if (sm.buscarPorID(IDMateria).getProfesor() != null) {
                JOptionPane.showMessageDialog(null, "No se puede sobreescribir. \nLa materia " + sm.buscarPorID(IDMateria).getNombre() +
                        " tiene al profesor " + buscarPorID(sm.buscarPorID(IDMateria).getProfesor()).getNombre() + " asignado");
            } else {
                // Validar que la Materia no exista ya en la lista del Profesor
                if (!buscarPorID(IDProfesor).getListaMaterias().contains(IDMateria)) {
                    buscarPorID(IDProfesor).getListaMaterias().add(IDMateria); // Agrega la materia al listado del profesor
                    sm.buscarPorID(IDMateria).setProfesor(IDProfesor); // Agrega el profesor a la materia
                    JOptionPane.showMessageDialog(null, "Se asignó la materia " + sm.buscarPorID(IDMateria).getNombre() +
                            " al profesor " + buscarPorID(IDProfesor).getNombre());
                } else { // Si existe en la lista del profesor
                    JOptionPane.showMessageDialog(null, "El profesor " + buscarPorID(IDProfesor).getNombre() +
                            " ya tiene la materia " + sm.buscarPorID(IDMateria).getNombre() + " asignada");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Verifique los ID proporcionados.");
        }
    }


    public void eliminar() {
        Integer ID = obtenerIDEliminar();

        if(ID != null){
            for (Profesor p : listaProfesores) {
                if (p != null && p.getID() == ID) {
                    // Eliminar al profesor de la materia
                    p.getListaMaterias().forEach(materiaID -> {
                        ServicioMateria sm = new ServicioMateria();
                        if (sm.buscarPorID(materiaID) != null) {
                            sm.buscarPorID(materiaID).setProfesor(null);
                        }
                    });

                    // Eliminar al profesor de la lista de profesores
                    listaProfesores.remove(p);
                    JOptionPane.showMessageDialog(null, "Profesor eliminado correctamente.");
                    return;
                }
            }
        }
    }

}
