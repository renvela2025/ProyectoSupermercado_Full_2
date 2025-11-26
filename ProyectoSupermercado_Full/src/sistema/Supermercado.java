package sistema;

import inventario.ArbolAVL;
import inventario.Producto;
import clientes.ColaPrioridad;
import clientes.Cliente;

/**
 * Clase que integra inventario (AVL) y atención de clientes (cola de prioridad).
 */
public class Supermercado {
    private ArbolAVL inventario;
    private ColaPrioridad cola;

    public Supermercado() {
        inventario = new ArbolAVL();
        cola = new ColaPrioridad();
    }

    public boolean insertarProducto(Producto p) { return inventario.insertar(p); }
    public Producto buscarProducto(String codigo) { return inventario.buscar(codigo); }
    public void mostrarInventario() { inventario.mostrarInOrden(); }
    public void eliminarProducto(String codigo) { inventario.eliminar(codigo); }
    public boolean actualizarProducto(String codigo, String nombre, String categoria, double precio, int cantidad) {
        Producto p = buscarProducto(codigo);
        if (p == null) {
            System.out.println("Producto no existe");
            return false;
        }
        if (precio <= 0 || cantidad < 0) {
            System.out.println("Valores inválidos (precio>0, cantidad>=0)");
            return false;
        }
        p.setNombre(nombre);
        p.setCategoria(categoria);
        p.setPrecio(precio);
        p.setCantidad(cantidad);
        System.out.println("Producto actualizado exitosamente");
        return true;
    }

    public void agregarCliente(Cliente c) { cola.agregar(c); }
    public Cliente atenderCliente() { return cola.extraer(); }
    public void mostrarColaClientes() { cola.mostrarCola(); }
    public boolean hayClientes() { return !cola.estaVacia(); }

    /**
     * Simula la atención de todos los clientes. Después de atender cada cliente, permite al usuario:
     * - 'a' para agregar un nuevo cliente (interactivo)
     * - 'c' para continuar
     * - 's' para detener la simulación
     */
    public void simularAtencion(java.util.Scanner sc) {
        System.out.println("\nIniciando simulación de atención...");
        while (true) {
            Cliente c = atenderCliente();
            if (c == null) {
                System.out.println("No hay más clientes. Simulación finalizada.");
                break;
            }
            int tiempo = c.getCantidadProductos() * 2;
            System.out.println("Tiempo de atención: " + tiempo + " segundos (simulado)");
            System.out.println("Cliente atendido correctamente: " + c.getNombre());
            // permitir acciones del usuario
            System.out.println("Presiona 'a' para agregar un cliente, 'c' para continuar, 's' para detener simulación:");
            String line = sc.nextLine().trim();
            if ("a".equalsIgnoreCase(line)) {
                // agregar cliente desde menú
                System.out.println("Agregar cliente durante simulación:");
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                int tipoNum = leerIntSim(sc, "Tipo (1 VIP, 2 ADULTO_MAYOR, 3 REGULAR): ");
                int productos = leerIntSim(sc, "Productos: ");
                Cliente.TipoCliente tipo = tipoNum == 1 ? Cliente.TipoCliente.VIP : (tipoNum == 2 ? Cliente.TipoCliente.ADULTO_MAYOR : Cliente.TipoCliente.REGULAR);
                agregarCliente(new Cliente(nombre, tipo, productos));
            } else if ("s".equalsIgnoreCase(line)) {
                System.out.println("Simulación detenida por usuario.");
                break;
            } // si 'c' o cualquier otra cosa, continuar
        }
    }

    private int leerIntSim(java.util.Scanner sc, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Inválido");
            }
        }
    }
}
