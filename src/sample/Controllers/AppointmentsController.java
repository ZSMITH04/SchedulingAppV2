package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.Database.AppointmentQueries;
import sample.Database.ContactQueries;
import sample.Database.CustomerQueries;
import sample.Database.UserQueries;
import sample.Main;
import sample.Models.Appointments;
import sample.Models.Contacts;
import sample.Models.Customers;
import sample.Models.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    @FXML
    private TextField appointmentIdTextfield;
    @FXML
    private ComboBox<String> contactCombo;
    @FXML
    private ComboBox<Integer> customerIdCombo;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField descrTextfield;
    @FXML
    private ComboBox<LocalTime> endTimeCombo;
    @FXML
    private Button exitButton;
    @FXML
    private TextField locTextfield;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox<LocalTime> startTimeCombo;
    @FXML
    private TextField titleTextfield;
    @FXML
    private ComboBox<String> typeCombo;
    @FXML
    private ComboBox<Integer> userIdCombo;
    private static Appointments appointmentSelection;

    /**
     * Saves a new appointment if all checks are true
     * if checks are not true, various alerts will inform the user what the issue is
     *
     * @throws IOException
     * @throws SQLException
     */
    private void saveNew() throws IOException, SQLException {
        if(isAppointmentValid()) {
            if (checkOverlappingNew() && checkTimeSelection() && checkEST()) {
                boolean addAppointment = AppointmentQueries.insertAppointment(
                        titleTextfield.getText(),
                        descrTextfield.getText(),
                        locTextfield.getText(),
                        typeCombo.getSelectionModel().getSelectedItem(),
                        Timestamp.valueOf((LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getSelectionModel().getSelectedItem()))),
                        Timestamp.valueOf((LocalDateTime.of(endDatePicker.getValue(), endTimeCombo.getSelectionModel().getSelectedItem()))),
                        customerIdCombo.getSelectionModel().getSelectedItem(),
                        contactCombo.getSelectionModel().getSelectedItem(),
                        userIdCombo.getSelectionModel().getSelectedItem());
                if (addAppointment) {
                    System.out.println("Add Appointment Success");
                    exit();
                } else {
                    System.out.println("Failure");
                    Main.createAlert(Alert.AlertType.ERROR, "Error saving appointment. Contact system administrator for troubleshooting.");
                }
            }
        }
        }
    /**
     * Saves an existing appointment if all checks are true
     * if checks are not true, various alerts will inform the user what the issue is
     *
     * @throws IOException
     * @throws SQLException
     */
    private void saveExisting() throws IOException, SQLException {
        if(checkOverlapping()) {
            if (isAppointmentValid() && checkTimeSelection() && checkEST()) {
                boolean modifyAppointment = AppointmentQueries.modifyAppointment(
                        Integer.parseInt(appointmentIdTextfield.getText()),
                        titleTextfield.getText(),
                        descrTextfield.getText(),
                        locTextfield.getText(),
                        typeCombo.getSelectionModel().getSelectedItem(),
                        Timestamp.valueOf((LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getSelectionModel().getSelectedItem()))),
                        Timestamp.valueOf((LocalDateTime.of(endDatePicker.getValue(), endTimeCombo.getSelectionModel().getSelectedItem()))),
                        customerIdCombo.getSelectionModel().getSelectedItem(),
                        contactCombo.getSelectionModel().getSelectedItem(),
                        userIdCombo.getSelectionModel().getSelectedItem());
                if (modifyAppointment) {
                    System.out.println("Modify Appointment Success");
                    exit();
                } else {
                    System.out.println("Failure");
                }
            }
        }
    }

    /**
     * Receives the selection from Appointments tableview in Menu
     * @param appointment
     */
    public static void receiveSelection(Appointments appointment){
        appointmentSelection = appointment;
    }

    /**
     * Makes selections on all fields based on the appointment selection passed by receive selection
     */
    public void makeSelections(){
        titleTextfield.setText(appointmentSelection.getTitle());
        descrTextfield.setText(appointmentSelection.getDescription());
        locTextfield.setText(appointmentSelection.getLocation());
        typeCombo.getSelectionModel().select(appointmentSelection.getType());
        startTimeCombo.getSelectionModel().select((appointmentSelection.getStartTime().toLocalDateTime().toLocalTime()));
        endTimeCombo.getSelectionModel().select((appointmentSelection.getEndTime().toLocalDateTime().toLocalTime()));
        customerIdCombo.setValue(appointmentSelection.getCustomerId());
        contactCombo.getSelectionModel().select(appointmentSelection.getContactName());
        userIdCombo.setValue(appointmentSelection.getUserId());
        startDatePicker.setValue(appointmentSelection.getStartTime().toLocalDateTime().toLocalDate());
        endDatePicker.setValue(appointmentSelection.getEndTime().toLocalDateTime().toLocalDate());
        appointmentIdTextfield.setText(String.valueOf(appointmentSelection.getAppointmentId()));
    }

    /**
     * Creates an observable list of all appointments
     * Creates an appointment object with the start and end datetime of the users input
     * References that appointment against each appointment in the list and returns false if it overlaps any existing appointments
     * @return
     * @throws SQLException
     */
    private boolean checkOverlapping() throws SQLException {
        ObservableList<Appointments> appointmentList = AppointmentQueries.getAppointmentsObservableList();
        Appointments newAppointment = new Appointments(appointmentSelection.getAppointmentId(), Timestamp.valueOf(LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getSelectionModel().getSelectedItem())), Timestamp.valueOf(LocalDateTime.of(endDatePicker.getValue(), endTimeCombo.getSelectionModel().getSelectedItem())));
        for (Appointments appointment :
                appointmentList) {
            if(appointment.getAppointmentId() != appointmentSelection.getAppointmentId()){
                if(appointment.getStartTime().before(newAppointment.getEndTime()) && appointment.getEndTime().after(newAppointment.getStartTime())){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Creates an observable list of all appointments
     * Creates an appointment object with the start and end datetime of the users input
     * References that appointment against each appointment in the list and returns false if it overlaps any existing appointments
     * @return
     * @throws SQLException
     */
    private boolean checkOverlappingNew() throws SQLException{
        ObservableList<Appointments> appointments= AppointmentQueries.getAppointmentsObservableList();
        Appointments newAppointment = new Appointments(999, Timestamp.valueOf(LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getSelectionModel().getSelectedItem())), Timestamp.valueOf(LocalDateTime.of(endDatePicker.getValue(), endTimeCombo.getSelectionModel().getSelectedItem())));

        for (Appointments appointment :
                appointments) {
                if(appointment.getStartTime().before(newAppointment.getEndTime()) && appointment.getEndTime().after(newAppointment.getStartTime())){
                    Main.createAlert(Alert.AlertType.ERROR, "Appointments must not be overlapping.");
                    return false;
                }
            }
        return true;
    }

    /**
     * Checks if users input, converted to EST, is between the business hours.
     * @return true if pass
     */
    private boolean checkEST(){
        LocalDateTime ESTStart = MenuController.convertSystemToEST(LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getSelectionModel().getSelectedItem()));
        LocalDateTime ESTEnd = MenuController.convertSystemToEST(LocalDateTime.of(endDatePicker.getValue(), endTimeCombo.getSelectionModel().getSelectedItem()));
        LocalDateTime openTime = LocalDateTime.of(startDatePicker.getValue(), LocalTime.parse("08:00:00.00"));
        LocalDateTime closeTime = LocalDateTime.of(endDatePicker.getValue(), LocalTime.parse("22:00:00.00"));
        if(ESTStart.isBefore(openTime) || ESTEnd.isAfter(closeTime)){
            Main.createAlert(Alert.AlertType.ERROR,"Appointment must be between 8:00am and 10:0PM EST.");
            return false;
        }else{
            return true;
        }
    }

    /**
     * If any of the data points are empty, alert the user.
     * @return true if all are filled
     */
    private boolean isAppointmentValid(){
        if(contactCombo.getSelectionModel().isEmpty() ||
                titleTextfield.getText().isEmpty() ||
                descrTextfield.getText().isEmpty() ||
                locTextfield.getText().isEmpty() ||
                typeCombo.getSelectionModel().isEmpty() ||
                startTimeCombo.getSelectionModel().isEmpty() ||
                endTimeCombo.getSelectionModel().isEmpty() ||
                customerIdCombo.getSelectionModel().isEmpty() ||
                userIdCombo.getSelectionModel().isEmpty()){
            Main.createAlert(Alert.AlertType.ERROR, "All fields must have a selection or be filled out.");
        }
        else{
            return true;
        }
        return false;
    }

    /**
     *Check the users time selection against itself to make sure the appointment end is after the appointment start.
     * @return
     */
    private boolean checkTimeSelection(){
        Timestamp start = Timestamp.valueOf(LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getSelectionModel().getSelectedItem()));
        Timestamp end = Timestamp.valueOf(LocalDateTime.of(endDatePicker.getValue(), endTimeCombo.getSelectionModel().getSelectedItem()));
        if(start.before(end)){
            return true;
        }else{
            Main.createAlert(Alert.AlertType.ERROR,"The appointment must start before it ends!");
            return false;
        }
    }

    /**
     * changes the scene back to main and closes the current window
     * @throws IOException
     */
    @FXML
    private void exit() throws IOException {
        Main.changeScene("views/menu.fxml");
        Main.closeScene(exitButton);
    }

    /**
     * fills a list with contact names for each contact in the database
     *fills the contact combo with the list of contact names
     */
    private void fillContacts(){
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        ObservableList<Contacts> contacts = ContactQueries.getContactsObservableList();
        for (Contacts contact:contacts
        ) { contactNames.add(contact.getContactName());
            contactCombo.setItems(contactNames);
        }
    }

    /**
     * fills a list with customer IDS for each customer in the database
     *fills the customer combo with the list of customer IDS
     */
    private void fillCustomerId() throws SQLException {
        ObservableList<Integer> customerId = FXCollections.observableArrayList();
        ObservableList<Customers> customers = CustomerQueries.getCustomersObservableList();
        for (Customers customer :
                customers) {
            customerId.add(customer.getCustomerId());
            customerIdCombo.setItems(customerId);
        }
    }

    /**
     * fills a list with user IDS for each user in the database
     * fills the user combo with the list of user IDS
     */
    private void fillUserID(){
        ObservableList<Integer> userId = FXCollections.observableArrayList();
        ObservableList<Users> users = UserQueries.getUsersObservableList();
        for (Users user :
                users) {
            userId.add(user.getUserId());
            userIdCombo.setItems(userId);
        }
    }

    /**
     * fills a list with 4 type names
     * sets the type combo with name list
     */
    private void fillType(){
        ObservableList<String> typeList = FXCollections.observableArrayList();
        typeList.addAll("Emergency", "Check-Up", "Procedure", "Consultation");
        typeCombo.setItems(typeList);
    }

    /**
     * Fill time combo.
     */
    private void fillTimeCombo() {
        ObservableList<LocalTime> time = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(23, 45);
        timeFill(time, startTime, endTime, startTimeCombo, endTimeCombo);
    }

    /**
     * Time fill.
     *
     * @param time           the time
     * @param startTime      the start time
     * @param endTime        the end time
     * @param startTimeCombo the start time combo
     * @param endTimeCombo   the end time combo
     */
    public static void timeFill(ObservableList<LocalTime> time, LocalTime startTime, LocalTime endTime, ComboBox<LocalTime> startTimeCombo, ComboBox<LocalTime> endTimeCombo) {
        int interval = 15;
        time.add(startTime);
        while (startTime.isBefore(endTime)){
            startTime = startTime.plusMinutes(interval);
            time.add(startTime);
        }
        startTimeCombo.setItems(time);
        endTimeCombo.setItems(time);
    }

    private static boolean action;
    public static void setAction(boolean input){
        action = input;
    }

    /**
     * checks for the action that opened the appointments page
     * makes selections and sets the action of the save button accordingly based on that
     * fills all combo boxes
     * disables appointment ID textfield
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!action){
            makeSelections();
        }else{
            ObservableList<Integer> appointmentIdList = FXCollections.observableArrayList();
            try {
                ObservableList<Appointments> appointments = AppointmentQueries.getAppointmentsObservableList();

                for (Appointments appointment :
                        appointments) {
                    appointmentIdList.add(appointment.getAppointmentId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(!appointmentIdList.isEmpty()){
            int apptId = Collections.max(appointmentIdList)+1;
            appointmentIdTextfield.setText(String.valueOf(apptId));}
        }
        saveButton.setOnAction(e->{
            if(!action){
                try {
                    saveExisting();
                } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                }
            }else{
                try {
                    saveNew();
                } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        fillContacts();
        try {
            fillCustomerId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fillUserID();
        fillType();
        fillTimeCombo();

        appointmentIdTextfield.setDisable(true);

    }
}
