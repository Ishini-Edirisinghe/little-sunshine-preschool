package lk.ijse.preschool.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class GetReportWindowController {
    public JFXButton Window12;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) Window12.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard-window-view.fxml"))));
    }
}
