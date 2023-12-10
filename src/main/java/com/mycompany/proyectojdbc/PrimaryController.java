package com.mycompany.proyectojdbc;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


public class PrimaryController implements Initializable {

    JDBC jdbc = new JDBC();

    @FXML
    private Button crearTablas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void closeApp(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void CrearTablas(ActionEvent event) throws IOException, SQLException {
        jdbc.crearTablasMostrandoSQL();

        // Inserciones en la tabla Alumno
        /*jdbc.insertarAlumno("María", 555555555, "Avenida Central 789");
        jdbc.insertarAlumno("Carlos", 111222333, "Calle Secundaria 456");
        jdbc.insertarAlumno("Sofía", 777777777, "Boulevard Primario 101");
        jdbc.insertarAlumno("Pedro", 888999000, "Avenida Secundaria 567");
        jdbc.insertarAlumno("Laura", 444444444, "Calle Principal 222");
        jdbc.insertarAlumno("Miguel", 333444555, "Avenida Central 333");
        jdbc.insertarAlumno("Ana", 666666666, "Ruta Secundaria 678");
        jdbc.insertarAlumno("David", 999888777, "Calle Principal 777");
        jdbc.insertarAlumno("Lucía", 222111000, "Avenida Central 999");
        jdbc.insertarAlumno("Jorge", 555111888, "Ruta Secundaria 345");

        jdbc.insertarDireccion(1, "Calle Madrid 123");
        jdbc.insertarDireccion(2, "Avenida Roma 456");
        jdbc.insertarDireccion(3, "Calle París 789");
        jdbc.insertarDireccion(4, "Avenida Berlín 101");
        jdbc.insertarDireccion(5, "Calle Londres 222");
        jdbc.insertarDireccion(6, "Avenida Bruselas 333");
        jdbc.insertarDireccion(7, "Calle Oslo 678");
        jdbc.insertarDireccion(8, "Avenida Estocolmo 777");
        jdbc.insertarDireccion(9, "Calle Helsinki 999");
        jdbc.insertarDireccion(10, "Avenida Dublín 345");

        // Inserciones en la tabla Familiar
        // Se asume que los métodos de inserción de familiar usan el ID autoincremental de Alumno
        jdbc.insertarFamiliar(1, "Padre María", "M", 111222333, true);
        jdbc.insertarFamiliar(2, "Madre Carlos", "F", 444555666, true);
        jdbc.insertarFamiliar(3, "Hermano Sofía", "M", 777888999, false);
        jdbc.insertarFamiliar(4, "Hermana Pedro", "F", 101202303, true);
        jdbc.insertarFamiliar(5, "Abuelo Laura", "M", 404505606, false);
        jdbc.insertarFamiliar(6, "Abuela Miguel", "F", 707808909, true);
        jdbc.insertarFamiliar(7, "Tío Ana", "M", 111213141, false);
        jdbc.insertarFamiliar(8, "Tía David", "F", 516171819, true);
        jdbc.insertarFamiliar(9, "Primo Lucía", "M", 212223242, true);
        jdbc.insertarFamiliar(10, "Prima Jorge", "F", 525354555, false);

        // Inserciones en la tabla Asignatura
        // Se asume que los métodos de inserción de asignatura usan el ID autoincremental de Alumno
        jdbc.insertarAsignatura(1, "Matemáticas", "1º ESO", 80);
        jdbc.insertarAsignatura(2, "Historia", "2º ESO", 70);
        jdbc.insertarAsignatura(3, "Biología", "3º ESO", 85);
        jdbc.insertarAsignatura(4, "Química", "4º ESO", 75);
        jdbc.insertarAsignatura(5, "Física", "1º Bachillerato", 90);
        jdbc.insertarAsignatura(6, "Inglés", "2º Bachillerato", 85);
        jdbc.insertarAsignatura(7, "Educación Física", "3º Bachillerato", 78);
        jdbc.insertarAsignatura(8, "Arte", "1º Universidad", 92);
        jdbc.insertarAsignatura(9, "Literatura", "2º Universidad", 88);
        jdbc.insertarAsignatura(10, "Programación", "3º Universidad", 95);*/

        System.out.println(jdbc.consultarTodosLosAlumnos());
        System.out.println(jdbc.consultarTodasLasDirecciones());
        System.out.println(jdbc.consultarTodosLosFamiliares());
        System.out.println(jdbc.consultarTodasLasAsignaturas());

        App.setRoot("secondary");
    }

}
