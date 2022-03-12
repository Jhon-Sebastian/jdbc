package org.sebas.java.jdbc;

import org.sebas.java.jdbc.modelo.Categoria;
import org.sebas.java.jdbc.modelo.Producto;
import org.sebas.java.jdbc.repository.ProductoRepositoryImpl;
import org.sebas.java.jdbc.repository.Repositorio;
import org.sebas.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.Date;

/**
 * @author jhonc
 * @version 1.0
 * @since 24/02/2022
 */
public class EjemploJdbcOptimizadoInsert {

    private static Repositorio<Producto> repositorio;

    public static void main(String[] args) {

        /*
            Metodo main, se ejecuta la app, ofreciendo las funcionalidades
            cuando se acabe la app ya sea porque se quiere o porque
            ocurrio un error, se aplica el auto close y se cierra la app
         */
        try (Connection conn = ConexionBaseDatos.getInstance()) {

            repositorio = new ProductoRepositoryImpl();
            listarProductos();

            //Buscar producto por id
            System.out.print("\n    *** Producto a buscar por Id= " + 1 + " ***\n " + repositorio.findById(1L));

            System.out.println("   *** Insertar nuevo producto ***");
            Producto p = new Producto();
            p.setNombre("Teclado Mecánico Red Dragon Ultime");
            p.setPrecio(5000D);
            p.setFechaRegistro(new Date());

            Categoria categoria = new Categoria();
            categoria.setId(3L);

            p.setCategoria(categoria);

            repositorio.save(p);
            System.out.println("\nProducto guardado con éxito!\n");

            listarProductos();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void listarProductos(){
        //Listado de productos
        System.out.println("    *** LISTADO DE PRODUCTOS ****");
        repositorio.getAll().forEach(System.out::print);
    }

}
