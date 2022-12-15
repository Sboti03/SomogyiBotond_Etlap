module com.example.etlap {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.etlap to javafx.fxml;
    exports com.example.etlap;
}