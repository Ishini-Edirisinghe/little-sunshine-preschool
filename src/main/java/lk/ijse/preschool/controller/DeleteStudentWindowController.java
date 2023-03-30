package lk.ijse.preschool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DeleteStudentWindowController {

    public AnchorPane deleteStudentAnchorPane;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("/view/manage-student-window-view.fxml"));
        deleteStudentAnchorPane.getChildren().clear();
        deleteStudentAnchorPane.getChildren().add(load);
    }
}