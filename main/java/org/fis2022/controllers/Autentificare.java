package org.fis2022.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.fis2022.exceptions.InvalidCredentials;
import org.fis2022.models.User;
import org.fis2022.services.UserService;

public class Autentificare {
    @FXML
    public Text loginMessage;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField usernameField;

    @FXML
    public void handleLoginAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.isEmpty()) {
            loginMessage.setText("Adauga username!");
            return;
        }

        if (password == null || password.isEmpty()) {
            loginMessage.setText("Adauga parola!");
            return;
        }

        String encoded_password = UserService.encodePassword(username, password);

        try{
            String stored_password = UserService.getHashedUserPassword(username);
            if(stored_password.equals(encoded_password)){
                if(UserService.getUser(username).getRole().equals("Client")){
                    loadHomePage();
                    return;
                }
                if(UserService.getUser(username).getRole().equals("Real Estate Agent")){
                    loadCafeneaHomePage();
                    return;
                }
                loginMessage.setText("nope");
                return;
            }

        } catch(InvalidCredentials e){
            loginMessage.setText(e.getMessage());
            return;
        }

        loginMessage.setText("Autentificare incorecta!");
    }

    @FXML
    public void loadRegisterPage(){
        try {
            Stage stage = (Stage) loginMessage.getScene().getWindow();
            Parent registerRoot = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
            Scene scene = new Scene(registerRoot, 640, 480);
            stage.setTitle("Inregistrare - RealEstate");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadHomePage(){
        try{
            User u = UserService.getUser(usernameField.getText());
            Stage stage = (Stage) loginMessage.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Rezerva-Comanda.fxml"));
            stage.setUserData(u);
            Parent homeRoot = loader.load();
            Scene scene = new Scene(homeRoot, 640, 480);
            stage.setTitle("Coffee Shop");
            stage.setScene(scene);
        } catch (InvalidCredentials e){
            loginMessage.setText(e.getMessage());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void loadRealEstateHomePage(){
    
    }
}
