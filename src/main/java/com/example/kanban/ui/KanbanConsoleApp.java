package com.example.kanban.ui;

import com.example.kanban.domain.exception.KanbanException;
import com.example.kanban.domain.model.Task;
import com.example.kanban.domain.model.TaskId;
import com.example.kanban.domain.model.TaskStatus;
import com.example.kanban.service.KanbanService;
import com.example.kanban.service.dto.CreateTaskCommand;
import com.example.kanban.service.dto.KanbanBoardView;
import com.example.kanban.ui.io.ConsoleIo;
import com.example.kanban.ui.render.KanbanBoardRenderer;

import java.util.Objects;

/**
 * 対話型カンバンボードコンソールアプリケーションの制御クラス。
 */
public class KanbanConsoleApp {

    private final KanbanService kanbanService;
    private final ConsoleIo consoleIo;
    private final KanbanBoardRenderer renderer;

    /**
     * コンストラクタ。
     *
     * @param kanbanService カンバンサービス
     * @param consoleIo     コンソール入出力抽象
     * @param renderer      ボードレンダラー
     */
    public KanbanConsoleApp(KanbanService kanbanService, ConsoleIo consoleIo, KanbanBoardRenderer renderer) {
        this.kanbanService = Objects.requireNonNull(kanbanService, "kanbanService must not be null");
        this.consoleIo = Objects.requireNonNull(consoleIo, "consoleIo must not be null");
        this.renderer = Objects.requireNonNull(renderer, "renderer must not be null");
    }

    /**
     * コンソールアプリケーションのメイン対話ループを実行します。
     */
    public void run() {
        consoleIo.println("==================================================");
        consoleIo.println("       カンバンボード アプリケーションへようこそ       ");
        consoleIo.println("==================================================");

        boolean running = true;
        while (running) {
            showMenu();
            String input = consoleIo.readLine("メニュー番号を選択してください: ");
            if (input == null || input.trim().equals("0")) {
                consoleIo.println("アプリケーションを終了します。お疲れ様でした！");
                running = false;
                continue;
            }

            switch (input.trim()) {
                case "1" -> showBoard();
                case "2" -> addTask();
                case "3" -> changeStatus();
                case "4" -> deleteTask();
                default -> consoleIo.println("無効な選択です。メニュー番号を入力してください。");
            }
            consoleIo.println("");
        }
    }

    private void showMenu() {
        consoleIo.println("--- メニュー ---");
        consoleIo.println("1. カンバンボードを表示");
        consoleIo.println("2. タスクを追加");
        consoleIo.println("3. タスクのステータスを変更");
        consoleIo.println("4. タスクを削除");
        consoleIo.println("0. 終了");
    }

    private void showBoard() {
        KanbanBoardView boardView = kanbanService.getBoardView();
        String renderedBoard = renderer.render(boardView);
        consoleIo.println(renderedBoard);
    }

    private void addTask() {
        try {
            consoleIo.println("[ タスクの追加 ]");
            String title = consoleIo.readLine("タイトル: ");
            String description = consoleIo.readLine("説明 (省略可): ");
            CreateTaskCommand command = new CreateTaskCommand(title, description);
            Task task = kanbanService.createTask(command);
            consoleIo.println(String.format("タスク #%d を追加しました: %s", task.id().value(), task.title()));
        } catch (KanbanException e) {
            consoleIo.println("エラー: " + e.getMessage());
        }
    }

    private void changeStatus() {
        try {
            consoleIo.println("[ タスクステータスの変更 ]");
            String idStr = consoleIo.readLine("タスクID: ");
            TaskId id = TaskId.of(idStr);

            consoleIo.println("選択可能なステータス:");
            consoleIo.println("  1: TODO (未着手)");
            consoleIo.println("  2: DOING (進行中)");
            consoleIo.println("  3: DONE (完了)");
            String statusStr = consoleIo.readLine("新ステータス (1-3 または TODO/DOING/DONE): ");
            TaskStatus newStatus = TaskStatus.fromCode(statusStr);

            Task updated = kanbanService.updateTaskStatus(id, newStatus);
            consoleIo.println(String.format("タスク #%d のステータスを [%s] (%s) に変更しました。",
                    updated.id().value(), updated.status().getDisplayName(), updated.status().getCode()));
        } catch (KanbanException e) {
            consoleIo.println("エラー: " + e.getMessage());
        }
    }

    private void deleteTask() {
        try {
            consoleIo.println("[ タスクの削除 ]");
            String idStr = consoleIo.readLine("削除するタスクID: ");
            TaskId id = TaskId.of(idStr);
            kanbanService.deleteTask(id);
            consoleIo.println(String.format("タスク #%d を削除しました。", id.value()));
        } catch (KanbanException e) {
            consoleIo.println("エラー: " + e.getMessage());
        }
    }
}
