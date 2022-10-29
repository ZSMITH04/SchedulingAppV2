package sample.Models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FirstLevelDivisions {
    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    private int divisionId;
    private String division;
    private LocalDateTime createDate;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryId;

    public FirstLevelDivisions(int division_id, String division, int country_id) {
        this.divisionId=division_id;
        this.division=division;
        this.countryId=country_id;
    }
}
