package org.sebas.practica.jdbc.singleton.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author jhonc
 * @version 1.0
 * @since 26/02/2022
 */
public class ConnectionDataBase {

    /*
       TODO -> Pool de Conexiones
       Se define un minimo y maximo de conexiones a la base de datos
       las cuales ya estan cargadas en sl sistema con ello cada ves
       que se requiera se llama 1 conexion de las
       disponibles y cuando termina la operacion libera el recurso
       para luego volver a ver usado, para asi evitar que se
       consuma tantos recursos del sistemas por abrir y cerrar
       conexiones,

       BasicDataSource -> 1 por cada aplicacion, este si seria singleton
     */

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
