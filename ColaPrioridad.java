package clientes;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Cola de prioridad implementada con un Min-Heap (ArrayList).
 * Ordena por prioridad numérica (1 = mayor prioridad) y, en caso de empate, por hora de llegada (FIFO).
 */
public class ColaPrioridad {
    private ArrayList<Cliente> heap = new ArrayList<Cliente>();

    private boolean less(Cliente a, Cliente b) {
        if (a.getPrioridad() != b.getPrioridad()) return a.getPrioridad() < b.getPrioridad();
        return a.getHoraLlegada() < b.getHoraLlegada();
    }

    private void up(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;
            if (less(heap.get(i), heap.get(p))) {
                Collections.swap(heap, i, p);
                i = p;
            } else break;
        }
    }

    private void down(int i) {
        int n = heap.size();
        while (true) {
            int l = 2 * i + 1, r = 2 * i + 2, m = i;
            if (l < n && less(heap.get(l), heap.get(m))) m = l;
            if (r < n && less(heap.get(r), heap.get(m))) m = r;
            if (m != i) {
                Collections.swap(heap, i, m);
                i = m;
            } else break;
        }
    }

    public void agregar(Cliente c) {
        heap.add(c);
        up(heap.size() - 1);
        int posicion = obtenerPosicion(c);
        System.out.println("Cliente " + c.getNombre() + " agregado a la cola. Posición: " + posicion);
    }

    private int obtenerPosicion(Cliente c) {
        // posición aproximada en 1-based recorriendo heap (no necesariamente orden total, pero útil para usuario)
        for (int i = 0; i < heap.size(); i++) {
            if (heap.get(i) == c) return i + 1;
        }
        return heap.size();
    }

    public Cliente extraer() {
        if (heap.isEmpty()) {
            System.out.println("No hay clientes en espera.");
            return null;
        }
        Cliente top = heap.get(0);
        Cliente last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            down(0);
        }
        System.out.println("Atendiendo a: " + top.getNombre() + " (" + top.getTipo() + ", " + top.getCantidadProductos() + " productos)");
        return top;
    }

    public void mostrarCola() {
        if (heap.isEmpty()) {
            System.out.println("No hay clientes en espera.");
            return;
        }
        System.out.println("\n=== CLIENTES EN ESPERA ===");
        for (int i = 0; i < heap.size(); i++) {
            System.out.println((i + 1) + ") " + heap.get(i));
        }
    }

    public boolean estaVacia() {
        return heap.isEmpty();
    }
}
