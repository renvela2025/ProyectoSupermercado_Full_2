package sistema;

import java.util.Scanner;
import inventario.Producto;
import clientes.Cliente;

/**
 * Menú interactivo para el sistema de supermercado.
 */
public class Menu {
    private Supermercado sm;
    private Scanner sc;

    public Menu() {
        sm = new Supermercado();
        sc = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Menu m = new Menu();
        m.iniciar();
    }

    public void iniciar() {
        int opcion = -1;
        do {
            mostrarMenu();
            opcion = leerInt("Seleccione una opción: ");
            ejecutar(opcion);
        } while (opcion != 11);
        System.out.println("Saliendo... ¡Hasta luego!");
        sc.close();
    }

    private void mostrarMenu() {
        System.out.println("\n========== SISTEMA DE SUPERMERCADO ==========");
        System.out.println("--- GESTIÓN DE INVENTARIO ---");
        System.out.println("1. Insertar nuevo producto");
        System.out.println("2. Buscar producto por código");
        System.out.println("3. Actualizar producto");
        System.out.println("4. Eliminar producto");
        System.out.println("5. Mostrar inventario completo");
        System.out.println("--- GESTIÓN DE CLIENTES ---");
        System.out.println("6. Agregar cliente a la cola");
        System.out.println("7. Atender siguiente cliente");
        System.out.println("8. Ver clientes en espera");
        System.out.println("9. Simular atención de todos los clientes");
        System.out.println("--- OTROS ---");
        System.out.println("10. Mostrar estadísticas del sistema");
        System.out.println("11. Salir");
    }

    private void ejecutar(int o) {
        switch (o) {
            case 1: insertarProducto(); break;
            case 2: buscarProducto(); break;
            case 3: actualizarProducto(); break;
            case 4: eliminarProducto(); break;
            case 5: sm.mostrarInventario(); break;
            case 6: agregarCliente(); break;
            case 7: atenderSiguiente(); break;
            case 8: sm.mostrarColaClientes(); break;
            case 9: sm.simularAtencion(sc); break;
            case 10: mostrarEstadisticas(); break;
            case 11: break;
            default: System.out.println("Opción inválida");
        }
    }

    private void insertarProducto() {
        System.out.print("Código: ");
        String codigo = sc.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Categoría: ");
        String categoria = sc.nextLine().trim();
        double precio = leerDouble("Precio: ");
        int stock = leerInt("Stock: ");
        Producto p = new Producto(codigo, nombre, categoria, precio, stock);
        sm.insertarProducto(p);
    }

    private void buscarProducto() {
        System.out.print("Código: ");
        String c = sc.nextLine().trim();
        sm.buscarProducto(c);
    }

    private void actualizarProducto() {
        System.out.print("Código: ");
        String codigo = sc.nextLine().trim();
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Nueva categoría: ");
        String categoria = sc.nextLine().trim();
        double precio = leerDouble("Nuevo precio: ");
        int stock = leerInt("Nuevo stock: ");
        sm.actualizarProducto(codigo, nombre, categoria, precio, stock);
    }

    private void eliminarProducto() {
        System.out.print("Código: ");
        String c = sc.nextLine().trim();
        sm.eliminarProducto(c);
    }

    private void agregarCliente() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        int tipo = leerInt("Tipo (1 VIP, 2 ADULTO_MAYOR, 3 REGULAR): ");
        int productos = leerInt("Productos: ");
        Cliente.TipoCliente tp = tipo == 1 ? Cliente.TipoCliente.VIP : (tipo == 2 ? Cliente.TipoCliente.ADULTO_MAYOR : Cliente.TipoCliente.REGULAR);
        sm.agregarCliente(new Cliente(nombre, tp, productos));
    }

    private void atenderSiguiente() {
        Cliente c = sm.atenderCliente();
        if (c != null) {
            int tiempo = c.getCantidadProductos() * 2;
            System.out.println("Tiempo de atención: " + tiempo + " segundos (simulado)");
        }
    }

    private void mostrarEstadisticas() {
        System.out.println("\n--- ESTADÍSTICAS ---");
        System.out.println("Inventario y cola integrados. (Más estadísticas se pueden agregar según requisitos del curso)");
    }

    private int leerInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Inválido");
            }
        }
    }

    private double leerDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Inválido");
            }
        }
    }
}
