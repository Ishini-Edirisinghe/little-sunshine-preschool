package lk.ijse.preschool.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class UpdateStudentWindowController {

    public AnchorPane updateStudentAnchorPane;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("/view/manage-student-window-view.fxml"));
        updateStudentAnchorPane.getChildren().clear();
        updateStudentAnchorPane.getChildren().add(load);


    }
}
