package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane loginContext;
    public TextField txtUsername;
    public PasswordField pwdUserPassword;
    public Label lblError;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String user = "A";
        String password = "1234";
        if ( txtUsername.getText().equals(user) &&  pwdUserPassword.getText().equals(password)) {
            Stage window = (Stage)  loginContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
        } else if ( txtUsername.getText().isEmpty() &&  pwdUserPassword.getText().isEmpty()) {
            lblError.setText("Your User Name Or Password IS Empty...!");
            txtUsername.clear();
            pwdUserPassword.clear();
        }
        else if (! txtUsername.getText().equals(user)) {
            lblError.setText("Your User Name is incorrect..!");
            txtUsername.clear();
            pwdUserPassword.clear();
        } else if (! pwdUserPassword.getText().equals(password)) {
            lblError.setText("Your Password is incorrect..!");
            txtUsername.clear();
            pwdUserPassword.clear();
        }


    }

    public void btnForgotPw(ActionEvent actionEvent) {
    }

}
