package org.sebas.java.jdbc.repository;

import org.sebas.java.jdbc.modelo.Categoria;
import org.sebas.java.jdbc.modelo.Producto;

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
        La conexion debe ser la misma para todos los metodos
     */
    private Connection conn;

    public ProductoRepositoryImpl() {
    }

    public ProductoRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Producto> getAll() throws SQLException {
        String sql = "SELECT p.*, c.nombre as nombre_categoria FROM productos as p " +
                "inner join categorias as c on (p.categoria_id=c.id)";
        List<Producto> productos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             //Sin Foreign Key -> SELECT * FROM productos
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productos.add(createProducto(rs));
            }
        }
        return productos;
    }


    //PreparedStatement debido a que va a ser preparado y usar where
    @Override
    public Producto findById(Long id) throws SQLException {
        String sql = "SELECT p.*, c.nombre as nombre_categoria FROM productos as p " +
                "inner join categorias as c on (p.categoria_id=c.id) where p.id=?";
        Producto producto = null;
        try (PreparedStatement stmt = conn
                .prepareStatement(sql)) {
            //Como solo hay un parametro id=?, solo tiene un indica ademas de el valor
            // Indice de los parametros que vamos a pasar, x = valor del parametro
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                //Pregunta si tiene un valor o encuentra el producto a buscar
                if (rs.next()) {
                    producto = createProducto(rs);
                }
            }
        }
        return producto;
    }


    @Override
    public Producto save(Producto producto) throws SQLException {
        String sql = null;
        if (producto.getId() != null && producto.getId() > 0) { //Update
            sql = "UPDATE productos SET nombre=?, precio=?, categoria_id=?, sku=? WHERE id=?";
        } else { // INSERT
            sql = "INSERT INTO productos (nombre, precio, categoria_id, sku, fecha_registro) VALUES (?,?,?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setLong(3, producto.getCategoria().getId());
            stmt.setString(4, producto.getSku());

            if (producto.getId() != null && producto.getId() > 0) { //UPDATE
                stmt.setLong(5, producto.getId());
            } else { //INSERT
                stmt.setDate(5, new Date(producto.getFechaRegistro().getTime()));
            }
            //Es executeUdpate debido a que vamos a guardar o ha actualizar
            stmt.executeUpdate();
            if (producto.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        //Con esto se obtiene el id del ultimo producto insertado, para luego tenerlo
                        //de referencia para poder insertar una categoria
                        producto.setId(rs.getLong(1));
                    }
                }
            }
        }
        return producto;
    }


    @Override
    public void deleteById(Long id) throws SQLException {
        try (PreparedStatement stmt = conn
                .prepareStatement("DELETE FROM productos WHERE id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
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
                categoria,
                rs.getString("sku")
        );
    }

}
