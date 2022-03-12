package org.sebas.java.jdbc;

import org.sebas.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;

/**
 * @author jhonc
 * @version 1.0
 * @since 24/02/2022
 */
public class EjemploJdbc {

    public static void main(String[] args) {

        // Connection -> Permite crear la conexion
//        Connection conn = null;
//        Statement stmt = null;
//        ResultSet resultado = null;

        Integer id = null;
        String nombre = null;
        Double precio = 0.0;
        Date fechaRegistro = null;

        /*
            TODO -> Nueva funcionalidad de java en el try-catch
            Permite cerrar las conexiones independientemente si ocurre o no
            un error ademas de que valida que los objetos no sean null,
            haciendo un un auto close, mas sencillo el codigo, legible
         */
        try (
                // DriverManager -> Administra los drivers instaladas en el sistema
                Connection conn = ConexionBaseDatos.getInstance();

                //Permite ejecutar sentencias sql sin parametros
                Statement stmt = conn.createStatement();

                //Atrapa las filas o resultados obtenidos de la BD
                ResultSet resultado = stmt.executeQuery("SELECT * FROM productos");
        ) {

            while (resultado.next()) {
                id = resultado.getInt("id");
                nombre = resultado.getString("nombre");
                precio = resultado.getDouble("precio");
                fechaRegistro = resultado.getDate("fecha_registro");

                System.out.println("Persona[ id=" + id
                        + ", nombre=" + nombre
                        + ", precio=" + precio
                        + ", fecha=" + fechaRegistro
                        + " ]"
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
