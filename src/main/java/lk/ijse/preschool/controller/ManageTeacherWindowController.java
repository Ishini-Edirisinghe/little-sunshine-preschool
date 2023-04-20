package lk.ijse.preschool.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.preschool.dto.Event;
import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.dto.Teacher;
import lk.ijse.preschool.dto.tm.EventTM;
import lk.ijse.preschool.dto.tm.StudentTM;
import lk.ijse.preschool.dto.tm.TeacherTM;
import lk.ijse.preschool.model.EventModel;
import lk.ijse.preschool.model.StudentModel;
import lk.ijse.preschool.model.TeacherModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageTeacherWindowController implements Initializable {


    public TextField txtTeachId;
    public TextField txtTeachName;
    public TextField txtTeachAddress;
    public TextField txtContact;
    public TableView tblTeacher;
    public TableColumn colTeachId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colDOB;
    public TableColumn colContact;
    public TableColumn colAction;
    public DatePicker dtpckrDOB;

    private ObservableList<TeacherTM> obList = FXCollections.observableArrayList();
    private String searchText="";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory(); //To show table data
        getAllTeachersToTable(searchText); //To get all Teacher details to table(Not show)

    }
    public void txtTeachIdOnAction(ActionEvent actionEvent) {
        btnSearchOnAction(actionEvent);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String teachId = txtTeachId.getText();
        String name = txtTeachName.getText();
        String address = txtTeachAddress.getText();
        String DOB = String.valueOf(dtpckrDOB.getValue());
        String contact = txtContact.getText();


        try {
            boolean isSaved = TeacherModel.save(teachId, name, address,DOB, contact);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Teacher saved!!!").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        }
        txtTeachId.clear();
        txtTeachName.clear();
        txtTeachAddress.clear();
        dtpckrDOB.setValue(null);
        txtContact.clear();

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String code = txtTeachId.getText();

        boolean isDeleted = TeacherModel.deleteStudent(code);
        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Teacher deleted !").show();
        }
        txtTeachId.clear();
        txtTeachName.clear();
        txtTeachAddress.clear();
        dtpckrDOB.setValue(null);
        txtContact.clear();


    }

    public void btnUpdateOnUpdate(ActionEvent actionEvent) {
        String teachId = txtTeachId.getText();
        String name = txtTeachName.getText();
        String address = txtTeachAddress.getText();
        String DOB =  String.valueOf(dtpckrDOB.getValue());
        String contact = txtContact.getText();


        try {
            boolean isUpdated = TeacherModel.update(teachId, name, address,DOB, contact);
            if (isUpdated) {

                new Alert(Alert.AlertType.CONFIRMATION, "huree! Teacher Updated!").show();
            }
        } catch (SQLException e) {
            System.out.println(e);
            //   new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }
        txtTeachId.clear();
        txtTeachName.clear();
        txtTeachAddress.clear();
        dtpckrDOB.setValue(null);
        txtContact.clear();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String code = txtTeachId.getText();
        try {
            Teacher teacher = TeacherModel.search(code);
            if (teacher != null) {
                txtTeachId.setText(teacher.getTeachId());
                txtTeachName.setText(teacher.getName());
                txtTeachAddress.setText(String.valueOf(teacher.getAddress()));

                dtpckrDOB.setValue(LocalDate.parse(teacher.getDOB()));
                txtContact.setText(String.valueOf(teacher.getContact()));

            } else {
                new Alert(Alert.AlertType.WARNING, "no teacher found :(").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }



    private void setCellValueFactory() {
        colTeachId.setCellValueFactory(new PropertyValueFactory<>("teachId")); //SupplierTM class attributes names
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
    private void getAllTeachersToTable(String searchText) {
        try {
            List<Teacher> teacherList = TeacherModel.getAll();
            for(Teacher teacher : teacherList) {
                if (teacher.getName().contains(searchText) || teacher.getAddress().contains(searchText)){  //Check pass text contains of the supName
                    JFXButton btnDel=new JFXButton("Delete");
                    btnDel.setAlignment(Pos.CENTER);
                    btnDel.setStyle("-fx-background-color: #686de0; ");
                    btnDel.setCursor(Cursor.HAND);

                    TeacherTM tm=new TeacherTM(
                            teacher.getTeachId(),
                            teacher.getName(),
                            teacher.getAddress(),
                            teacher.getDOB(),
                            teacher.getContact(),
                            btnDel);

                    obList.add(tm);

                    setDeleteButtonTableOnAction(btnDel);
                }
            }
            tblTeacher.setItems(obList);
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
             //   txtTeachId.setText(tblTeacher.getSelectionModel().getSelectedItem().);
               // btnSearchEventOnAction(e);
                try {
                    btnDeleteOnAction(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                tblTeacher.getItems().clear();
                getAllTeachersToTable(searchText);
            }
        });
    }
}
