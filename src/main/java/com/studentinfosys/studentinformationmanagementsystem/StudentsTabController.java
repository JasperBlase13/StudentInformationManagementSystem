package com.studentinfosys.studentinformationmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StudentsTabController extends MainWindowController implements Initializable {



    @FXML
   private Button Addbutton;

   @FXML
   private Button UpdateButton;

   @FXML
   private Button DeleteButton;


    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, String> IDColumn;
    @FXML
    private TableColumn<Student, String> NameColumn;
    @FXML
    private TableColumn<Student, String> AddressColumn;
    @FXML
    private TableColumn<Student, String> GuardianColumn;
    @FXML
    private TableColumn<Student, String> PhoneColumn;



    private  ObservableList<Student> data;

    ObservableList<Student> selectedDataUpdate = FXCollections.observableArrayList();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        IDColumn.setCellValueFactory(new PropertyValueFactory<>("student_ID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("student_Name"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("student_Address"));
        GuardianColumn.setCellValueFactory(new PropertyValueFactory<>("student_Guardian"));
        PhoneColumn.setCellValueFactory(new PropertyValueFactory<>("student_PhnNumber"));

        data = fetchfromDatabase();
        studentTableView.setItems(data);
        studentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        studentTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        studentTableView.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    selectedDataUpdate = studentTableView.getSelectionModel().getSelectedItems();
                }
            });
            return row;
        });



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


    //This is a method that when the add student is clicked in the students display, it will show up.
    @FXML
    private void AddButtonClicked(ActionEvent event) throws IOException

    {
        Scene mainScene = ((Node) event.getSource()).getScene();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStudentDialog.fxml"));
        Scene dialogScene = new Scene(loader.load());

        AddStudentDialogController ASDC1 = loader.getController();
        ASDC1.setStudentTableView(data);
        ASDC1.setTableView(studentTableView);



        Stage dialogStage = new Stage();
        dialogStage.setScene(dialogScene);
        dialogStage.setTitle("ADD STUDENT");
        dialogStage.setResizable(false);

        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(mainScene.getWindow());
        dialogStage.showAndWait();

        mainScene.getRoot().setDisable(false);

    }

    @FXML
    private void UpdateButtonClicked(ActionEvent event) throws IOException {

        if(selectedDataUpdate.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("YOU HAVE NOT SELECTED A ROW");
            alert.show();
        }
        else {

            int i = 0;
            while (i < selectedDataUpdate.size()) {

                Student newStudent = selectedDataUpdate.get(i);


                Scene mainScene = ((Node) event.getSource()).getScene();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateStudentDialog.fxml"));


                Scene dialogScene = new Scene(loader.load());
                Stage dialogStage = new Stage();
                dialogStage.setScene(dialogScene);
                dialogStage.setTitle("UPDATE STUDENT");
                dialogStage.setResizable(false);
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());


                UpdateStudentDialogController UPDC1 = loader.getController();
                UPDC1.setOriginalData(data);
                UPDC1.setTableview(studentTableView);
                UPDC1.setnewStudent(newStudent);
                UPDC1.openDialog();

                i++;


            }
        }

    }

    @FXML
    private void DeleteButtonPressed(ActionEvent event) throws SQLException {
        deleteProcess(studentTableView);
        RefreshTable();
    }

    private void deleteProcess(TableView<Student> studentTableView) throws SQLException {
        Student student = studentTableView.getSelectionModel().getSelectedItem();
        if (student != null) {
            deleteFromDatabase(student);
        }
    }

    private void deleteFromDatabase(Student student) throws SQLException {
        String url ="jdbc:mysql://localhost:3306/studentinformationsys";
        String sql = "DELETE FROM student WHERE student_ID = ?";
        try ( Connection connect = DriverManager.getConnection(url, "root", "password")) {

            try(PreparedStatement stm1 = connect.prepareStatement(sql))
            {
                int targetID = Integer.parseInt(student.getStudent_ID());
                stm1.setInt(1, targetID);
                stm1.executeUpdate();
            }
        }
    }

    private void RefreshTable() {
        data = fetchfromDatabase();
        studentTableView.setItems(data);
    }




}
