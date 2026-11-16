public class Producto {
    private String codigo;
    private String nombre;
    private String categoria;
    private int cantidad;
    private int stockMinimo;

    public Producto(String codigo, String nombre, String categoria,
            int cantidad, int stockMinimo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.stockMinimo = stockMinimo;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public int getCantidad() { return cantidad; }
    public int getStockMinimo() { return stockMinimo; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
