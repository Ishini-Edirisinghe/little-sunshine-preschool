package lk.ijse.preschool.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.preschool.dto.SkillStatus;
import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.dto.tm.SkillStatusTM;
import lk.ijse.preschool.dto.tm.StudentTM;
import lk.ijse.preschool.model.SkillStatusModel;
import lk.ijse.preschool.model.StudentModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentSkillStatusWindowController implements Initializable {


    @FXML
    private JFXComboBox<String> cmbStId;

    @FXML
    private TextField txtStudentName;

    private ObservableList<SkillStatusTM> obList = FXCollections.observableArrayList();
    private String searchText="";

    @FXML
    private JFXComboBox<String> cmbCountngStatus;

    @FXML
    private JFXComboBox<String> cmbCraftingStatus;

    @FXML
    private JFXComboBox<String> cmbDrawingStatus;

    @FXML
    private JFXComboBox<String> cmbReadingStatus;

    @FXML
    private JFXComboBox<String> cmbSingingStatus;

    @FXML
    private JFXComboBox<String> cmbWritingStatus;

    ObservableList<String> items = FXCollections.observableArrayList("Excellent", "Good", "Weak");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadStid();
        loadStatus();

    }

    private void loadStatus()  {
        cmbCountngStatus.getItems().addAll(items);
        cmbCraftingStatus.getItems().addAll(items);
        cmbDrawingStatus.getItems().addAll(items);
        cmbReadingStatus.getItems().addAll(items);
        cmbSingingStatus.getItems().addAll(items);
        cmbWritingStatus.getItems().addAll(items);
    }




    private void loadStid() {
        try {
            List<String> ids = StudentModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbStId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cmbStIdOnActon(ActionEvent event) {
        String studentId = cmbStId.getSelectionModel().getSelectedItem();

        try {
            Student student = StudentModel.searchById(studentId);
            txtStudentName.setText(student.getName());
            SkillStatus skillStatus=SkillStatusModel.searchByIdGetSkills(studentId);

            if (skillStatus!=null){
                cmbWritingStatus.setValue(skillStatus.getWriting());
                cmbSingingStatus.setValue(skillStatus.getSinging());
                cmbReadingStatus.setValue(skillStatus.getReading());
                cmbDrawingStatus.setValue(skillStatus.getDrawing());
                cmbCraftingStatus.setValue(skillStatus.getCrafting());
                cmbCountngStatus.setValue(skillStatus.getCrafting());
            }else{
                cmbWritingStatus.getItems().clear();
                cmbDrawingStatus.getItems().clear();
                cmbReadingStatus.getItems().clear();
                cmbSingingStatus.getItems().clear();
                cmbCraftingStatus.getItems().clear();
                cmbCountngStatus.getItems().clear();
                loadStid();
                loadStatus();
                new Alert(Alert.AlertType.ERROR, "No input for Students Skill Please Input Student Skills").show();
               // return;
            }


        } catch (SQLException e) {
          //  e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }




}
