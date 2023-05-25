package DAO.TaskDAO;

import model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Этот класс реализует интерфейс TaskDAO и предоставляет методы для работы с задачами в базе данных Postgres через JDBC
 */
public class TaskDAOPos implements TaskDAO {
    private Connection conn;

    /**
     * Конструктор класса с установлением подключения к базе данных
     */
    public TaskDAOPos() {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/taskMaster", "postgres", "183729");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает список всех задач из базы данных
     * @return
     */
    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM task");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("date"));
                tasks.add(task);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Возвращает список задач, отсортированных по указанному параметру (дате)
     * @param sortData
     * @return
     */
    @Override
    public List<Task> sortTask(String sortData) {

        List<Task> tasks = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            if(Objects.equals(sortData, null)) {
                ps = conn.prepareStatement("SELECT * FROM task");
            }
            else{
                ps = conn.prepareStatement( "SELECT * FROM task WHERE date = '" + sortData + "'");
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task task = new Task(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                );
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Добавляет новую задачу в базу данных
     * @param task
     */
    @Override
    public void addTask(Task task) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO task (name, description, date) VALUES (?, ?, ?)");
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обновляет данные задачи
     * @param task
     */
    @Override
    public void updateTask(Task task) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE task SET name = ?, description = ?, date = ? WHERE id = ?");
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getDate());
            ps.setInt(4, task.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Удаляет задачу из базы данных по её идентификатору
     * @param id
     */
    @Override
    public void deleteTask(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM task WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляет все задачи из базы данных
     */
    @Override
    public void drop() {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM task");
            ps.executeUpdate();

            PreparedStatement as = conn.prepareStatement("ALTER TABLE task ALTER COLUMN id RESTART WITH 1;");
            as.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
