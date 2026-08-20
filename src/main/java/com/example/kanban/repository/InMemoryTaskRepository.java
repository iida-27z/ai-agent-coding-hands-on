package com.example.kanban.repository;

import com.example.kanban.domain.exception.ValidationException;
import com.example.kanban.domain.model.Task;
import com.example.kanban.domain.model.TaskId;
import com.example.kanban.domain.model.TaskStatus;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * メモリ上でタスクを管理するスレッドセーフなリポジトリ実装。
 */
public class InMemoryTaskRepository implements TaskRepository {

    private final AtomicLong idSequence = new AtomicLong(0L);
    private final ConcurrentMap<TaskId, Task> storage = new ConcurrentHashMap<>();

    @Override
    public TaskId nextId() {
        return TaskId.of(idSequence.incrementAndGet());
    }

    @Override
    public Task save(Task task) {
        if (task == null) {
            throw new ValidationException("保存対象のタスクにnullを指定することはできません。");
        }
        storage.put(task.id(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(TaskId id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Task> findAll() {
        return storage.values().stream()
                .sorted(Comparator.comparingLong(t -> t.id().value()))
                .toList();
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        if (status == null) {
            return List.of();
        }
        return storage.values().stream()
                .filter(t -> t.status() == status)
                .sorted(Comparator.comparingLong(t -> t.id().value()))
                .toList();
    }

    @Override
    public boolean deleteById(TaskId id) {
        if (id == null) {
            return false;
        }
        return storage.remove(id) != null;
    }

    @Override
    public boolean existsById(TaskId id) {
        if (id == null) {
            return false;
        }
        return storage.containsKey(id);
    }
}
