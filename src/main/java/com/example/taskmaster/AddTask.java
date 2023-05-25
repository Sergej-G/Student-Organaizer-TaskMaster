package com.example.taskmaster;

import DAO.TaskDAO.TaskDAO;
import DAO.TaskDAO.TaskDAOPos;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Task;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Класс окна добовления задачи
 */
public class AddTask implements Initializable {
    @FXML
    public DatePicker addWindowDatapicker;
    @FXML
    public TextField addWindowNameField;
    @FXML
    public TextArea addWindowDescriptionField;
    @FXML
    public Button addWindowButton;
    public TableView table;
    TaskDAO taskDAO;

    String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    private HelloController helloController = new HelloController();

    public void setParent (HelloController helloController){
        this.helloController = helloController;
    }

    /**
     * Метод добавления новой задачи
     * @param event
     */
    public void addTaskWindow(ActionEvent event) {
        String name2 = addWindowNameField.getText();
        String description2 = addWindowDescriptionField.getText();
        String date2 = addWindowDatapicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        if (addWindowDatapicker.getValue() == null || addWindowNameField.getText() == "") {
            showAlertWithHeaderText();
        }
        else if(date2.compareTo(dateNow) < 0){
            pickDateAlert();
        }
        else if(Objects.equals(description2, "")){
            try{
                String description3 = "Без описания";
                taskDAO = new TaskDAOPos();

                taskDAO.addTask(new Task(0, name2, description3, date2));
                table.setItems(FXCollections.observableList(taskDAO.getAllTasks()));

            }catch (Exception e){
                e.getMessage();
            }
        }
        else{
            try{
                taskDAO = new TaskDAOPos();

                taskDAO.addTask(new Task(0, name2, description2, date2));
                table.setItems(FXCollections.observableList(taskDAO.getAllTasks()));

            }catch (Exception e){
                e.getMessage();
            }
        }
    }

    /**
     * Метод закрытия окна по нажатию на кнопку "Отмена"
     * @param actionEvent
     */
    public void close(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Метод выводящий пользователю сообщение о проблемме
     */
    private void showAlertWithHeaderText() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание!");
        alert.setHeaderText("Для добавления задачи обязательно заполните поле названия и укажите дедлайн!");

        alert.showAndWait();
    }

    /**
     * Метод выводящий пользователю сообщение о проблемме
     */
    private void pickDateAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Что-то не так...");
        alert.setHeaderText("Машину времени ещё не изобрели!!!" +
                "\nНазначить дедлайн прошедшим числом невозможно!!!");

        alert.showAndWait();
    }

    /**
     * Инициализирует форму
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addWindowDatapicker.setValue(LocalDate.now());
    }
}
