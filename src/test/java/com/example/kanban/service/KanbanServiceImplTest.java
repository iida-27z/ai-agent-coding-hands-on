package com.example.kanban.service;

import com.example.kanban.domain.exception.TaskNotFoundException;
import com.example.kanban.domain.exception.ValidationException;
import com.example.kanban.domain.model.Task;
import com.example.kanban.domain.model.TaskId;
import com.example.kanban.domain.model.TaskStatus;
import com.example.kanban.repository.TaskRepository;
import com.example.kanban.service.dto.CreateTaskCommand;
import com.example.kanban.service.dto.KanbanBoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("KanbanServiceImpl のテスト")
class KanbanServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private KanbanServiceImpl kanbanService;

    @Nested
    @DisplayName("createTask メソッドのテスト")
    class CreateTaskTest {

        @Test
        @DisplayName("有効なコマンドを指定してタスクを登録できる（初期ステータスはTODO）")
        void should_createTaskWithTodoStatus_when_validCommandGiven() {
            // Arrange
            CreateTaskCommand command = new CreateTaskCommand("テストタスク", "詳細説明");
            TaskId generatedId = TaskId.of(1L);
            Task expectedTask = new Task(generatedId, "テストタスク", "詳細説明", TaskStatus.TODO);

            when(taskRepository.nextId()).thenReturn(generatedId);
            when(taskRepository.save(any(Task.class))).thenReturn(expectedTask);

            // Act
            Task actual = kanbanService.createTask(command);

            // Assert
            assertThat(actual).isEqualTo(expectedTask);
            assertThat(actual.status()).isEqualTo(TaskStatus.TODO);
            verify(taskRepository).nextId();
            verify(taskRepository).save(any(Task.class));
        }

        @Test
        @DisplayName("nullコマンドを指定した場合、ValidationExceptionがスローされる")
        void should_throwValidationException_when_commandIsNull() {
            // Arrange & Act & Assert
            assertThatThrownBy(() -> kanbanService.createTask(null))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage("作成コマンドにnullを指定することはできません。");
        }

        @Test
        @DisplayName("タイトルが空白の場合、ValidationExceptionがスローされる")
        void should_throwValidationException_when_titleIsBlank() {
            // Arrange
            CreateTaskCommand command = new CreateTaskCommand("   ", "詳細");

            // Act & Assert
            assertThatThrownBy(() -> kanbanService.createTask(command))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage("タスクタイトルは必須項目であり、空白のみにすることはできません。");
        }
    }

    @Nested
    @DisplayName("updateTaskStatus メソッドのテスト")
    class UpdateTaskStatusTest {

        @Test
        @DisplayName("存在するタスクのステータスを更新できる")
        void should_updateTaskStatus_when_taskExists() {
            // Arrange
            TaskId id = TaskId.of(1L);
            Task existingTask = new Task(id, "タスク1", "説明", TaskStatus.TODO);
            Task updatedTask = existingTask.withStatus(TaskStatus.DOING);

            when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
            when(taskRepository.save(updatedTask)).thenReturn(updatedTask);

            // Act
            Task result = kanbanService.updateTaskStatus(id, TaskStatus.DOING);

            // Assert
            assertThat(result).isEqualTo(updatedTask);
            assertThat(result.status()).isEqualTo(TaskStatus.DOING);
            verify(taskRepository).findById(id);
            verify(taskRepository).save(updatedTask);
        }

        @Test
        @DisplayName("存在しないタスクIDを指定した場合、TaskNotFoundExceptionがスローされる")
        void should_throwTaskNotFoundException_when_taskDoesNotExist() {
            // Arrange
            TaskId id = TaskId.of(999L);
            when(taskRepository.findById(id)).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> kanbanService.updateTaskStatus(id, TaskStatus.DOING))
                    .isInstanceOf(TaskNotFoundException.class)
                    .hasMessage("タスクが見つかりません: ID=" + id.value());
        }

        @Test
        @DisplayName("IDまたは新ステータスがnullの場合、ValidationExceptionがスローされる")
        void should_throwValidationException_when_idOrStatusIsNull() {
            // Arrange & Act & Assert
            assertThatThrownBy(() -> kanbanService.updateTaskStatus(null, TaskStatus.DOING))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage("タスクIDにnullを指定することはできません。");

            assertThatThrownBy(() -> kanbanService.updateTaskStatus(TaskId.of(1L), null))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage("タスクステータスにnullを指定することはできません。");
        }
    }

    @Nested
    @DisplayName("deleteTask メソッドのテスト")
    class DeleteTaskTest {

        @Test
        @DisplayName("存在するタスクを正常に削除できる")
        void should_deleteTask_when_taskExists() {
            // Arrange
            TaskId id = TaskId.of(1L);
            when(taskRepository.deleteById(id)).thenReturn(true);

            // Act
            kanbanService.deleteTask(id);

            // Assert
            verify(taskRepository).deleteById(id);
        }

        @Test
        @DisplayName("存在しないタスクを指定した場合、TaskNotFoundExceptionがスローされる")
        void should_throwTaskNotFoundException_when_deletingNonExistentTask() {
            // Arrange
            TaskId id = TaskId.of(999L);
            when(taskRepository.deleteById(id)).thenReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> kanbanService.deleteTask(id))
                    .isInstanceOf(TaskNotFoundException.class)
                    .hasMessage("削除対象のタスクが見つかりません: ID=" + id.value());
        }

        @Test
        @DisplayName("IDがnullの場合、ValidationExceptionがスローされる")
        void should_throwValidationException_when_nullIdGiven() {
            // Arrange & Act & Assert
            assertThatThrownBy(() -> kanbanService.deleteTask(null))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage("タスクIDにnullを指定することはできません。");
        }
    }

    @Nested
    @DisplayName("getTask メソッドのテスト")
    class GetTaskTest {

        @Test
        @DisplayName("存在するタスクを取得できる")
        void should_returnTask_when_taskExists() {
            // Arrange
            TaskId id = TaskId.of(1L);
            Task task = new Task(id, "タスク1", "説明", TaskStatus.TODO);
            when(taskRepository.findById(id)).thenReturn(Optional.of(task));

            // Act
            Task actual = kanbanService.getTask(id);

            // Assert
            assertThat(actual).isEqualTo(task);
        }

        @Test
        @DisplayName("存在しないタスクを取得しようとするとTaskNotFoundExceptionがスローされる")
        void should_throwTaskNotFoundException_when_taskDoesNotExist() {
            // Arrange
            TaskId id = TaskId.of(999L);
            when(taskRepository.findById(id)).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> kanbanService.getTask(id))
                    .isInstanceOf(TaskNotFoundException.class)
                    .hasMessage("タスクが見つかりません: ID=" + id.value());
        }
    }

    @Nested
    @DisplayName("getBoardView メソッドのテスト")
    class GetBoardViewTest {

        @Test
        @DisplayName("全タスクがステータス別に分類されたKanbanBoardViewを取得できる")
        void should_returnKanbanBoardView_when_getBoardViewCalled() {
            // Arrange
            Task task1 = new Task(TaskId.of(1L), "Todo Task", "", TaskStatus.TODO);
            Task task2 = new Task(TaskId.of(2L), "Doing Task", "", TaskStatus.DOING);
            Task task3 = new Task(TaskId.of(3L), "Done Task", "", TaskStatus.DONE);

            when(taskRepository.findAll()).thenReturn(List.of(task1, task2, task3));

            // Act
            KanbanBoardView boardView = kanbanService.getBoardView();

            // Assert
            assertThat(boardView.todoTasks()).containsExactly(task1);
            assertThat(boardView.doingTasks()).containsExactly(task2);
            assertThat(boardView.doneTasks()).containsExactly(task3);
            assertThat(boardView.totalTaskCount()).isEqualTo(3);
        }
    }
}
