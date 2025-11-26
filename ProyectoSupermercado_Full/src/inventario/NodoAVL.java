package inventario;

/**
 * Nodo del árbol AVL que contiene un Producto y referencias a sus hijos.
 */
public class NodoAVL {
    public Producto producto;
    public NodoAVL izquierda;
    public NodoAVL derecha;
    public int altura;

    public NodoAVL(Producto producto) {
        this.producto = producto;
        this.altura = 1; // altura de nodo hoja = 1
    }
}
