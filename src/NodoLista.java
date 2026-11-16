public class NodoLista {
    public Producto dato; // Producto guardado en este nodo.
    public NodoLista sig; // Enlace al siguiente nodo de la lista.

    public NodoLista(Producto dato) {
        this.dato = dato;
        sig = null; // El nodo nuevo queda al final.
    }
}
