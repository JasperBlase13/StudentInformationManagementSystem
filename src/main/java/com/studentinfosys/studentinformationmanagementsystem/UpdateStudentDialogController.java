package com.studentinfosys.studentinformationmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class UpdateStudentDialogController implements Initializable {

    @FXML
    private TextField IDTextField;

    @FXML
    private TextField NameTextField;
    @FXML
    private TextField AddressField;
    @FXML
    private TextField GuardianField;

    @FXML
    private TextField PhnNumberField;

    @FXML
    private VBox mainBox;



    @FXML
    private Button updateButton;


    private Student student;

    private ObservableList<Student> data;

    private TableView<Student> studentTableView;


    public void setnewStudent(Student student)
    {
        this.student = student;
    }

    public void setOriginalData(ObservableList<Student> data)
    {
        this.data = data;
    }

    public void setTableview(TableView<Student> studentTableView)
    {
        this.studentTableView = studentTableView;
    }

    public void openDialog() {
        IDTextField.setText(student.getStudent_ID());
        NameTextField.setText(student.getStudent_Name());
        AddressField.setText(student.getStudent_Address());
        GuardianField.setText(student.getStudent_Guardian());
        PhnNumberField.setText(student.getStudent_PhnNumber());

        Stage stage = (Stage) mainBox.getScene().getWindow();
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void UpdateButtonClicked(ActionEvent event)
    {

      UpdateDatabase();
      RefreshTable();

    }

    private void UpdateDatabase()
    {
        String url ="jdbc:mysql://localhost:3306/studentinformationsys";
        String sql = "UPDATE student SET student_ID = ?, student_NAME = ?, student_ADDRESS = ?, student_GUARDIAN = ?, student_PHONENUMBER = ? WHERE student_ID = ? ";
        try ( Connection connect = DriverManager.getConnection(url, "root", "password"))
        {

            try(PreparedStatement stm1 = connect.prepareStatement(sql))
            {
                int changeID = Integer.parseInt(IDTextField.getText());
                int recentID = Integer.parseInt(student.getStudent_ID());
                String name = NameTextField.getText();
                String address = AddressField.getText();
                String guardian = GuardianField.getText();
                String phoneNum = PhnNumberField.getText();

                stm1.setInt(1, changeID);
                stm1.setString(2, name);
                stm1.setString(3, address);
                stm1.setString(4, guardian);
                stm1.setString(5, phoneNum);
                stm1.setInt(6, recentID);

                stm1.execute();
                System.out.println("Data Updated");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void RefreshTable()
    {
        data = fetchfromDatabase();
        studentTableView.setItems(data);
        showAlert();
        Stage stage = (Stage) mainBox.getScene().getWindow();
        stage.close();
    }


    private ObservableList<Student> fetchfromDatabase() {


        ObservableList<Student> data =  FXCollections.observableArrayList();
        String url ="jdbc:mysql://localhost:3306/studentinformationsys";
        try
        {
            Connection connect = DriverManager.getConnection(url, "root", "password");
            Statement stamement = connect.createStatement();
            System.out.println("Connected");
            ResultSet rs = stamement.executeQuery("SELECT * FROM student ORDER BY student_ID");
            while(rs.next())
            {
                String stuID = Integer.toString(rs.getInt("student_ID"));
                String stuNname = rs.getString("student_NAME");
                String stuAdd = rs.getString("student_ADDRESS");
                String stuGua = rs.getString("student_GUARDIAN");
                String stuPhn = rs.getString("student_PHONENUMBER");
                String stuCourse = rs.getString("student_COURSE");

                Student student = new Student(stuID, stuNname, stuAdd, stuGua, stuPhn, stuCourse);
                data.add(student);
            }
            rs.close();
            stamement.close();
            connect.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;

    }

    private void showAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText("DATABASE UPDATED!");
        alert.showAndWait();
    }









}
