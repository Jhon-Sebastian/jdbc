package org.sebas.java.jdbc;

import org.sebas.java.jdbc.modelo.Categoria;
import org.sebas.java.jdbc.modelo.Producto;
import org.sebas.java.jdbc.repository.ProductoRepositoryImpl;
import org.sebas.java.jdbc.repository.Repositorio;
import org.sebas.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author jhonc
 * @version 1.0
 * @since 24/02/2022
 */
public class EjemploJdbcOptimizadoUpdate {


    public static void main(String[] args) {

        try (Connection conn = ConexionBaseDatos.getInstance()) {

            Repositorio<Producto> repositorio = new ProductoRepositoryImpl();
            listarProductos(repositorio);

            //Buscar producto por id
            System.out.print("\n    *** Producto a buscar por Id= " + 1 + " ***\n " + repositorio.findById(1L));

            System.out.println("   *** Insertar nuevo producto ***");
            Producto p = new Producto();
            p.setId(7L);
            p.setNombre("Balon de Futbol");
            p.setPrecio(12000D);

            Categoria categoria = new Categoria();
            categoria.setId(1L);

            p.setCategoria(categoria);

            repositorio.save(p);
            System.out.println("\nProducto editado con éxito!\n");

            listarProductos(repositorio);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void listarProductos(Repositorio<Producto> repositorio){
        System.out.println("    *** LISTADO DE PRODUCTOS ****");
        repositorio.getAll().forEach(System.out::print);
    }

}
