package com.example.taskmaster;

import DAO.DescriptionSubtaskDAO.DescriptionSubtaskDAO;
import DAO.DescriptionSubtaskDAO.DescriptionSubtaskDAOPos;
import DAO.SubtaskDAO.SubtaskDAO;
import DAO.SubtaskDAO.TaskDAOPosSubtask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.DescriptionSubtask;
import model.Subtask;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Класс окна детализации задачи
 */
public class DetailsTask implements Initializable {
    public TextArea updateSubtaskDescr;
    public TextField updateSubtaskName;
    Image image = new Image("D:\\User\\Desktop\\Программная инженерия\\Органайзер\\TaskMaster\\src\\main\\resources\\icon.png");

    @FXML
    public TextField detailsName;
    @FXML
    public TextArea detailsDescription;
    @FXML
    public Button addDetails;
    @FXML
    public Button closeDetails;
    @FXML
    public TableView tableDetails;
    @FXML
    public TableView tableDescription;

    private AddDescriptionSubtask addDescriptionSubtask;

    private ObservableList<Subtask> fxlistSubtask;

    private ObservableList<AddDescriptionSubtask> fxlistAddDescriptionSubtask;
    SubtaskDAO taskDAODetails;

    DescriptionSubtaskDAO descriptionSubtaskDAO;

    Integer customId;
    private HelloController helloController = new HelloController();

    public Integer getCustomId() {
        return customId;
    }

    public void setCustomId(Integer customId) {
        this.customId = customId;
    }

    /**
     * Метод setParent(HelloController helloController) устанавливает контроллер HelloController как родительский контроллер этого контроллера.
     * @param helloController
     */
    public void setParent (HelloController helloController){
        this.helloController = helloController;
    }

    /**
     * Метод windowAddDescriptionSubtask(ActionEvent event) открывает форму добавления описания в виде пунктов для выбранной подзадачи.
     * Если в таблице выбрана подзадача, то открывается новое окно description-subtask.fxml, которое содержит форму для заполнения детализации.
     * Если подзадача не выбрана, то выводится сообщение об ошибке.
     * @param event
     * @throws IOException
     */
    public void windowAddDescriptionSubtask(ActionEvent event) throws IOException {
        if (tableDetails.getSelectionModel().getSelectedItem() != null){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("description-subtask.fxml"));

        Stage primaryStage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 1010, 535);
        primaryStage.setTitle("Описание подзадач");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(image);
        primaryStage.show();

        addDescriptionSubtask = fxmlLoader.getController();
        addDescriptionSubtask.setParent(this);

