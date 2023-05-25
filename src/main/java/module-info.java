module com.example.taskmaster {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.taskmaster to javafx.fxml;
    exports com.example.taskmaster;
    exports model;
    opens model to javafx.fxml;
}