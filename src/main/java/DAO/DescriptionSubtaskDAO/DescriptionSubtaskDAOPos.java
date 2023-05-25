package DAO.DescriptionSubtaskDAO;

import model.DescriptionSubtask;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс - реализация интерфейса DescriptionSubtaskDAO, который предоставляет методы для работы с пунктами подзадач в приложении, используя базу данных Postgresql.
 */
public class DescriptionSubtaskDAOPos implements DescriptionSubtaskDAO {
    private Connection conn;

    /**
     * Конструктор класса DescriptionSubtaskDAOPos создает соединение с базой данных Postgresql, используя имя пользователя, пароль и адрес сервера, на котором работает база данных.
     */
    public DescriptionSubtaskDAOPos() {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/taskMaster", "postgres", "183729");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * возвращает все пункты подзадач для задачи с заданным идентификатором из базы данных
     * @param subtask_id
     * @return
     */
    @Override
    public List<DescriptionSubtask> getAllDescriptionSubtask(Integer subtask_id) {
        List<DescriptionSubtask> descriptionSubtasks = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM descriptionSubtask WHERE subtask_id = '" + subtask_id + "'");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DescriptionSubtask descriptionSubtask = new DescriptionSubtask(rs.getInt("id"),
                        rs.getInt("subtask_id"),
                        rs.getString("name"),
                        rs.getString("opisanie"));
                descriptionSubtasks.add(descriptionSubtask);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return descriptionSubtasks;
    }


    /**
     * добавляет новый пункт подзадачи в базу данных
     * @param descriptionSubtask
     */
    @Override
    public void addDescriptionSubtask(DescriptionSubtask descriptionSubtask) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO descriptionSubtask (subtask_id, name, opisanie) VALUES (?, ?, ?)");
            ps.setInt(1, descriptionSubtask.getSubtask_id());
            ps.setString(2, descriptionSubtask.getNameDescriptionSubtask());
            ps.setString(3, descriptionSubtask.getOpisanieDescriptionSubtask());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * обновляет пункт подзадачи в базе данных
     * @param descriptionSubtask
     */
    @Override
    public void updateDescriptionSubtask(DescriptionSubtask descriptionSubtask) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE descriptionSubtask SET subtask_id = ?, name = ?, opisanie = ? WHERE id = ?");
            ps.setInt(1, descriptionSubtask.getSubtask_id());
            ps.setString(2, descriptionSubtask.getNameDescriptionSubtask());
            ps.setString(3, descriptionSubtask.getOpisanieDescriptionSubtask());
            ps.setInt(4, descriptionSubtask.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * удаляет пункт подзадачи из базы данных по ее идентификатору
     * @param id
     */
    @Override
    public void deleteDescriptionSubtask(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM descriptionSubtask WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
