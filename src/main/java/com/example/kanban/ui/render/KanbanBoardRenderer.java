package com.example.kanban.ui.render;

import com.example.kanban.domain.model.Task;
import com.example.kanban.domain.model.TaskStatus;
import com.example.kanban.service.dto.KanbanBoardView;

import java.util.List;

/**
 * カンバンボードのステータス別タスク情報を縦並びのコンソール用文字列にフォーマットするレンダラー。
 */
public class KanbanBoardRenderer {

    private static final String BORDER_LINE = "==================================================";
    private static final String SECTION_LINE = "--------------------------------------------------";

    /**
     * カンバンボードビューを縦並びの文字列にレンダリングします。
     *
     * @param boardView カンバンボードビュー
     * @return レンダリングされた文字列
     */
    public String render(KanbanBoardView boardView) {
        StringBuilder sb = new StringBuilder();
        sb.append(BORDER_LINE).append("\n");
        sb.append("                 KANBAN BOARD                     \n");
        sb.append(BORDER_LINE).append("\n\n");

        renderSection(sb, TaskStatus.TODO, boardView.tasksFor(TaskStatus.TODO));
        sb.append("\n");
        renderSection(sb, TaskStatus.DOING, boardView.tasksFor(TaskStatus.DOING));
        sb.append("\n");
        renderSection(sb, TaskStatus.DONE, boardView.tasksFor(TaskStatus.DONE));
        sb.append("\n");

        sb.append(BORDER_LINE);
        return sb.toString();
    }

    private void renderSection(StringBuilder sb, TaskStatus status, List<Task> tasks) {
        int count = tasks.size();
        String taskCountText = count == 1 ? "1 task" : count + " tasks";
        sb.append(String.format("[ %s ] (%s)\n", status.getCode(), taskCountText));
        sb.append(SECTION_LINE).append("\n");

        if (tasks.isEmpty()) {
            sb.append("  (タスクなし)\n");
            return;
        }

        for (Task task : tasks) {
            sb.append(String.format("  #%d: %s\n", task.id().value(), task.title()));
            if (!task.description().isBlank()) {
                sb.append(String.format("      説明: %s\n", task.description()));
            }
        }
    }
}
