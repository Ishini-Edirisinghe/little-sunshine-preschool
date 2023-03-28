package lk.ijse.preschool.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DashboardWindowController {
    @FXML
    private AnchorPane dashboardAnchorPane;

    @FXML
    private AnchorPane dashboardMainAnchorPane;

    @FXML
    void btnEventOnAction(ActionEvent event) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("/view/event-window-view.fxml"));
        dashboardAnchorPane.getChildren().clear();
        dashboardAnchorPane.getChildren().add(load);
    }

    @FXML
    void btnGetReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnManageStudentOnAction(ActionEvent event) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("/view/manage-student-window-view.fxml"));
        dashboardAnchorPane.getChildren().clear();
        dashboardAnchorPane.getChildren().add(load);
    }

    @FXML
    void btnSyllabusOnAction(ActionEvent event) {

    }
}

