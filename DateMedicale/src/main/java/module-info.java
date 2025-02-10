module com.example.datemedicale {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.datemedicale to javafx.fxml;
    exports com.example.datemedicale;
}