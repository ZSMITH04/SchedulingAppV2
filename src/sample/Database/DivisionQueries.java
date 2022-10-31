package sample.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controllers.JDBC;
import sample.Models.FirstLevelDivisions;

import java.sql.*;

/**
 * The type Division queries.
 */
public class DivisionQueries {
    private static final Connection conn = JDBC.getConnection();

    /**
     * Get divisions observable list observable list.
     *
     * @return the observable list
     */
    public static ObservableList<FirstLevelDivisions> getDivisionsObservableList(){
        ObservableList<FirstLevelDivisions> divisionsObservableList = FXCollections.observableArrayList();
        String query = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        try{
            JDBC.makePreparedStatement(query,conn);
            PreparedStatement ps =JDBC.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            FirstLevelDivisions division;
            while (rs.next()) {
                division = new FirstLevelDivisions(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getInt("COUNTRY_ID"));
                divisionsObservableList.add(division);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return divisionsObservableList;
    }

    /**
     * Get division id by name integer.
     *
     * @param division the division
     * @return the integer
     */
    public static Integer getDivisionIdByName(String division) {
        String query = "SELECT * FROM first_level_divisions WHERE Division = ?";
        int divisionId;
        try {
            JDBC.makePreparedStatement(query, conn);
            JDBC.getPreparedStatement().setString(1, division);
            JDBC.getPreparedStatement().execute();
            ResultSet rs = JDBC.getPreparedStatement().getResultSet();
            if(rs.next()){
                divisionId=rs.getInt("Division_ID");
                return divisionId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
