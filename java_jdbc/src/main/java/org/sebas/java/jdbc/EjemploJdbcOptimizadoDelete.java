package org.sebas.java.jdbc;

import org.sebas.java.jdbc.modelo.Producto;
import org.sebas.java.jdbc.repository.ProductoRepositoryImpl;
import org.sebas.java.jdbc.repository.Repositorio;
import org.sebas.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author jhonc
 * @version 1.0
 * @since 24/02/2022
 */
public class EjemploJdbcOptimizadoDelete {

    private static Repositorio<Producto> repositorio;

    public static void main(String[] args) {

        try (Connection conn = ConexionBaseDatos.getInstance()) {

            repositorio = new ProductoRepositoryImpl();
            listarProductos();

            //Buscar producto por id
            System.out.print("\n    *** Producto a buscar por Id= " + 1 + " ***\n " + repositorio.findById(8L));

            System.out.println("   *** Eliminar producto ***");
            repositorio.deleteById(8L);

            System.out.println("\nProducto eliminado con éxito!\n");

            listarProductos();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void listarProductos(){
        System.out.println("    *** LISTADO DE PRODUCTOS ****");
        repositorio.getAll().forEach(System.out::print);
    }

}
