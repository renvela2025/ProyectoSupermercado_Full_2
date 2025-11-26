package clientes;

/**
 * Representa un cliente que entra a la cola de atención.
 */
public class Cliente {
    public enum TipoCliente { VIP, ADULTO_MAYOR, REGULAR }

    private String nombre;
    private TipoCliente tipo;
    private int productos;
    private long llegada;

    public Cliente(String nombre, TipoCliente tipo, int productos) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.productos = productos;
        this.llegada = System.currentTimeMillis();
    }

    public String getNombre() { return nombre; }
    public TipoCliente getTipo() { return tipo; }
    public int getCantidadProductos() { return productos; }
    public long getHoraLlegada() { return llegada; }

    /**
     * Prioridad numérica: menor = más prioridad
     * VIP = 1, ADULTO_MAYOR = 2, REGULAR = 3
     */
    public int getPrioridad() {
        if (tipo == TipoCliente.VIP) return 1;
        if (tipo == TipoCliente.ADULTO_MAYOR) return 2;
        return 3;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ") - " + productos + " productos";
    }
}
