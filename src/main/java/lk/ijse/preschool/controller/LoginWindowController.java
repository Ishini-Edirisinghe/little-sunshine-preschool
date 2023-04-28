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
import lk.ijse.preschool.model.LoginModel;
import lk.ijse.preschool.util.Regex;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginWindowController implements Initializable {
    public AnchorPane loginContext;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loginContext.getScene().getWindow();
        stage.close();

        Stage stage1 = new Stage();
        stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard-window-view.fxml"))));
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        if (Regex.validateUsername(username)&&Regex.validatePassword(password)) {

            boolean isUserVerified ; //check in the DB
            try {
                isUserVerified = LoginModel.userCheckedInDB(username,password);
                if (isUserVerified) {

                    new Alert(Alert.AlertType.CONFIRMATION, "Login successful!").showAndWait();
                    stage.close();
                    stage1.setMaximized(true);

                    Thread mailThread=new Thread(()->{
                        try {
                            EmailController.sendMail("ishiniedirisinghe331@gmail.com");
                        } catch (Exception e) {
                            // System.out.println("Failed to send e-mail.Network err!");
                            //e.printStackTrace();
                            System.out.println(e);
                        }
                    });

                    mailThread.start();

                    stage1.show();
                }else{
                    new Alert(Alert.AlertType.WARNING, "User Not Found in DB!!!").showAndWait();
                    txtUserName.clear();
                    txtPassword.clear();
                    txtUserName.requestFocus();
                    stage.show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


           /* new Alert(Alert.AlertType.CONFIRMATION, "Login successful!").showAndWait();
            stage.close();
            stage1.setMaximized(true);
            stage1.show();*/
        } else {
            new Alert(Alert.AlertType.WARNING, "Invalid username or password").showAndWait();
            txtUserName.clear();
            txtPassword.clear();
            txtUserName.requestFocus();
            // stage1.setMaximized(true);
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



  /*  Stage stage = (Stage) loginContext.getScene().getWindow();


    Stage stage2 = new Stage();
        stage2.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard-view-form.fxml"))));
                stage2.setTitle("Dashboard");

                stage2.setMaximized(true);

                String username = txtUsername.getText();
                String password = txtPassword.getText();

                if(Regex.validateUsername(username)&&Regex.validatePassword(password)){

                try {
                boolean isUserVerified = LoginModel.userCheckedInDB(username,password); //check in the DB
                if (isUserVerified) {

                new Alert(Alert.AlertType.CONFIRMATION,"Login successful!").showAndWait();
                stage.close();
                stage2.show();
                } else {
                new Alert(Alert.AlertType.WARNING, "User Not Found in DB!!!").show();
                }
                }catch (SQLException | ClassNotFoundException e){
                new Alert(Alert.AlertType.ERROR,"Oops something wrong!!!").show();
                }
                }else {
                new Alert(Alert.AlertType.ERROR,"Invalid Input !").show();
                txtUsername.clear();
                txtPassword.clear();
                txtUsername.requestFocus();
                stage.show();
                }*/