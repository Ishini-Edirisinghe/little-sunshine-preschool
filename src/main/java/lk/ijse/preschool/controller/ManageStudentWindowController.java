package lk.ijse.preschool.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
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
import lk.ijse.preschool.db.DBConnection;
import lk.ijse.preschool.dto.SkillStatus;
import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.dto.tm.StudentTM;
import lk.ijse.preschool.model.PlaceStudentModel;
import lk.ijse.preschool.model.SkillStatusModel;
import lk.ijse.preschool.model.StudentModel;
import lk.ijse.preschool.model.TeacherModel;
import lk.ijse.preschool.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.nio.file.FileSystems;
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
    private JFXComboBox<String> cmbCounting;

    @FXML
    private JFXComboBox<String> cmbCrafting;

    @FXML
    private JFXComboBox<String> cmbDrawing;

    @FXML
    private JFXComboBox<String> cmbReading;

    @FXML
    private JFXComboBox<String> cmbSinging;


    @FXML
    private JFXComboBox<String> cmbWriting;

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
    private TextField txtSearch;


    @FXML
    private TextField txtParentName;

    @FXML
    private TextField txtStId;

    private ObservableList<StudentTM> obList = FXCollections.observableArrayList();


    public AnchorPane addStudentAnchorPane;

    private String searchText="";

    @FXML
    private JFXButton btnSaveStudent;
    private static ObservableList<String> items = FXCollections.observableArrayList("Excellent", "Good", "Weak");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> txtSearch.requestFocus());
        loadTeacherids();
        setCellValueFactory(); //To show table data
        getAllStudentsToTable(searchText); //To get all students details to table(Not show)
        loadTeacherids();
        loadStatus();
        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //Add ActionListener to selected column and display text field values
            //Check select value is not null
            if(null!=newValue) { //newValue!=null --> Get more time to compare (newValue object compare)
                btnSaveStudent.setText("Update");
                setDataToTextFields(newValue); //Set data to text field of selected row data of table

                String studentId = txtStId.getText();

                try {
                    Student student = StudentModel.searchById(studentId);
                  //  txtStudentName.setText(student.getName());
                    SkillStatus skillStatus= SkillStatusModel.search(studentId);

                    if (skillStatus!=null){
                        cmbWriting.setValue(skillStatus.getWriting());
                        cmbSinging.setValue(skillStatus.getSinging());
                        cmbReading.setValue(skillStatus.getReading());
                        cmbDrawing.setValue(skillStatus.getDrawing());
                        cmbCrafting.setValue(skillStatus.getCrafting());
                        cmbCounting.setValue(skillStatus.getCrafting());
                    }else{
                        cmbWriting.getItems().clear();
                        cmbDrawing.getItems().clear();
                        cmbReading.getItems().clear();
                        cmbSinging.getItems().clear();
                        cmbCrafting.getItems().clear();
                        cmbCounting.getItems().clear();
                   //     loadStid();
                    //    loadStatus();
                        new Alert(Alert.AlertType.ERROR, "No input for Students Skill Please Input Student Skills").show();
                        // return;
                    }


                } catch (SQLException e) {
                    //  e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
                }
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> { //Add action listener to txtSearch to search and display table
            tblStudent.getItems().clear();
            searchText=newValue;
            getAllStudentsToTable(searchText);
        });
    }

    private void loadStatus() {
        cmbCounting.getItems().addAll(items);
        cmbCrafting.getItems().addAll(items);
        cmbDrawing.getItems().addAll(items);
        cmbReading.getItems().addAll(items);
        cmbSinging.getItems().addAll(items);
        cmbWriting.getItems().addAll(items);
    }

    private void setDataToTextFields(StudentTM studentTM) {
        txtStId.setText(studentTM.getStId());
        txtName.setText(studentTM.getName());
        txtAddress.setText(studentTM.getAddress());
        dtpckrDOB.setValue(LocalDate.parse(studentTM.getDOB()));
        txtContact.setText(studentTM.getContact());
        txtParentName.setText(studentTM.getParentsName());
        try {
            Student student=StudentModel.searchById(studentTM.getStId());
            cmbTeacherId.setValue(student.getTeachId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
                                student.getParentName());
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
        String count = cmbCounting.getSelectionModel().getSelectedItem().toString();
        String craft = cmbCrafting.getSelectionModel().getSelectedItem().toString();
        String draw = cmbDrawing.getSelectionModel().getSelectedItem().toString();
        String read = cmbReading.getSelectionModel().getSelectedItem().toString();
        String sing = cmbSinging.getSelectionModel().getSelectedItem().toString();
        String write = cmbWriting.getSelectionModel().getSelectedItem().toString();

        SkillStatus s1 = new SkillStatus(id,name,count,craft,draw,read,sing,write);
        Student s2 = new Student(id,name,address,DOB,contact,parentName,teachId);

        boolean isSaved;

        if (btnSaveStudent.getText().equalsIgnoreCase("Save")){
            try {
                Boolean placeStudent = PlaceStudentModel.PlaceStudent(s2, s1);
                if (placeStudent) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Student saved!!!").show();
                    tblStudent.getItems().clear();
                    getAllStudentsToTable("");
                    // clearFieldsRefreshTable();
                }
            } catch (SQLException e) {
               // System.out.println(e);
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
               // clearFieldsRefreshTable();
            }
        }else{
            try {
                boolean isUpdated = StudentModel.update(id, name, address,DOB, contact,parentName,teachId);
                if (isUpdated) {

                    new Alert(Alert.AlertType.CONFIRMATION, "huree! Student Updated!").show();
                    tblStudent.getItems().clear();
                    getAllStudentsToTable("");
                  //  clearFieldsRefreshTable();
                }
            } catch (SQLException e) {

                new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
              //  clearFieldsRefreshTable();
            }
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
        /*String code = txtStId.getText();

        boolean isDeleted = StudentModel.deleteStudent(code);
        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Student deleted !").show();
            tblStudent.getItems().clear();
            getAllStudentsToTable("");
          // clearFieldsRefreshTable();

        }else {
            new Alert(Alert.AlertType.CONFIRMATION, "Student not deleted !").show();
          // clearFieldsRefreshTable();
        }*/

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete?", yes, no).showAndWait();

        if (buttonType.get() == yes) {
            txtStId.setText(tblStudent.getSelectionModel().getSelectedItem().getStId());
            btnSearchStudentOnAction(actionEvent);
            try {
                String code = txtStId.getText();

                boolean isDeleted = StudentModel.deleteStudent(code);
                if(isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Student deleted !").show();
                    tblStudent.getItems().clear();
                    getAllStudentsToTable("");
                    // clearFieldsRefreshTable();

                }else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Student not deleted !").show();
                    // clearFieldsRefreshTable();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }


            tblStudent.getItems().clear();
            getAllStudentsToTable(searchText);
        }

    }

   /* public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
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
    }*/

    public void btnSearchStudentOnAction(ActionEvent actionEvent) {
       // txtStIdOnAction(actionEvent);
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
        String id=txtStId.getText();
        if (Regex.validateStudentId(id)){
            btnSearchStudentOnAction(actionEvent);
            txtName.requestFocus();
        }else {
            txtStId.clear();
            new Alert(Alert.AlertType.WARNING, "No matching Student ID please Input SUP format!!!").show();
        }
       /* String code = txtStId.getText();
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
                new Alert(Alert.AlertType.WARNING, "No student found :(").show();
                txtName.requestFocus();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }*/

    }
    /*private void clearFieldsRefreshTable(){
        txtStId.clear();
        txtName.clear();
        txtAddress.clear();
        dtpckrDOB.setValue(null);
        txtContact.clear();
        txtParentName.clear();
        cmbTeacherId.getItems().clear();
        tblStudent.getItems().clear();
        getAllStudentsToTable("");

    }*/

    @FXML
    void btnClearOnAction(ActionEvent event) {
            txtSearch.clear();
            txtStId.requestFocus();
            btnSaveStudent.setText("Save");
            txtStId.clear();
            txtContact.clear();
            txtName.clear();
            txtParentName.clear();
            txtAddress.clear();
            cmbTeacherId.getItems().clear();
            dtpckrDOB.getEditor().clear();

            cmbCounting.getItems().clear();
            cmbCrafting.getItems().clear();
            cmbDrawing.getItems().clear();
            cmbReading.getItems().clear();
            cmbSinging.getItems().clear();
            cmbWriting.getItems().clear();
             loadTeacherids();
             loadStatus();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        txtStId.requestFocus();
    }
    @FXML
    void txtAddressOnAction(ActionEvent event) {
        txtContact.requestFocus();
    }

    @FXML
    void txtContactOnAction(ActionEvent event) {
        String contact=txtContact.getText();
        if (Regex.validateContact(contact)){
            //  btnSaveOnAction(actionEvent);
            txtParentName.requestFocus();
        }else {
            txtContact.clear();
            new Alert(Alert.AlertType.WARNING, "No matching contact number please Input SUP format!!!").show();
        }
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void btnGetReportOnAction(ActionEvent event) {
        Thread t1=new Thread(
                () -> {
                    String reportPath = "H:\\MY FIRST PROJECT =)\\Little Sunshine_Project\\src\\main\\resources\\reports\\studentReport.jrxml";
                    String sql="select * from student";
                    String path = FileSystems.getDefault().getPath("/reports/studentReport.jrxml").toAbsolutePath().toString();
                    JasperDesign jasdi = null;
                    try {
                        jasdi = JRXmlLoader.load(reportPath);
                        JRDesignQuery newQuery = new JRDesignQuery();
                        newQuery.setText(sql);
                        jasdi.setQuery(newQuery);
                        JasperReport js = JasperCompileManager.compileReport(jasdi);
                        JasperPrint jp = JasperFillManager.fillReport(js, null, DBConnection.getInstance().getConnection());
                        JasperViewer viewer = new JasperViewer(jp, false);
                        viewer.show();
                    } catch (JRException e) {
                        e.printStackTrace();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }

                });

        t1.start();
    }




}
