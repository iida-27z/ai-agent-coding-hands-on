package com.example.kanban.domain.model;

import com.example.kanban.domain.exception.ValidationException;

/**
 * カンバンボード上の1つのタスクを表す不変ドメインエンティティ。
 *
 * @param id          タスクID（必須）
 * @param title       タスクタイトル（必須、非空）
 * @param description タスク説明（任意、null時は空文字へ正規化）
 * @param status      タスクステータス（必須）
 */
public record Task(TaskId id, String title, String description, TaskStatus status) {

    /**
     * コンパクトコンストラクタによるバリデーションと正規化。
     *
     * @param id          タスクID
     * @param title       タスクタイトル
     * @param description タスク説明
     * @param status      タスクステータス
     * @throws ValidationException id, title, status が不正な場合
     */
    public Task {
        if (id == null) {
            throw new ValidationException("タスクIDにnullを指定することはできません。");
        }
        if (title == null || title.isBlank()) {
            throw new ValidationException("タスクタイトルは必須項目であり、空白のみにすることはできません。");
        }
        if (status == null) {
            throw new ValidationException("タスクステータスにnullを指定することはできません。");
        }
        description = description != null ? description : "";
    }

    /**
     * ステータスを変更した新しい {@link Task} インスタンスを返却します。
     *
     * @param newStatus 新しいステータス
     * @return ステータスが変更された新しいTask
     * @throws ValidationException newStatusがnullの場合
     */
    public Task withStatus(TaskStatus newStatus) {
        if (newStatus == null) {
            throw new ValidationException("タスクステータスにnullを指定することはできません。");
        }
        return new Task(this.id, this.title, this.description, newStatus);
    }

    /**
     * タイトルおよび説明を変更した新しい {@link Task} インスタンスを返却します。
     *
     * @param newTitle       新しいタイトル
     * @param newDescription 新しい説明
     * @return 詳細情報が変更された新しいTask
     * @throws ValidationException newTitleが不正な場合
     */
    public Task withDetails(String newTitle, String newDescription) {
        return new Task(this.id, newTitle, newDescription, this.status);
    }
}
