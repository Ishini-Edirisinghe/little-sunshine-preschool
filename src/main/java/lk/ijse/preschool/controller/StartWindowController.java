package lk.ijse.preschool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartWindowController {
    public AnchorPane Window1;

    public void btnStartOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) Window1.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login-window-view.fxml"))));

    }
}
