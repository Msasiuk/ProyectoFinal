package vista;

import servicios.ServicioAlumno;
import servicios.ServicioMateria;
import servicios.ServicioProfesor;

import javax.swing.*;

// Clase que manejará el Menú el proyecto
public class ManejadorMenu {

    public void mostrarMenu() {
        while (true) {
            String[] opciones = {"Gestión Materias", "Gestión Profesores", "Gestión Alumnos", "Salir"};
            int seleccion = mostrarOpciones("Seleccione una opción:", "Menú Principal", opciones);
            switch (seleccion) {
                case 0: // Gestión Materias
                    gestionMaterias();
                    break;
                case 1: // Gestión Profesores
                    gestionProfesores();
                    break;
                case 2: // Gestión Alumnos
                    gestionAlumnos();
                    break;
                case 3: // Salir
                    JOptionPane.showMessageDialog(null, "Saliendo del programa.");
                    System.exit(0);
            }
        }
    }

    private int mostrarOpciones(String mensaje, String titulo, String[] opciones) {
        return JOptionPane.showOptionDialog(null, mensaje, titulo, JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
    }

    private void gestionMaterias() {
        ServicioMateria sm = new ServicioMateria();
        int contador = 1;
        do {
            String[] opciones = {"Ver Materias", "Crear Nueva Materia", "Eliminar Materia", "Menu Principal"};
            int seleccion = mostrarOpciones("Seleccione una opción:", "Gestión Materias", opciones);
            switch (seleccion) {
                case 0: // Ver Materias
                    sm.ver("materias");
                    break;
                case 1: // Crear Nueva Materia
                    sm.crear();
                    break;
                case 2: // Eliminar Materia
                    sm.eliminar();
                    break;
                case 3: // Salir
                    contador = 0;
                    break;
            }
        } while (contador != 0);
    }

    private void gestionProfesores() {
        ServicioProfesor sp = new ServicioProfesor();
        int contador = 1;
        do {
            String[] opciones = {"Ver Profesores", "Crear nuevo Profesor", "Asignar Curso a Profesor", "Eliminar Profesor", "Menu Principal"};
            int seleccion = mostrarOpciones("Seleccione una opción:", "Gestión Profesores", opciones);
            switch (seleccion) {
                case 0: // Ver Profesores
                    sp.ver("profesores");
                    break;
                case 1: // Crear Nuevo Profesor
                    sp.crear();
                    break;
                case 2: // Asignar Curso a Profesor
                    sp.asignarAMAteria();
                    break;
                case 3: // Eliminar Profesor
                    sp.eliminar();
                    break;
                case 4: // Salir
                    contador = 0;
                    break;
            }
        } while (contador != 0);
    }

    private void gestionAlumnos() {
        ServicioAlumno sa = new ServicioAlumno() ;
        int contador = 1;
        do {
            String[] opciones = {"Ver Alumnos", "Crear nuevo Alumno", "Asignar Materia a Alumno", "Eliminar Alumno", "Menu Principal"};
            int seleccion = mostrarOpciones("Seleccione una opción:", "Gestión Alumno", opciones);
            switch (seleccion) {
                case 0: // Ver Alumno
                    sa.ver("alumnos");
                    break;
                case 1: // Crear Nuevo Alumno
                    sa.crear();
                    break;
                case 2: // Asignar Curso a Alumno
                    sa.asignarAMAteria();
                    break;
                case 3: // Eliminar Alumno
                    sa.eliminar();
                    break;
                case 4: //  Salir
                    contador = 0;
                    break;
            }
        } while (contador != 0);
    }
}
