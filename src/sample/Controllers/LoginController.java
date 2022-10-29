package sample.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import sample.Database.AppointmentQueries;
import sample.Main;
import sample.Models.Appointments;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController implements Initializable {

    @FXML
    private Button ExitButton;

    @FXML
    private Label LocationLabel;

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField PasswordTextField;

    @FXML
    private Text Title;

    @FXML
    private TextField UsernameTextField;


    private static final Connection conn = JDBC.getConnection();

    private final ResourceBundle rb = ResourceBundle.getBundle("Translations", Locale.getDefault());

    public static int currentUserId;
    /**
     * Creates a new printwriter that makes a new file stream, creating login_activity.txt if it doesn't exist, or appending to existing if it does.
     * Appends all login attempts including the username and timestamp of attempt to the log file.
     * @param userName the user name
     * @param success  the success
     * @throws IOException the io exception
     */
    public static void logActivity(String userName, String success) throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(("login_activity.txt"),true));
        pw.append("Username: ").append(userName).append(" attempted to login on ").append(String.valueOf(LocalDateTime.now())).append(TimeZone.getTimeZone(ZoneId.systemDefault()).toString()).append("Result: ").append(success).append("\n");
        pw.close();
    }

    /**
     * Login to application.
     * Queries the database for a username and password match based on user input, if not found, notifies the user and logs the attempt.
     * If successful, logs the successful attempt and continues into the main application. Alerts for appointments in the next 15 minutes.
     */
    public void loginToApplication(){

        try{
            String resultOf = "";
            String userID = UsernameTextField.getText().trim();
            String password = PasswordTextField.getText().trim();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM USERS WHERE User_Name = ?");
            ps.setString(1, userID);
            ResultSet result = ps.executeQuery();
            if(!result.next() || !result.getString("Password").equals(password)){
                Main.createAlert(Alert.AlertType.ERROR, rb.getString("loginFailed"));
                System.out.println("InvalidLogin");
                resultOf = "Failure";
                logActivity(UsernameTextField.getText().trim(), resultOf);
            }else {
                resultOf = "Success";
                logActivity(UsernameTextField.getText().trim(),resultOf);
                appointmentAlert();
                currentUserId = result.getInt("User_ID");
                Main.changeScene("views/menu.fxml");
                Main.closeScene(LoginButton);
                System.out.println("Login Success");
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
    public static Integer getCurrentUser(){
        return currentUserId;
    }
    /**
     * Exit.
     */
    public void exit(){
        System.exit(0);
    }

    private void appointmentAlert() throws SQLException {
        ObservableList<Appointments> appointments = AppointmentQueries.getAppointmentsObservableList();
        Appointments upcomingAppts = null;
        for (Appointments appointment :
                appointments) {
            if(appointment.getStartTime().before(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15))) && appointment.getStartTime().after(Timestamp.valueOf(LocalDateTime.now()))){
                upcomingAppts = appointment;
            }
        }
        if(upcomingAppts != null){
            Main.createAlert(Alert.AlertType.INFORMATION, rb.getString("upcomingAppointments1") + upcomingAppts.getAppointmentId() + rb.getString("upcomingAppointments2") + upcomingAppts.getStartTime());
        }else{Main.createAlert(Alert.AlertType.INFORMATION, rb.getString("noUpcomingAppointments"));}

    }
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        // Locale.setDefault(new Locale("fr"));
        ZoneId zoneID = ZoneId.systemDefault();
        LocationLabel.setText(String.valueOf(zoneID));
        ExitButton.setText(rb.getString("exit"));
        LoginButton.setText(rb.getString("login"));
        PasswordTextField.setPromptText(rb.getString("password"));
        UsernameTextField.setPromptText(rb.getString("username"));
        Title.setText(rb.getString("title"));
    }
}
