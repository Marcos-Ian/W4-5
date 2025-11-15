module seneca.college.wk4_5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.google.guice;
    requires javax.inject;


    opens seneca.college.wk4_5 to javafx.fxml;
    exports seneca.college.wk4_5;
    exports seneca.college.wk4_5.model;
    opens seneca.college.wk4_5.model to javafx.fxml;
    exports seneca.college.wk4_5.controller;
    opens seneca.college.wk4_5.controller to javafx.fxml, com.google.guice;
    opens seneca.college.wk4_5.config to com.google.guice;
    opens seneca.college.wk4_5.repository to com.google.guice;
    opens seneca.college.wk4_5.service to com.google.guice;
    opens seneca.college.wk4_5.util to com.google.guice;
}