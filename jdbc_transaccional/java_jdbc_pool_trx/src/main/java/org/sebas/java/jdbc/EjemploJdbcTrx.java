package org.sebas.java.jdbc;

import org.sebas.java.jdbc.modelo.Categoria;
import org.sebas.java.jdbc.modelo.Producto;
import org.sebas.java.jdbc.repository.CategoriaRepositoryImpl;
import org.sebas.java.jdbc.repository.ProductoRepositoryImpl;
import org.sebas.java.jdbc.repository.Repositorio;
import org.sebas.java.jdbc.service.CatalogoService;
import org.sebas.java.jdbc.service.Service;
import org.sebas.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;

/**
 * @author jhonc
 * @version 1.0
 * @since 24/02/2022
 */
public class EjemploJdbcTrx {

    public static void main(String[] args) throws SQLException {


        Service service = new CatalogoService();

        System.out.println("\n-- Listado de CATEGORIAS --");
        service.listarCategoria().forEach(System.out::print);

        System.out.println("\n** Listado de PRODUCTOS **");
        service.listarProductos().forEach(System.out::print);

        System.out.println("\n-- Insertar nueva CATEGORIA --");
        Categoria categoria = new Categoria("BigData");

        System.out.println("** Insertar nuevo PRODUCTO **");
        Producto producto = new Producto();
        producto.setNombre("Mac Book Pro M1 Ultra");
        producto.setPrecio(4999.9D);
        producto.setFechaRegistro(new java.util.Date());
        producto.setSku("aeiou812");

        service.guardarProductoConCategoria(producto, categoria);

        System.out.println("\n-- Listado de CATEGORIAS --");
        service.listarCategoria().forEach(System.out::print);

        System.out.println("\n** Listado de PRODUCTOS **");
        service.listarProductos().forEach(System.out::print);


//        try (Connection conn = ConexionBaseDatos.oneInstance()) {
//
//            if (conn.getAutoCommit()) {
//                conn.setAutoCommit(false);
//            }
//
//            try {
//
//                Repositorio<Categoria> repositorioCategoria = new CategoriaRepositoryImpl(conn);
//                Repositorio<Producto> repositorioProducto = new ProductoRepositoryImpl(conn);
//
//
//                System.out.println("-- Insertar nueva CATEGORIA --");
//                Categoria categoria = new Categoria("TecnoVentas");
//                Categoria nuevaCategoria = repositorioCategoria.save(categoria);
//                System.out.println("-- CATEGORIA guardada con éxito! = "+ nuevaCategoria.getId() +"--\n");
//
//
//                System.out.println("-- Listado de CATEGORIAS --");
//                repositorioCategoria.getAll().forEach(System.out::print);
//
//                System.out.println("\n** Listado de PRODUCTOS **");
//                repositorioProducto.getAll().forEach(System.out::print);
//
//                System.out.println("\n** Insertar nuevo PRODUCTO **");
//                Producto p = new Producto();
//                p.setNombre("Ipod Apple");
//                p.setPrecio(1222D);
//                p.setFechaRegistro(new java.util.Date());
//                p.setSku("wxyz1234");
//                p.setCategoria(nuevaCategoria);
//                repositorioProducto.save(p);
//
//                System.out.println("\n** PRODUCTO guardado con éxito! = "+ p.getId() +"**\n");
//                repositorioProducto.getAll().forEach(System.out::print);
//
//                conn.commit();
//            } catch (SQLException ex) {
//                conn.rollback();
//                ex.printStackTrace();
//            }
//        }

    }

}
