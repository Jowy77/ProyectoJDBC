package com.mycompany.proyectojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {

    String url = "jdbc:h2:file:./src/main/resources/database/BBDD";
    String user = "sa";
    String password = "";

    public void crearTablasMostrandoSQL() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            // Eliminar tabla Alumno si existe y luego crearla con ID autoincremental
            String createAlumnoTableQuery = "DROP TABLE IF EXISTS Alumno;"
                    + "CREATE TABLE Alumno ("
                    + "    id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "    nombre VARCHAR(50),"
                    + "    telefono INT,"
                    + "    direccion VARCHAR(100)"
                    + ")";
            System.out.println("SQL para crear tabla Alumno: " + createAlumnoTableQuery);
            statement.executeUpdate(createAlumnoTableQuery);

            // Eliminar tabla Direccion si existe y luego crearla con ID autoincremental
            String createDireccionTableQuery = "DROP TABLE IF EXISTS Direccion;"
                    + "CREATE TABLE Direccion ("
                    + "    id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "    idAlumno INT,"
                    + "    direccion VARCHAR(100),"
                    + "    FOREIGN KEY (idAlumno) REFERENCES Alumno(id)"
                    + ")";
            System.out.println("SQL para crear tabla Direccion: " + createDireccionTableQuery);
            statement.executeUpdate(createDireccionTableQuery);

            // Eliminar tabla Familiar si existe y luego crearla con ID autoincremental
            String createFamiliarTableQuery = "DROP TABLE IF EXISTS Familiar;"
                    + "CREATE TABLE Familiar ("
                    + "    id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "    idAlumno INT,"
                    + "    nombre VARCHAR(50),"
                    + "    sexo VARCHAR(10),"
                    + "    telefono INT,"
                    + "    custodia BOOLEAN,"
                    + "    FOREIGN KEY (idAlumno) REFERENCES Alumno(id)"
                    + ")";
            System.out.println("SQL para crear tabla Familiar: " + createFamiliarTableQuery);
            statement.executeUpdate(createFamiliarTableQuery);

            // Eliminar tabla Asignatura si existe y luego crearla con ID autoincremental
            String createAsignaturaTableQuery = "DROP TABLE IF EXISTS Asignatura;"
                    + "CREATE TABLE Asignatura ("
                    + "    id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "    idAlumno INT,"
                    + "    nombreAsignatura VARCHAR(50),"
                    + "    curso VARCHAR(20),"
                    + "    notas INT,"
                    + "    FOREIGN KEY (idAlumno) REFERENCES Alumno(id)"
                    + ")";
            System.out.println("SQL para crear tabla Asignatura: " + createAsignaturaTableQuery);
            statement.executeUpdate(createAsignaturaTableQuery);

            // Cerrar recursos
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al crear las tablas: " + e.getMessage());
        }
    }

    public boolean insertarAlumno(String nombre, int telefono, String direccion) {
        boolean exito = false;
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String insertQuery = "INSERT INTO Alumno (nombre, telefono, direccion) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, telefono);
            preparedStatement.setString(3, direccion);

            int filasInsertadas = preparedStatement.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Datos insertados en la tabla Alumno.");
                exito = true; // Se insertó correctamente si filasInsertadas es mayor que 0
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al insertar datos en la tabla Alumno: " + e.getMessage());
        }
        return exito;
    }

    public boolean insertarDireccion(int idAlumno, String direccion) {
        boolean exito = false;
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String insertQuery = "INSERT INTO Direccion (idAlumno, direccion) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, idAlumno);
            preparedStatement.setString(2, direccion);

            int filasInsertadas = preparedStatement.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Datos insertados en la tabla Direccion.");
                exito = true;
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al insertar datos en la tabla Direccion: " + e.getMessage());
        }
        return exito;
    }

    public boolean insertarFamiliar(int idAlumno, String nombre, String sexo, int telefono, boolean custodia) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String insertQuery = "INSERT INTO Familiar (idAlumno, nombre, sexo, telefono, custodia) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, idAlumno);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, sexo);
            preparedStatement.setInt(4, telefono);
            preparedStatement.setBoolean(5, custodia);

            int filasInsertadas = preparedStatement.executeUpdate();
            System.out.println("Datos insertados en la tabla Familiar.");

            preparedStatement.close();
            connection.close();

            return filasInsertadas > 0; // Devuelve true si se insertó al menos una fila
        } catch (SQLException e) {
            System.err.println("Error al insertar datos en la tabla Familiar: " + e.getMessage());
            return false; // Retorna false en caso de error
        }
    }

    public void insertarAsignatura(int idAlumno, String nombreAsignatura, String curso, int notas) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String insertQuery = "INSERT INTO Asignatura (idAlumno, nombreAsignatura, curso, notas) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, idAlumno);
            preparedStatement.setString(2, nombreAsignatura);
            preparedStatement.setString(3, curso);
            preparedStatement.setInt(4, notas);

            preparedStatement.executeUpdate();
            System.out.println("Datos insertados en la tabla Asignatura.");

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al insertar datos en la tabla Asignatura: " + e.getMessage());
        }
    }

    public String consultarTodosLosAlumnos() {
        StringBuilder result = new StringBuilder();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String query = "SELECT * FROM Alumno";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                int telefono = resultSet.getInt("telefono");
                String direccion = resultSet.getString("direccion");

                result.append("ID: ").append(id)
                        .append(", Nombre: ").append(nombre)
                        .append(", Teléfono: ").append(telefono)
                        .append(", Dirección: ").append(direccion)
                        .append("\n");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al consultar los alumnos: " + e.getMessage());
        }
        return result.toString();
    }

    public String consultarTodosLosFamiliares() {
        StringBuilder result = new StringBuilder();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String query = "SELECT * FROM Familiar";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idAlumno = resultSet.getInt("idAlumno");
                String nombre = resultSet.getString("nombre");
                String sexo = resultSet.getString("sexo");
                int telefono = resultSet.getInt("telefono");
                boolean custodia = resultSet.getBoolean("custodia");

                result.append("ID: ").append(id)
                        .append(", ID Alumno: ").append(idAlumno)
                        .append(", Nombre: ").append(nombre)
                        .append(", Sexo: ").append(sexo)
                        .append(", Teléfono: ").append(telefono)
                        .append(", Custodia: ").append(custodia)
                        .append("\n");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al consultar los familiares: " + e.getMessage());
        }
        return result.toString();
    }

    public String consultarTodasLasAsignaturas() {
        StringBuilder result = new StringBuilder();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String query = "SELECT * FROM Asignatura";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idAlumno = resultSet.getInt("idAlumno");
                String nombreAsignatura = resultSet.getString("nombreAsignatura");
                String curso = resultSet.getString("curso");
                int notas = resultSet.getInt("notas");

                result.append("ID: ").append(id)
                        .append(", ID Alumno: ").append(idAlumno)
                        .append(", Nombre Asignatura: ").append(nombreAsignatura)
                        .append(", Curso: ").append(curso)
                        .append(", Notas: ").append(notas)
                        .append("\n");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al consultar las asignaturas: " + e.getMessage());
        }
        return result.toString();
    }

    public String consultarTodasLasDirecciones() {
        StringBuilder result = new StringBuilder();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String query = "SELECT * FROM Direccion";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idAlumno = resultSet.getInt("idAlumno");
                String direccion = resultSet.getString("direccion");

                result.append("ID: ").append(id)
                        .append(", ID Alumno: ").append(idAlumno)
                        .append(", Dirección: ").append(direccion)
                        .append("\n");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al consultar las direcciones: " + e.getMessage());
        }
        return result.toString();
    }

    public String consultarAlumnosPorNombre(String nombre) {
        StringBuilder result = new StringBuilder();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String query = "SELECT * FROM Alumno WHERE nombre = ? LIMIT 3";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nombre);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombreAlumno = resultSet.getString("nombre");
                int telefono = resultSet.getInt("telefono");
                String direccion = resultSet.getString("direccion");

                result.append("ID: ").append(id)
                        .append(", Nombre: ").append(nombreAlumno)
                        .append(", Teléfono: ").append(telefono)
                        .append(", Dirección: ").append(direccion)
                        .append("\n");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al consultar los alumnos por nombre: " + e.getMessage());
        }
        return result.toString();
    }

    public String consultarFamiliaresDeAlumno(int idAlumno) {
        StringBuilder result = new StringBuilder();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String query = "SELECT * FROM Familiar WHERE idAlumno = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idAlumno);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idFamiliar = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String sexo = resultSet.getString("sexo");
                int telefono = resultSet.getInt("telefono");
                boolean custodia = resultSet.getBoolean("custodia");

                result.append("ID Familiar: ").append(idFamiliar)
                        .append(", Nombre: ").append(nombre)
                        .append(", Sexo: ").append(sexo)
                        .append(", Teléfono: ").append(telefono)
                        .append(", Custodia: ").append(custodia)
                        .append("\n");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al consultar los familiares del alumno: " + e.getMessage());
        }
        return result.toString();
    }

    public String consultarFamiliaresConCustodia() {
        StringBuilder result = new StringBuilder();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String query = "SELECT * FROM Familiar WHERE custodia = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, true); // Filtra por custodia true

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idFamiliar = resultSet.getInt("id");
                int idAlumno = resultSet.getInt("idAlumno");
                String nombre = resultSet.getString("nombre");
                String sexo = resultSet.getString("sexo");
                int telefono = resultSet.getInt("telefono");

                result.append("ID Familiar: ").append(idFamiliar)
                        .append(", ID Alumno: ").append(idAlumno)
                        .append(", Nombre: ").append(nombre)
                        .append(", Sexo: ").append(sexo)
                        .append(", Teléfono: ").append(telefono)
                        .append("\n");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al consultar los familiares con custodia: " + e.getMessage());
        }
        return result.toString();
    }

    public boolean modificarAsignatura(int idAlumno, String nombreAsignatura, String curso, int notas) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String updateQuery = "UPDATE Asignatura SET curso = ?, notas = ? WHERE idAlumno = ? AND nombreAsignatura = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, curso);
            preparedStatement.setInt(2, notas);
            preparedStatement.setInt(3, idAlumno);
            preparedStatement.setString(4, nombreAsignatura);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return rowsAffected > 0; // Devuelve verdadero si se han modificado filas en la base de datos
        } catch (SQLException e) {
            System.err.println("Error al modificar la asignatura: " + e.getMessage());
            return false;
        }
    }

    public boolean modificarCustodiaFamiliar(int idAlumno, String nombreFamiliar, boolean nuevaCustodia) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String updateQuery = "UPDATE Familiar SET custodia = ? WHERE idAlumno = ? AND nombre = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setBoolean(1, nuevaCustodia);
            preparedStatement.setInt(2, idAlumno);
            preparedStatement.setString(3, nombreFamiliar);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return rowsAffected > 0; // Devuelve verdadero si se han modificado filas en la base de datos
        } catch (SQLException e) {
            System.err.println("Error al modificar la custodia del familiar: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarAlumno(int idAlumno) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false); // Desactiva el modo de confirmación automática

            // Elimina los registros asociados al alumno en las otras tablas
            String deleteFamiliarQuery = "DELETE FROM Familiar WHERE idAlumno = ?";
            PreparedStatement deleteFamiliarStatement = connection.prepareStatement(deleteFamiliarQuery);
            deleteFamiliarStatement.setInt(1, idAlumno);
            deleteFamiliarStatement.executeUpdate();

            String deleteDireccionQuery = "DELETE FROM Direccion WHERE idAlumno = ?";
            PreparedStatement deleteDireccionStatement = connection.prepareStatement(deleteDireccionQuery);
            deleteDireccionStatement.setInt(1, idAlumno);
            deleteDireccionStatement.executeUpdate();

            String deleteAsignaturaQuery = "DELETE FROM Asignatura WHERE idAlumno = ?";
            PreparedStatement deleteAsignaturaStatement = connection.prepareStatement(deleteAsignaturaQuery);
            deleteAsignaturaStatement.setInt(1, idAlumno);
            deleteAsignaturaStatement.executeUpdate();

            // Elimina al alumno de la tabla Alumno
            String deleteAlumnoQuery = "DELETE FROM Alumno WHERE id = ?";
            PreparedStatement deleteAlumnoStatement = connection.prepareStatement(deleteAlumnoQuery);
            deleteAlumnoStatement.setInt(1, idAlumno);
            deleteAlumnoStatement.executeUpdate();

            connection.commit(); // Confirma la transacción

            deleteFamiliarStatement.close();
            deleteDireccionStatement.close();
            deleteAsignaturaStatement.close();
            deleteAlumnoStatement.close();
            connection.close();

            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar al alumno y sus datos asociados: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarAsignatura(int idAlumno, String nombreAsignatura) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String deleteQuery = "DELETE FROM Asignatura WHERE idAlumno = ? AND nombreAsignatura = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);

            preparedStatement.setInt(1, idAlumno);
            preparedStatement.setString(2, nombreAsignatura);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return rowsAffected > 0; // Devuelve verdadero si se han eliminado filas en la base de datos
        } catch (SQLException e) {
            System.err.println("Error al eliminar la asignatura: " + e.getMessage());
            return false;
        }
    }
}
