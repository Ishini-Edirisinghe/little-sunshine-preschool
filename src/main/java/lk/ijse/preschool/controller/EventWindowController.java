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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.preschool.db.DBConnection;
import lk.ijse.preschool.dto.Event;
import lk.ijse.preschool.dto.Student;
import lk.ijse.preschool.dto.tm.EventTM;
import lk.ijse.preschool.dto.tm.StudentTM;
import lk.ijse.preschool.model.EventModel;
import lk.ijse.preschool.model.StudentModel;
import lk.ijse.preschool.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EventWindowController implements Initializable {


    @FXML
    private TableView<EventTM> tblEvent;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colEventName;

    @FXML
    private TableColumn<?, ?> colEventNo;

    @FXML
    private TableColumn<?, ?> colMonth;

    @FXML
    private TextField txtEventName;

    @FXML
    private TextField txtEventNo;

    @FXML
    private TextField txtMonth;

    private ObservableList<EventTM> obList = FXCollections.observableArrayList();

    private String searchText="";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory(); //To show table data
        getAlleventsToTable(searchText); //To get all event details to table(Not show)
        tblEvent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //Add ActionListener to selected column and display text field values
            //Check select value is not null
            if(null!=newValue) { //newValue!=null --> Get more time to compare (newValue object compare)
                // btnSaveSupplier.setText("Update Supplier");
                setDataToTextFields(newValue); //Set data to text field of selected row data of table
            }
        });
    }

    private void setDataToTextFields(EventTM eventTM) {
        txtEventNo.setText(eventTM.getEvent_no());
        txtEventName.setText(eventTM.getName());
        txtMonth.setText(eventTM.getMonth());
    }

    private void getAlleventsToTable(String searchText) {
        try {
            List<Event> eventList = EventModel.getAll();
            for(Event event : eventList) {
                if (event.getName().contains(searchText) || event.getMonth().contains(searchText)){  //Check pass text contains of the eveName
                    JFXButton btnDel=new JFXButton("Delete");
                    btnDel.setAlignment(Pos.CENTER);
                    btnDel.setStyle("-fx-background-color: #686de0; ");
                    btnDel.setCursor(Cursor.HAND);

                    EventTM tm=new EventTM(
                            event.getEvent_no(),
                            event.getName(),
                            event.getMonth(),
                            btnDel);

                    obList.add(tm);

                    setDeleteButtonTableOnAction(btnDel);
                }
            }
            tblEvent.setItems(obList);
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
                txtEventNo.setText(tblEvent.getSelectionModel().getSelectedItem().getEvent_no());
                btnSearchEventOnAction(e);
                try {
                    btnDeleteOnAction(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                tblEvent.getItems().clear();
                getAlleventsToTable(searchText);
            }
        });
    }

    private void setCellValueFactory() {
        colEventNo.setCellValueFactory(new PropertyValueFactory<>("event_no")); //SupplierTM class attributes names
        colEventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String event_no = txtEventNo.getText();
        String name = txtEventName.getText();
        String month = txtMonth.getText();


        try {
            boolean isSaved = EventModel.save(event_no,name,month);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Event saved!!!").show();
                tblEvent.getItems().clear();
                getAlleventsToTable(searchText);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        }
        txtEventNo.clear();
        txtEventName.clear();
        txtMonth.clear();


    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String code = txtEventNo.getText();

        boolean isDeleted = EventModel.deleteEvent(code);
        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Student deleted !").show();
            tblEvent.getItems().clear();
            getAlleventsToTable(searchText);
        }
        txtEventNo.clear();
        txtEventName.clear();
        txtMonth.clear();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String event_no = txtEventNo.getText();
        String name = txtEventName.getText();
        String month = txtMonth.getText();

        try {
            boolean isUpdated = EventModel.update(event_no, name, month);
            if (isUpdated) {

                new Alert(Alert.AlertType.CONFIRMATION, "huree! Student Updated!").show();
                tblEvent.getItems().clear();
                getAlleventsToTable("");
            }
        } catch (SQLException e) {
            System.out.println(e);
            //   new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }
        txtEventNo.clear();
        txtEventName.clear();
        txtMonth.clear();
    }
    @FXML
    void btnSearchEventOnAction(ActionEvent event) {
        String code = txtEventNo.getText();
        try {
            Event event1 = EventModel.search(code);
            if (event1 != null) {
                txtEventNo.setText(event1.getEvent_no());
                txtEventName.setText(event1.getName());
                txtMonth.setText(event1.getMonth());

            } else {
                new Alert(Alert.AlertType.WARNING, "no event found :(").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }
    @FXML
    void btnGetReportOnAction(ActionEvent event) {
        Thread t1=new Thread(
                () -> {
                    String ReportPath = "H:\\MY FIRST PROJECT =)\\Little Sunshine_Project\\src\\main\\resources\\reports\\eventreport.jrxml";
                    String sql="select * from event";
                    String path = FileSystems.getDefault().getPath("/reports/eventreport.jrxml").toAbsolutePath().toString();
                    JasperDesign jasdi = null;
                    try {
                        jasdi = JRXmlLoader.load(ReportPath);
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

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtEventNo.clear();
        txtEventName.clear();
        txtMonth.clear();
    }
    @FXML
    void txtEventNoOnAction(ActionEvent event) {
        String event_no=txtEventNo.getText();
        if (Regex.validateEventNo(event_no)){
            btnSearchEventOnAction(event);
            txtEventName.requestFocus();
        }else {
            txtEventNo.clear();
            new Alert(Alert.AlertType.WARNING, "No matching Student ID please Input SUP format!!!").show();
        }
    }
    @FXML
    void txtEventNameOnAction(ActionEvent event) {
        txtMonth.requestFocus();
    }

}
