package lk.ijse.preschool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DeleteStudentWindowController {

    public AnchorPane Window7;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) Window7.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/manage-student-window-view.fxml"))));
    }
}
