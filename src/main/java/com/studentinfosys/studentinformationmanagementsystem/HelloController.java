package com.studentinfosys.studentinformationmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloController {

    private final String Realusername = "Jasper.Blase";
    private final String Realpassword = "basicpassword";
    @FXML
    private TextField UsernameTextField;

    @FXML
    private PasswordField passwordFieldTextField;

    @FXML
    private Button LoginButton;

    @FXML
    private VBox LoginWindow;


    @FXML
    private void checkCredentials(ActionEvent Event) throws IOException {
        String getUser = UsernameTextField.getText();
        String getPass = passwordFieldTextField.getText();

        if(checkAuthentication(getUser, getPass))
        {
            if(showAlert("CREDENTIALS MATCHED", "PASSED")) {//After the alert shows it must stay until the user presses ok button
                showMainWindow();
                Stage stage = new Stage();
                stage = (Stage) LoginWindow.getScene().getWindow();
                stage.close();
            }
        }
        else
        {
            showAlert("CREDENTIALS NOT MATCHED", "NOT PASSED");
        }

    }

    private Boolean checkAuthentication(String username, String password)
    {
        return Realusername.equals(username) && Realpassword.equals(password);
    }

    @FXML
    private boolean showAlert(String message, String title)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();

        // Check if the user clicked the OK button
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FXML
    private void showMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main-Window.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Student Information Management System");
        stage.show();



    }





}
