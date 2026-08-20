package com.example.kanban.repository;

import com.example.kanban.domain.exception.ValidationException;
import com.example.kanban.domain.model.Task;
import com.example.kanban.domain.model.TaskId;
import com.example.kanban.domain.model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("InMemoryTaskRepository のテスト")
class InMemoryTaskRepositoryTest {

    private InMemoryTaskRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTaskRepository();
    }

    @Nested
    @DisplayName("nextId メソッドのテスト")
    class NextIdTest {

        @Test
        @DisplayName("呼ぶたびに連番のTaskIdが生成される")
        void should_generateSequentialTaskIds_when_nextIdCalled() {
            // Act
            TaskId id1 = repository.nextId();
            TaskId id2 = repository.nextId();
            TaskId id3 = repository.nextId();

            // Assert
            assertThat(id1.value()).isEqualTo(1L);
            assertThat(id2.value()).isEqualTo(2L);
            assertThat(id3.value()).isEqualTo(3L);
        }
    }

    @Nested
    @DisplayName("save および findById メソッドのテスト")
    class SaveAndFindByIdTest {

        @Test
        @DisplayName("タスクを正常に保存し、IDで取得できる")
        void should_saveAndFindTask_when_validTaskGiven() {
            // Arrange
            TaskId id = repository.nextId();
            Task task = new Task(id, "テストタスク", "説明文", TaskStatus.TODO);

            // Act
            Task saved = repository.save(task);
            Optional<Task> found = repository.findById(id);

            // Assert
            assertThat(saved).isEqualTo(task);
            assertThat(found).isPresent().contains(task);
        }

        @Test
        @DisplayName("同じIDでsaveを呼ぶとタスクが上書き更新される")
        void should_updateTask_when_taskWithSameIdSaved() {
            // Arrange
            TaskId id = repository.nextId();
            Task initialTask = new Task(id, "初期タイトル", "初期説明", TaskStatus.TODO);
            repository.save(initialTask);

            Task updatedTask = initialTask.withStatus(TaskStatus.DOING);

            // Act
            repository.save(updatedTask);
            Optional<Task> found = repository.findById(id);

            // Assert
            assertThat(found).isPresent().contains(updatedTask);
            assertThat(found.get().status()).isEqualTo(TaskStatus.DOING);
        }

        @Test
        @DisplayName("nullのタスクをsaveしようとするとValidationExceptionがスローされる")
        void should_throwValidationException_when_nullTaskSaved() {
            // Arrange & Act & Assert
            assertThatThrownBy(() -> repository.save(null))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage("保存対象のタスクにnullを指定することはできません。");
        }

        @Test
        @DisplayName("存在しないIDを検索した場合、Optional.emptyが返却される")
        void should_returnEmpty_when_nonExistentIdSearched() {
            // Act
            Optional<Task> result = repository.findById(TaskId.of(999L));

            // Assert
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("findAll および findByStatus メソッドのテスト")
    class FindAllAndFindByStatusTest {

        @Test
        @DisplayName("全件取得でID順にソートされたタスク一覧が返却される")
        void should_returnAllTasksOrderedById_when_findAllCalled() {
            // Arrange
            Task task1 = repository.save(new Task(repository.nextId(), "Task 1", "", TaskStatus.TODO));
            Task task2 = repository.save(new Task(repository.nextId(), "Task 2", "", TaskStatus.DOING));
            Task task3 = repository.save(new Task(repository.nextId(), "Task 3", "", TaskStatus.DONE));

            // Act
            List<Task> allTasks = repository.findAll();

            // Assert
            assertThat(allTasks).containsExactly(task1, task2, task3);
        }

        @Test
        @DisplayName("ステータス指定で該当するタスクのみが取得できる")
        void should_returnTasksFilteredByStatus_when_findByStatusCalled() {
            // Arrange
            Task task1 = repository.save(new Task(repository.nextId(), "Task 1", "", TaskStatus.TODO));
            Task task2 = repository.save(new Task(repository.nextId(), "Task 2", "", TaskStatus.DOING));
            Task task3 = repository.save(new Task(repository.nextId(), "Task 3", "", TaskStatus.TODO));

            // Act
            List<Task> todoTasks = repository.findByStatus(TaskStatus.TODO);
            List<Task> doingTasks = repository.findByStatus(TaskStatus.DOING);
            List<Task> doneTasks = repository.findByStatus(TaskStatus.DONE);

            // Assert
            assertThat(todoTasks).containsExactly(task1, task3);
            assertThat(doingTasks).containsExactly(task2);
            assertThat(doneTasks).isEmpty();
        }
    }

    @Nested
    @DisplayName("deleteById および existsById メソッドのテスト")
    class DeleteAndExistsTest {

        @Test
        @DisplayName("存在するタスクを削除するとtrueが返り、検索できなくなる")
        void should_deleteTaskAndReturnTrue_when_taskExists() {
            // Arrange
            TaskId id = repository.nextId();
            repository.save(new Task(id, "Task to delete", "", TaskStatus.TODO));

            // Act
            boolean existsBefore = repository.existsById(id);
            boolean deleted = repository.deleteById(id);
            boolean existsAfter = repository.existsById(id);

            // Assert
            assertThat(existsBefore).isTrue();
            assertThat(deleted).isTrue();
            assertThat(existsAfter).isFalse();
            assertThat(repository.findById(id)).isEmpty();
        }

        @Test
        @DisplayName("存在しないタスクを削除しようとするとfalseが返る")
        void should_returnFalse_when_deletingNonExistentTask() {
            // Act
            boolean deleted = repository.deleteById(TaskId.of(999L));

            // Assert
            assertThat(deleted).isFalse();
        }
    }
}
