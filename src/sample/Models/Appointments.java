package sample.Models;

import java.sql.Timestamp;

public class Appointments {
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

    public Appointments(int appointmentId, Timestamp valueOf, Timestamp valueOf1) {
        this.appointmentId=appointmentId;
        this.startTime=valueOf;
        this.endTime=valueOf1;
    }

    public Appointments(int appointment_id, String title, String description, String type, Timestamp start, Timestamp end, int customer_id) {
    this.appointmentId=appointment_id;
    this.title=title;
    this.description=description;
    this.type=type;
    this.startTime=start;
    this.endTime=end;
    this.customerId=customer_id;
    }

    public Appointments(String month, int year, String type, int count) {
        this.month=month;
        this.year=year;
        this.type=type;
        this.total=count;
    }

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


    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private int year;

}
