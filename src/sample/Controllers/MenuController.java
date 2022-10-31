package sample.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Database.AppointmentQueries;
import sample.Database.CustomerQueries;
import sample.Main;
import sample.Models.Appointments;
import sample.Models.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;

public class MenuController implements Initializable {

    @FXML
    private Button AppointmentAdd;
    @FXML
    private Button AppointmentDelete;
    @FXML
    private Button AppointmentModify;
    @FXML
    private TableView<Appointments> AppointmentTableview;
    @FXML
    private Button CustomerAdd;
    @FXML
    private Button CustomerDelete;
    @FXML
    private Button CustomerModify;
    @FXML
    private TableView<Customers> CustomerTableview;
    @FXML
    private Button LogOut;
    @FXML
    private Button ReportByContacts;
    @FXML
    private Button ReportByType;
    @FXML
    private TableColumn<Customers, String> colAddress;
    @FXML
    private TableColumn<Appointments, Integer> colApptId;
    @FXML
    private TableColumn<Customers, String> colCountry;
    @FXML
    private TableColumn<Appointments, Integer> colApptCustId;
    @FXML
    private TableColumn<Customers, Integer> colCustomerId;
    @FXML
    private TableColumn<Appointments, String> colDescr;
    @FXML
    private TableColumn<Customers, String> colDivision;
    @FXML
    private TableColumn<Appointments, LocalDateTime> colEndDate;
    @FXML
    private TableColumn<Appointments, String> colLoc;
    @FXML
    private TableColumn<Customers, String> colName;
    @FXML
    private TableColumn<Customers, String> colPhone;
    @FXML
    private TableColumn<Customers, String> colPostcode;
    @FXML
    private TableColumn<Appointments, LocalDateTime> colStartDate;
    @FXML
    private TableColumn<Appointments, String> colTitle;
    @FXML
    private TableColumn<Appointments, String> colType;
    @FXML
    private TableColumn<Appointments, Integer> colUserId;
    @FXML
    private TableColumn<Customers, Integer> colDivisionId;
    @FXML
    private TableColumn<Appointments, String> colContact;
    @FXML
    private RadioButton weeklyRadio;
    @FXML
    private RadioButton monthlyRadio;
    @FXML
    private RadioButton viewAllRadio;
    @FXML
    private TextArea changeText;

    /**
     * Convert system to est local date time.
     *
     * @param time the time
     * @return the local date time
     */
    public static LocalDateTime convertSystemToEST(LocalDateTime time){
        ZonedDateTime timeToSystem = time.atZone(of(systemDefault().toString()));
        ZonedDateTime timeToEST = timeToSystem.withZoneSameInstant(of("America/New_York"));
        return timeToEST.toLocalDateTime();
    }

    /**
     * Convert system to utc local date time.
     *
     * @param time the time
     * @return the local date time
     */
    public static LocalDateTime convertSystemToUTC(LocalDateTime time){
        ZonedDateTime timeToSystem = time.atZone(of(systemDefault().toString()));
        ZonedDateTime timeToUTC = timeToSystem.withZoneSameInstant(of("UTC"));
        return timeToUTC.toLocalDateTime();
    }

    /**
     * Exit.
     */
    public void exit(){
        JDBC.closeConnection();
        System.exit(0);
    }

