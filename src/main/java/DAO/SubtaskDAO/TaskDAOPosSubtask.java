package DAO.SubtaskDAO;


import model.Subtask;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Класс TaskDAOPosSubtask является реализацией интерфейса SubtaskDAO для работы с подзадачами в базе данных PostgreSQL
 */
public class TaskDAOPosSubtask implements SubtaskDAO {
    private Connection conn;

    /**
     * В конструкторе класса устанавливается соединение с базой данных, используя драйвер PostgreSQL JDBC
     */
    public TaskDAOPosSubtask() {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/taskMaster", "postgres", "183729");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * возвращает список всех подзадач задачи с заданным идентификатором (taskId)
     * @param taskId
     * @return
     */
    @Override
    public List<Subtask> getAllSubtasks(Integer taskId) {
        List<Subtask> subtasks = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM subtask WHERE task_id = '" + taskId + "'");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Subtask subtask = new Subtask(rs.getInt("id"),
                        rs.getInt("task_id"),
                        rs.getString("name"),
                        rs.getString("description"));
                subtasks.add(subtask);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subtasks;
    }

    /**
     * добавляет новую подзадачу в базу данных
     * @param subtask
     */
    @Override
    public void addSubtask(Subtask subtask) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO subtask (task_id, name, description) VALUES (?, ?, ?)");
            ps.setInt(1, subtask.getTask_id());
            ps.setString(2, subtask.getNameSubtask());
            ps.setString(3, subtask.getDescriptionSubtask());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * обновляет данные подзадачи
     * @param subtask
     */
    @Override
    public void updateSubtask(Subtask subtask) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE subtask SET task_id = ?, name = ?, description = ? WHERE id = ?");
            ps.setInt(1, subtask.getTask_id());
            ps.setString(2, subtask.getNameSubtask());
            ps.setString(3, subtask.getDescriptionSubtask());
            ps.setInt(4, subtask.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * удаляет подзадачу из базы данных по её идентификатору
     * @param id
     */
    @Override
    public void deleteSubtask(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM subtask WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * удаляет все подзадачи из базы данных
     */
    @Override
    public void drop() {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM subtask");
            ps.executeUpdate();

            PreparedStatement as = conn.prepareStatement("ALTER TABLE subtask ALTER COLUMN id RESTART WITH 1;");
            as.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
