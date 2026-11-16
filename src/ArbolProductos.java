import java.util.ArrayList;

public class ArbolProductos {
    public NodoArbol raiz; // Primer nodo del árbol binario.

    public void insertar(Producto producto) {
        raiz = insertar(raiz, producto); // Inserta desde la raíz.
    }

    private NodoArbol insertar(NodoArbol apu, Producto producto) {
        if (apu == null) return new NodoArbol(producto); // Encuentra el espacio del nuevo nodo.
        if (producto.getCantidad() < apu.dato.getCantidad()
                || (producto.getCantidad() == apu.dato.getCantidad()
                && producto.getCodigo().compareToIgnoreCase(apu.dato.getCodigo()) < 0))
            apu.izq = insertar(apu.izq, producto); // Menor cantidad va a la izquierda.
        else apu.der = insertar(apu.der, producto); // Mayor cantidad va a la derecha.
        return apu;
    }

    public void inOrden(NodoArbol apu, ArrayList<Producto> productos) {
        if (apu == null) return;
        inOrden(apu.izq, productos); // Recorre primero las cantidades menores.
        productos.add(apu.dato); // Agrega el producto en orden.
        inOrden(apu.der, productos); // Continúa con las cantidades mayores.
    }
}
