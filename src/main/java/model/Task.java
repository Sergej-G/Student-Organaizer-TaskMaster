package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Этот класс представляет собой модель данных для задачи (task), заданной её идентификатором, названием, описанием и датой.
 * Для каждого поля задачи (id, name, description, date) предоставляется геттер и сеттер. Геттеры возвращают значения полей задачи, а сеттеры устанавливают новые значения полей.
 */
public class Task {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleStringProperty date;

    public Task(int id, String name, String description, String date) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.date = new SimpleStringProperty(date);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setDescription(String time) {
        this.description.set(time);
    }

    public void setDate(String status) {
        this.date.set(status);
    }
}
