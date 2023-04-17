package lk.ijse.preschool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    void btnGetReportOnAction(ActionEvent event) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("/view/get-report-window-view.fxml"));
        dashboardAnchorPane.getChildren().clear();
        dashboardAnchorPane.getChildren().add(load);

    }

    @FXML
    void btnManageStudentOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/manage-student-window-view.fxml"));
        dashboardAnchorPane.getChildren().clear();
        dashboardAnchorPane.getChildren().add(load);
    }
    @FXML
        void btnPaymentOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/payments-window-view.fxml"));
        dashboardAnchorPane.getChildren().clear();
        dashboardAnchorPane.getChildren().add(load);
    }
    @FXML
    void btnAttendanceOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/attendance-window-view.fxml"));
        dashboardAnchorPane.getChildren().clear();
        dashboardAnchorPane.getChildren().add(load);
    }
    @FXML
    void btnSyllabusOnAction(ActionEvent event) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("/view/syllabus-window-view.fxml"));
        dashboardAnchorPane.getChildren().clear();
        dashboardAnchorPane.getChildren().add(load);

    }
    @FXML
    void btnStudentSkillsStatusOnAction(ActionEvent event) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("/view/student-skill-status-window-view.fxml"));
        dashboardAnchorPane.getChildren().clear();
        dashboardAnchorPane.getChildren().add(load);
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        Stage thisStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader= new FXMLLoader(LoginWindowController.class.getResource("/view/dashboard-window-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        thisStage.setScene(scene);
        thisStage.setMaximized(true);

    }

    public void btnLogoutOnAction(ActionEvent actionEvent) {
    }
}

