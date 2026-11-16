public class ListaProductos {
    public NodoLista cab; // Inicio de la lista dinámica.
    public NodoLista p; // Nodo usado para recorrer la lista.
    public NodoLista q; // Nodo nuevo o anterior durante los cambios.

    public ListaProductos() {
        cab = null;
        p = null;
        q = null;
    }

    public boolean insertar(Producto producto) {
        if (buscar(producto.getCodigo()) != null) return false; // Evita códigos repetidos.
        q = new NodoLista(producto); // Crea el nodo del producto.
        if (cab == null) cab = q;
        else {
            p = cab;
            while (p.sig != null) p = p.sig; // Recorre hasta el último nodo.
            p.sig = q; // Enlaza el producto al final.
        }
        return true;
    }

    public Producto buscar(String codigo) {
        p = cab; // Empieza la búsqueda desde la cabeza.
        while (p != null) {
            if (p.dato.getCodigo().equalsIgnoreCase(codigo)) return p.dato;
            p = p.sig; // Avanza al nodo siguiente.
        }
        return null;
    }

    public boolean eliminar(String codigo) {
        p = cab; // Busca el nodo que se va a quitar.
        q = null;
        while (p != null && !p.dato.getCodigo().equalsIgnoreCase(codigo)) {
            q = p;
            p = p.sig;
        }
        if (p == null) return false;
        if (q == null) cab = p.sig; // Si era el primero, cambia la cabeza.
        else q.sig = p.sig; // El nodo anterior salta el nodo eliminado.
        return true;
    }
}
