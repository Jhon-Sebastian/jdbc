package org.sebas.java.jdbc;

import org.sebas.java.jdbc.modelo.Categoria;
import org.sebas.java.jdbc.modelo.Producto;
import org.sebas.java.jdbc.repository.ProductoRepositoryImpl;
import org.sebas.java.jdbc.repository.Repositorio;
import org.sebas.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;

/**
 * @author jhonc
 * @version 1.0
 * @since 24/02/2022
 */
public class EjemploJdbcTrx {

    public static void main(String[] args) throws SQLException {


        Integer id = null;
        String nombre = null;
        Double precio = 0.0;
        Date fechaRegistro = null;
        Repositorio<Producto> repositorio = new ProductoRepositoryImpl();

        try (Connection conn = ConexionBaseDatos.getInstance()) {

            /*
                TODO -> Configuracion
                Para evitar que se haga algun cambio en la BD cuando ocurre un error
                y que la info quede incosistente, se usa Transaccionalidad lo que
                precisamente evitar esto con algunas configuraciones como lo son el
                commit cuando todoo esta bien o hacer rollback cuando ocurre un error
                y asi evitar que la BD se afecte con inconsistencias y ademas volver
                al estado anterior

                *Se tiene que anidar un try-catch debido a que el try princiapl
                cierra la conexion y no permite hacer el rollback, por ello el try
                interno para encerrar la exception y poder hacer rollback en caso necesario
             */

            //Lo desabilitados para que no haga un commit o cambio en la BD si ocurre o no un error
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            try {
                System.out.println("Listado de Productos");
                repositorio.getAll().forEach(System.out::print);

                System.out.println("   *** Insertar nuevo producto ***");
                Producto p = new Producto();
                p.setNombre("Teclado Mecánico Red Dragon Ultime");
                p.setPrecio(5000D);
                p.setFechaRegistro(new java.util.Date());
                p.setSku("abdce1234");
                Categoria categoria = new Categoria();
                categoria.setId(3L);
                p.setCategoria(categoria);
                repositorio.save(p);
                System.out.println("\nProducto guardado con éxito!\n");


                System.out.println("   *** Editar nuevo producto ***");
                p = new Producto();
                p.setId(5L);
                p.setNombre("Balon de Futbol");
                p.setPrecio(12000D);
                p.setSku("abdce12345");
                categoria = new Categoria();
                categoria.setId(1L);
                p.setCategoria(categoria);
                repositorio.save(p);
                System.out.println("\nProducto editado con éxito!\n");
                repositorio.getAll().forEach(System.out::print);


                //Si todoo va bien, que haga el commit
                conn.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                conn.rollback();
            }
        }

    }

}
