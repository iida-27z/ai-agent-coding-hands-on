package com.example.kanban.service.dto;

import com.example.kanban.domain.model.Task;
import com.example.kanban.domain.model.TaskStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CreateTaskCommand および KanbanBoardView のテスト")
class DtoTest {

    @Test
    @DisplayName("CreateTaskCommandが正しく値を保持する")
    void should_holdValues_when_createTaskCommandCreated() {
        // Arrange & Act
        CreateTaskCommand command = new CreateTaskCommand("タイトル", "説明");

        // Assert
        assertThat(command.title()).isEqualTo("タイトル");
        assertThat(command.description()).isEqualTo("説明");
    }

    @Test
    @DisplayName("KanbanBoardViewが集約情報およびステータス別タスクを正しく返却する")
    void should_returnAggregateInfo_when_kanbanBoardViewGiven() {
        // Arrange
        Task todo1 = new Task(com.example.kanban.domain.model.TaskId.of(1L), "Todo 1", "", TaskStatus.TODO);
        Task todo2 = new Task(com.example.kanban.domain.model.TaskId.of(2L), "Todo 2", "", TaskStatus.TODO);
        Task doing1 = new Task(com.example.kanban.domain.model.TaskId.of(3L), "Doing 1", "", TaskStatus.DOING);
        Task done1 = new Task(com.example.kanban.domain.model.TaskId.of(4L), "Done 1", "", TaskStatus.DONE);

        // Act
        KanbanBoardView view = new KanbanBoardView(
                List.of(todo1, todo2),
                List.of(doing1),
                List.of(done1)
        );

        // Assert
        assertThat(view.totalTaskCount()).isEqualTo(4);
        assertThat(view.tasksFor(TaskStatus.TODO)).containsExactly(todo1, todo2);
        assertThat(view.tasksFor(TaskStatus.DOING)).containsExactly(doing1);
        assertThat(view.tasksFor(TaskStatus.DONE)).containsExactly(done1);
    }
}
