package org.sebas.java.jdbc.repository;

import org.sebas.java.jdbc.modelo.Categoria;
import org.sebas.java.jdbc.modelo.Producto;
import org.sebas.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jhonc
 * @version 1.0
 * @since 24/02/2022
 */
public class ProductoRepositoryImpl implements Repositorio<Producto> {

    /*
        TODO: Creando y cerrando Conexion BD en cada metodo del CRUD
     */

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getInstance();
    }


    @Override
    public List<Producto> getAll() {
        String sql = "SELECT p.*, c.nombre as nombre_categoria FROM productos as p " +
                "inner join categorias as c on (p.categoria_id=c.id)";
        List<Producto> productos = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             //Sin Foreign Key -> SELECT * FROM productos
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productos.add(createProducto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }


    @Override
    public Producto findById(Long id) {
        String sql = "SELECT p.*, c.nombre as nombre_categoria FROM productos as p " +
                "inner join categorias as c on (p.categoria_id=c.id) where p.id=?";
        Producto producto = null;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = createProducto(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }


    @Override
    public void save(Producto producto) {
        String sql = null;
        if (producto.getId() != null && producto.getId() > 0) { //Update
            sql = "UPDATE productos SET nombre=?, precio=?, categoria_id=? WHERE id=?";
        } else { // INSERT
            sql = "INSERT INTO productos (nombre, precio, categoria_id, fecha_registro) VALUES (?,?,?,?)";
        }
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setLong(3, producto.getCategoria().getId());

            if (producto.getId() != null && producto.getId() > 0) { //UPDATE
                stmt.setLong(4, producto.getId());
            } else { //INSERT
                stmt.setDate(4, new Date(producto.getFechaRegistro().getTime()));
            }
            //Es executeUdpate debido a que vamos a guardar o ha actualizar
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deleteById(Long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn
                     .prepareStatement("DELETE FROM productos WHERE id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private Producto createProducto(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("categoria_id"));
        categoria.setNombre(rs.getString("nombre_categoria"));

        return new Producto(
                rs.getLong("id"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getDate("fecha_registro"),
                categoria
        );
    }

}
