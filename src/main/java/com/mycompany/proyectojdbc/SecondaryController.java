package com.mycompany.proyectojdbc;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

public class SecondaryController implements Initializable {

    JDBC jdbc = new JDBC();

    @FXML
    private TextArea consultasTextArea = new TextArea();
    @FXML
    private MenuItem consultaAlumnosPorNombreMenuItem;
    @FXML
    private MenuItem consultarTodosLosAlumnosMenuItem;
    @FXML
    private Button salirButton;
    @FXML
    private MenuItem consultarFamiliaresButton;
    @FXML
    private MenuItem consultaExtraFamiliaresConCustodia;
    @FXML
    private MenuItem insertarAlumnoMenuItem;
    @FXML
    private MenuItem insertarDireccionMenuItem;
    @FXML
    private MenuItem insertarFamiliarMenuItem;
    @FXML
    private MenuItem modificarAsignaturaMenuItem;
    @FXML
    private MenuItem modificarCustodiaMenuItem;
    @FXML
    private MenuItem eliminarAlumnoMenuItem;
    @FXML
    private MenuItem eliminarAsignaturaAlumnoMenuItem;
    @FXML
    private MenuItem consultarDireccionesMenuItem;
    @FXML
    private MenuItem consultarFamiliaresMenuItem;
    @FXML
    private MenuItem consultarAsignaturasMenuItem;

    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        consultasTextArea.setText("EN ESTE VENTANA PODRAS HACER ALGUNAS OPERACIONES SOBRE LA BASE DE DATOS\n"
                + "LOS RESULTADOS SE MOSTRARAN AQUI..... YA HAY DATOS INSERTADOS EN LA BASE DE DATOS...");
    }

    @FXML
    private void ConsultarAlumnosPorNombre(ActionEvent event) {
        consultasTextArea.setText("SE HAN USADO ESTOS NOMBRES : MARIA,CARLOS,SOFIA PARA HACER LA CONSULTA\n"
                + jdbc.consultarAlumnosPorNombre("María") + "\n"
                + jdbc.consultarAlumnosPorNombre("Carlos") + "\n"
                + jdbc.consultarAlumnosPorNombre("Sofía") + "\n");
    }

    @FXML
    private void ConsultarTodosLosAlumnos(ActionEvent event) {
        consultasTextArea.setText(jdbc.consultarTodosLosAlumnos());
    }

    @FXML
    private void onSalir(ActionEvent event) throws IOException {
        switchToPrimary();
    }

    @FXML
    private void onConsultarFamiliarDeCuatroAlumnos(ActionEvent event) {
        consultasTextArea.setText("SE HAN COLSULTADO LOS FAMILIARES DE LOS ALUMNOS CON EL ID DE 1 AL 4\n"
                + "\n" + jdbc.consultarFamiliaresDeAlumno(1) + "\n"
                + jdbc.consultarFamiliaresDeAlumno(2) + "\n"
                + jdbc.consultarFamiliaresDeAlumno(3) + "\n"
                + jdbc.consultarFamiliaresDeAlumno(4) + "\n");
    }

    @FXML
    private void onConsultarFamiliaresConCustodia(ActionEvent event) {
        consultasTextArea.setText("AQUI APARECERAN SOLO LOS FAMILIARES QUE TENGAN LA CUSTODIA DE ALGUN ALUMNO....\n"
                + jdbc.consultarFamiliaresConCustodia());
    }

    @FXML
    private void onInsertarAlumno(ActionEvent event) {
        TextInputDialog nombreDialog = new TextInputDialog();
        nombreDialog.setTitle("Ingreso de datos de alumno");
        nombreDialog.setHeaderText("Por favor, ingresa los datos del alumno");
        nombreDialog.setContentText("Nombre:");

        TextInputDialog telefonoDialog = new TextInputDialog();
        telefonoDialog.setTitle("Ingreso de datos de alumno");
        telefonoDialog.setHeaderText("Por favor, ingresa los datos del alumno");
        telefonoDialog.setContentText("Número de teléfono:");

        TextInputDialog direccionDialog = new TextInputDialog();
        direccionDialog.setTitle("Ingreso de datos de alumno");
        direccionDialog.setHeaderText("Por favor, ingresa los datos del alumno");
        direccionDialog.setContentText("Dirección:");

        Optional<String> nombreResult = nombreDialog.showAndWait();
        nombreResult.ifPresent(nombre -> {
            Optional<String> telefonoResult = telefonoDialog.showAndWait();
            telefonoResult.ifPresent(telefono -> {
                Optional<String> direccionResult = direccionDialog.showAndWait();
                direccionResult.ifPresent(direccion -> {
                    // Llamada al método para insertar los datos del alumno en la base de datos
                    boolean exito = jdbc.insertarAlumno(nombre, Integer.parseInt(telefono), direccion);
                    if (exito) {
                        mostrarAlerta("Datos insertados", "Los datos del alumno se han insertado correctamente.");
                    } else {
                        mostrarAlerta("Error", "Hubo un error al insertar los datos del alumno.");
                    }
                });
            });
        });
    }

    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void onInsertarDireccion(ActionEvent event) {
        TextInputDialog idAlumnoDialog = new TextInputDialog();
        idAlumnoDialog.setTitle("Ingreso de datos de dirección");
        idAlumnoDialog.setHeaderText("Por favor, ingresa el ID del alumno");
        idAlumnoDialog.setContentText("ID del alumno:");

        Optional<String> idAlumnoResult = idAlumnoDialog.showAndWait();
        idAlumnoResult.ifPresent(idAlumno -> {
            TextInputDialog direccionDialog = new TextInputDialog();
            direccionDialog.setTitle("Ingreso de datos de dirección");
            direccionDialog.setHeaderText("Por favor, ingresa los datos de la dirección");
            direccionDialog.setContentText("Dirección:");

            Optional<String> direccionResult = direccionDialog.showAndWait();
            direccionResult.ifPresent(direccion -> {
                // Llamada al método para insertar los datos de la dirección en la base de datos
                boolean exito = jdbc.insertarDireccion(Integer.parseInt(idAlumno), direccion);
                if (exito) {
                    mostrarAlerta("Datos insertados", "Los datos de la dirección se han insertado correctamente.");
                } else {
                    mostrarAlerta("Error", "Hubo un error al insertar los datos de la dirección.");
                }
            });
        });
    }

    @FXML
    private void onInsertarFamiliar(ActionEvent event) {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Ingreso de datos de familiar");
        idDialog.setHeaderText("Por favor, ingresa los datos del familiar");
        idDialog.setContentText("ID del alumno:");

        Optional<String> idResult = idDialog.showAndWait();
        idResult.ifPresent(id -> {
            TextInputDialog nombreDialog = new TextInputDialog();
            nombreDialog.setTitle("Ingreso de datos de familiar");
            nombreDialog.setHeaderText("Por favor, ingresa los datos del familiar");
            nombreDialog.setContentText("Nombre:");

            Optional<String> nombreResult = nombreDialog.showAndWait();
            nombreResult.ifPresent(nombre -> {
                TextInputDialog sexoDialog = new TextInputDialog();
                sexoDialog.setTitle("Ingreso de datos de familiar");
                sexoDialog.setHeaderText("Por favor, ingresa los datos del familiar");
                sexoDialog.setContentText("Sexo:");

                Optional<String> sexoResult = sexoDialog.showAndWait();
                sexoResult.ifPresent(sexo -> {
                    TextInputDialog telefonoDialog = new TextInputDialog();
                    telefonoDialog.setTitle("Ingreso de datos de familiar");
                    telefonoDialog.setHeaderText("Por favor, ingresa los datos del familiar");
                    telefonoDialog.setContentText("Teléfono:");

                    Optional<String> telefonoResult = telefonoDialog.showAndWait();
                    telefonoResult.ifPresent(telefono -> {
                        TextInputDialog custodiaDialog = new TextInputDialog();
                        custodiaDialog.setTitle("Ingreso de datos de familiar");
                        custodiaDialog.setHeaderText("Por favor, ingresa los datos del familiar");
                        custodiaDialog.setContentText("Custodia (true/false):");

                        Optional<String> custodiaResult = custodiaDialog.showAndWait();
                        custodiaResult.ifPresent(custodiaStr -> {
                            boolean custodia = Boolean.parseBoolean(custodiaStr);
                            int idAlumno = Integer.parseInt(id);
                            int telefonoFamiliar = Integer.parseInt(telefono);

                            boolean exito = jdbc.insertarFamiliar(idAlumno, nombre, sexo, telefonoFamiliar, custodia);
                            if (exito) {
                                mostrarAlerta("Datos insertados", "Los datos del familiar se han insertado correctamente.");
                            } else {
                                mostrarAlerta("Error", "Hubo un error al insertar los datos del familiar.");
                            }
                        });
                    });
                });
            });
        });
    }

    @FXML
    private void onModificarAsignatura(ActionEvent event) {
        TextInputDialog idAlumnoDialog = new TextInputDialog();
        idAlumnoDialog.setTitle("Modificar asignatura");
        idAlumnoDialog.setHeaderText("Ingrese el ID del alumno:");
        idAlumnoDialog.setContentText("ID del alumno:");

        Optional<String> idAlumnoResult = idAlumnoDialog.showAndWait();
        idAlumnoResult.ifPresent(idAlumno -> {
            TextInputDialog nombreAsignaturaDialog = new TextInputDialog();
            nombreAsignaturaDialog.setTitle("Modificar asignatura");
            nombreAsignaturaDialog.setHeaderText("Ingrese el nuevo nombre de la asignatura:");
            nombreAsignaturaDialog.setContentText("Nombre de la asignatura:");

            Optional<String> nombreAsignaturaResult = nombreAsignaturaDialog.showAndWait();
            nombreAsignaturaResult.ifPresent(nombreAsignatura -> {
                TextInputDialog cursoDialog = new TextInputDialog();
                cursoDialog.setTitle("Modificar asignatura");
                cursoDialog.setHeaderText("Ingrese el nuevo curso de la asignatura:");
                cursoDialog.setContentText("Curso:");

                Optional<String> cursoResult = cursoDialog.showAndWait();
                cursoResult.ifPresent(curso -> {
                    TextInputDialog notasDialog = new TextInputDialog();
                    notasDialog.setTitle("Modificar asignatura");
                    notasDialog.setHeaderText("Ingrese las nuevas notas de la asignatura:");
                    notasDialog.setContentText("Notas:");

                    Optional<String> notasResult = notasDialog.showAndWait();
                    notasResult.ifPresent(notas -> {
                        int idAlumnoInt = Integer.parseInt(idAlumno);
                        int notasInt = Integer.parseInt(notas);

                        boolean exito = jdbc.modificarAsignatura(idAlumnoInt, nombreAsignatura, curso, notasInt);
                        if (exito) {
                            mostrarAlerta("Asignatura modificada", "La asignatura del alumno se ha modificado correctamente.");
                        } else {
                            mostrarAlerta("Error", "Hubo un error al modificar la asignatura del alumno.");
                        }
                    });
                });
            });
        });
    }

    @FXML
    private void onModificarCustodia(ActionEvent event) {
        TextInputDialog idAlumnoDialog = new TextInputDialog();
        idAlumnoDialog.setTitle("Modificar custodia de familiar");
        idAlumnoDialog.setHeaderText("Ingresa el ID del alumno:");
        idAlumnoDialog.setContentText("ID del alumno:");

        TextInputDialog nombreFamiliarDialog = new TextInputDialog();
        nombreFamiliarDialog.setTitle("Modificar custodia de familiar");
        nombreFamiliarDialog.setHeaderText("Ingresa el nombre del familiar:");
        nombreFamiliarDialog.setContentText("Nombre del familiar:");

        ChoiceDialog<String> custodiaDialog = new ChoiceDialog<>("true", "true", "false");
        custodiaDialog.setTitle("Modificar custodia de familiar");
        custodiaDialog.setHeaderText("Elige la nueva custodia:");
        custodiaDialog.setContentText("Custodia:");

        Optional<String> idAlumnoResult = idAlumnoDialog.showAndWait();
        idAlumnoResult.ifPresent(idAlumno -> {
            Optional<String> nombreFamiliarResult = nombreFamiliarDialog.showAndWait();
            nombreFamiliarResult.ifPresent(nombreFamiliar -> {
                Optional<String> custodiaResult = custodiaDialog.showAndWait();
                custodiaResult.ifPresent(nuevaCustodia -> {
                    boolean custodia = Boolean.parseBoolean(nuevaCustodia);
                    boolean exito = jdbc.modificarCustodiaFamiliar(Integer.parseInt(idAlumno), nombreFamiliar, custodia);
                    if (exito) {
                        mostrarAlerta("Custodia modificada", "La custodia del familiar se ha modificado correctamente.");
                    } else {
                        mostrarAlerta("Error", "Hubo un error al modificar la custodia del familiar.");
                    }
                });
            });
        });
    }

    @FXML
    private void onEliminarAlumno(ActionEvent event) {
        TextInputDialog idAlumnoDialog = new TextInputDialog();
        idAlumnoDialog.setTitle("Eliminar Alumno");
        idAlumnoDialog.setHeaderText("Ingrese el ID del alumno a eliminar:");
        idAlumnoDialog.setContentText("ID del Alumno:");

        Optional<String> idAlumnoResult = idAlumnoDialog.showAndWait();
        idAlumnoResult.ifPresent(idAlumno -> {
            int id = Integer.parseInt(idAlumno);
            eliminarAlumnoConAlert(id);
        });
    }

    private void eliminarAlumnoConAlert(int idAlumno) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Está seguro de que desea eliminar este alumno y sus datos asociados?");
        alert.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean exito = jdbc.eliminarAlumno(idAlumno);
            if (exito) {
                mostrarAlerta("Alumno eliminado", "El alumno y sus datos asociados han sido eliminados correctamente.");
            } else {
                mostrarAlerta("Error", "Hubo un error al eliminar el alumno y sus datos asociados.");
            }
        }
    }

    @FXML
    private void onEliminarAsignaturaAlumno(ActionEvent event) {
        TextInputDialog idAlumnoDialog = new TextInputDialog();
        idAlumnoDialog.setTitle("Eliminar asignatura de alumno");
        idAlumnoDialog.setHeaderText("Ingresa el ID del alumno:");
        idAlumnoDialog.setContentText("ID del alumno:");

        TextInputDialog nombreAsignaturaDialog = new TextInputDialog();
        nombreAsignaturaDialog.setTitle("Eliminar asignatura de alumno");
        nombreAsignaturaDialog.setHeaderText("Ingresa el nombre de la asignatura:");
        nombreAsignaturaDialog.setContentText("Nombre de la asignatura:");

        Optional<String> idAlumnoResult = idAlumnoDialog.showAndWait();
        idAlumnoResult.ifPresent(idAlumno -> {
            Optional<String> nombreAsignaturaResult = nombreAsignaturaDialog.showAndWait();
            nombreAsignaturaResult.ifPresent(nombreAsignatura -> {
                boolean exito = jdbc.eliminarAsignatura(Integer.parseInt(idAlumno), nombreAsignatura);
                if (exito) {
                    mostrarAlerta("Asignatura eliminada", "La asignatura se ha eliminado correctamente.");
                } else {
                    mostrarAlerta("Error", "Hubo un error al eliminar la asignatura.");
                }
            });
        });
    }

    @FXML
    private void onConsultarDirecciones(ActionEvent event) {
        consultasTextArea.setText(jdbc.consultarTodasLasDirecciones());
    }

    @FXML
    private void onConsultarFamiliares(ActionEvent event) {
        consultasTextArea.setText(jdbc.consultarTodosLosFamiliares());
    }

    @FXML
    private void onConsultarAsignaturas(ActionEvent event) {
        consultasTextArea.setText(jdbc.consultarTodasLasAsignaturas());
    }
}
