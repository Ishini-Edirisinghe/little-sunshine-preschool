package lk.ijse.preschool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindowController {
    public AnchorPane loginContext;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loginContext.getScene().getWindow();
        stage.close();
        Stage stage1=new Stage();
        stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard-window-view.fxml"))));
        stage1.setMaximized(true);
        stage1.show();
    }
}