        Subtask subtask = (Subtask) tableDetails.getSelectionModel().getSelectedItem();
        addDescriptionSubtask.setCustomIdSubtask(subtask.getId());
        addDescriptionSubtask.outputDescriptionSubtask();
        }
        else {
            alertPicItem();
        }
    }

    /**
     * Метод addDetails(ActionEvent event) добавляет подзадачу для выбранной задачи в базу данных.
     * Если пользователь не ввел название подзадачи, то выводится сообщение об ошибке.
     * Если пользователь не ввел описание, то значение по умолчанию — «Без описания».
     * Если пользователь ввел название и описание подзадачи, то оба значения добавляются в базу данных.
     * @param event
     */
    public void addDetails(ActionEvent event) {
        String nameDetails = detailsName.getText();
        String descriptionDetails = detailsDescription.getText();

        if (Objects.equals(detailsName.getText(), "")) {
            showAlertWithHeaderText();
        }
        else if(Objects.equals(descriptionDetails, "")){
            try{
                String descriptionDetails2 = "Без описания";
                taskDAODetails = new TaskDAOPosSubtask();

                taskDAODetails.addSubtask(new Subtask(0, customId, nameDetails, descriptionDetails2));
                tableDetails.setItems(FXCollections.observableList(taskDAODetails.getAllSubtasks(customId)));

            }catch (Exception e){
                e.getMessage();
            }
        }
        else{
            try{
                taskDAODetails = new TaskDAOPosSubtask();

                taskDAODetails.addSubtask(new Subtask(0, customId, nameDetails, descriptionDetails));
                tableDetails.setItems(FXCollections.observableList(taskDAODetails.getAllSubtasks(customId)));

            }catch (Exception e){
                e.getMessage();
            }
        }
    }

    /**
     * Метод outputDetails() выводит список подзадач для выбранной задачи.
     */
    @FXML
    protected void outputDetails() {
        try{
            taskDAODetails = new TaskDAOPosSubtask();
            tableDetails.setItems(FXCollections.observableList(taskDAODetails.getAllSubtasks(customId)));
        }catch (Exception e){
            e.getMessage();
        }
    }

    /**
     * Метод closeDetails(ActionEvent actionEvent) закрывает окно детализации после сохранения деталей задачи.
     * @param actionEvent
     */
    public void closeDetails(ActionEvent actionEvent) {
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
        alert.setHeaderText("Для добавления подзадачи обязательно укажите её название!");

        alert.showAndWait();
    }

    /**
     * Метод выводящий пользователю сообщение о проблемме
     */
    private void alertPicItemForUpdate() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание!");
        alert.setHeaderText("Для редактирования подзадачи необходимо выбрать её из списка подзадач и указать новое название!");

        alert.showAndWait();
    }

    /**
     * Метод выводящий пользователю сообщение о проблемме
     */
    private void alertPicItem() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание!");
        alert.setHeaderText("Для описания подзадачи необходимо выбрать её из списка!");

        alert.showAndWait();
    }

    /**
     * Создает таблицу подзадач.
     * Он создает 4 столбца таблицы: «Номер», «id задачи», «Название» и «Описание».
     * Значения для каждого столбца берутся из свойства объекта Subtask.
     */
    private void createTableSubtask() {

        TableColumn Col0 = new TableColumn("Номер");//отображаемый заголовок столбца
        Col0.setMinWidth(15);//ширина
        Col0.setCellValueFactory(new PropertyValueFactory<Subtask, Integer>("id"));
        Col0.setVisible(false);

        TableColumn Col1 = new TableColumn("id задачи");//отображаемый заголовок столбца
        Col1.setMinWidth(15);//ширина
        Col1.setCellValueFactory(new PropertyValueFactory<Subtask, Integer>("task_id"));
        Col1.setVisible(false);

        TableColumn Col2 = new TableColumn("Название");//отображаемый заголовок столбца
        Col2.setMinWidth(120);//ширина
        Col2.setCellValueFactory(new PropertyValueFactory<Subtask, String>("nameSubtask"));

        TableColumn Col3 = new TableColumn("Описание");//отображаемый заголовок столбца
        Col3.setMinWidth(400);//ширина
        Col3.setCellValueFactory(new PropertyValueFactory<Subtask, String>("descriptionSubtask"));


        tableDetails.getColumns().clear();// если поля созданы в Builder, их удалить
        tableDetails.getColumns().addAll(Col0, Col1, Col2,Col3);// добавление столбцов
        tableDetails.setItems(fxlistSubtask);// загрузка списка объектов Task из fx_ListTask


        Col0.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Subtask, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Subtask, Integer> t) {
                ((Subtask) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue()); }
        });

        Col1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Subtask, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Subtask, Integer> t) {
                ((Subtask) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue()); }
        });

        Col2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Subtask, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Subtask, Integer> t) {
                ((Subtask) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue()); }
        });

        Col3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Subtask, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Subtask, Integer> t) {
                ((Subtask) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue()); }
        });

    }

    /**
     * Cоздает таблицу пунктов выбранной подзадачи.
     * Он создает 4 столбца таблицы: «Номер», «id подзадачи», «Название» и «Описание».
     * Значения для каждого столбца берутся из свойства объекта DescriptionSubtask.
     */
    private void createTableDescriptionSubtask2() {

        TableColumn Col0 = new TableColumn("Номер");//отображаемый заголовок столбца
        Col0.setMinWidth(15);//ширина
        Col0.setCellValueFactory(new PropertyValueFactory<DescriptionSubtask, Integer>("id"));
        Col0.setVisible(false);

        TableColumn Col1 = new TableColumn("id подзадачи");//отображаемый заголовок столбца
        Col1.setMinWidth(15);//ширина
        Col1.setCellValueFactory(new PropertyValueFactory<DescriptionSubtask, Integer>("subtask_id"));
        Col1.setVisible(false);

        TableColumn Col2 = new TableColumn("Название");//отображаемый заголовок столбца
        Col2.setMinWidth(120);//ширина
        Col2.setCellValueFactory(new PropertyValueFactory<DescriptionSubtask, String>("nameDescriptionSubtask"));

        TableColumn Col3 = new TableColumn("Описание");//отображаемый заголовок столбца
        Col3.setMinWidth(400);//ширина
        Col3.setCellValueFactory(new PropertyValueFactory<DescriptionSubtask, String>("opisanieDescriptionSubtask"));


        tableDescription.getColumns().clear();// если поля созданы в Builder, их удалить
        tableDescription.getColumns().addAll(Col0, Col1, Col2,Col3);// добавление столбцов
        tableDescription.setItems(fxlistAddDescriptionSubtask);// загрузка списка объектов Task из fx_ListTask


        Col0.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<DescriptionSubtask, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<DescriptionSubtask, Integer> t) {
                ((DescriptionSubtask) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue()); }
        });

        Col1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<DescriptionSubtask, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<DescriptionSubtask, Integer> t) {
                ((DescriptionSubtask) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue()); }
        });

        Col2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<DescriptionSubtask, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<DescriptionSubtask, Integer> t) {
                ((DescriptionSubtask) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue()); }
        });

        Col3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<DescriptionSubtask, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<DescriptionSubtask, Integer> t) {
                ((DescriptionSubtask) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue()); }
        });

    }

    /**
     * Метод updateSubtask() отвечает за обновление данных о выбранной подзадаче.
     * Он получает выбранную подзадачу и новые значения названия и описания.
     * Если название не заполнено или подзадача не выбрана, метод вызывает метод alertPicItemForUpdate(), который выводит сообщение об ошибке.
     * Если описание не заполнено, метод обновляет подзадачу без описания, иначе обновляет подзадачу с новыми значениями названия и описания.
     */
    @FXML
    protected void updateSubtask() {
        Subtask subtask = (Subtask) tableDetails.getSelectionModel().getSelectedItem();
        String nameDetails2 = updateSubtaskName.getText();
        String descriptionDetails2 = updateSubtaskDescr.getText();

        if (Objects.equals(updateSubtaskName.getText(), "") || tableDetails.getSelectionModel().getSelectedItem() == null) {
            alertPicItemForUpdate();
        }
        else if(Objects.equals(descriptionDetails2, "")){
            try{
                String descriptionDetails = "Без описания";
                taskDAODetails = new TaskDAOPosSubtask();

                taskDAODetails.updateSubtask(new Subtask(subtask.getId(), customId, nameDetails2, descriptionDetails));
                tableDetails.setItems(FXCollections.observableList(taskDAODetails.getAllSubtasks(customId)));

            }catch (Exception e){
                e.getMessage();
            }
        }
        else{
            try{
                taskDAODetails = new TaskDAOPosSubtask();

                taskDAODetails.updateSubtask(new Subtask(subtask.getId(), customId, nameDetails2, descriptionDetails2));
                tableDetails.setItems(FXCollections.observableList(taskDAODetails.getAllSubtasks(customId)));

            }catch (Exception e){
                e.getMessage();
            }
        }
    }


    /**
     * Метод deleteSubtask() удаляет выбранную подзадачу и обновляет таблицы подзадач и их описаний.
     */
    @FXML
    protected void deleteSubtask() {
        try{
            Subtask subtask = (Subtask) tableDetails.getSelectionModel().getSelectedItem();

            taskDAODetails = new TaskDAOPosSubtask();
            taskDAODetails.deleteSubtask(subtask.getId());

            tableDetails.setItems(FXCollections.observableList(taskDAODetails.getAllSubtasks(customId)));
            tableDescription.setItems(FXCollections.observableList(descriptionSubtaskDAO.getAllDescriptionSubtask(subtask.getId())));
        }catch (Exception e){
            e.getMessage();
        }
    }

    /**
     * Инициализирует форму.
     * Вызывает методы createTableSubtask() и createTableDescriptionSubtask2(), которые создают таблицы, и устанавливают при нажатии на ячейку таблицы подзадач, отображение их описаний в таблице с описаниями.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getCustomId());
        createTableSubtask();
        createTableDescriptionSubtask2();

        tableDetails.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Subtask subtask = (Subtask) tableDetails.getSelectionModel().getSelectedItem();
                if (tableDetails.getSelectionModel().getSelectedItem() != null) {
                    System.out.println(subtask.getId());
                    descriptionSubtaskDAO = new DescriptionSubtaskDAOPos();
                    tableDescription.setItems(FXCollections.observableList(descriptionSubtaskDAO.getAllDescriptionSubtask(subtask.getId())));
                }
            }
        });
    }
}
