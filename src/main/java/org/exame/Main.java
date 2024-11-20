package org.exame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class Main extends Application{
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public void start(Stage stage) {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/exame/BancoDadosManagement.fxml")));

            Scene scene = new Scene(root);
            stage.setScene(scene);

            Image appIcon = new Image("img/BD.png");
            stage.getIcons().add(appIcon);
            stage.setResizable(false);
            stage.setTitle("Banco de Dados");

            stage.show();
        }
        catch(Exception e){
            log.error("e: ", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}