module com.intellij.salve {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.intellij.salve to javafx.fxml;
    exports com.intellij.salve;
}