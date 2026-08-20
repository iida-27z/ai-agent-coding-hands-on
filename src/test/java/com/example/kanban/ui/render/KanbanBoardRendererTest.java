package com.example.kanban.ui.render;

import com.example.kanban.domain.model.Task;
import com.example.kanban.domain.model.TaskId;
import com.example.kanban.domain.model.TaskStatus;
import com.example.kanban.service.dto.KanbanBoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("KanbanBoardRenderer のテスト")
class KanbanBoardRendererTest {

    private KanbanBoardRenderer renderer;

    @BeforeEach
    void setUp() {
        renderer = new KanbanBoardRenderer();
    }

    @Test
    @DisplayName("タスクが存在する場合、TODO, DOING, DONE の順に縦並びで整形されて出力される")
    void should_renderBoardWithTasksVertically_when_tasksExist() {
        // Arrange
        Task todoTask1 = new Task(TaskId.of(1L), "ログイン画面の作成", "Spring Securityを用いた認証画面", TaskStatus.TODO);
        Task todoTask2 = new Task(TaskId.of(2L), "データベース設計", "ER図の作成とマイグレーション", TaskStatus.TODO);
        Task doingTask1 = new Task(TaskId.of(3L), "APIエンドポイント実装", "RESTful APIのコントローラー作成", TaskStatus.DOING);
        Task doneTask1 = new Task(TaskId.of(4L), "要件定義", "仕様書とタスク分解の作成", TaskStatus.DONE);

        KanbanBoardView view = new KanbanBoardView(
                List.of(todoTask1, todoTask2),
                List.of(doingTask1),
                List.of(doneTask1)
        );

        // Act
        String rendered = renderer.render(view);

        // Assert
        assertThat(rendered).contains("KANBAN BOARD");
        assertThat(rendered).contains("[ TODO ] (2 tasks)");
        assertThat(rendered).contains("#1: ログイン画面の作成");
        assertThat(rendered).contains("説明: Spring Securityを用いた認証画面");
        assertThat(rendered).contains("#2: データベース設計");
        assertThat(rendered).contains("説明: ER図の作成とマイグレーション");

        assertThat(rendered).contains("[ DOING ] (1 task)");
        assertThat(rendered).contains("#3: APIエンドポイント実装");
        assertThat(rendered).contains("説明: RESTful APIのコントローラー作成");

        assertThat(rendered).contains("[ DONE ] (1 task)");
        assertThat(rendered).contains("#4: 要件定義");
        assertThat(rendered).contains("説明: 仕様書とタスク分解の作成");

        // 順序の検証 (TODO -> DOING -> DONE)
        int todoIndex = rendered.indexOf("[ TODO ]");
        int doingIndex = rendered.indexOf("[ DOING ]");
        int doneIndex = rendered.indexOf("[ DONE ]");
        assertThat(todoIndex).isLessThan(doingIndex);
        assertThat(doingIndex).isLessThan(doneIndex);
    }

    @Test
    @DisplayName("タスクが0件のステータスセクションには (タスクなし) が表示される")
    void should_renderNoTasksNotice_when_sectionIsEmpty() {
        // Arrange
        KanbanBoardView view = new KanbanBoardView(List.of(), List.of(), List.of());

        // Act
        String rendered = renderer.render(view);

        // Assert
        assertThat(rendered).contains("[ TODO ] (0 tasks)");
        assertThat(rendered).contains("[ DOING ] (0 tasks)");
        assertThat(rendered).contains("[ DONE ] (0 tasks)");
        assertThat(rendered).contains("(タスクなし)");
    }

    @Test
    @DisplayName("説明が空のタスクは説明行が出力されないか、適切に表示される")
    void should_renderTaskWithoutDescription_when_descriptionIsEmpty() {
        // Arrange
        Task todoTask = new Task(TaskId.of(1L), "シンプルタスク", "", TaskStatus.TODO);
        KanbanBoardView view = new KanbanBoardView(List.of(todoTask), List.of(), List.of());

        // Act
        String rendered = renderer.render(view);

        // Assert
        assertThat(rendered).contains("#1: シンプルタスク");
        assertThat(rendered).doesNotContain("説明:");
    }
}
