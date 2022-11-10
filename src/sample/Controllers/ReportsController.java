package sample.Controllers;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import sample.Database.AppointmentQueries;
import sample.Database.ContactQueries;
import sample.Database.CustomerQueries;
import sample.Main;
import sample.Models.Appointments;
import sample.Models.Contacts;
import sample.Models.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The type Reports controller.
 */
public class ReportsController implements Initializable {

    @FXML
    private Button exitButton;

    @FXML
    private RadioButton customerRadio;
    @FXML
    private RadioButton typeRadio;
    @FXML
    private ComboBox<String> variableComboBox;
    @FXML
    private RadioButton contactRadio;
    @FXML
    private TableView<Appointments> appointmentsTableView;
    @FXML
    private TableColumn<?, ?> colApptId;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDescr;

    @FXML
    private TableColumn<?, ?> colEnd;

    @FXML
    private TableColumn<?, ?> colMonth;

    @FXML
    private TableColumn<?, ?> colStart;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colType;
    @FXML
    private TableColumn<?,?> colType2;
    @FXML
    private TableColumn<?,?> colYear;





    /**
     * changes display based on users choices of radio button
     * builds a new string and displays in a TextArea based on Combobox selection
     */
    private void variableComboBoxAction(){
        if(contactRadio.isSelected()){
            showTypeColumns(false);
            showAppointmentColumns(true);
            String contactName = variableComboBox.getSelectionModel().getSelectedItem();
            ObservableList<Appointments> appointmentsObservableList = AppointmentQueries.getAppointmentsByContactName(contactName);
            colApptId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colDescr.setCellValueFactory(new PropertyValueFactory<>("description"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            colEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appointmentsTableView.setItems(appointmentsObservableList);

        }else if(customerRadio.isSelected()){
            showTypeColumns(false);
            showAppointmentColumns(true);
            String customerName = variableComboBox.getSelectionModel().getSelectedItem();
            ObservableList<Appointments> appointmentsObservableList = AppointmentQueries.getAppointmentsObservableListByCustomerName(customerName);
            colApptId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colDescr.setCellValueFactory(new PropertyValueFactory<>("description"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            colEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appointmentsTableView.setItems(appointmentsObservableList);

        }else{
            Main.createAlert(Alert.AlertType.ERROR, "Type Report Selected. Select other report type.");
        }
    }


    private void exit(){
        try {
            Main.changeScene("views/menu.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Main.closeScene(exitButton);
    }
    private void showAppointmentColumns(boolean visible){
        colApptId.setVisible(visible);
        colCustomerId.setVisible(visible);
        colStart.setVisible(visible);
        colEnd.setVisible(visible);
        colDescr.setVisible(visible);
        colTitle.setVisible(visible);
        colType.setVisible(visible);
    }
    private void showTypeColumns(boolean visible){
        colMonth.setVisible(visible);
        colType2.setVisible(visible);
        colTotal.setVisible(visible);
        colYear.setVisible(visible);
    }


    /**
     * Lambda used here to add a listener to the reports toggle group
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ToggleGroup tg = new ToggleGroup();
        customerRadio.setToggleGroup(tg);
        typeRadio.setToggleGroup(tg);
        contactRadio.setToggleGroup(tg);

        exitButton.setOnAction(e->{
            exit();
        });
        variableComboBox.setOnAction(e->{
            variableComboBoxAction();
        });
        tg.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if(t1.equals(customerRadio)){
                variableComboBox.setVisible(true);
                showTypeColumns(false);
                showAppointmentColumns(true);
                ObservableList<String> customerNames = FXCollections.observableArrayList();
                ObservableList<Customers> customers = null;
                try {
                    customers = CustomerQueries.getCustomersObservableList();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (Customers customer : customers
                ) {
                    customerNames.add(customer.getCustomerName());
                    variableComboBox.setItems(customerNames);
                }
                variableComboBox.getSelectionModel().select(1);
            }
            if(t1.equals(typeRadio)){
                variableComboBox.setVisible(false);
                showAppointmentColumns(false);
                showTypeColumns(true);
                appointmentsTableView.setItems(AppointmentQueries.getAppointmentCounts());
                colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
                colType2.setCellValueFactory(new PropertyValueFactory<>("type"));
                colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
                colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
            }
            if(t1.equals(contactRadio)){
                variableComboBox.setVisible(true);
                showTypeColumns(false);
                showAppointmentColumns(true);
                ObservableList<String> contactNames = FXCollections.observableArrayList();
                ObservableList<Contacts> contacts = ContactQueries.getContactsObservableList();
                for (Contacts contact : contacts
                ) {
                    contactNames.add(contact.getContactName());
                    variableComboBox.setItems(contactNames);
                }
                variableComboBox.getSelectionModel().select(1);
            }
        });
        typeRadio.setSelected(true);
    }
}