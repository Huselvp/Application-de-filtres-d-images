module uae.mesbahi.houda.ips.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires java.rmi;
    requires javafx.graphics;

    exports uae.mesbahi.houda.ips.controller;
    opens uae.mesbahi.houda.ips.controller to javafx.fxml;
    exports uae.mesbahi.houda.ips;
    opens uae.mesbahi.houda.ips to javafx.fxml;
}