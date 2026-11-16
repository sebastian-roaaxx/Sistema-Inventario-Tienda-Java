public class NodoArbol {
    public Producto dato; // Producto almacenado en el nodo.
    public NodoArbol izq; // Enlace al hijo izquierdo.
    public NodoArbol der; // Enlace al hijo derecho.

    public NodoArbol(Producto dato) {
        this.dato = dato;
        izq = null;
        der = null;
    }
}
