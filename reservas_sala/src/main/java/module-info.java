module com.example.reservas_sala {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires kernel;
    requires layout;
    requires io;

    opens com.example.reservas_sala to javafx.fxml;
    exports com.example.reservas_sala;
}
