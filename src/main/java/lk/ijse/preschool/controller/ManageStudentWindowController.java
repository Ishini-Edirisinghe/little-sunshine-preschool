package lk.ijse.preschool.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class ManageStudentWindowController {

    public AnchorPane manageStudentWindow;


    public void btnAddStudentOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) manageStudentWindow.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add-student-window-view.fxml"))));
    }

    public void btnDeleteStudentOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) manageStudentWindow.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/delete-student-window-view.fxml"))));
    }

    public void btnUpdateStudentOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) manageStudentWindow.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/update-student-window-view.fxml"))));
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) manageStudentWindow.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard-window-view.fxml"))));
    }
}
