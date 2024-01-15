package com.studentinfosys.studentinformationmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CoursesTabController implements Initializable {

    @FXML
    private VBox mainBox;

    @FXML
    Label LabelTitle;

    @FXML
    private TableView<EnrolledStudent> MainTable;

    @FXML
    private TableColumn<EnrolledStudent, Integer> IDColumn;

    @FXML
    private TableColumn<EnrolledStudent, String> NameColumn;

    ObservableList<EnrolledStudent> data;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LabelTitle.setText("PICK A GRADE");
        MainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("student_name"));





    }

    private ObservableList<EnrolledStudent> fetchfromDatabase(int grade) throws SQLException {

        ObservableList<EnrolledStudent> data = FXCollections.observableArrayList();
        String url ="jdbc:mysql://localhost:3306/studentinformationsys";

        try
        {

            Connection connect = DriverManager.getConnection(url, "root", "password");
            String sql0 = "SELECT student_ID, student_NAME FROM student WHERE student_COURSE = ? ORDER BY student_ID";
            PreparedStatement stm1 = connect.prepareStatement(sql0);
            stm1.setInt(1, grade);
            ResultSet rs = stm1.executeQuery();
            while(rs.next())
            {
                int student_ID = rs.getInt("student_ID");
                String student_NAME = rs.getString("student_NAME");
                EnrolledStudent ES = new EnrolledStudent(student_ID, student_NAME);
                data.add(ES);
            }

            stm1.close();
            rs.close();


        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return data;
    }

    @FXML
    private void Grade1ButtonClicked(ActionEvent event) throws SQLException {
        setTableAndData(1);

    }
    @FXML
    private void Grade2ButtonClicked(ActionEvent event) throws SQLException {
        setTableAndData(2);
    }

    @FXML
    private void Grade3ButtonClicked(ActionEvent event) throws SQLException {
        setTableAndData(3);
    }

    @FXML
    private void Grade4ButtonClicked(ActionEvent event) throws SQLException {
        setTableAndData(4);
    }

    @FXML
    private void Grade5ButtonClicked(ActionEvent event) throws SQLException
    {
        setTableAndData(5);
    }

    @FXML
    private void Grade6ButtonClicked(ActionEvent event) throws SQLException
    {
        setTableAndData(6);
    }


    private void setTableAndData(int grade) throws SQLException {
        LabelTitle.setText("GRADE " + grade);
        data = fetchfromDatabase(grade);
        MainTable.setItems(null);
        MainTable.setItems(data);
    }

}
