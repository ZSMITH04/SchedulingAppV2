package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

public class ReportsController implements Initializable {

    @FXML
    private Button exitButton;
    @FXML
    private TextArea textDisplay;
    @FXML
    private RadioButton customerRadio;
    @FXML
    private RadioButton typeRadio;
    @FXML
    private ComboBox<String> variableComboBox;
    @FXML
    private RadioButton contactRadio;

    /**
     * changes display based on users choices of radio button
     * builds a new string and displays in a TextArea based on Combobox selection
     */
    private void variableComboBoxAction(){
        if(contactRadio.isSelected()){
            textDisplay.clear();
            String contactName = variableComboBox.getSelectionModel().getSelectedItem();
            ObservableList<Appointments> appointmentsObservableList = AppointmentQueries.getAppointmentsByContactName(contactName);
            StringBuilder contactReport = new StringBuilder();
            contactReport.append(String.join("                     ", "Appt ID", "Title", "Description", "Type","Start                ","End", "       Customer ID", "\n"));
            contactReport.append("\n");
            for (Appointments appointment :
                    appointmentsObservableList) {
                contactReport.append(String.join("                    ",String.valueOf(appointment.getAppointmentId()), appointment.getTitle(), appointment.getDescription(), appointment.getType(), appointment.getStartTime().toString(),appointment.getEndTime().toString(), String.valueOf(appointment.getCustomerId()), "\n"));
            }
            textDisplay.setText(contactReport.toString());
        }else if(customerRadio.isSelected()){
            textDisplay.clear();
            String customerName = variableComboBox.getSelectionModel().getSelectedItem();
            ObservableList<Appointments> appointmentsObservableList = AppointmentQueries.getAppointmentsObservableListByCustomerName(customerName);
            StringBuilder customerReport = new StringBuilder();
            customerReport.append(String.join("                     ", "Appt ID", "Title", "Description", "Type","Start                ","End", "       Customer ID", "\n"));
            customerReport.append("\n");
            for (Appointments appointment :
                    appointmentsObservableList) {
                customerReport.append(String.join("                    ",String.valueOf(appointment.getAppointmentId()), appointment.getTitle(), appointment.getDescription(), appointment.getType(), appointment.getStartTime().toString(),appointment.getEndTime().toString(), String.valueOf(appointment.getContactId()), "\n"));
            }
            textDisplay.setText(customerReport.toString());
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
                textDisplay.clear();

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
                textDisplay.clear();
                ObservableList<Appointments> appointmentsObservableList = AppointmentQueries.getAppointmentCounts();
                StringBuilder typeReport = new StringBuilder();
                typeReport.append(String.join("      ", "Month", "Appointment Type", "Total", "\n"));
                typeReport.append("\n");
                for (Appointments appointment :
                        appointmentsObservableList) {
                    typeReport.append(String.join("      ", appointment.getMonth(), appointment.getType(), String.valueOf(appointment.getTotal()), "\n"));
                }
                textDisplay.setText(typeReport.toString());
            }
            if(t1.equals(contactRadio)){
                textDisplay.clear();

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