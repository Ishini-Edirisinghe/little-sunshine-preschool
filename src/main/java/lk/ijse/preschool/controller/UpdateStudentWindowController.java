package lk.ijse.preschool.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class UpdateStudentWindowController {
    public JFXButton Window8;
    public JFXButton Window9;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) Window9.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-student-window-view.fxml"))));
    }
}
