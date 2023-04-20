package lk.ijse.preschool.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginWindowController implements Initializable {
    public AnchorPane loginContext;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;


    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "admin";

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loginContext.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard-window-view.fxml"))));
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Login successful!").showAndWait();
            stage.close();
            stage1.setMaximized(true);
            stage1.show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Invalid username or password").showAndWait();
            txtUserName.clear();
            txtPassword.clear();
            txtUserName.requestFocus();
          //  stage1.setMaximized(true);
            stage.show();
        }
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) throws IOException {
      btnLoginOnAction(event);
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->txtUserName.requestFocus());
    }
}



    /*Stage stage = (Stage) loginContext.getScene().getWindow();


    Stage stage2 = new Stage();
        stage2.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard-view-form.fxml"))));
                stage2.setTitle("Dashboard");

                stage2.setMaximized(true);

                String username = txtUsername.getText();
                String password = txtPassword.getText();
                if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
                new Alert(Alert.AlertType.CONFIRMATION,"Login successful!").showAndWait();
                stage.close();
                stage2.show();
                } else {
                new Alert(Alert.AlertType.WARNING,"Invalid username or password").show();
                txtUsername.clear();
                txtPassword.clear();
                txtUsername.requestFocus();
                stage.show();
                }*/
