package com.example.taskmaster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * запускает приложение с графическим интерфейсом на языке JavaFX, отображая окно с заголовком « Органайзер», размещенным на сцене, загруженной из файла hello-view.fxml.
 */
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        Image image = new Image("D:\\User\\Desktop\\Программная инженерия\\Органайзер\\TaskMaster\\src\\main\\resources\\icon.png");
        stage.setTitle("Органайзер");
        stage.setScene(scene);
        stage.getIcons().add(image);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}