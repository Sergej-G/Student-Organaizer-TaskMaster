package DAO.DescriptionSubtaskDAO;

import model.DescriptionSubtask;


import java.util.List;

/**
 * Интерфейс, определяющий методы для работы с описанием подзадач (в виде пунктов) в приложении.
 */
public interface DescriptionSubtaskDAO {

        /**
         * возвращает все пункты подзадач для задачи с заданным идентификатором из базы данных
         * @param taskId
         * @return
         */
        List<DescriptionSubtask> getAllDescriptionSubtask(Integer taskId);

        /**
         * добавляет новый пункт подзадачи в базу данных
         * @param DescriptionSubtask
         */
        void addDescriptionSubtask(DescriptionSubtask DescriptionSubtask);

        /**
         * обновляет пункт подзадачи в базе данных
         * @param DescriptionSubtask
         */
        void updateDescriptionSubtask(DescriptionSubtask DescriptionSubtask);

        /**
         * удаляет пункт подзадачи из базы данных по ее идентификатору
         * @param id
         */
        void deleteDescriptionSubtask(int id);

}
