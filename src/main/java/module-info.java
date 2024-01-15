module com.studentinfosys.studentinformationmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.studentinfosys.studentinformationmanagementsystem to javafx.fxml;
    exports com.studentinfosys.studentinformationmanagementsystem;
}