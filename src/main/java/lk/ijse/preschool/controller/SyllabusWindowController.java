package lk.ijse.preschool.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.dto.Syllabus;
import lk.ijse.preschool.dto.tm.StudentTM;
import lk.ijse.preschool.dto.tm.SyllabusTM;
import lk.ijse.preschool.model.StudentModel;
import lk.ijse.preschool.model.SyllabusModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SyllabusWindowController implements Initializable {

    public TextField txtConNo;
    public TextField txtConName;
    public TableView tblSyllabus;
    public TableColumn colConNo;
    public TableColumn colConName;
    public TableColumn colAction;

    private ObservableList<SyllabusTM> obList = FXCollections.observableArrayList();
    private String searchText="";



    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String code = txtConNo.getText();

        boolean isDeleted = SyllabusModel.deleteSyllabus(code);
        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Student deleted !").show();
        }
        txtConNo.clear();
        txtConName.clear();


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String subject_id = txtConNo.getText();
        String sub_name = txtConName.getText();

        try {
            boolean isSaved = SyllabusModel.save(subject_id,sub_name);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Student saved!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        }
        txtConNo.clear();
        txtConName.clear();


    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String code = txtConNo.getText();
        try {
            Syllabus syllabus = SyllabusModel.search(code);
            if (syllabus != null) {
                txtConNo.setText(syllabus.getSubject_id());
                txtConName.setText(syllabus.getSub_name());

            } else {
                new Alert(Alert.AlertType.WARNING, "no student found :(").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String subject_id = txtConNo.getText();
        String sub_name = txtConName.getText();


        try {
            boolean isUpdated = SyllabusModel.update(subject_id,sub_name);
            if (isUpdated) {

                new Alert(Alert.AlertType.CONFIRMATION, "huree! Student Updated!").show();
            }
        } catch (SQLException e) {
            System.out.println(e);
            //   new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }
        txtConNo.clear();
        txtConName.clear();

    }

    @FXML
    void txtConNoOnAction(ActionEvent event) {
        btnSearchOnAction(event);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setCellValueFactory(); //To show table data
        getAllSyllabusToTable(searchText); //To get all students details to table(Not show)
    }

    private void getAllSyllabusToTable(String searchText) {
        try {
            List<Syllabus> syllabusList = SyllabusModel.getAll();
            for(Syllabus syllabus : syllabusList) {
                if (syllabus.getSubject_id().contains(searchText) || syllabus.getSub_name().contains(searchText)){  //Check pass text contains of the supName
                    JFXButton btnDel=new JFXButton("Delete");
                    btnDel.setAlignment(Pos.CENTER);
                    btnDel.setStyle("-fx-background-color: #686de0; ");
                    btnDel.setCursor(Cursor.HAND);

                    SyllabusTM tm=new SyllabusTM(
                            syllabus.getSubject_id(),
                            syllabus.getSub_name(),

                            btnDel);

                    obList.add(tm);

                    setDeleteButtonTableOnAction(btnDel);
                }
            }
            tblSyllabus.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    private void setDeleteButtonTableOnAction(JFXButton btnDel) {
        btnDel.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete?", yes, no).showAndWait();

            if (buttonType.get() == yes) {
               // txtConNo.setText(tblSyllabus.getSelectionModel().getSelectedItem().getStId());
                //btnSearchOnAction(e);
                try {
                    btnDeleteOnAction(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                tblSyllabus.getItems().clear();
                getAllSyllabusToTable(searchText);
            }
        });
    }
}
