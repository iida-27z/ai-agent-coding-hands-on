package com.example.kanban.repository;

import java.util.List;
import java.util.Optional;
import com.example.kanban.domain.model.Task;
import com.example.kanban.domain.model.TaskId;
import com.example.kanban.domain.model.TaskStatus;

/**
 * タスクの永続化および検索を行うリポジトリインターフェース。
 */
public interface TaskRepository {

    /**
     * 新しい一意なタスクIDを生成して返却します。
     *
     * @return 新規タスクID
     */
    TaskId nextId();

    /**
     * タスクを保存します（新規登録または更新）。
     *
     * @param task 保存対象のタスク
     * @return 保存されたタスク
     */
    Task save(Task task);

    /**
     * 指定されたIDのタスクを取得します。
     *
     * @param id タスクID
     * @return タスクが存在する場合はOptionalにラップされたタスク、存在しない場合は空のOptional
     */
    Optional<Task> findById(TaskId id);

    /**
     * 全てのタスクを取得します。
     *
     * @return タスク一覧
     */
    List<Task> findAll();

    /**
     * 指定されたステータスのタスク一覧を取得します。
     *
     * @param status タスクステータス
     * @return 該当ステータスのタスク一覧
     */
    List<Task> findByStatus(TaskStatus status);

    /**
     * 指定されたIDのタスクを削除します。
     *
     * @param id タスクID
     * @return 削除に成功した場合（タスクが存在して削除された場合）はtrue、存在しなかった場合はfalse
     */
    boolean deleteById(TaskId id);

    /**
     * 指定されたIDのタスクが存在するかどうかを確認します。
     *
     * @param id タスクID
     * @return 存在する場合はtrue、しない場合はfalse
     */
    boolean existsById(TaskId id);
}
