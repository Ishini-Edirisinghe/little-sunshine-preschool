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
import lk.ijse.preschool.dto.Payment;
import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.dto.tm.PaymentTM;
import lk.ijse.preschool.dto.tm.StudentTM;
import lk.ijse.preschool.model.PaymentModel;
import lk.ijse.preschool.model.StudentModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentsWindowController implements Initializable {

    public TextField txtRefNo;
    public TextField txtStId;
    public TextField txtType;
    public TableView tblPayment;
    public TableColumn colRefNo;
    public TableColumn colStId;
    public TableColumn colDate;
    public TableColumn colType;
    @FXML
    private TableColumn<?, ?> colAction;
    public DatePicker dtpckrDate;

    private ObservableList<PaymentTM> obList = FXCollections.observableArrayList();
    private String searchText="";


    public void txtRefNoOnAction(ActionEvent actionEvent) {
        btnSearchOnAction(actionEvent);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String ref_no = txtRefNo.getText();
        String date = String.valueOf(dtpckrDate.getValue());
        String stid = txtStId.getText();
        String type = txtType.getText();

        try {
            boolean isSaved = PaymentModel.save(ref_no, date, stid,type);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment saved!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        }
        txtRefNo.clear();
        dtpckrDate.setValue(null);
        txtStId.clear();
        txtType.clear();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String code = txtRefNo.getText();

        boolean isDeleted = PaymentModel.deletePayment(code);
        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Payment deleted !").show();
        }
        txtRefNo.clear();
        dtpckrDate.setValue(null);
        txtStId.clear();
        txtType.clear();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String ref_no = txtRefNo.getText();
        String date =  String.valueOf(dtpckrDate.getValue());
        String stid = txtStId.getText();
        String type = txtType.getText();

        try {
            boolean isUpdated = PaymentModel.update(ref_no, date, stid,type);
            if (isUpdated) {

                new Alert(Alert.AlertType.CONFIRMATION, "huree! Payment Updated!").show();
            }
        } catch (SQLException e) {
            System.out.println(e);
            //   new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }
        txtRefNo.clear();
        dtpckrDate.setValue(null);
        txtStId.clear();
        txtType.clear();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String code = txtRefNo.getText();
        try {
            Payment payment = PaymentModel.search(code);
            if (payment != null) {
                txtRefNo.setText(payment.getRef_no());
                dtpckrDate.setValue(LocalDate.parse(payment.getDate()));
                txtStId.setText(String.valueOf(payment.getStid()));
                txtType.setText(String.valueOf(payment.getType()));
            } else {
                new Alert(Alert.AlertType.WARNING, "no student found :(").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory(); //To show table data
        getAllPaymentsToTable(searchText); //To get all students details to table(Not show)
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
}
