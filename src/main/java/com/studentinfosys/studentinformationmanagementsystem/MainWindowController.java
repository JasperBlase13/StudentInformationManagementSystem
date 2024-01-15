package com.studentinfosys.studentinformationmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {


    @FXML
    private Pane DashboardPane;


    @FXML
    private Pane StudentPane;

    @FXML
    private Pane CoursePane;

    @FXML
    public BorderPane MainWindowPane;

    @FXML
    private VBox DashBoardVBox;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        DashboardPane.setOnMouseEntered(
                event -> {
                    DashboardPane.setStyle("-fx-background-color: lightblue;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 0 0 2 0");
                }
        );
        DashboardPane.setOnMouseExited(
                event -> {
                    DashboardPane.setStyle("-fx-background-color: #3559E0;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 0 0 2 0");
                }
        );

        StudentPane.setOnMouseEntered(
                event -> {
                    StudentPane.setStyle("-fx-background-color: lightblue;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 0 0 2 0");
                }
        );
        StudentPane.setOnMouseExited(
                event -> {
                    StudentPane.setStyle("-fx-background-color: #3559E0;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 0 0 2 0");
                }
        );

        CoursePane.setOnMouseEntered(
                event -> {
                    CoursePane.setStyle("-fx-background-color: lightblue;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 0 0 2 0");
                }
        );
        CoursePane.setOnMouseExited(
                event -> {
                    CoursePane.setStyle("-fx-background-color: #3559E0;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 0 0 2 0");
                }
        );





    }

    @FXML
    private void DashBoardPaneClicked(MouseEvent event)
    {
        MainWindowPane.setCenter(DashBoardVBox);
        DashboardPane.setStyle("-fx-background-color: lightblue;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 0 0 2 0");
        StudentPane.setStyle("-fx-background-color: #3559E0;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 0 0 2 0");
        CoursePane.setStyle("-fx-background-color: #3559E0;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 0 0 2 0");
    }

    @FXML
    private void StudentPaneClicked(MouseEvent event) throws IOException {
        VBox studentWindow = FXMLLoader.load(getClass().getResource("StudentsTab.fxml"));
        MainWindowPane.setCenter(studentWindow);


        DashboardPane.setStyle("-fx-background-color: #3559E0;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 0 0 2 0");
        StudentPane.setStyle("-fx-background-color: lightblue;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 0 0 2 0");
        CoursePane.setStyle("-fx-background-color: #3559E0;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 0 0 2 0");


    }

    @FXML
    private void CourseTabCLicked(MouseEvent event) throws  IOException
    {
        VBox coursesTab = FXMLLoader.load(getClass().getResource("CoursesTab.fxml"));
        MainWindowPane.setCenter(coursesTab);

        CoursePane.setStyle("-fx-background-color: lightblue;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 0 0 2 0");
        DashboardPane.setStyle("-fx-background-color: #3559E0;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 0 0 2 0");

        StudentPane.setStyle("-fx-background-color: #3559E0;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 0 0 2 0");
    }
}
