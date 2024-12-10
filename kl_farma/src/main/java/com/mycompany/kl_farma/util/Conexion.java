package com.mycompany.kl_farma.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/kl_farma"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "123456"; 
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Cargar el controlador JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
