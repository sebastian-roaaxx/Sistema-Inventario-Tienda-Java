import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Inventario {
    public ListaProductos lista; // Lista dinámica con todos los productos.
    public ArbolProductos arbol; // Árbol que los ordena por cantidad.
    private final File archivo = new File("productos.txt"); // Archivo permanente del inventario.

    public Inventario() {
        lista = new ListaProductos();
        arbol = new ArbolProductos();
        cargar(); // Recupera los productos guardados.
        if (lista.cab == null) cargarEjemplos();
        actualizarArbol(); // Construye el árbol con los datos cargados.
    }

    public boolean agregar(Producto producto) {
        if (!lista.insertar(producto)) return false;
        actualizarArbol(); // Refleja el cambio en el árbol.
        guardar(); // Conserva el cambio en el archivo.
        return true;
    }

    public boolean eliminar(String codigo) {
        if (!lista.eliminar(codigo)) return false;
        actualizarArbol();
        guardar();
        return true;
    }

    public boolean actualizarCantidad(String codigo, int cantidad) {
        Producto producto = lista.buscar(codigo);
        if (producto == null) return false;
        producto.setCantidad(cantidad);
        actualizarArbol();
        guardar();
        return true;
    }

    public ArrayList<Producto> obtenerUrgentes() {
        ArrayList<Producto> urgentes = new ArrayList<>();
        for (Producto x : obtenerProductosOrdenados()) // Revisa de menor a mayor cantidad.
            if (x.getCantidad() <= x.getStockMinimo()) urgentes.add(x);
        return urgentes;
    }

    public ArrayList<Producto> obtenerProductosOrdenados() {
        ArrayList<Producto> productos = new ArrayList<>();
        arbol.inOrden(arbol.raiz, productos); // Obtiene los productos ordenados por cantidad.
        return productos;
    }

    public String generarCodigo() {
        int mayor = 0;
        NodoLista p = lista.cab;
        while (p != null) {
            try {
                mayor = Math.max(mayor, Integer.parseInt(p.dato.getCodigo().substring(2)));
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) { }
            p = p.sig;
        }
        return String.format("P-%03d", mayor + 1);
    }

    private void actualizarArbol() {
        arbol = new ArbolProductos(); // Reinicia el árbol para evitar datos repetidos.
        NodoLista p = lista.cab;
        while (p != null) {
            arbol.insertar(p.dato); // Copia cada producto de la lista al árbol.
            p = p.sig;
        }
    }

    private void cargar() {
        if (!archivo.exists()) return;
        try (BufferedReader entrada = new BufferedReader(new FileReader(archivo))) { // Lee el archivo línea por línea.
            String linea;
            while ((linea = entrada.readLine()) != null) {
                String[] datos = linea.split("\\|", -1); // Separa los cinco datos del producto.
                if (datos.length == 5) {
                    lista.insertar(new Producto(datos[0], datos[1], datos[2],
                        Integer.parseInt(datos[3]), Integer.parseInt(datos[4])));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar el inventario: " + e.getMessage());
        }
    }

    private void guardar() {
        try (BufferedWriter salida = new BufferedWriter(new FileWriter(archivo))) { // Guarda la lista actual.
            NodoLista p = lista.cab;
            while (p != null) {
                Producto x = p.dato;
                salida.write(x.getCodigo() + "|" + x.getNombre() + "|" + x.getCategoria() // Escribe los datos separados.
                    + "|" + x.getCantidad() + "|" + x.getStockMinimo());
                salida.newLine();
                p = p.sig;
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el inventario: " + e.getMessage());
        }
    }

    private void cargarEjemplos() {
        lista.insertar(new Producto("P-001", "Arroz 1 kg", "Granos", 3, 5));
        lista.insertar(new Producto("P-002", "Leche entera", "Lácteos", 12, 5));
        lista.insertar(new Producto("P-003", "Aceite vegetal", "Despensa", 5, 6));
        lista.insertar(new Producto("P-004", "Huevos (docena)", "Refrigerados", 2, 4));
        lista.insertar(new Producto("P-005", "Papel higiénico", "Aseo", 8, 5));
        guardar();
    }
}
