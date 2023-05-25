package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Этот класс представляет собой модель данных для пунктов подзадач (descriptionSubtask), заданной её идентификатором, идентификатором подзадачи, к которой принадлежит пункт, названием и описанием.
 * Для каждого поля пункта (id, subtask_id, name, description) предоставляется геттер и сеттер.
 * Геттеры возвращают значения полей задачи, а сеттеры устанавливают новые значения полей.
 */
public class DescriptionSubtask {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty subtask_id;
    private SimpleStringProperty nameDescriptionSubtask;
    private SimpleStringProperty opisanieDescriptionSubtask;

    public DescriptionSubtask(int id, int subtask_id, String nameDescriptionSubtask, String opisanieDescriptionSubtask) {
        this.id = new SimpleIntegerProperty(id);
        this.subtask_id = new SimpleIntegerProperty(subtask_id);
        this.nameDescriptionSubtask = new SimpleStringProperty(nameDescriptionSubtask);
        this.opisanieDescriptionSubtask = new SimpleStringProperty(opisanieDescriptionSubtask);
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

    public int getSubtask_id() {
        return subtask_id.get();
    }

    public SimpleIntegerProperty subtask_idProperty() {
        return subtask_id;
    }

    public void setSubtask_id(int subtask_id) {
        this.subtask_id.set(subtask_id);
    }

    public String getNameDescriptionSubtask() {
        return nameDescriptionSubtask.get();
    }

    public SimpleStringProperty nameDescriptionSubtaskProperty() {
        return nameDescriptionSubtask;
    }

    public void setNameDescriptionSubtask(String nameDescriptionSubtask) {
        this.nameDescriptionSubtask.set(nameDescriptionSubtask);
    }

    public String getOpisanieDescriptionSubtask() {
        return opisanieDescriptionSubtask.get();
    }

    public SimpleStringProperty opisanieDescriptionSubtaskProperty() {
        return opisanieDescriptionSubtask;
    }

    public void setOpisanieDescriptionSubtask(String opisanieDescriptionSubtask) {
        this.opisanieDescriptionSubtask.set(opisanieDescriptionSubtask);
    }
}
