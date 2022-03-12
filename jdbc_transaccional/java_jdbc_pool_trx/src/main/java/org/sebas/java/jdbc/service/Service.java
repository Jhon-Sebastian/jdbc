package org.sebas.java.jdbc.service;

import org.sebas.java.jdbc.modelo.Categoria;
import org.sebas.java.jdbc.modelo.Producto;

import java.sql.SQLException;
import java.util.List;

/**
 * @author jhonc
 * @version 1.0
 * @since 12/03/2022
 */
public interface Service {

    List<Producto> listarProductos() throws SQLException;

    Producto porId(Long id) throws SQLException;

    Producto guardar(Producto producto) throws SQLException;

    void eliminar(Long id) throws SQLException;

    //Guarda el producto con la categoria
    void guardarProductoConCategoria(Producto producto, Categoria categoria) throws SQLException;

    List<Categoria> listarCategoria() throws SQLException;

    Categoria porIdCategoria(Long id) throws SQLException;

    Categoria guardarCategoria(Categoria categoria) throws SQLException;

    void eliminarCategoria(Long id) throws SQLException;

}
