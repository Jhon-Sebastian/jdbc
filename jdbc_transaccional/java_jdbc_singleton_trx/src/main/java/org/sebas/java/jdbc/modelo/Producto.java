package org.sebas.java.jdbc.modelo;

import java.util.Date;

/**
 * @author jhonc
 * @version 1.0
 * @since 24/02/2022
 */
public class Producto {

    private Long id;
    private String nombre;
    private Double precio;
    private Date fechaRegistro;
    private Categoria categoria;
    private String sku;

    public Producto() {
    }

    public Producto(Long id, String nombre, Double precio, Date fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.fechaRegistro = fechaRegistro;
    }

    public Producto(Long id, String nombre, Double precio, Date fechaRegistro, Categoria categoria, String sku) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.fechaRegistro = fechaRegistro;
        this.categoria = categoria;
        this.sku = sku;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return this.id + " | " +
                this.nombre + " | " +
                this.precio + " | " +
                this.fechaRegistro + " | " +
                this.categoria.getId() + " | " +
                this.sku + " | " +
                this.categoria.getNombre() + "\n";
    }
}
