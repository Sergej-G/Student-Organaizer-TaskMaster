package DAO.SubtaskDAO;

import model.Subtask;


import java.util.List;

/**
 * Этот класс является интерфейсом, определяющим методы для работы с подзадачами в базе данных.
 */
public interface SubtaskDAO {

    /**
     *  возвращает список всех подзадач задачи с заданным идентификатором (taskId)
     * @param taskId
     * @return
     */
    List<Subtask> getAllSubtasks(Integer taskId);

    /**
     * добавляет новую подзадачу в базу данных
     * @param subtask
     */
    void addSubtask(Subtask subtask);

    /**
     * обновляет данные подзадачи
     * @param subtask
     */
    void updateSubtask(Subtask subtask);

    /**
     * удаляет подзадачу из базы данных по её идентификатору
     * @param id
     */
    void deleteSubtask(int id);

    /**
     * удаляет все подзадачи из базы данных
     */
    void drop();
}
