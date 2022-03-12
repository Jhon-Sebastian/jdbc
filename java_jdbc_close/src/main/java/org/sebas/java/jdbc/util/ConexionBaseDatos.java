package org.sebas.java.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author jhonc
 * @version 1.0
 * @since 24/02/2022
 */
public class ConexionBaseDatos {

    private static final String URL = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=America/Bogota";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";

    private static Connection connection;


    public static Connection getInstance() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
