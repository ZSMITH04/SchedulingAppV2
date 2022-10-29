package sample.Models;

public class Countries {
    public Countries(int country_id) {
        this.countryId=country_id;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    private int countryId;
    private String country;
}
