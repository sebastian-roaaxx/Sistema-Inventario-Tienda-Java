import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Interfaz extends JFrame {
    private final Inventario inventario;
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private final JPanel contenido;
    private final JButton productosBoton;
    private final JButton stockBoton;

    private final Color azul = new Color(27, 42, 74);
    private final Color verde = new Color(22, 139, 111);
    private final Color fondo = new Color(241, 246, 250);
    private final Color rojo = new Color(211, 55, 62);

    public Interfaz(Inventario inventario) {
        this.inventario = inventario;
        setTitle("Abarrotes | Inventario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 680);
        setMinimumSize(new Dimension(850, 560));
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel(new Object[] {
            "Código", "Producto", "Categoría", "Cantidad", "Stock mínimo"
        }, 0) {
            public boolean isCellEditable(int fila, int columna) { return false; }
        };
        tabla = new JTable(modelo);
        contenido = new JPanel(new BorderLayout());
        productosBoton = crearBotonMenu("  Productos", verde);
        stockBoton = crearBotonMenu("  Stock bajo", azul);
        configurarTabla();

        JPanel menu = new JPanel();
        menu.setBackground(azul);
        menu.setPreferredSize(new Dimension(205, 0));
        menu.setBorder(BorderFactory.createEmptyBorder(25, 16, 20, 16));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        JLabel marca = new JLabel("TIENDA\nAbarrotes");
        marca.setForeground(Color.WHITE);
        marca.setFont(new Font("Segoe UI", Font.BOLD, 23));
        marca.setBorder(BorderFactory.createEmptyBorder(0, 5, 28, 0));
        productosBoton.addActionListener(e -> mostrarProductos());
        stockBoton.addActionListener(e -> mostrarStock());
        menu.add(marca);
        menu.add(productosBoton);
        menu.add(Box.createVerticalStrut(10));
        menu.add(stockBoton);

        JPanel marco = new JPanel(new BorderLayout());
        marco.setBackground(fondo);
        marco.add(menu, BorderLayout.WEST);
        marco.add(contenido, BorderLayout.CENTER);
        setContentPane(marco);
        mostrarProductos();
    }

    private JButton crearBotonMenu(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
        boton.setMaximumSize(new Dimension(175, 52));
        boton.setPreferredSize(new Dimension(175, 52));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        boton.setHorizontalAlignment(JButton.LEFT);
        return boton;
    }

    private void mostrarProductos() {
        productosBoton.setBackground(verde);
        stockBoton.setBackground(azul);
        contenido.removeAll();
        JPanel pagina = new JPanel(new BorderLayout(0, 18));
        pagina.setBackground(fondo);
        pagina.setBorder(BorderFactory.createEmptyBorder(30, 32, 28, 32));

        JPanel titulo = new JPanel(new BorderLayout(0, 5));
        titulo.setOpaque(false);
        JLabel nombre = new JLabel("Productos disponibles");
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 29));
        nombre.setForeground(azul);
        JLabel ayuda = new JLabel("Ordenados por cantidad: de menor a mayor");
        ayuda.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ayuda.setForeground(new Color(94, 108, 126));
        titulo.add(nombre, BorderLayout.NORTH);
        titulo.add(ayuda, BorderLayout.CENTER);

        actualizarProductosDisponibles();
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(211, 222, 231)));
        scroll.getViewport().setBackground(Color.WHITE);
        JButton agregar = botonGrande("＋  AGREGAR PRODUCTO", verde);
        agregar.addActionListener(e -> agregarProducto());
        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        abajo.setOpaque(false);
        abajo.add(agregar);
        pagina.add(titulo, BorderLayout.NORTH);
        pagina.add(scroll, BorderLayout.CENTER);
        pagina.add(abajo, BorderLayout.SOUTH);
        contenido.add(pagina);
        contenido.revalidate();
        contenido.repaint();
    }

    private void mostrarStock() {
        productosBoton.setBackground(azul);
        stockBoton.setBackground(rojo);
        contenido.removeAll();
        JPanel pagina = new JPanel(new BorderLayout(0, 18));
        pagina.setBackground(fondo);
        pagina.setBorder(BorderFactory.createEmptyBorder(30, 32, 28, 32));

        ArrayList<Producto> urgentes = inventario.obtenerUrgentes(); // Alertas ordenadas por el árbol.
        JPanel titulo = new JPanel(new BorderLayout(0, 5));
        titulo.setOpaque(false);
        JLabel nombre = new JLabel("Stock bajo");
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 29));
        nombre.setForeground(rojo);
        JLabel ayuda = new JLabel("Productos por reponer, ordenados desde la menor cantidad");
        ayuda.setForeground(new Color(94, 108, 126));
        titulo.add(nombre, BorderLayout.NORTH);
        titulo.add(ayuda, BorderLayout.CENTER);

        JPanel tarjetas = new JPanel();
        tarjetas.setBackground(fondo);
        tarjetas.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tarjetas.setLayout(new BoxLayout(tarjetas, BoxLayout.Y_AXIS));
        if (urgentes.isEmpty()) {
            JLabel vacio = new JLabel("¡Todo bien! No hay productos pendientes de reposición.");
            vacio.setFont(new Font("Segoe UI", Font.BOLD, 18));
            vacio.setForeground(verde);
            vacio.setBorder(BorderFactory.createEmptyBorder(25, 15, 15, 15));
            tarjetas.add(vacio);
        }
        for (Producto producto : urgentes) tarjetas.add(crearTarjeta(producto));
        JScrollPane scroll = new JScrollPane(tarjetas);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(fondo);
        pagina.add(titulo, BorderLayout.NORTH);
        pagina.add(scroll, BorderLayout.CENTER);
        contenido.add(pagina);
        contenido.revalidate();
        contenido.repaint();
    }

    private JPanel crearTarjeta(Producto producto) {
        JPanel tarjeta = new JPanel(new BorderLayout(18, 0));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(228, 82, 88), 2),
            BorderFactory.createEmptyBorder(15, 18, 15, 18)));
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 112));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel datos = new JPanel(new BorderLayout(0, 5));
        datos.setOpaque(false);
        JLabel nombre = new JLabel(producto.getNombre() + "  ·  " + producto.getCategoria());
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 19));
        nombre.setForeground(azul);
        JLabel codigo = new JLabel("Código " + producto.getCodigo() + "   ·   Reposición necesaria");
        codigo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        codigo.setForeground(new Color(112, 124, 140));
        datos.add(nombre, BorderLayout.NORTH);
        datos.add(codigo, BorderLayout.CENTER);

        JPanel indicadores = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        indicadores.setOpaque(false);
        indicadores.add(crearIndicador("DISPONIBLE", producto.getCantidad(),
            new Color(255, 226, 224), new Color(181, 34, 44)));
        indicadores.add(crearIndicador("MÍNIMO", producto.getStockMinimo(),
            new Color(230, 239, 249), azul));

        JButton reponer = botonGrande("＋  REPONER", verde);
        reponer.addActionListener(e -> reponerProducto(producto));
        tarjeta.add(datos, BorderLayout.CENTER);
        JPanel centro = new JPanel(new BorderLayout(12, 0));
        centro.setOpaque(false);
        centro.add(indicadores, BorderLayout.CENTER);
        centro.add(reponer, BorderLayout.EAST);
        tarjeta.add(centro, BorderLayout.EAST);
        return tarjeta;
    }

    private JPanel crearIndicador(String titulo, int valor, Color color, Color texto) {
        JPanel caja = new JPanel(new BorderLayout(0, 2));
        caja.setBackground(color);
        caja.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            BorderFactory.createEmptyBorder(7, 15, 7, 15)));
        caja.setPreferredSize(new Dimension(112, 76));
        JLabel numero = new JLabel(Integer.toString(valor), JLabel.CENTER);
        numero.setFont(new Font("Segoe UI", Font.BOLD, 27));
        numero.setForeground(texto);
        JLabel etiqueta = new JLabel(titulo, JLabel.CENTER);
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 10));
        etiqueta.setForeground(texto);
        caja.add(numero, BorderLayout.CENTER);
        caja.add(etiqueta, BorderLayout.SOUTH);
        return caja;
    }

    private JButton botonGrande(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(210, 48));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        return boton;
    }

    private void configurarTabla() {
        tabla.setRowHeight(43);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(new Color(230, 235, 240));
        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setBackground(azul);
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(JLabel.CENTER);
        tabla.getColumnModel().getColumn(0).setCellRenderer(centrado);
        tabla.getColumnModel().getColumn(3).setCellRenderer(centrado);
        tabla.getColumnModel().getColumn(4).setCellRenderer(centrado);
    }

    private void actualizarProductosDisponibles() {
        modelo.setRowCount(0);
        for (Producto x : inventario.obtenerProductosOrdenados()) { // Recibe el recorrido en orden.
            if (x.getCantidad() > x.getStockMinimo()) // Solo muestra productos disponibles.
                modelo.addRow(new Object[] {x.getCodigo(), x.getNombre(), x.getCategoria(),
                    x.getCantidad(), x.getStockMinimo()});
        }
    }

    private void agregarProducto() {
        JTextField nombre = new JTextField(18);
        String[] categorias = {"Abarrotes", "Bebidas", "Lácteos", "Frutas y verduras",
            "Refrigerados", "Aseo", "Snacks", "Otro"};
        JComboBox<String> categoria = new JComboBox<>(categorias);
        JComboBox<Integer> cantidad = crearCantidades(0, 0);
        JComboBox<Integer> minimo = crearCantidades(5, 0);
        Object[] campos = {"Nombre del producto:", nombre, "Categoría:", categoria,
            "Cantidad disponible:", cantidad, "Stock mínimo:", minimo};
        int opcion = JOptionPane.showConfirmDialog(this, campos, "Nuevo producto",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcion != JOptionPane.OK_OPTION) return;
        String nom = nombre.getText().trim();
        if (nom.isEmpty() || nom.contains("|")) {
            JOptionPane.showMessageDialog(this, "Escribe un nombre válido para el producto.");
            return;
        }
        String codigo = inventario.generarCodigo();
        int unidades = (Integer) cantidad.getSelectedItem();
        int minimoStock = (Integer) minimo.getSelectedItem();
        inventario.agregar(new Producto(codigo, nom, (String) categoria.getSelectedItem(),
            unidades, minimoStock));
        if (unidades <= minimoStock) mostrarStock(); // Si nace con poco stock, aparece en alertas.
        else mostrarProductos();
    }

    private JComboBox<Integer> crearCantidades(int seleccion, int minimo) {
        Integer[] valores = new Integer[101 - minimo];
        for (int i = minimo; i <= 100; i++) valores[i - minimo] = i;
        JComboBox<Integer> combo = new JComboBox<>(valores);
        combo.setSelectedItem(seleccion);
        return combo;
    }

    private void reponerProducto(Producto producto) {
        JComboBox<Integer> unidades = crearCantidades(1, 1);
        Object[] campos = {"Unidades para sumar:", unidades};
        int opcion = JOptionPane.showConfirmDialog(this, campos,
            "Reponer " + producto.getNombre(), JOptionPane.OK_CANCEL_OPTION);
        if (opcion != JOptionPane.OK_OPTION) return;
        int nuevaCantidad = producto.getCantidad() + (Integer) unidades.getSelectedItem();
        inventario.actualizarCantidad(producto.getCodigo(), nuevaCantidad); // Guarda y actualiza lista y árbol.
        if (nuevaCantidad > producto.getStockMinimo()) mostrarProductos(); // Al reponer, pasa a disponibles.
        else mostrarStock();
    }
}
