package org.sebas.java.jdbc.modelo;

/**
 * @author jhonc
 * @version 1.0
 * @since 25/02/2022
 */
public class Categoria {

    private Long id;
    private String nombre;

    public Categoria() {

    }

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public Categoria(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return  this.id + " | " +
                this.nombre + "\n";
    }
}
