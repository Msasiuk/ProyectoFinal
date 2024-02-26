package servicios;

import entidades.Identificable;

import java.util.ArrayList;
import javax.swing.JOptionPane;

// Clase abstracta que tendrá métodos comunes de los servicios
// Se usa genérico T ya que Object, obliga a hacer casting para no perder info
public abstract class Servicio<T extends Identificable> {

    //    Al no ser statico inicia cada vez que se usa la clase servicio
    ArrayList<T> listaEntidad = getLista();
    T entidad = getEntidad();

    // Para que devuelva listadoEntidad y entidad de la claseServicio llamada
    protected abstract ArrayList<T> getLista();
    protected abstract T getEntidad();
    public void ver(String tipoEntidad) {
        if (existeListado()) { // Ingresa si la lista de entidades tiene elementos
            // Se usa StringBuilder para no tener que concatenar y luego guardar el concatena en otro String
            StringBuilder impresion = new StringBuilder("*  LISTADO DE " + tipoEntidad.toUpperCase() + "  *\n \n");
            for (T entidad : listaEntidad) {
                impresion.append(entidad.toString() + "\n" + informacionEntidad(entidad) + "\n");
            }
            JOptionPane.showMessageDialog(null, impresion.toString()); // Se imprime final el StringBuilder
        } else { // Si listado de clase no tiene elementos
            JOptionPane.showMessageDialog(null, mensajeListaVacia());
        }
    }

    // Evalua la lista de objetos y si es nula o está vacía devuelve false.
    public boolean existeListado() {
        return (getLista() != null && !getLista().isEmpty());
    }

    protected abstract String informacionEntidad(T entidad);

    // Devuelve la entidad que coincide con el ID de listaEntidad
    public  T buscarPorID(Integer ID){
        for(T entidad : listaEntidad){
            if(entidad.getID() == ID){
                return entidad;
            }
        }
        return entidad;
    }

    // Método que validará los Datos pedidos que sean String
    public String pedirDato(String datoPedido) {
        String dato = JOptionPane.showInputDialog("Ingrese el " + datoPedido);

        if (dato == null) {   // Verificar si el usuario ha cancelado la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
            return null;
        }

        while (dato.trim().isEmpty()) { // Validar que el dato no esté vacío
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos.");
            dato = JOptionPane.showInputDialog("Ingrese nuevamente el " + datoPedido);
            if (dato == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada.");
                return null;
            }
        }
        return formatearTexto(dato);
    }

    public static String formatearTexto(String texto){
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    // Devuelve un booleno dependiendo si el nombre ya existe en otra materia
    public boolean existeNombre(String nombre) {
        for (T entidad : listaEntidad) {
            if (entidad.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    public Integer obtenerIDEliminar() {
        if (!existeListado()) { // Devuelve false
            JOptionPane.showMessageDialog(null, mensajeListaVacia());
            return null;
        }
        Integer ID;
        try {
            ID = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID a eliminar:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor numérico válido.");
            return null;
        }
        if(!existePorID(ID)){ // Si no existe con ese ID
            JOptionPane.showMessageDialog(null, "No existe el ID indicado.");
        }
        return ID;
    }

    // De acuerdo a la clase hija cada una devolverá una frase en particular
    public abstract String mensajeListaVacia();
    public boolean existePorID (Integer ID){
        for (T entidad : listaEntidad) {
            if (entidad != null && entidad.getID() == ID) {
                return true;
            }
        }
        return false;
    }

}
