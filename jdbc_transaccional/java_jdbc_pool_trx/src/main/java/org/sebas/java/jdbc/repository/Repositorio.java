package org.sebas.java.jdbc.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author jhonc
 * @version 1.0
 * @since 24/02/2022
 */
public interface Repositorio<T> {

    List<T> getAll() throws SQLException;

    T findById(Long id) throws SQLException;

    T save(T t) throws SQLException;

    void deleteById(Long id) throws SQLException;

    void setConn(Connection conn);

}
