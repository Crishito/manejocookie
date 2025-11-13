package models;

/*
 *Autor: Christian Zumárraga
 * Fecha: 7/11/2025
 * Descripción: Esta clase Producto va a instanciar todos nuestros modelos y atributos
 */

public class Producto {

    //Declaramos la variables de mi objeto producto
    private Long idProducto;
    private String nombre;
    private String categoria;
    private Double precio;

    /*
     * Constructor vacío obligatorio para JavaBeans
     */
    public Producto() {
    }

    /*
     * Sobre carga de constructores
     */
    public Producto(Long idProducto, String nombre, String categoria, Double precio) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    // metodos getter a setter
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}