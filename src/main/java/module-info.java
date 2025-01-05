module dk.easv.mytunes.privatemoviecollection {
    requires javafx.controls;
    requires javafx.fxml;


    opens dk.easv.mytunes.privatemoviecollection to javafx.fxml;
    exports dk.easv.mytunes.privatemoviecollection;
}