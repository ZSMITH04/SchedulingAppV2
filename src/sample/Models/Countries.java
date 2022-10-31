package sample.Models;

public class Countries {
    /**
     * Instantiates a new Countries.
     *
     * @param country_id the country id
     */
    public Countries(int country_id) {
        this.countryId=country_id;
    }

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets country id.
     *
     * @param countryId the country id
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Instantiates a new Countries.
     *
     * @param countryId the country id
     * @param country   the country
     */
    public Countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    private int countryId;
    private String country;
}
