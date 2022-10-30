package sample.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controllers.JDBC;
import sample.Controllers.LoginController;
import sample.Models.Customers;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;


public class CustomerQueries {
    /**
     * Gets customers observable list.
     *
     * @return the customers observable list
     */

    private static final Connection conn = JDBC.getConnection();
    public static ObservableList<Customers> getCustomersObservableList() throws SQLException {
        ObservableList<Customers> customersObservableList = FXCollections.observableArrayList();
        String query = "SELECT * FROM CUSTOMERS INNER JOIN first_level_divisions fld on customers.Division_ID = fld.Division_ID INNER JOIN countries c on fld.COUNTRY_ID = c.Country_ID ";
        try {
            JDBC.makePreparedStatement(query, conn);
            PreparedStatement ps =JDBC.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            Customers customer;
            while (rs.next()) {
                customer = new Customers(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getString("Division"),
                        rs.getString("Country"),
                        rs.getInt("Division_ID"),
                        rs.getTimestamp("Create_Date"),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update"),
                        rs.getString("Last_Updated_By")
                );
                customersObservableList.add(customer);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customersObservableList;
    }

    /**
     * Delete customer boolean.
     *
     * @param customerId the customer id
     * @return the boolean
     */
    public static boolean deleteCustomer(int customerId) {
        try {
            String query = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
            JDBC.makePreparedStatement(query,conn);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setInt(1, customerId);
            ps.execute();
            if(ps.getUpdateCount()>0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }return false;
    }

    /**
     * Insert customer boolean.
     *
     * @param customerName the customer name
     * @param address      the address
     * @param postalCode   the postal code
     * @param phone        the phone
     * @param division     the division
     * @return the boolean
     */
    public static boolean insertCustomer(String customerName, String address, String postalCode, String phone, String division){
        try{
            String query = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Create_Date, Created_By) VALUES (?,?,?,?,?,?,?)";
            JDBC.makePreparedStatement(query,conn);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setString(1, customerName);
            ps.setString(2,address);
            ps.setString(3, postalCode);
            ps.setString(4,phone);
            ps.setInt(5, DivisionQueries.getDivisionIdByName(division));
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(7, String.valueOf(LoginController.getCurrentUser()));
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Modify customer boolean.
     *
     * @param customerName the customer name
     * @param address      the address
     * @param postalCode   the postal code
     * @param phone        the phone
     * @param division     the division
     * @param customerId   the customer id
     * @return the boolean
     */
    public static boolean modifyCustomer(String customerName, String address, String postalCode, String phone, String division, int customerId){
        try{
            String query = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?, Last_Update = ?, Last_Updated_By = ? WHERE Customer_ID = ?";
            JDBC.makePreparedStatement(query, conn);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setString(1, customerName);
            ps.setString(2,address);
            ps.setString(3, postalCode);
            ps.setString(4,phone);
            ps.setInt(5, DivisionQueries.getDivisionIdByName(division));
            ps.setTimestamp(6,Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(7, String.valueOf(LoginController.getCurrentUser()));
            ps.setInt(8,customerId);
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
