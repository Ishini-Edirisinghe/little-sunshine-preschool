package lk.ijse.preschool.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.preschool.dto.Teacher;
import lk.ijse.preschool.model.StudentModel;
import lk.ijse.preschool.model.TeacherModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class AddStudentWindowController implements Initializable {
    public ComboBox cmbTeacherId;
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDOB;

    @FXML
    private TextField txtParentName;

    @FXML
    private TextField txtStId;


    public AnchorPane addStudentAnchorPane;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("/view/manage-student-window-view.fxml"));
        addStudentAnchorPane.getChildren().clear();
        addStudentAnchorPane.getChildren().add(load);
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTeacherids();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String id = txtStId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String DOB = txtDOB.getText();
        String contact = txtContact.getText();
        String parentName = txtParentName.getText();

        try {
            boolean isSaved = StudentModel.save(id, name, address,DOB, contact,parentName);
            if (isSaved) {
              new Alert(Alert.AlertType.CONFIRMATION, "Student saved!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        }
    }
    private void loadTeacherids(){
        try {
            List<String> ids = TeacherModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbTeacherId.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
}
