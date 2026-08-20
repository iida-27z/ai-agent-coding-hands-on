package com.example.kanban;

import com.example.kanban.repository.InMemoryTaskRepository;
import com.example.kanban.repository.TaskRepository;
import com.example.kanban.service.KanbanService;
import com.example.kanban.service.KanbanServiceImpl;
import com.example.kanban.service.dto.CreateTaskCommand;
import com.example.kanban.ui.KanbanConsoleApp;
import com.example.kanban.ui.io.ConsoleIo;
import com.example.kanban.ui.io.StandardConsoleIo;
import com.example.kanban.ui.render.KanbanBoardRenderer;

/**
 * カンバンボードコンソールアプリケーションのエントリーポイント。
 */
public class Main {

    /**
     * メインメソッド。アプリケーションの初期化および対話ループを開始します。
     *
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        TaskRepository repository = new InMemoryTaskRepository();
        KanbanService service = new KanbanServiceImpl(repository);
        ConsoleIo consoleIo = new StandardConsoleIo();
        KanbanBoardRenderer renderer = new KanbanBoardRenderer();

        // 初期サンプルデータの投入（初回起動時の使いやすさ向上のため）
        seedSampleData(service);

        KanbanConsoleApp app = new KanbanConsoleApp(service, consoleIo, renderer);
        app.run();
    }

    private static void seedSampleData(KanbanService service) {
        service.createTask(new CreateTaskCommand("ログイン画面の作成", "Spring Securityを用いた認証画面"));
        service.createTask(new CreateTaskCommand("データベース設計", "ER図の作成とマイグレーション"));
        var doingTask = service.createTask(new CreateTaskCommand("APIエンドポイント実装", "RESTful APIのコントローラー作成"));
        var doneTask = service.createTask(new CreateTaskCommand("要件定義", "仕様書とタスク分解の作成"));

        service.updateTaskStatus(doingTask.id(), com.example.kanban.domain.model.TaskStatus.DOING);
        service.updateTaskStatus(doneTask.id(), com.example.kanban.domain.model.TaskStatus.DONE);
    }
}
