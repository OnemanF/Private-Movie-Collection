module dk.easv.mytunes.privatemoviecollection {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    requires java.naming;


    opens dk.easv.mytunes.privatemoviecollection to javafx.fxml;
    opens dk.easv.mytunes.privatemoviecollection.GUI.Controller to javafx.fxml;
    opens dk.easv.mytunes.privatemoviecollection.BE to javafx.base;
    exports dk.easv.mytunes.privatemoviecollection;
    exports dk.easv.mytunes.privatemoviecollection.GUI.Controller to javafx.fxml;
}