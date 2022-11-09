Scheduling Application
Zak Smith
Zsmit75@wgu.edu
IntelliJ IDEA 2021.2.4 (Ultimate Edition)
Java SE 17.0.5
OpenJFX 11.0
MySQL 8.0.25

USER GUIDE:

This is a customer and appointment scheduling application. Each user will have a login ID and password. Use your login ID and password to gain access to the application.
The login screen supports multiple languages, currently English and French, with ability to add more.

Upon login, user will be greeted by the main menu view, where you can view/modify/delete all appointments, view appointments upcoming in the next week or month, all customers, as well as generate all reports.
If there are any appointments within 15 minutes of the users login, an alert will appear notifying the user of the time, Appointment ID, and date.

Operations to the Customer and Appointment database can be completed using the "ADD", "MODIFY", and "DELETE" for their respective operations.
The first is an appointment report by month and type. A count for all appointments of each type will appear, sorted by month.
The second is an appointment report by contact ID. A dropdown menu will appear with each Contacts ID, and upon selection the table will populate with the applicable appointments.
The third is an appointment report by customer name. A dropdown menu will appear with each Customer Name, and upon selection the table will populate with the applicable appointments.

User can exit the application with the logout button.

Both successful and failed user activity is logged to login_activity.txt.