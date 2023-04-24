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
import lk.ijse.preschool.dto.Payment;
import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.dto.tm.PaymentTM;
import lk.ijse.preschool.dto.tm.StudentTM;
import lk.ijse.preschool.model.PaymentModel;
import lk.ijse.preschool.model.StudentModel;
import lk.ijse.preschool.model.TeacherModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentsWindowController implements Initializable {

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colRefNo;

    @FXML
    private TableColumn<?, ?> colStId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private DatePicker dtpckrDate;

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private TextField txtRefNo;

    @FXML
    private JFXComboBox<String> cmbStId;

    @FXML
    private JFXComboBox<String> cmbType;


    private ObservableList<PaymentTM> obList = FXCollections.observableArrayList();
    private String searchText="";


    public void txtRefNoOnAction(ActionEvent actionEvent) {
        btnSearchOnAction(actionEvent);
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {
        String ref_no = txtRefNo.getText();
        String date = String.valueOf(dtpckrDate.getValue());
        String stid = String.valueOf(cmbStId.getSelectionModel().getSelectedItem());
        String type = String.valueOf(cmbType.getSelectionModel().getSelectedItem());

        try {
            boolean isSaved = PaymentModel.save(ref_no, date, stid,type);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment saved!!!").show();
                clearFieldsRefreshTable();
            }
        } catch (SQLException e) {
            System.out.println(e);
            //new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            clearFieldsRefreshTable();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String code = txtRefNo.getText();

        boolean isDeleted = PaymentModel.deletePayment(code);
        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Payment deleted !").show();
            clearFieldsRefreshTable();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION, "Payment not deleted !").show();
            clearFieldsRefreshTable();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String ref_no = txtRefNo.getText();
        String date =  String.valueOf(dtpckrDate.getValue());
        String stid = String.valueOf(cmbStId.getSelectionModel().getSelectedItem());
        String type = String.valueOf(cmbType.getSelectionModel().getSelectedItem());

        try {
            boolean isUpdated = PaymentModel.update(ref_no, date, stid,type);
            if (isUpdated) {

                new Alert(Alert.AlertType.CONFIRMATION, "huree! Payment Updated!").show();
                clearFieldsRefreshTable();
            }
        } catch (SQLException e) {
            System.out.println(e);
              new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
                clearFieldsRefreshTable();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String code = txtRefNo.getText();
        try {
            Payment payment = PaymentModel.search(code);
            if (payment != null) {
                txtRefNo.setText(payment.getRef_no());
                dtpckrDate.setValue(LocalDate.parse(payment.getDate()));
                cmbStId.setValue(payment.getStid());
                cmbType.setValue(payment.getType());
            } else {
                new Alert(Alert.AlertType.WARNING, "no student found :(").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadType();
        loadStid();
        setCellValueFactory(); //To show table data
        getAllPaymentsToTable(searchText); //To get all students details to table(Not show)
        loadType();
        loadStid();
        tblPayment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //Add ActionListener to selected column and display text field values
            //Check select value is not null
            if(null!=newValue) { //newValue!=null --> Get more time to compare (newValue object compare)
                // btnSaveSupplier.setText("Update Supplier");
                setDataToTextFields(newValue); //Set data to text field of selected row data of table
            }
        });
    }

    private void loadStid() {
        try {
            List<String>  ids = StudentModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbStId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadType() {
       //  ids = null;
        try {
            List<String>  ids = PaymentModel.getType();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbType.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void setDataToTextFields(PaymentTM paymentTM) {
        txtRefNo.setText(paymentTM.getRef_no());
        dtpckrDate.setValue(LocalDate.parse(paymentTM.getDate()));
        cmbStId.setValue(paymentTM.getType());
        cmbType.setValue(paymentTM.getType());
    }


    private void setCellValueFactory() {
        colRefNo.setCellValueFactory(new PropertyValueFactory<>("ref_no")); //SupplierTM class attributes names
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStId.setCellValueFactory(new PropertyValueFactory<>("stid"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

   private void getAllPaymentsToTable(String searchText) {
        try {
            List<Payment> paymentList = PaymentModel.getAll();
            for(Payment payment : paymentList) {
                if (payment.getDate().contains(searchText) || payment.getStid().contains(searchText)){  //Check pass text contains of the supName
                    JFXButton btnDel=new JFXButton("Delete");
                    btnDel.setAlignment(Pos.CENTER);
                    btnDel.setStyle("-fx-background-color: #686de0; ");
                    btnDel.setCursor(Cursor.HAND);

                    PaymentTM tm=new PaymentTM(
                            payment.getRef_no(),
                            payment.getDate(),
                            payment.getStid(),
                            payment.getType(),
                            btnDel);

                    obList.add(tm);

                    setDeleteButtonTableOnAction(btnDel);
                }
            }
            tblPayment.setItems(obList);
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
               // txtRefNo.setText(tblPayment.getSelectionModel().getSelectedItem().getStId());
               // btnSearchOnAction(e);
                try {
                    btnDeleteOnAction(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                tblPayment.getItems().clear();
                getAllPaymentsToTable(searchText);
            }
        });
    }
    private void clearFieldsRefreshTable(){
        txtRefNo.clear();
        dtpckrDate.setValue(null);
        cmbStId.getItems().clear();
        cmbType.getItems().clear();
        tblPayment.getItems().clear();
        getAllPaymentsToTable("");
        loadStid();
        loadType();

    }
}
