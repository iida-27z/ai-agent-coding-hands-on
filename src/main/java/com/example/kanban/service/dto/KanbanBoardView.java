package com.example.kanban.service.dto;

import com.example.kanban.domain.model.Task;
import com.example.kanban.domain.model.TaskStatus;

import java.util.List;

/**
 * カンバンボード全体のステータス別タスク一覧を保持するビューオブジェクト。
 *
 * @param todoTasks  TODOステータスのタスク一覧
 * @param doingTasks DOINGステータスのタスク一覧
 * @param doneTasks  DONEステータスのタスク一覧
 */
public record KanbanBoardView(
        List<Task> todoTasks,
        List<Task> doingTasks,
        List<Task> doneTasks
) {

    /**
     * コンパクトコンストラクタによるnull安全性確保。
     */
    public KanbanBoardView {
        todoTasks = todoTasks != null ? List.copyOf(todoTasks) : List.of();
        doingTasks = doingTasks != null ? List.copyOf(doingTasks) : List.of();
        doneTasks = doneTasks != null ? List.copyOf(doneTasks) : List.of();
    }

    /**
     * ボード全体のタスク総数を取得します。
     *
     * @return タスク総数
     */
    public int totalTaskCount() {
        return todoTasks.size() + doingTasks.size() + doneTasks.size();
    }

    /**
     * 指定されたステータスのタスク一覧を取得します。
     *
     * @param status タスクステータス
     * @return 該当ステータスのタスク一覧
     */
    public List<Task> tasksFor(TaskStatus status) {
        if (status == null) {
            return List.of();
        }
        return switch (status) {
            case TODO -> todoTasks;
            case DOING -> doingTasks;
            case DONE -> doneTasks;
        };
    }
}
