package inventario;

/**
 * Implementación de un árbol AVL para almacenar productos.
 * Soporta inserción, búsqueda, eliminación y recorrido in-orden.
 */
public class ArbolAVL {
    private NodoAVL raiz;

    private int altura(NodoAVL n) {
        return n == null ? 0 : n.altura;
    }

    private int balance(NodoAVL n) {
        return n == null ? 0 : altura(n.izquierda) - altura(n.derecha);
    }

    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izquierda;
        NodoAVL T2 = x.derecha;
        // rotación
        x.derecha = y;
        y.izquierda = T2;
        // actualizar alturas
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        return x;
    }

    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.derecha;
        NodoAVL T2 = y.izquierda;
        // rotación
        y.izquierda = x;
        x.derecha = T2;
        // actualizar alturas
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        return y;
    }

    /**
     * Inserta un producto en el árbol AVL manteniendo el balance.
     * @param producto el producto a insertar
     * @return true si la inserción fue exitosa, false si el código ya existe
     */
    public boolean insertar(Producto producto) {
        if (buscar(producto.getCodigo()) != null) {
            System.out.println("Error: Producto duplicado");
            return false;
        }
        raiz = insertarNodo(raiz, producto);
        System.out.println("Producto " + producto.getCodigo() + " insertado correctamente");
        return true;
    }

    private NodoAVL insertarNodo(NodoAVL node, Producto p) {
        if (node == null) return new NodoAVL(p);
        if (p.getCodigo().compareTo(node.producto.getCodigo()) < 0) {
            node.izquierda = insertarNodo(node.izquierda, p);
        } else {
            node.derecha = insertarNodo(node.derecha, p);
        }
        node.altura = Math.max(altura(node.izquierda), altura(node.derecha)) + 1;
        int bal = balance(node);
        // LL
        if (bal > 1 && p.getCodigo().compareTo(node.izquierda.producto.getCodigo()) < 0) {
            return rotacionDerecha(node);
        }
        // RR
        if (bal < -1 && p.getCodigo().compareTo(node.derecha.producto.getCodigo()) > 0) {
            return rotacionIzquierda(node);
        }
        // LR
        if (bal > 1 && p.getCodigo().compareTo(node.izquierda.producto.getCodigo()) > 0) {
            node.izquierda = rotacionIzquierda(node.izquierda);
            return rotacionDerecha(node);
        }
        // RL
        if (bal < -1 && p.getCodigo().compareTo(node.derecha.producto.getCodigo()) < 0) {
            node.derecha = rotacionDerecha(node.derecha);
            return rotacionIzquierda(node);
        }
        return node;
    }

    /**
     * Busca un producto por código.
     * @param codigo código del producto
     * @return Producto si se encuentra, null si no existe
     */
    public Producto buscar(String codigo) {
        NodoAVL n = buscarNodo(raiz, codigo);
        if (n != null) {
            System.out.println("Producto encontrado: " + n.producto);
            return n.producto;
        } else {
            System.out.println("Producto no encontrado");
            return null;
        }
    }

    private NodoAVL buscarNodo(NodoAVL node, String codigo) {
        if (node == null) return null;
        if (codigo.equals(node.producto.getCodigo())) return node;
        if (codigo.compareTo(node.producto.getCodigo()) < 0) return buscarNodo(node.izquierda, codigo);
        else return buscarNodo(node.derecha, codigo);
    }

    /**
     * Elimina un producto por código.
     * @param codigo código del producto a eliminar
     */
    public void eliminar(String codigo) {
        if (buscar(codigo) == null) {
            System.out.println("No se puede eliminar: producto no encontrado.");
            return;
        }
        raiz = eliminarNodo(raiz, codigo);
        System.out.println("Producto eliminado");
    }

    private NodoAVL eliminarNodo(NodoAVL node, String codigo) {
        if (node == null) {
            return null;
        }
        if (codigo.compareTo(node.producto.getCodigo()) < 0) node.izquierda = eliminarNodo(node.izquierda, codigo);
        else if (codigo.compareTo(node.producto.getCodigo()) > 0) node.derecha = eliminarNodo(node.derecha, codigo);
        else {
            // este es el nodo a eliminar
            if (node.izquierda == null || node.derecha == null) {
                NodoAVL temp = (node.izquierda != null) ? node.izquierda : node.derecha;
                if (temp == null) {
                    // no hijos
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                // dos hijos: obtener sucesor inorder (menor en la derecha)
                NodoAVL sucesor = node.derecha;
                while (sucesor.izquierda != null) sucesor = sucesor.izquierda;
                node.producto = sucesor.producto;
                node.derecha = eliminarNodo(node.derecha, sucesor.producto.getCodigo());
            }
        }
        if (node == null) return null;
        // actualizar altura y balance
        node.altura = Math.max(altura(node.izquierda), altura(node.derecha)) + 1;
        int bal = balance(node);
        // LL
        if (bal > 1 && balance(node.izquierda) >= 0) return rotacionDerecha(node);
        // LR
        if (bal > 1 && balance(node.izquierda) < 0) {
            node.izquierda = rotacionIzquierda(node.izquierda);
            return rotacionDerecha(node);
        }
        // RR
        if (bal < -1 && balance(node.derecha) <= 0) return rotacionIzquierda(node);
        // RL
        if (bal < -1 && balance(node.derecha) > 0) {
            node.derecha = rotacionDerecha(node.derecha);
            return rotacionIzquierda(node);
        }
        return node;
    }

    /**
     * Muestra los productos en orden (in-order).
     */
    public void mostrarInOrden() {
        System.out.println("\n=== INVENTARIO DEL SUPERMERCADO ===");
        System.out.println("Código | Nombre | Categoría | Precio | Stock");
        inOrden(raiz);
    }

    private void inOrden(NodoAVL node) {
        if (node != null) {
            inOrden(node.izquierda);
            System.out.println(node.producto);
            inOrden(node.derecha);
        }
    }
}
