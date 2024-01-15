package com.studentinfosys.studentinformationmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;


import java.net.URL;
import java.util.ResourceBundle;

public class AddStudentDialogController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField AddressField;
    @FXML
    private TextField GuardianField;
    @FXML
    private TextField PhnNumberField;
    @FXML
    private TextField IDField;

    @FXML
    private Button AddButton;

   @FXML
    ChoiceBox<Integer> gradeBox = new ChoiceBox<>();

    @FXML
    private VBox MainBox;


    private ObservableList<Student> data;

    private TableView<Student> studentTableView;


    public void setTableView(TableView<Student> studentTableView)
    {
        this.studentTableView = studentTableView;
    }


    public void setStudentTableView(ObservableList<Student> data)
    {
        this.data = data;
    }


    int selectedvalue = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Integer[] listOptions = {1, 2, 3, 4, 5, 6};
        gradeBox.getItems().addAll(listOptions);
        gradeBox.setOnAction(event -> {
            selectedvalue = gradeBox.getValue();
        });


    }

    @FXML
    private void addButtonClicked(ActionEvent event)
    {
        boolean InsertingToList;
        if (data != null) {


            InsertingToList = InsertNewData(IDField.getText(), nameField.getText(), AddressField.getText(), GuardianField.getText(), PhnNumberField.getText(), selectedvalue);
            if(InsertingToList)
            {
               RefreshTable();
            }

        }

    }





    private boolean InsertNewData(String student_Num, String student_Name, String student_Address, String student_Guardian, String student_PhnNumber, int selectedGrade)
    {
        boolean allCorrect;


        allCorrect = checkCredentials(student_Num, student_Name, student_Address, student_Guardian, student_PhnNumber, selectedGrade);



        if(allCorrect) {
            String url = "jdbc:mysql://localhost:3306/studentinformationsys";
            String sql = "INSERT INTO student(student_ID, student_NAME, student_ADDRESS,  student_GUARDIAN, student_PHONENUMBER, student_COURSE) VALUES (?, ?, ?, ?, ?, ?)";

            try ( Connection connect = DriverManager.getConnection(url, "root", "password"))
            {

                try( PreparedStatement statement = connect.prepareStatement(sql))
                {

                int convertStudent_ID = Integer.parseInt(student_Num);
                statement.setInt(1, convertStudent_ID);
                statement.setString(2, student_Name);
                statement.setString(3, student_Address);
                statement.setString(4, student_Guardian);
                statement.setString(5, student_PhnNumber);
                statement.setInt(6, selectedGrade);

                statement.execute();

                System.out.println("Data Inserted");
                }

            } catch (SQLException e) {
                System.out.println("DUPLICATEEEEEEE");
                showAlert("DUPLICATE KEY", "CANNOT PROCEED", "THE ID IS CONSIRED AS PRIMARY KEY IT CANNOT HAVE A DUPLICATE OR A SAME KEY ");
                return false;
            }
            catch (NumberFormatException e)
            {
                System.out.println("ERRORRRRRRRRR");
                showAlert("NON NUMERIC INPUT", "CANNOT PROCEED", "NON NUMERIC INPUT HAS FOUND PLEASE REMOVE AND RETRY");
                return false;
            }
            return true;
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("DETECTED AN EMPTY FIELD PLEASE FILL IN");
            alert.setTitle("EMPTY FIELD");
            alert.show();
            return false;
        }

    }

    private boolean checkCredentials(String student_Num, String student_Name, String student_Address, String student_Guardian, String student_PhnNumber, int selectGrade)
    {
        if(student_Num.isBlank() || student_Name.isBlank() || student_Address.isBlank() || student_Guardian.isBlank() ||student_PhnNumber.isBlank() || selectGrade == 0){
            return false;
        }
        return true;
    }


    private void showAlert(String HMessage, String title, String CMessage)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(CMessage);
        alert.setHeaderText(HMessage);
        alert.show();

    }


    private void RefreshTable()
    {
        data = fetchfromDatabase();
        studentTableView.setItems(data);
        Stage stage = (Stage) MainBox.getScene().getWindow();
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
                String stuCourse = Integer.toString(rs.getInt("student_COURSE"));
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


}
