module org.example.tp1algo3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tp1algo3 to javafx.fxml;
    exports org.example.tp1algo3;
    exports org.example.tp1algo3.modelo;
    opens org.example.tp1algo3.modelo to javafx.fxml;
    exports org.example.tp1algo3.vista;
    opens org.example.tp1algo3.vista to javafx.fxml;
}