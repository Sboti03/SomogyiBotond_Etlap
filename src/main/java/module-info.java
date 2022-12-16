module hu.petrik.etlap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens hu.petrik.etlap.database to java.sql;
    opens hu.petrik.etlap to java.sql, javafx.fxml;
    exports hu.petrik.etlap;
    exports hu.petrik.etlap.database;
}