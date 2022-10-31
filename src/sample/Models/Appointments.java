package sample.Models;

import java.sql.Timestamp;

public class Appointments {
    /**
     * Instantiates a new Appointments.
     *
     * @param appointment_id  the appointment id
     * @param title           the title
     * @param description     the description
     * @param location        the location
     * @param type            the type
     * @param start           the start
     * @param end             the end
     * @param customer_id     the customer id
     * @param user_id         the user id
     * @param contact_name    the contact name
     * @param create_date     the create date
     * @param created_by      the created by
     * @param last_update     the last update
     * @param last_updated_by the last updated by
     * @param contact_id      the contact id
     */
    public Appointments(int appointment_id, String title, String description, String location, String type, Timestamp start, Timestamp end, int customer_id, int user_id, String contact_name, Timestamp create_date, String created_by, Timestamp last_update, String last_updated_by, int contact_id) {
        this.appointmentId = appointment_id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = start;
        this.endTime = end;
        this.customerId = customer_id;
        this.userId = user_id;
        this.contactName = contact_name;
        this.createDate = create_date;
        this.createdBy = created_by;
        this.lastUpdate = last_update;
        this.lastUpdatedBy = last_updated_by;
        this.contactId = contact_id;
    }

    /**
     * Instantiates a new Appointments.
     *
     * @param appointmentId the appointment id
     * @param valueOf       the value of
     * @param valueOf1      the value of 1
     */
    public Appointments(int appointmentId, Timestamp valueOf, Timestamp valueOf1) {
        this.appointmentId=appointmentId;
        this.startTime=valueOf;
        this.endTime=valueOf1;
    }

    /**
     * Instantiates a new Appointments.
     *
     * @param appointment_id the appointment id
     * @param title          the title
     * @param description    the description
     * @param type           the type
     * @param start          the start
     * @param end            the end
     * @param customer_id    the customer id
     */
    public Appointments(int appointment_id, String title, String description, String type, Timestamp start, Timestamp end, int customer_id) {
    this.appointmentId=appointment_id;
    this.title=title;
    this.description=description;
    this.type=type;
    this.startTime=start;
    this.endTime=end;
    this.customerId=customer_id;
    }

    /**
     * Instantiates a new Appointments.
     *
     * @param month the month
     * @param year  the year
     * @param type  the type
     * @param count the count
     */
    public Appointments(String month, int year, String type, int count) {
        this.month=month;
        this.year=year;
        this.type=type;
        this.total=count;
    }

    /**
     * Instantiates a new Appointments.
     *
     * @param appointment_id the appointment id
     * @param title          the title
     * @param description    the description
     * @param location       the location
     * @param type           the type
     * @param start          the start
     * @param end            the end
     * @param customer_id    the customer id
     * @param user_id        the user id
     * @param contact_id     the contact id
     * @param customer_name  the customer name
     */
    public Appointments(int appointment_id, String title, String description, String location, String type, Timestamp start, Timestamp end, int customer_id, int user_id, int contact_id, String customer_name) {
    this.appointmentId=appointment_id;
    this.title=title;
    this.description=description;
    this.location=location;
    this.type=type;
    this.startTime=start;
    this.endTime=end;
    this.customerId=customer_id;
    this.userId=user_id;
    this.contactId=contact_id;
    this.customerName=customer_name;
    }


    /**
     * Gets appointment id.
     *
     * @return the appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets appointment id.
     *
     * @param appointmentId the appointment id
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets contact id.
     *
     * @param contactId the contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets contact name.
     *
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets contact name.
     *
     * @param contactName the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets total.
     *
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets total.
     *
     * @param total the total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Gets month.
     *
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets month.
     *
     * @param month the month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets last update.
     *
     * @return the last update
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets last update.
     *
     * @param lastUpdate the last update
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets last updated by.
     *
     * @return the last updated by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets last updated by.
     *
     * @param lastUpdatedBy the last updated by
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Appointments(int appointmentId, String title, String description, String location, String type, Timestamp startTime, Timestamp endTime, int customerId, int userId, int contactId, String contactName, int total, String month, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
        this.total = total;
        this.month = month;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp startTime;
    private Timestamp endTime;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;
    private int total;
    private String month;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private String customerName;

    /**
     * Gets customer name.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets customer name.
     *
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets year.
     *
     * @param year the year
     */
    public void setYear(int year) {
        this.year = year;
    }

    private int year;

}
