package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Database.CountryQueries;
import sample.Database.CustomerQueries;
import sample.Database.DivisionQueries;
import sample.Main;
import sample.Models.Countries;
import sample.Models.Customers;
import sample.Models.FirstLevelDivisions;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {
    @FXML
    private ComboBox<String> DivisionCombo;

    @FXML
    private TextField customerAddressTextfield;

    @FXML
    private ComboBox<String> customerCountryCombo;

    @FXML
    private TextField customerIdTextfield;

    @FXML
    private TextField customerNameTextfield;

    @FXML
    private TextField customerPhoneTextfield;

    @FXML
    private TextField customerZipcodeTextfield;

    @FXML
    private Button exitButton;

    @FXML
    private Button saveButton;
    private static Customers customerSelection;

    /**
     * opens menu and closes the current window
     * @throws IOException
     */
    @FXML
    private void exit() throws IOException {
        Main.changeScene("views/menu.fxml");
        Main.closeScene(exitButton);
    }

    /**
     * Checks selectionsFilled(), if true, tries bool InsertCustomer query, if selectionsFilled() is false alerts to fill out all fields.
     * @throws IOException
     */
    @FXML
    private void saveNew() throws IOException {
        if(selectionsFilled()) {
            boolean addCustomer = CustomerQueries.insertCustomer(
                    customerNameTextfield.getText(),
                    customerAddressTextfield.getText(),
                    customerZipcodeTextfield.getText(),
                    customerPhoneTextfield.getText(),
                    DivisionCombo.getSelectionModel().getSelectedItem());
            if (addCustomer) {
                System.out.println("Add Customer Success");
                exit();
            } else {
                System.out.println("Failure");
            }
        }else{
            Main.createAlert(Alert.AlertType.ERROR, "You must fill out all fields.");
        }
    }

    /**
     * checks selectionsFilled(), if true, modifyCustomer query, if selectionsFilled() is false, alert to fill out all fields.
     * @throws IOException
     */
    @FXML
    private void saveExisting() throws IOException {

        if(selectionsFilled()) {
            boolean modifyCustomer = CustomerQueries.modifyCustomer(
                    customerNameTextfield.getText(),
                    customerAddressTextfield.getText(),
                    customerZipcodeTextfield.getText(),
                    customerPhoneTextfield.getText(),
                    DivisionCombo.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(customerIdTextfield.getText())
            );
            if (modifyCustomer) {
                System.out.println("Modify Customer Success");
                exit();
            } else {
                System.out.println("Failure");
                Main.createAlert(Alert.AlertType.ERROR, "Error modifying appointment. Please try again.");

            }
        }else{
            Main.createAlert(Alert.AlertType.ERROR, "You must fill out all fields.");

        }
    }

    /**
     * check if all fields are filled, returns false if not.
     * @return true if all fields are filled
     */
    private boolean selectionsFilled(){
        return !customerZipcodeTextfield.getText().isEmpty() && !customerNameTextfield.getText().isEmpty() && !customerAddressTextfield.getText().isEmpty() && !customerPhoneTextfield.getText().isEmpty() && !DivisionCombo.getSelectionModel().getSelectedItem().isEmpty();
    }

    /**
     * Creates an observableList of all countries and iterates through them, adding each name to a list which is
     * then used to set customerCountryCombo
     * @throws SQLException
     */
    private void fillCountry() throws SQLException {
        ObservableList<Countries> countries = CountryQueries.getCountriesObservableList();
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        for (Countries country :
                countries) {
            countryNames.add(country.getCountry());
            customerCountryCombo.setItems(countryNames);
        }
    }

    /**
     * Creates a list of all divisions and iterates through them adding each division name to a list
     * which is used to set the items of DivisionCombo
     */
    private void fillDivision(){
        ObservableList<FirstLevelDivisions> divisions = DivisionQueries.getDivisionsObservableList();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        for (FirstLevelDivisions division: divisions){
            divisionNames.add(division.getDivision());
            DivisionCombo.setItems(divisionNames);
        }
    }

    /**
     * Sets division combo based on the country selection, dynamically.
     */
    @FXML
    private void handleCountryAction(){
        String countryName = customerCountryCombo.getSelectionModel().getSelectedItem();
        int countryId = CountryQueries.getCountryIdByName(countryName);
        ObservableList<FirstLevelDivisions> divisions = DivisionQueries.getDivisionsObservableList();
        ObservableList<FirstLevelDivisions> filteredDivisions = FXCollections.observableArrayList();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        DivisionCombo.getItems().clear();
        for (FirstLevelDivisions division :
                divisions) {
            if (countryId == division.getCountryId()) {
                filteredDivisions.add(division);
            }
        }
        for (FirstLevelDivisions filteredDivision:filteredDivisions
        ) {
            divisionNames.add(filteredDivision.getDivision());
        }
        DivisionCombo.setItems(divisionNames);
    }

    /**
     * Sets all fields when modifying a customer using the users selection on the Customer Tableview in Menu.
     */
    public void makeSelections() {
        customerIdTextfield.setText(String.valueOf(customerSelection.getCustomerId()));
        customerCountryCombo.getSelectionModel().select(customerSelection.getCountry());
        customerAddressTextfield.setText(customerSelection.getAddress());
        customerZipcodeTextfield.setText(customerSelection.getPostal_code());
        customerPhoneTextfield.setText(customerSelection.getPhone());
        customerNameTextfield.setText(customerSelection.getCustomerName());
        DivisionCombo.getSelectionModel().select(customerSelection.getDivision());
    }

    /**
     * Receives the customer object that the user has selected in the tableview in Menu
     * @param customer
     */
    public static void receiveSelection(Customers customer){
        customerSelection = customer;
    }
    private static boolean action;

    /**
     * Sets a boolean to determine whether the call to this view is a new save, or existing save function.
     * @param input
     */
    public static void setAction(boolean input){
        action = input;
    }

    /**
     * Lambda used here to set the on action of the save button in a concise manner based on the
     * input from the last scene.
     *
     * Sets the on action of the save button determined by the button action previously taken to get to this view.
     * if modify, save existing, if add, save new.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!action){
            makeSelections();
        }else{ObservableList<Integer> customerIdList = FXCollections.observableArrayList();
            try {
                ObservableList<Customers> customers = CustomerQueries.getCustomersObservableList();
                for (Customers customer :
                        customers) {
                    customerIdList.add(customer.getCustomerId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int customerId = Collections.max(customerIdList)+1;
            customerIdTextfield.setText(String.valueOf(customerId));}

        saveButton.setOnAction(e->{
            if(!action){
                try{
                    saveExisting();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }else{
                try{
                    saveNew();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        try {
            fillCountry();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fillDivision();
        customerIdTextfield.setDisable(true);
    }
}
