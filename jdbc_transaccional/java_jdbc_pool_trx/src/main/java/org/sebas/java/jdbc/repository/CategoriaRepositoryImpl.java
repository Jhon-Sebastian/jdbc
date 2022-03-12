package org.sebas.java.jdbc.repository;

import org.sebas.java.jdbc.modelo.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jhonc
 * @version 1.0
 * @since 8/03/2022
 */
public class CategoriaRepositoryImpl implements Repositorio<Categoria> {

    private Connection conn;

    public CategoriaRepositoryImpl() {
    }

    public CategoriaRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Categoria> getAll() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM categorias")) {
            while (rs.next()) {
                categorias.add(parserToCategoria(rs));
            }
        }
        return categorias;
    }

    @Override
    public Categoria findById(Long id) throws SQLException {
        Categoria categoria = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM categoria WHERE id=?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categoria = parserToCategoria(rs);
                }
            }
        }
        return categoria;
    }

    @Override
    public Categoria save(Categoria categoria) throws SQLException {
        String sql = (categoria.getId() != null && categoria.getId() > 0) ?
                "UPDATE categorias SET nombre=? WHERE id=?" :
                "INSERT INTO categorias (nombre) VALUES (?)";
        //Se habilita para que pueda retornar el ultimo id generado
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, categoria.getNombre());
            if (categoria.getId() != null && categoria.getId() > 0) {
                stmt.setLong(2, categoria.getId());
            }
            stmt.executeUpdate();
            if(categoria.getId() == null){
                //Se tra el el Id del ultimo objeto generado gracias al ResultSet
                try(ResultSet rs = stmt.getGeneratedKeys()){
                    if(rs.next()){
                        categoria.setId(rs.getLong(1));
                    }
                }
            }
        }
        return categoria;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM categorias WHERE id=?")){
            stmt.setLong(1,id);
            stmt.executeUpdate();
        }
    }

    private Categoria parserToCategoria(ResultSet rs) throws SQLException {
        return new Categoria(
                rs.getLong("id"),
                rs.getString("nombre"));
    }


}
