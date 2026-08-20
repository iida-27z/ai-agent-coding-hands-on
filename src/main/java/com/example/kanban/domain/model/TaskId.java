package com.example.kanban.domain.model;

import com.example.kanban.domain.exception.ValidationException;

/**
 * タスクの一意な識別子を表現する値オブジェクト。
 *
 * @param value タスクIDの数値（1以上の正の整数）
 */
public record TaskId(long value) {

    /**
     * コンストラクタ。ID値が正の整数であることを検証します。
     *
     * @param value タスクIDの数値
     * @throws ValidationException valueが0以下の場合
     */
    public TaskId {
        if (value <= 0) {
            throw new ValidationException("タスクIDは1以上の正の整数である必要があります: " + value);
        }
    }

    /**
     * 数値から {@link TaskId} を生成するファクトリメソッド。
     *
     * @param value タスクIDの数値
     * @return TaskIdインスタンス
     * @throws ValidationException valueが0以下の場合
     */
    public static TaskId of(long value) {
        return new TaskId(value);
    }

    /**
     * 文字列から {@link TaskId} を生成するファクトリメソッド。
     *
     * @param value タスクIDの文字列表現
     * @return TaskIdインスタンス
     * @throws ValidationException 文字列がnull、空文字、または数値として不正な場合
     */
    public static TaskId of(String value) {
        if (value == null) {
            throw new ValidationException("タスクIDにnullを指定することはできません。");
        }
        try {
            long parsed = Long.parseLong(value.trim());
            return new TaskId(parsed);
        } catch (NumberFormatException e) {
            throw new ValidationException("タスクIDは有効な数値である必要があります: " + value);
        }
    }
}
