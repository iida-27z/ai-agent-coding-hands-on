package com.example.kanban.service;

import com.example.kanban.domain.exception.TaskNotFoundException;
import com.example.kanban.domain.exception.ValidationException;
import com.example.kanban.domain.model.Task;
import com.example.kanban.domain.model.TaskId;
import com.example.kanban.domain.model.TaskStatus;
import com.example.kanban.service.dto.CreateTaskCommand;
import com.example.kanban.service.dto.KanbanBoardView;

/**
 * カンバンボードの操作およびタスク管理のユースケースを提供するサービスインターフェース。
 */
public interface KanbanService {

    /**
     * 新しいタスクを作成して登録します。初期ステータスは TODO となります。
     *
     * @param command タスク作成コマンド
     * @return 登録されたタスク
     * @throws ValidationException コマンドがnull、またはタイトルが不正な場合
     */
    Task createTask(CreateTaskCommand command);

    /**
     * 指定されたタスクのステータスを更新します。
     *
     * @param id        対象タスクID
     * @param newStatus 新しいステータス
     * @return 更新されたタスク
     * @throws TaskNotFoundException 指定されたIDのタスクが存在しない場合
     * @throws ValidationException   IDまたは新ステータスがnullの場合
     */
    Task updateTaskStatus(TaskId id, TaskStatus newStatus);

    /**
     * 指定されたタスクを削除します。
     *
     * @param id 対象タスクID
     * @throws TaskNotFoundException 指定されたIDのタスクが存在しない場合
     * @throws ValidationException   IDがnullの場合
     */
    void deleteTask(TaskId id);

    /**
     * 指定されたタスクを1件取得します。
     *
     * @param id 対象タスクID
     * @return 該当タスク
     * @throws TaskNotFoundException 指定されたIDのタスクが存在しない場合
     * @throws ValidationException   IDがnullの場合
     */
    Task getTask(TaskId id);

    /**
     * カンバンボード全体のステータス別タスク集約ビューを取得します。
     *
     * @return カンバンボードビュー
     */
    KanbanBoardView getBoardView();
}
