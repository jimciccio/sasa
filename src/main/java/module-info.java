module ferranti.bikerbikus {
	requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires selenium.api;
    requires selenium.chrome.driver;
    requires org.testng;
    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.intellij.bikerbikus to javafx.fxml;
    opens com.intellij.bikerbikus.models to javafx.base;
    exports com.intellij.bikerbikus;
    opens com.intellij.bikerbikus.controllers1 to javafx.fxml;
    opens com.intellij.bikerbikus.grafico to javafx.fxml;
}