package org.sebas.practica.jdbc.singleton.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author jhonc
 * @version 1.0
 * @since 26/02/2022
 */
public class ConnectionDataBase {

    private static final String URL = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=America/Bogota";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";

    private static Connection connection;


    public static Connection getConnection() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }

}
