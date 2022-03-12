package org.sebas.practica.jdbc.singleton.repository;

import org.sebas.practica.jdbc.singleton.model.User;
import org.sebas.practica.jdbc.singleton.util.ConnectionDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jhonc
 * @version 1.0
 * @since 26/02/2022
 */
public class UserRepositoryImpl implements ModelRepository<User> {

    private Connection getInstance() throws SQLException {
        return ConnectionDataBase.getConnection();
    }


    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Statement stmt = getInstance().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios")) {
            while (rs.next()) {
                users.add(createUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findById(Long id) {
        User u = null;
        try (PreparedStatement stmt = getInstance().prepareStatement("SELECT * FROM usuarios WHERE id=?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    u = createUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }


    @Override
    public void save(User user) {
        String sqlStatement = (user.getId() != null && user.getId() > 0) ?
                "UPDATE usuarios SET username=?, password=?, email =? WHERE id=?" :
                "INSERT INTO usuarios (username, password, email) VALUES (?,?,?)";

        try (PreparedStatement stmt = getInstance().prepareStatement(sqlStatement)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());

            if (user.getId() != null && user.getId() > 0)
                stmt.setLong(4, user.getId());

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public void delete(Long id) {
        try (PreparedStatement stmt = getInstance().prepareStatement("DELETE FROM usuarios WHERE id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private User createUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email")
        );
    }

}
