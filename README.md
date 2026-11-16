# Sistema de Inventario para Tienda

Aplicación de escritorio desarrollada en Java Swing para administrar los productos de una tienda y facilitar la reposición de existencias.

## Funcionalidades

- Registro de productos con código automático, categoría, cantidad y stock mínimo.
- Separación entre productos disponibles y productos que requieren reposición.
- Alertas ordenadas por cantidad para priorizar los productos con menos existencias.
- Reposición de productos y actualización automática de su estado.
- Guardado del inventario en un archivo de texto local.

## Estructuras de datos

- **Lista dinámica:** almacena, busca y elimina productos.
- **Árbol binario:** organiza los productos por cantidad y permite recorrerlos de menor a mayor.

## Clases principales

- `App`: inicia el programa.
- `Interfaz`: presenta las pantallas de productos disponibles y stock bajo.
- `Inventario`: conecta las estructuras de datos y guarda o carga los productos.
- `ListaProductos` y `NodoLista`: implementan la lista dinámica.
- `ArbolProductos` y `NodoArbol`: implementan el árbol binario.
- `Producto`: representa los datos de cada producto.

## Ejecución

Abre la carpeta del proyecto en Visual Studio Code y ejecuta `App.java`. También puedes compilar y ejecutar desde la terminal:

```bash
javac -d bin src/*.java
java -cp bin App
```

El archivo `productos.txt` se crea en la carpeta desde donde se inicia el programa y conserva los cambios del inventario.
