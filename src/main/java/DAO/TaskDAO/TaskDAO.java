package DAO.TaskDAO;

import model.Task;

import java.util.List;

/**
 * Этот класс объявляет интерфейс TaskDAO, который определяет методы для работы с задачами (Task)
 */
public interface TaskDAO {
    /**
     * возвращает список всех задач
     * @return
     */
    List<Task> getAllTasks();

    /**
     * возвращает список задач, отсортированных по указанному параметру (дате);* @param sortData
     * @return
     */
    List<Task> sortTask(String sortData);

    /**
     * добавляет новую задачу
     * @param task
     */
    void addTask(Task task);

    /**
     * обновляет данные задачи
     * @param task
     */
    void updateTask(Task task);

    /**
     * удаляет задачу по её идентификатору
     * @param id
     */
    void deleteTask(int id);

    /**
     * удаляет все задачи из базы данных
     */
    void drop();
}
