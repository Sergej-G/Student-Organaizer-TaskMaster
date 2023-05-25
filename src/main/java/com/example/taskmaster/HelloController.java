package com.example.taskmaster;

import DAO.DescriptionSubtaskDAO.DescriptionSubtaskDAO;
import DAO.SubtaskDAO.SubtaskDAO;
import DAO.SubtaskDAO.TaskDAOPosSubtask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Task;
import DAO.TaskDAO.TaskDAO;
import DAO.TaskDAO.TaskDAOPos;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Класс главного окна программы
 */
public class HelloController implements Initializable {
    @FXML
    public TextField updateTaskField;
    @FXML
    public DatePicker updateTaskData;
    @FXML
    public TextArea updateTaskArea;
    @FXML
    private AddTask addTaskWin;
    @FXML
    private DetailsTask detailsTask;
    @FXML
    public Label oglav;
    @FXML
    public Button drop;
    @FXML
    public DatePicker dateTask;
    @FXML
    public Button addTask;
    Image image = new Image("D:\\User\\Desktop\\Программная инженерия\\Органайзер\\TaskMaster\\src\\main\\resources\\icon.png");

    TaskDAO taskDAO;
    SubtaskDAO subtaskDAO;

    Task task;
    public TableView table;/*таблица из fxml-определения*/
    private ObservableList<Task> fxlist;/* cпециальный список для работы GUI */

    /**
     * Метод windowAddTask(ActionEvent event) является обработчиком события и вызывается при нажатии на кнопку «Добавить задачу». Он определяет логику открытия формы добавления задачи и установление связи между главным контроллером и контроллером формы добавления задачи.
     * @param event
     * @throws IOException
     */
    public void windowAddTask(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-task.fxml"));

        Stage primaryStage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 551, 426);
        primaryStage.setTitle("Добавление задачи");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(image);
        primaryStage.show();

