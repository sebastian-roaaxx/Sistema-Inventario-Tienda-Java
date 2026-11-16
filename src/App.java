import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Inventario inventario = new Inventario(); // Carga los datos y las estructuras.
            Interfaz ventana = new Interfaz(inventario); // Abre la interfaz conectada al inventario.
            ventana.setVisible(true);
        });
    }
}
