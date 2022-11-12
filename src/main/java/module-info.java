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
    requires jdatepicker;

    opens ferranti.bikerbikus to javafx.fxml;
    opens ferranti.bikerbikus.models to javafx.base;
    exports ferranti.bikerbikus;
    opens ferranti.bikerbikus.controllers1 to javafx.fxml;
    opens ferranti.bikerbikus.grafico to javafx.fxml;
}