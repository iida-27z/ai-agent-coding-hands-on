package com.example.kanban.service.dto;

/**
 * タスク作成指示を表すコマンドオブジェクト。
 *
 * @param title       タスクタイトル（必須）
 * @param description タスク説明（任意）
 */
public record CreateTaskCommand(String title, String description) {
}
