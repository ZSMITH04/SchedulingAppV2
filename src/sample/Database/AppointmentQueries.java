package sample.Database;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sample.Controllers.JDBC;
import sample.Main;
import sample.Models.Appointments;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentQueries {
    static Connection conn = JDBC.getConnection();
    public static ObservableList<Appointments> getAppointmentsObservableList() throws SQLException {
        String ps = "SELECT * FROM APPOINTMENTS INNER JOIN contacts c on appointments.Contact_ID = c.Contact_ID";
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        JDBC.makePreparedStatement(ps, conn);
        JDBC.getPreparedStatement().execute();
        ResultSet rs = JDBC.getPreparedStatement().getResultSet();
        while(rs.next()){
            Appointments appointment = new Appointments(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start"),
                    rs.getTimestamp("End"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getString("Contact_Name"),
                    rs.getTimestamp("Create_Date"),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update"),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Contact_ID"));
        appointments.add(appointment);
        }
        return appointments;
    }
    public static boolean insertAppointment(String Title, String Description, String Location, String Type, Timestamp Start, Timestamp End, int Customer_ID,String Contact_Name, int User_ID) throws SQLException{
        String query = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID, Create_Date, Created_By) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        JDBC.makePreparedStatement(query,conn);
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setString(1, Title);
        ps.setString(2,Description);
        ps.setString(3,Location);
        ps.setString(4, Type);
        ps.setTimestamp(5, Start);
        ps.setTimestamp(6, End);
        ps.setInt(7, Customer_ID);
        ps.setInt(8, User_ID);
        ps.setInt(9, ContactQueries.getContactIdByName(Contact_Name));
        ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(11, String.valueOf(User_ID));
        ps.execute();
        return ps.getUpdateCount() > 0;

    }

    public static ObservableList<Appointments> getAppointmentsObservableListByMonth() {
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        try {
            String ps = "SELECT * FROM APPOINTMENTS INNER JOIN contacts c on appointments.Contact_ID = c.Contact_ID WHERE START BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL 30 DAY";
            JDBC.makePreparedStatement(ps, conn);
            JDBC.getPreparedStatement().execute();
            ResultSet rs = JDBC.getPreparedStatement().getResultSet();
            Appointments appointment;
            while (rs.next()) {
                appointment = new Appointments(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start"),
                        rs.getTimestamp("End"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getString("Contact_Name"),
                        rs.getTimestamp("Create_Date"),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update"),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Contact_ID"));
                appointmentsObservableList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsObservableList;
    }

    /**
     * Gets appointments observable list by week.
     *
     * @return the appointments observable list by week
     */
    public static ObservableList<Appointments> getAppointmentsObservableListByWeek() {
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        String ps = "SELECT * FROM APPOINTMENTS INNER JOIN contacts c on appointments.Contact_ID = c.Contact_ID WHERE START BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL 7 DAY";

        try {
            JDBC.makePreparedStatement(ps,conn);
            JDBC.getPreparedStatement().execute();
            ResultSet rs = JDBC.getPreparedStatement().getResultSet();
            Appointments appointment;
            while (rs.next()) {
                appointment = new Appointments(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start"),
                        rs.getTimestamp("End"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getString("Contact_Name"),
                        rs.getTimestamp("Create_Date"),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update"),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Contact_ID"));
                appointmentsObservableList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsObservableList;
    }

    /**
     * Delete appointment boolean.
     *
     * @param appointmentId the appointment id
     * @return the boolean
     */
    public static boolean deleteAppointment(int appointmentId) {
        try {
            String query = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
            JDBC.makePreparedStatement(query,conn);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setInt(1, appointmentId);
            ps.execute();
            if(ps.getUpdateCount() > 0){
                return true;
            }
        } catch (SQLException throwables) {
            Main.createAlert(Alert.AlertType.ERROR, "Error deleting appointment");
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean modifyAppointment(int appointmentId, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, String contactId, int userId) throws SQLException{

        String query = "UPDATE appointments SET Title = ?,Description = ?,Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?, Last_Updated_By = ?, Last_Update = ? WHERE Appointment_ID = ?";
            JDBC.makePreparedStatement(query,conn);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, ContactQueries.getContactIdByName(contactId));
            ps.setString(10, String.valueOf(userId));
            ps.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(12, appointmentId);
            ps.execute();
            return ps.getUpdateCount()>0;
    }



    /**
     * Get appointments by contact name observable list.
     *
     * @param contactName the contact name
     * @return the observable list
     */
    public static ObservableList<Appointments> getAppointmentsByContactName(String contactName){
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        try{

            String query = "SELECT * FROM appointments INNER JOIN contacts c on appointments.Contact_ID = c.Contact_ID WHERE Contact_Name = ? ORDER BY DATE(Start)";
            JDBC.makePreparedStatement(query,conn);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setString(1, contactName);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            Appointments appointment;
            while(rs.next()){
                appointment = new Appointments(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start"),
                        rs.getTimestamp("End"),
                        rs.getInt("Customer_ID"));
                appointmentsObservableList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsObservableList;
    }

    /**
     * Get appointment counts observable list.
     *
     * @return the observable list
     */
    public static ObservableList<Appointments> getAppointmentCounts(){
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        String query = "SELECT TYPE, MONTHNAME(START) as MONTH, year(Start) as YEAR, COUNT(*) as 'COUNT' FROM appointments GROUP BY MONTH(Start) and YEAR(START), type";
        try {
            JDBC.makePreparedStatement(query,conn);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            Appointments appointment;
            while (rs.next()) {
                appointment = new Appointments(

                        rs.getString("Month"),
                        rs.getInt("Year"),
                        rs.getString("Type"),
                        rs.getInt("COUNT"));
                appointmentsObservableList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsObservableList;
    }

    /**
     * Gets appointments observable list by customer name.
     *
     * @param customerName the customer name
     * @return the appointments observable list by customer name
     */
    public static ObservableList<Appointments> getAppointmentsObservableListByCustomerName(String customerName) {
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();

        String query = "SELECT Appointment_ID, Title,Description,Location,Type,Start,End,c.Customer_ID,User_ID,Contact_ID, c.Customer_Name FROM APPOINTMENTS INNER JOIN customers c on appointments.Customer_ID = c.Customer_ID WHERE Customer_Name = ?";

        try {
            JDBC.makePreparedStatement(query,conn);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.setString(1, customerName);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            Appointments appointment;
            while (rs.next()) {
                appointment = new Appointments(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start"),
                        rs.getTimestamp("End"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Customer_Name"));
                appointmentsObservableList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsObservableList;
    }
}
