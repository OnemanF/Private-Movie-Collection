module dk.easv.mytunes.privatemoviecollection {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;


    opens dk.easv.mytunes.privatemoviecollection to javafx.fxml;
    exports dk.easv.mytunes.privatemoviecollection;
}