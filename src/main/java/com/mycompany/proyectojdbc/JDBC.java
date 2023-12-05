package com.mycompany.proyectojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {

    public static void main(String[] args) {
        String url = "jdbc:h2:file:./src/main/resources/database/BBDD";
        String user = "sa";
        String password = "";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida a la base de datos H2");

            Statement statement = connection.createStatement();

            // Crear tabla Alumno
            String createAlumnoTableQuery = "CREATE TABLE IF NOT EXISTS Alumno (" +
                    "id INT PRIMARY KEY, " +
                    "nombre VARCHAR(50), " +
                    "telefono INT, " +
                    "direccion VARCHAR(100)" +
                    ")";
            statement.executeUpdate(createAlumnoTableQuery);
            System.out.println("Tabla Alumno creada o ya existente");

            // Crear tabla Direccion
            String createDireccionTableQuery = "CREATE TABLE IF NOT EXISTS Direccion (" +
                    "id INT PRIMARY KEY, " +
                    "idAlumno INT, " +
                    "direccion VARCHAR(100), " +
                    "FOREIGN KEY (idAlumno) REFERENCES Alumno(id)" +
                    ")";
            statement.executeUpdate(createDireccionTableQuery);
            System.out.println("Tabla Direccion creada o ya existente");

            // Crear tabla Familiar
            String createFamiliarTableQuery = "CREATE TABLE IF NOT EXISTS Familiar (" +
                    "id INT PRIMARY KEY, " +
                    "idAlumno INT, " +
                    "nombre VARCHAR(50), " +
                    "sexo VARCHAR(10), " +
                    "telefono INT, " +
                    "custodia BOOLEAN, " +
                    "FOREIGN KEY (idAlumno) REFERENCES Alumno(id)" +
                    ")";
            statement.executeUpdate(createFamiliarTableQuery);
            System.out.println("Tabla Familiar creada o ya existente");

            // Crear tabla Asignatura
            String createAsignaturaTableQuery = "CREATE TABLE IF NOT EXISTS Asignatura (" +
                    "id INT PRIMARY KEY, " +
                    "idAlumno INT, " +
                    "nombreAsignatura VARCHAR(50), " +
                    "curso VARCHAR(20), " +
                    "notas INT, " +
                    "FOREIGN KEY (idAlumno) REFERENCES Alumno(id)" +
                    ")";
            statement.executeUpdate(createAsignaturaTableQuery);
            System.out.println("Tabla Asignatura creada o ya existente");

            // Cerrar recursos
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al crear las tablas: " + e.getMessage());
        }
    }
}