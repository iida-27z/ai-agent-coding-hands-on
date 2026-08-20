package com.example.kanban.ui;

import com.example.kanban.domain.exception.TaskNotFoundException;
import com.example.kanban.domain.exception.ValidationException;
import com.example.kanban.domain.model.Task;
import com.example.kanban.domain.model.TaskId;
import com.example.kanban.domain.model.TaskStatus;
import com.example.kanban.service.KanbanService;
import com.example.kanban.service.dto.CreateTaskCommand;
import com.example.kanban.service.dto.KanbanBoardView;
import com.example.kanban.ui.io.ConsoleIo;
import com.example.kanban.ui.render.KanbanBoardRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("KanbanConsoleApp のテスト")
class KanbanConsoleAppTest {

    @Mock
    private KanbanService kanbanService;

    @Mock
    private ConsoleIo consoleIo;

    private KanbanBoardRenderer renderer;
    private KanbanConsoleApp app;

    @BeforeEach
    void setUp() {
        renderer = new KanbanBoardRenderer();
        app = new KanbanConsoleApp(kanbanService, consoleIo, renderer);
    }

    @Nested
    @DisplayName("メインメニューと終了処理")
    class MainMenuTest {

        @Test
        @DisplayName("メニューで0を選択するとアプリケーションが終了する")
        void should_exitApp_when_zeroSelected() {
            // Arrange
            when(consoleIo.readLine(any())).thenReturn("0");

            // Act
            app.run();

            // Assert
            verify(consoleIo).println("アプリケーションを終了します。お疲れ様でした！");
        }

        @Test
        @DisplayName("無効なメニュー番号が入力された場合、エラーメッセージを表示してループを継続する")
        void should_showErrorMessage_when_invalidMenuOptionSelected() {
            // Arrange
            when(consoleIo.readLine(any())).thenReturn("99", "0");

            // Act
            app.run();

            // Assert
            verify(consoleIo).println("無効な選択です。メニュー番号を入力してください。");
        }
    }

    @Nested
    @DisplayName("カンバンボード表示 (メニュー1)")
    class ShowBoardTest {

        @Test
        @DisplayName("メニュー1を選択するとボードが表示される")
        void should_showBoard_when_menuOneSelected() {
            // Arrange
            KanbanBoardView view = new KanbanBoardView(List.of(), List.of(), List.of());
            when(kanbanService.getBoardView()).thenReturn(view);
            when(consoleIo.readLine(any())).thenReturn("1", "0");

            // Act
            app.run();

            // Assert
            verify(kanbanService).getBoardView();
            verify(consoleIo).println(contains("KANBAN BOARD"));
        }
    }

    @Nested
    @DisplayName("タスク追加 (メニュー2)")
    class AddTaskTest {

        @Test
        @DisplayName("メニュー2を選択してタイトル・説明を入力するとタスクが作成される")
        void should_createTask_when_menuTwoSelected() {
            // Arrange
            when(consoleIo.readLine(any())).thenReturn("2", "新規タスク", "説明文", "0");
            Task createdTask = new Task(TaskId.of(1L), "新規タスク", "説明文", TaskStatus.TODO);
            when(kanbanService.createTask(new CreateTaskCommand("新規タスク", "説明文"))).thenReturn(createdTask);

            // Act
            app.run();

            // Assert
            verify(kanbanService).createTask(new CreateTaskCommand("新規タスク", "説明文"));
            verify(consoleIo).println("タスク #1 を追加しました: 新規タスク");
        }

        @Test
        @DisplayName("バリデーションエラーが発生した場合、エラーメッセージが表示されてアプリが継続する")
        void should_showValidationError_when_blankTitleGiven() {
            // Arrange
            when(consoleIo.readLine(any())).thenReturn("2", "   ", "説明", "0");
            when(kanbanService.createTask(any())).thenThrow(new ValidationException("タスクタイトルは必須項目であり、空白のみにすることはできません。"));

            // Act
            app.run();

            // Assert
            verify(consoleIo).println("エラー: タスクタイトルは必須項目であり、空白のみにすることはできません。");
        }
    }

    @Nested
    @DisplayName("タスクステータス変更 (メニュー3)")
    class ChangeStatusTest {

        @Test
        @DisplayName("メニュー3を選択してIDと新ステータスを入力するとステータスが更新される")
        void should_updateStatus_when_menuThreeSelected() {
            // Arrange
            when(consoleIo.readLine(any())).thenReturn("3", "1", "2", "0");
            Task updatedTask = new Task(TaskId.of(1L), "タスク1", "", TaskStatus.DOING);
            when(kanbanService.updateTaskStatus(TaskId.of(1L), TaskStatus.DOING)).thenReturn(updatedTask);

            // Act
            app.run();

            // Assert
            verify(kanbanService).updateTaskStatus(TaskId.of(1L), TaskStatus.DOING);
            verify(consoleIo).println("タスク #1 のステータスを [進行中] (DOING) に変更しました。");
        }

        @Test
        @DisplayName("存在しないタスクIDを指定した場合、エラーメッセージが表示される")
        void should_showError_when_taskNotFound() {
            // Arrange
            when(consoleIo.readLine(any())).thenReturn("3", "999", "2", "0");
            when(kanbanService.updateTaskStatus(TaskId.of(999L), TaskStatus.DOING))
                    .thenThrow(new TaskNotFoundException("タスクが見つかりません: ID=999"));

            // Act
            app.run();

            // Assert
            verify(consoleIo).println("エラー: タスクが見つかりません: ID=999");
        }
    }

    @Nested
    @DisplayName("タスク削除 (メニュー4)")
    class DeleteTaskTest {

        @Test
        @DisplayName("メニュー4を選択してIDを入力するとタスクが削除される")
        void should_deleteTask_when_menuFourSelected() {
            // Arrange
            when(consoleIo.readLine(any())).thenReturn("4", "1", "0");

            // Act
            app.run();

            // Assert
            verify(kanbanService).deleteTask(TaskId.of(1L));
            verify(consoleIo).println("タスク #1 を削除しました。");
        }

        @Test
        @DisplayName("存在しないタスクIDを指定した場合、エラーメッセージが表示される")
        void should_showError_when_deleteTargetNotFound() {
            // Arrange
            when(consoleIo.readLine(any())).thenReturn("4", "999", "0");
            doThrow(new TaskNotFoundException("削除対象のタスクが見つかりません: ID=999"))
                    .when(kanbanService).deleteTask(TaskId.of(999L));

            // Act
            app.run();

            // Assert
            verify(consoleIo).println("エラー: 削除対象のタスクが見つかりません: ID=999");
        }
    }
}
