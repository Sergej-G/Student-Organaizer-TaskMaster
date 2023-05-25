package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Этот класс представляет собой модель данных для подзадач (subtask), заданной её идентификатором, идентификатором задачи, к которой принадлежит подзадача, названием и описанием.
 * Для каждого поля подзадачи (id, task_id, name, description) предоставляется геттер и сеттер.
 * Геттеры возвращают значения полей задачи, а сеттеры устанавливают новые значения полей.
 */
public class Subtask {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty task_id;
    private SimpleStringProperty nameSubtask;
    private SimpleStringProperty descriptionSubtask;

    public Subtask(int id, int task_id, String nameSubtask, String descriptionSubtask) {
        this.id = new SimpleIntegerProperty(id);
        this.task_id = new SimpleIntegerProperty(task_id);
        this.nameSubtask = new SimpleStringProperty(nameSubtask);
        this.descriptionSubtask = new SimpleStringProperty(descriptionSubtask);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getTask_id() {
        return task_id.get();
    }

    public SimpleIntegerProperty task_idProperty() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id.set(task_id);
    }

    public String getNameSubtask() {
        return nameSubtask.get();
    }

    public SimpleStringProperty nameSubtaskProperty() {
        return nameSubtask;
    }

    public void setNameSubtask(String nameSubtask) {
        this.nameSubtask.set(nameSubtask);
    }

    public String getDescriptionSubtask() {
        return descriptionSubtask.get();
    }

    public SimpleStringProperty descriptionSubtaskProperty() {
        return descriptionSubtask;
    }

    public void setDescriptionSubtask(String descriptionSubtask) {
        this.descriptionSubtask.set(descriptionSubtask);
    }



}
