package lk.ijse.preschool.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.dto.tm.StudentTM;
import lk.ijse.preschool.model.StudentModel;
import lk.ijse.preschool.model.TeacherModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageStudentWindowController implements Initializable {
    public ComboBox cmbTeacherId;
    public AnchorPane manageStudentAnchorPane;
    public DatePicker dtpckrDOB;


    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colParentsName;

    @FXML
    private TableColumn<?, ?> colStId;

    @FXML
    private TableColumn<?, ?> colTeacherId;

    @FXML
    private TableView<StudentTM> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;


    @FXML
    private TextField txtParentName;

    @FXML
    private TextField txtStId;

    private ObservableList<StudentTM> obList = FXCollections.observableArrayList();


    public AnchorPane addStudentAnchorPane;

    private String searchText="";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadTeacherids();
        setCellValueFactory(); //To show table data
        getAllStudentsToTable(searchText); //To get all students details to table(Not show)
        loadTeacherids();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //Add ActionListener to selected column and display text field values
            //Check select value is not null
            if(null!=newValue) { //newValue!=null --> Get more time to compare (newValue object compare)
               // btnSaveSupplier.setText("Update Supplier");
                setDataToTextFields(newValue); //Set data to text field of selected row data of table
            }
        });
    }

    private void setDataToTextFields(StudentTM studentTM) {
        txtStId.setText(studentTM.getStId());
        txtName.setText(studentTM.getName());
        txtAddress.setText(studentTM.getAddress());
        dtpckrDOB.setValue(LocalDate.parse(studentTM.getDOB()));
        txtContact.setText(studentTM.getContact());
        txtParentName.setText(studentTM.getParentsName());
        cmbTeacherId.setValue(studentTM.getTeacherId());
    }

    private void getAllStudentsToTable(String searchText) {
            try {
                List<Student> studentList = StudentModel.getAll();
                for(Student student : studentList) {
                    if (student.getName().contains(searchText) || student.getAddress().contains(searchText)){  //Check pass text contains of the supName
                        JFXButton btnDel=new JFXButton("Delete");
                        btnDel.setAlignment(Pos.CENTER);
                        btnDel.setStyle("-fx-background-color: #686de0; ");
                        btnDel.setCursor(Cursor.HAND);

                        StudentTM tm=new StudentTM(
                                student.getStId(),
                                student.getName(),
                                student.getAddress(),
                                student.getDOB(),
                                student.getContact(),
                                student.getParentName(),
                                student.getTeachId(),btnDel);
                        obList.add(tm);

                    setDeleteButtonTableOnAction(btnDel);
                }
            }
            tblStudent.setItems(obList);
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
                txtStId.setText(tblStudent.getSelectionModel().getSelectedItem().getStId());
                btnSearchStudentOnAction(e);
                try {
                    btnDeleteOnAction(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                tblStudent.getItems().clear();
                getAllStudentsToTable(searchText);
            }
        });
    }

    private void setCellValueFactory() {
        colStId.setCellValueFactory(new PropertyValueFactory<>("stId")); //SupplierTM class attributes names
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colParentsName.setCellValueFactory(new PropertyValueFactory<>("parentsName"));
        colTeacherId.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String id = txtStId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String DOB = String.valueOf(dtpckrDOB.getValue());
        String contact = txtContact.getText();
        String parentName = txtParentName.getText();
        String teachId = String.valueOf(cmbTeacherId.getSelectionModel().getSelectedItem());

        try {
            boolean isSaved = StudentModel.save(id, name, address,DOB, contact,parentName,teachId);
            if (isSaved) {
              new Alert(Alert.AlertType.CONFIRMATION, "Student saved!!!").show();
               clearFieldsRefreshTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
           clearFieldsRefreshTable();
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

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String code = txtStId.getText();

        boolean isDeleted = StudentModel.deleteStudent(code);
        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Student deleted !").show();
           clearFieldsRefreshTable();

        }else {
            new Alert(Alert.AlertType.CONFIRMATION, "Student not deleted !").show();
           clearFieldsRefreshTable();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtStId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String DOB =  String.valueOf(dtpckrDOB.getValue());
        String contact = txtContact.getText();
        String parentName = txtParentName.getText();
        String teachId = String.valueOf(cmbTeacherId.getSelectionModel().getSelectedItem());

        try {
            boolean isUpdated = StudentModel.update(id, name, address,DOB, contact,parentName,teachId);
            if (isUpdated) {

                new Alert(Alert.AlertType.CONFIRMATION, "huree! Student Updated!").show();
                clearFieldsRefreshTable();
            }
        } catch (SQLException e) {

              new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
            clearFieldsRefreshTable();
        }
    }

    public void btnSearchStudentOnAction(ActionEvent actionEvent) {
        String code = txtStId.getText();
        try {
            Student student = StudentModel.search(code);
            if (student != null) {
                txtStId.setText(student.getStId());
                txtName.setText(student.getName());
                txtAddress.setText(String.valueOf(student.getAddress()));
                cmbTeacherId.setValue(student.getTeachId());
                dtpckrDOB.setValue(LocalDate.parse(student.getDOB()));
                txtContact.setText(String.valueOf(student.getContact()));
                txtParentName.setText(String.valueOf(student.getParentName()));
            } else {
                new Alert(Alert.AlertType.WARNING, "no student found :(").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }



    public void txtStIdOnAction(ActionEvent actionEvent) {
        btnSearchStudentOnAction(actionEvent);
    }
    private void clearFieldsRefreshTable(){
        txtStId.clear();
        txtName.clear();
        txtAddress.clear();
        dtpckrDOB.setValue(null);
        txtContact.clear();
        txtParentName.clear();
        cmbTeacherId.getItems().clear();
        tblStudent.getItems().clear();
        getAllStudentsToTable("");
        loadTeacherids();
    }

}
