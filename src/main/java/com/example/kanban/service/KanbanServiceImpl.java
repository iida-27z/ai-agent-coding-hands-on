package com.example.kanban.service;

import com.example.kanban.domain.exception.TaskNotFoundException;
import com.example.kanban.domain.exception.ValidationException;
import com.example.kanban.domain.model.Task;
import com.example.kanban.domain.model.TaskId;
import com.example.kanban.domain.model.TaskStatus;
import com.example.kanban.repository.TaskRepository;
import com.example.kanban.service.dto.CreateTaskCommand;
import com.example.kanban.service.dto.KanbanBoardView;

import java.util.List;
import java.util.Objects;

/**
 * カンバンボード操作サービスの実装クラス。
 */
public class KanbanServiceImpl implements KanbanService {

    private final TaskRepository taskRepository;

    /**
     * リポジトリを受け取るコンストラクタ。
     *
     * @param taskRepository タスクリポジトリ
     */
    public KanbanServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = Objects.requireNonNull(taskRepository, "taskRepository must not be null");
    }

    @Override
    public Task createTask(CreateTaskCommand command) {
        if (command == null) {
            throw new ValidationException("作成コマンドにnullを指定することはできません。");
        }
        if (command.title() == null || command.title().isBlank()) {
            throw new ValidationException("タスクタイトルは必須項目であり、空白のみにすることはできません。");
        }
        TaskId id = taskRepository.nextId();
        Task newTask = new Task(id, command.title(), command.description(), TaskStatus.TODO);
        return taskRepository.save(newTask);
    }

    @Override
    public Task updateTaskStatus(TaskId id, TaskStatus newStatus) {
        if (id == null) {
            throw new ValidationException("タスクIDにnullを指定することはできません。");
        }
        if (newStatus == null) {
            throw new ValidationException("タスクステータスにnullを指定することはできません。");
        }
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("タスクが見つかりません: ID=" + id.value()));
        Task updated = task.withStatus(newStatus);
        return taskRepository.save(updated);
    }

    @Override
    public void deleteTask(TaskId id) {
        if (id == null) {
            throw new ValidationException("タスクIDにnullを指定することはできません。");
        }
        boolean deleted = taskRepository.deleteById(id);
        if (!deleted) {
            throw new TaskNotFoundException("削除対象のタスクが見つかりません: ID=" + id.value());
        }
    }

    @Override
    public Task getTask(TaskId id) {
        if (id == null) {
            throw new ValidationException("タスクIDにnullを指定することはできません。");
        }
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("タスクが見つかりません: ID=" + id.value()));
    }

    @Override
    public KanbanBoardView getBoardView() {
        List<Task> allTasks = taskRepository.findAll();
        List<Task> todoTasks = allTasks.stream()
                .filter(t -> t.status() == TaskStatus.TODO)
                .toList();
        List<Task> doingTasks = allTasks.stream()
                .filter(t -> t.status() == TaskStatus.DOING)
                .toList();
        List<Task> doneTasks = allTasks.stream()
                .filter(t -> t.status() == TaskStatus.DONE)
                .toList();
        return new KanbanBoardView(todoTasks, doingTasks, doneTasks);
    }
}
