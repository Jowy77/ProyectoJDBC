module com.mycompany.proyectojdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    
    opens com.mycompany.proyectojdbc to javafx.fxml;
    exports com.mycompany.proyectojdbc;
}