        addTaskWin = fxmlLoader.getController();
        addTaskWin.setParent(this);
    }

    /**
     * Метод windowDetailsTask() является обработчиком события и вызывается при нажатии на кнопку «Детализация задачи». Он определяет логику открытия формы детализации выбранной задачи и устанавливает связь между главным контроллером и контроллером детализации задачи.
     * @param event
     * @throws IOException
     */
    public void windowDetailsTask(ActionEvent event) throws IOException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("details-task.fxml"));

            Stage primaryStage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 1110, 615);
            primaryStage.setTitle("Детализация задачи");
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(image);
            primaryStage.show();

            detailsTask = fxmlLoader.getController();
            detailsTask.setParent(this);

            Task task = (Task) table.getSelectionModel().getSelectedItem();
            detailsTask.setCustomId(task.getId());
            detailsTask.outputDetails();
            }
        else {
            alertPicItem();
        }
    }

    /**
     * Метод createtable() создает таблицу с данными задач, которая будет отображаться в главном окне приложения. Он определяет структуру таблицы с задачами, ее заголовки и свойства столбцов, заполняет ее данными.
     */
    private void createtable() {

        TableColumn Col0 = new TableColumn("Номер");//отображаемый заголовок столбца
        Col0.setMinWidth(15);//ширина
        Col0.setCellValueFactory(new PropertyValueFactory<Task, Integer>("id"));
        Col0.setVisible(false);

        TableColumn Col1 = new TableColumn("Название");//отображаемый заголовок столбца
        Col1.setMinWidth(180);//ширина
        Col1.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));


        TableColumn Col2 = new TableColumn("Описание");//отображаемый заголовок столбца
        Col2.setMinWidth(600);//ширина
        Col2.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));

        TableColumn Col3 = new TableColumn("Дата");//отображаемый заголовок столбца
        Col3.setMinWidth(50);//ширина
        Col3.setCellValueFactory(new PropertyValueFactory<Task, String>("date"));


        table.getColumns().clear();// если поля созданы в Builder, их удалить
        table.getColumns().addAll(Col0, Col1, Col2,Col3);// добавление столбцов
        table.setItems(fxlist);// загрузка списка объектов Task из fx_ListTask


        Col0.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Task, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Task, Integer> t) {
                ((Task) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue()); }
        });

        Col1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Task, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Task, Integer> t) {
                ((Task) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue()); }
        });

        Col2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Task, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Task, Integer> t) {
                ((Task) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue()); }
        });

        Col3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Task, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Task, Integer> t) {
                ((Task) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue()); }
        });

    }

    /**
     * Метод output() выполняет вывод списка задач за определенную дату на экран.
     * Он выполняет обработку пользовательского ввода даты, извлекает данные из базы данных и выводит список задач на определенную дату на экран.
     * Вызывается при запуске программы или при нажатии на кнопку «Обновить список».
     */
    @FXML
    protected void output() {
        try{
            taskDAO = new TaskDAOPos();
            oglav.setText("Ваши задачи на " + dateTask.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            table.setItems(FXCollections.observableList(taskDAO.sortTask(dateTask.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))));
        }catch (Exception e){
            e.getMessage();
        }
    }

    /**
     * Метод sortByDate() сортирует задачи по определенной дате.
     * При этом изменяется текст в элементе «Оглавление» и задается новый список задач в таблице.
     * Вызывается при выборе даты
     */
    @FXML
    protected void sortByDate() {
        oglav.setText("Ваши задачи на " + dateTask.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        table.setItems(FXCollections.observableList(taskDAO.sortTask(dateTask.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))));
    }

    /**
     * Метод drop() удаляет все задачи и информацию о них из базы данных.
     * Если при выполнении запроса возникает ошибка, выводится сообщение об этом.
     */
    @FXML
    protected void drop() {
        try{
            taskDAO = new TaskDAOPos();
            subtaskDAO = new TaskDAOPosSubtask();

            subtaskDAO.drop();
            taskDAO.drop();
            table.setItems(FXCollections.observableList(taskDAO.getAllTasks()));
        }catch (Exception e){
            e.getMessage();
        }
    }

    /**
     * Метод updateTask() обновляет выбранную пользователем задачу, изменяя ее название, описание и дату.
     * Если какое-то из полей не заполнено или выбранная задача отсутствует, выводится сообщение с предупреждением.
     * Если дата выбрана раньше текущей даты, выводится сообщение об этом.
     * Если в описании задачи отсутствует текст, то в базу данных передается значение «Без описания».
     * После обновления задачи в базе данных метод обновляет список задач в таблице, используя сортировку по дате.
     */
    @FXML
    protected void updateTask() {
        Task task = (Task) table.getSelectionModel().getSelectedItem();
        String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String name2 = updateTaskField.getText();
        String description2 = updateTaskArea.getText();
        String date2 = updateTaskData.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        if (updateTaskData.getValue() == null || updateTaskField.getText() == ""  || table.getSelectionModel().getSelectedItem() == null) {
            showAlertWithHeaderTextforUpdate();
        }
        else if(date2.compareTo(dateNow) < 0){
            pickDateAlertforUpdate();
        }
        else if(Objects.equals(description2, "")){
            try{
                String description3 = "Без описания";
                taskDAO = new TaskDAOPos();

                taskDAO.updateTask(new Task(task.getId(), name2, description3, date2));
                table.setItems(FXCollections.observableList(taskDAO.sortTask(dateTask.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))));

            }catch (Exception e){
                e.getMessage();
            }
        }
        else{
            try{
                taskDAO = new TaskDAOPos();

                taskDAO.updateTask(new Task(task.getId(), name2, description2, date2));
                table.setItems(FXCollections.observableList(taskDAO.sortTask(dateTask.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))));
            }catch (Exception e){
                e.getMessage();
            }
        }
    }

    /**
     * Метод deleteTask() удаляет выбранную в таблице задачу из базы данных и обновляет список задач в таблице с помощью сортировки по дате. Если при выполнении запроса возникает ошибка, выводится сообщение об этом.
     */
    @FXML
    protected void deleteTask() {
        try{
            Task task = (Task) table.getSelectionModel().getSelectedItem();

            taskDAO = new TaskDAOPos();
            taskDAO.deleteTask(task.getId());

            table.setItems(FXCollections.observableList(taskDAO.sortTask(dateTask.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))));
        }catch (Exception e){
            e.getMessage();
        }
    }

    /**
     * Метод выводящий пользователю сообщение о проблемме
     */
    private void showAlertWithHeaderTextforUpdate() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание!");
        alert.setHeaderText("Для редактирования задачи обязательно выберите её в окне задач, заполните поле названия и укажите дедлайн!");

        alert.showAndWait();
    }

    /**
     * Метод выводящий пользователю сообщение о проблемме
     */
    private void pickDateAlertforUpdate() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Что-то не так...");
        alert.setHeaderText("Машину времени ещё не изобрели!!!" +
                "\nНазначить дедлайн прошедшим числом невозможно!!!");

        alert.showAndWait();
    }

    /**
     * Метод выводящий пользователю сообщение о проблемме
     */
    private void alertPicItem() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание!");
        alert.setHeaderText("Для детализации необходимо выбрать задачу из списка!");

        alert.showAndWait();
    }

    /**
     * Метод initialize() инициализирует форму и вызывает другие методы, необходимые для корректной работы приложения: задает начальное значение для элемента выбора даты; задает начальные значения для элементов, используемых при редактировании задачи, задает текст для элемента «оглавление»; вызывает методы createtable(), output() и sortByDate(); создает обработчик событий для элемента «table», который позволяет получить id выбранной задачи при клике на нее мышью.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateTask.setValue(LocalDate.now());
        updateTaskData.setValue(LocalDate.now());
        oglav.setText("Ваши задачи на " + dateTask.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        createtable();
        output();
        sortByDate();


        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Task task = (Task) table.getSelectionModel().getSelectedItem();
                if (table.getSelectionModel().getSelectedItem() != null) {
                    System.out.println(task.getId());
                }
            }
        });
    }
}