    /**
     * Populate customers.
     */
    public void populateCustomers() {
        try {
            ObservableList<Customers> customers = CustomerQueries.getCustomersObservableList();

            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colPostcode.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
            colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
            colDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
            colDivisionId.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
            CustomerTableview.setItems(customers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * general use function to set the cellvaluefactory of each column in appointment tableview
     * @param appointments
     */
    public void setAppointmentValueFactories(ObservableList<Appointments> appointments){
        colApptId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescr.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colApptCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        AppointmentTableview.setItems(appointments);
    }
    /**
     * Populate all appointments
     */
    public void populateAppointments() {
        try {
            ObservableList<Appointments> appointments = AppointmentQueries.getAppointmentsObservableList();
            setAppointmentValueFactories(appointments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Populate weekly appointments.
     */
    public void populateWeeklyAppointments() {
        try {
            ObservableList<Appointments> appointments = AppointmentQueries.getAppointmentsObservableListByWeek();
            setAppointmentValueFactories(appointments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Populate monthly appointments.
     */
    public void populateMonthlyAppointments() {
        try {
            ObservableList<Appointments> appointments = AppointmentQueries.getAppointmentsObservableListByMonth();
            setAppointmentValueFactories(appointments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Confirm delete appointment.
     * Lambda is used here to filter for response type of the alert and respond with appropriate response if present
     */
    @FXML
    public void confirmDeleteAppointment() {
        Appointments deletedAppointment = AppointmentTableview.getSelectionModel().getSelectedItem();
        if (AppointmentTableview.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected appointment?");
            alert.showAndWait()
                    .filter(response->response == ButtonType.OK)
                    .ifPresent(response-> {AppointmentQueries.deleteAppointment(deletedAppointment.getAppointmentId());
                        changeText.setText("Appointment ID " + deletedAppointment.getAppointmentId() + " successfully deleted.");
                        populateAppointments();
                    });
        } else {
            Main.createAlert(Alert.AlertType.ERROR, "You must have an appointment selected.");
        }
    }


    /**
     * Add customer.
     *
     * @throws IOException the io exception
     */
    @FXML
    public void addCustomer() throws IOException{
        CustomersController.setAction(true);
        Main.changeScene("views/Customers.fxml");
        Main.closeScene(CustomerAdd);
    }

    /**
     * Confirm delete customer. Confirms that a customer is selected.
     * If so, customer is asked to confirm the delete. Response is filtered for an ok press.
     * If present, the selected customer is deleted and the table is repopulated.
     */
    @FXML
    public void confirmDeleteCustomer() throws SQLException {
        Customers deletedCustomer = CustomerTableview.getSelectionModel().getSelectedItem();
        if (deletedCustomer == null) {
            Main.createAlert(Alert.AlertType.ERROR, "You must have a customer selected.");
        }else if (checkAssociatedAppointments()){
            Main.createAlert(Alert.AlertType.ERROR, "Error deleting customer. Make sure all associated appointments are cancelled");
        }
        else if (CustomerQueries.deleteCustomer(deletedCustomer.getCustomerId())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected customer?");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        CustomerQueries.deleteCustomer(deletedCustomer.getCustomerId());
                        changeText.setText(deletedCustomer.getCustomerName() + " successfully deleted.");
                        populateCustomers();
                    });
        }
        else{
            Main.createAlert(Alert.AlertType.ERROR, "Delete failed. Contact your system administrator for troubleshooting.");
        }
    }

    /**
     * Creates a list of appointments to check against for associated appointments with the selected customer ID
     * If found, returns
     * @return
     */

    private boolean checkAssociatedAppointments() throws SQLException {
        Customers deletedCustomer = CustomerTableview.getSelectionModel().getSelectedItem();
        int custID = deletedCustomer.getCustomerId();
        ObservableList<Appointments> appts = AppointmentQueries.getAppointmentsObservableList();
        for (Appointments appointment :
                appts) {
            if(appointment.getCustomerId() == custID)
            {return true;}
        }
        return false;
    }

    private void returnSelectedAppointment(){
        AppointmentsController.receiveSelection(AppointmentTableview.getSelectionModel().getSelectedItem());

    }
    private void returnSelectedCustomer(){
        CustomersController.receiveSelection(CustomerTableview.getSelectionModel().getSelectedItem());
    }
    @FXML
    private void addAppointment()throws IOException {
        AppointmentsController.setAction(true);
        Main.changeScene("views/Appointments.fxml");
        Main.closeScene(AppointmentAdd);
    }

    /**
     * Opens the Appointment view, sets the action boolean to false, and passes the selected appointment to the Appointment controller
     * @throws IOException
     */
    @FXML
    private void modifyAppointment() throws IOException {
        if(AppointmentTableview.getSelectionModel().getSelectedItem() != null) {
            AppointmentsController.setAction(false);
            returnSelectedAppointment();
            Main.changeScene("views/Appointments.fxml");
            Main.closeScene(AppointmentModify);
        }else{
            Main.createAlert(Alert.AlertType.ERROR, "You must have an appointment selected to modify.");
        }
    }

    /**
     * Opens the customer view, sets the action boolean to false
     * passes the selected appointment to the appointment controller
     * @throws IOException
     */
    @FXML
    private void modifyCustomer() throws IOException {
        if(CustomerTableview.getSelectionModel().getSelectedItem() != null) {
            CustomersController.setAction(false);
            returnSelectedCustomer();
            Main.changeScene("views/Customers.fxml");
            Main.closeScene(CustomerModify);
        }else{
            Main.createAlert(Alert.AlertType.ERROR, "You must have a customer selected to modify.");
        }
    }

    @FXML
    private void openTypeReport() throws IOException {
        Main.changeScene("views/Reports.fxml");
        Main.closeScene(ReportByType);
    }

    /**
     * Sets a toggle group for calendar view toggles, and adds a listener to that toggle group to determine
     * what view should be displayed actively.
     * populates appointments & customers
     * sets the changeText TextArea to non-editable
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        ToggleGroup appointmentToggle = new ToggleGroup();
        weeklyRadio.setToggleGroup(appointmentToggle);
        monthlyRadio.setToggleGroup(appointmentToggle);
        viewAllRadio.setToggleGroup(appointmentToggle);
        viewAllRadio.setSelected(true);
        appointmentToggle.selectedToggleProperty().addListener(((observableValue, toggle, t1) -> {
            if(t1 == weeklyRadio){
                populateWeeklyAppointments();
            }
            else if(t1 == monthlyRadio){
                populateMonthlyAppointments();
            }
            else{
                populateAppointments();
            }
        }));
        populateAppointments();
        populateCustomers();
        changeText.setEditable(false);
    }

}
