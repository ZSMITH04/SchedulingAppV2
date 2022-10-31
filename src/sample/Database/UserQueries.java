package sample.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controllers.JDBC;
import sample.Models.Users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * The type User queries.
 */
public class UserQueries {
    private static final Connection conn = JDBC.getConnection();

    /**
     * Gets users observable list.
     *
     * @return the users observable list
     */
    public static ObservableList<Users> getUsersObservableList() {
        ObservableList<Users> usersObservableList = FXCollections.observableArrayList();

        String ps = "SELECT * FROM users";

        try {
            JDBC.makePreparedStatement(ps,conn);
            JDBC.getPreparedStatement().execute();
            ResultSet rs = JDBC.getPreparedStatement().getResultSet();
            Users users;
            while (rs.next()) {
                users = new Users(
                        rs.getInt("User_ID"),
                        rs.getString("User_Name"),
                        rs.getString("Password"));
                usersObservableList.add(users);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usersObservableList;
    }
}
