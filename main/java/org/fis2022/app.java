package org.fis2022;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.fis2022.models.User;
import org.fis2022.services.DBService;
import org.fis2022.services.FileSystemService;
import org.fis2022.services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
    }

    public static void main(String[] args) {
    }
}