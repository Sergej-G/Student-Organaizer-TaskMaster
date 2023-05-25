package com.example.taskmaster;

import DAO.DescriptionSubtaskDAO.DescriptionSubtaskDAO;
import DAO.DescriptionSubtaskDAO.DescriptionSubtaskDAOPos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DescriptionSubtask;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Класс окна описания подзадачи пунктами
 */
public class AddDescriptionSubtask implements Initializable {
    public TableView tableDescription;
    public TextArea areaDescription;
    public TextField nameDescription;
    public TextField updateDescrSubtaskName;
    public TextArea updateDescrSubraskDesc;

    private ObservableList<AddDescriptionSubtask> fxlistAddDescriptionSubtask;

    DescriptionSubtaskDAO descriptionSubtaskDAO;

    Integer customIdSubtask;

    private DetailsTask detailsTask = new DetailsTask();

    public Integer getCustomIdSubtask() {
        return customIdSubtask;
    }

    public void setCustomIdSubtask(Integer customIdSubtask) {
        this.customIdSubtask = customIdSubtask;
    }

    /**
     * Добавляет новый пункт подзадачи в базу данных, если все поля заполнены, и обновляет таблицу с пунктами подзадачи.
     * Если поле с названием пустое, вызывается метод showAlertWithHeaderText(), который выводит соответствующее сообщение.
     * @param event
     */
    public void addDescriptionSubtask1(ActionEvent event) {
        String nameDetailsDescription = nameDescription.getText();
        String descriptionDetailsDescription = areaDescription.getText();

        if (Objects.equals(nameDescription.getText(), "")) {
            showAlertWithHeaderText();
        }
        else if(Objects.equals(descriptionDetailsDescription, "")){
            try{
                String descriptionDetails2 = "Без описания";
                descriptionSubtaskDAO = new DescriptionSubtaskDAOPos();

                descriptionSubtaskDAO.addDescriptionSubtask(new DescriptionSubtask(0, customIdSubtask, nameDetailsDescription, descriptionDetails2));
                tableDescription.setItems(FXCollections.observableList(descriptionSubtaskDAO.getAllDescriptionSubtask(customIdSubtask)));

            }catch (Exception e){
                e.getMessage();
            }
        }
        else{
            try{
                descriptionSubtaskDAO = new DescriptionSubtaskDAOPos();

                descriptionSubtaskDAO.addDescriptionSubtask(new DescriptionSubtask(0, customIdSubtask, nameDetailsDescription, descriptionDetailsDescription));
                tableDescription.setItems(FXCollections.observableList(descriptionSubtaskDAO.getAllDescriptionSubtask(customIdSubtask)));

            }catch (Exception e){
                e.getMessage();
            }
        }
    }

    /**
     * Выводит все пункты подзадачи из базы данных и обновляет таблицу.
     */
    @FXML
    protected void outputDescriptionSubtask() {
        try{
            descriptionSubtaskDAO = new DescriptionSubtaskDAOPos();
            tableDescription.setItems(FXCollections.observableList(descriptionSubtaskDAO.getAllDescriptionSubtask(customIdSubtask)));
        }catch (Exception e){
            e.getMessage();
        }
    }

    /**
     * Закрывает окно добавления/редактирования пунктов подзадачи.
     * @param actionEvent
     */
    public void closeDescriptionSubtask(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Метод выводит сообщение об ошибке, когда поле с названием пустое.
     */
    private void showAlertWithHeaderText() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание!");
        alert.setHeaderText("Для добавления обязательно укажите название!");

        alert.showAndWait();
    }

    /**
     * Метод выводит сообщение об ошибке, когда не выбран пункт подзадачи для редактирования.
     */
    private void alertPicItemForUpdate() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание!");
        alert.setHeaderText("Для редактирования пункта необходимо выбрать его в списке!");

