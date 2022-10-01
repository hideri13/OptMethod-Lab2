module co.hideri.fxkaballab0 {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.math3;

    opens co.hideri.fxkaballab0 to javafx.fxml;
    exports co.hideri.fxkaballab0;
}