package sample.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controllers.JDBC;
import sample.Models.Countries;

import java.sql.*;

public class CountryQueries {
    private static final Connection conn = JDBC.getConnection();
    /**
     * Get countries observable list observable list.
     *
     * @return the observable list
     */
    public static ObservableList<Countries> getCountriesObservableList() {
        ObservableList<Countries> countriesObservableList = FXCollections.observableArrayList();


        try{
            String query = "SELECT * FROM countries";
            JDBC.makePreparedStatement(query,conn);
            PreparedStatement ps = JDBC.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            Countries country;
            while (rs.next()) {
                country = new Countries(
                        rs.getInt("Country_ID"),
                        rs.getString("Country"));
                countriesObservableList.add(country);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countriesObservableList;
    }

    /**
     * Get country id by name integer.
     *
     * @param country the country
     * @return the integer
     */
    public static Integer getCountryIdByName(String country){



        try {
            String query = "SELECT * FROM countries WHERE Country = ?";
            JDBC.makePreparedStatement(query,conn);
            PreparedStatement ps =JDBC.getPreparedStatement();
            ps.setString(1, country);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            int countryId;
            if(rs.next()){
                countryId = (rs.getInt("Country_ID"));
                return countryId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