        alert.showAndWait();
    }

    /**
     * Запоминает ссылку на родительское окно.
     * @param detailsTask
     */
    public void setParent (DetailsTask detailsTask){
        this.detailsTask = detailsTask;
    }

    /**
     * Создает таблицу для отображения пунктов подзадачи и заполняет её данными из базы данных.
     * Также задает возможность редактирования отдельных полей в ячейках таблицы.
     */
    private void createTableDescriptionSubtask() {

        TableColumn Col0 = new TableColumn("Номер");//отображаемый заголовок столбца
        Col0.setMinWidth(15);//ширина
        Col0.setCellValueFactory(new PropertyValueFactory<DescriptionSubtask, Integer>("id"));
        Col0.setVisible(false);

        TableColumn Col1 = new TableColumn("id подзадачи");//отображаемый заголовок столбца
        Col1.setMinWidth(15);//ширина
        Col1.setCellValueFactory(new PropertyValueFactory<DescriptionSubtask, Integer>("subtask_id"));
        Col1.setVisible(false);

        TableColumn Col2 = new TableColumn("Название");//отображаемый заголовок столбца
        Col2.setMinWidth(100);//ширина
        Col2.setCellValueFactory(new PropertyValueFactory<DescriptionSubtask, String>("nameDescriptionSubtask"));

        TableColumn Col3 = new TableColumn("Описание");//отображаемый заголовок столбца
        Col3.setMinWidth(340);//ширина
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
     * Выполняет редактирование выбранного пункта подзадачи.
     * Он получает данные о выбранном описании подзадачи из таблицы, а также новые данные из соответствующих текстовых полей.
     * Затем он проверяет, заполнены ли все обязательные поля и выбрана ли подзадача для редактирования.
     */
    @FXML
    protected void updateDescrSubtask() {
        DescriptionSubtask descriptionSubtask = (DescriptionSubtask) tableDescription.getSelectionModel().getSelectedItem();
        String newNameDetailsDescription = updateDescrSubtaskName.getText();
        String newDescriptionDetailsDescription = updateDescrSubraskDesc.getText();

        if (Objects.equals(updateDescrSubtaskName.getText(), "") || tableDescription.getSelectionModel().getSelectedItem() == null) {
            alertPicItemForUpdate();
        }
        else if(Objects.equals(newDescriptionDetailsDescription, "")){
            try{
                String descriptionDetails2 = "Без описания";
                descriptionSubtaskDAO = new DescriptionSubtaskDAOPos();

                descriptionSubtaskDAO.updateDescriptionSubtask(new DescriptionSubtask(descriptionSubtask.getId(), customIdSubtask, newNameDetailsDescription, descriptionDetails2));
                tableDescription.setItems(FXCollections.observableList(descriptionSubtaskDAO.getAllDescriptionSubtask(customIdSubtask)));

            }catch (Exception e){
                e.getMessage();
            }
        }
        else{
            try{
                descriptionSubtaskDAO = new DescriptionSubtaskDAOPos();

                descriptionSubtaskDAO.updateDescriptionSubtask(new DescriptionSubtask(descriptionSubtask.getId(), customIdSubtask, newNameDetailsDescription, newDescriptionDetailsDescription));
                tableDescription.setItems(FXCollections.observableList(descriptionSubtaskDAO.getAllDescriptionSubtask(customIdSubtask)));

            }catch (Exception e){
                e.getMessage();
            }
        }
    }

    /**
     * Удаляет пункт подзадачи из базы данных и обновляет таблицу.
     */
    @FXML
    protected void deleteDescriptionSubtask() {
        try{
            DescriptionSubtask descriptionSubtask = (DescriptionSubtask) tableDescription.getSelectionModel().getSelectedItem();

            descriptionSubtaskDAO = new DescriptionSubtaskDAOPos();
            descriptionSubtaskDAO.deleteDescriptionSubtask(descriptionSubtask.getId());

            tableDescription.setItems(FXCollections.observableList(descriptionSubtaskDAO.getAllDescriptionSubtask(descriptionSubtask.getId())));
            tableDescription.setItems(FXCollections.observableList(descriptionSubtaskDAO.getAllDescriptionSubtask(customIdSubtask)));
        }catch (Exception e){
            e.getMessage();
        }
    }

    /**
     * Инициализирует форму и вызывает метод createTableDescriptionSubtask() и хранит обработчик получения id пункта из таблицы.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTableDescriptionSubtask();

        tableDescription.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                DescriptionSubtask descriptionSubtask = (DescriptionSubtask) tableDescription.getSelectionModel().getSelectedItem();
                if (tableDescription.getSelectionModel().getSelectedItem() != null) {
                    System.out.println(descriptionSubtask.getId());
                    descriptionSubtaskDAO = new DescriptionSubtaskDAOPos();
                }
            }
        });
    }
}
