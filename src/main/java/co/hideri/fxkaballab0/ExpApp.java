package co.hideri.fxkaballab0;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ExpApp extends Application {

    @Override public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ExpApp.class.getResource("exp-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        String css = Objects.requireNonNull(this.getClass().getResource("App.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("EXP");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Throwable {
        launch();
    }
}