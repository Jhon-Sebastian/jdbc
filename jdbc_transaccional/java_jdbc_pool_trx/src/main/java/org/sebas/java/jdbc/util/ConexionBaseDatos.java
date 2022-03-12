package org.sebas.java.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
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

    private static BasicDataSource pool;


    public static BasicDataSource getInstance() throws SQLException {
        if(pool == null){
            pool = new BasicDataSource();
            pool.setUrl(URL);
            pool.setUsername(USERNAME);
            pool.setPassword(PASSWORD);
            //# de conexiones para comenzar, si no se configura es 0
            pool.setInitialSize(3);
            //# minimo de conexiones inactivas esperando para ser utilizadas
            pool.setMinIdle(3);
            //# maximo de conexiones inactivas esperando para ser utilizadas
            pool.setMaxIdle(8);
            //# pool total entre inactivas y activas
            pool.setMaxTotal(8);
        }
        return pool;
    }

    //Con este metodo obtenemos una conexion de las 8 definidas arriba
    public static Connection oneInstance() throws SQLException {
        return getInstance().getConnection();
    }

}
