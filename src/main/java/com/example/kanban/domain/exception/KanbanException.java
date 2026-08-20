package com.example.kanban.domain.exception;

/**
 * カンバンアプリケーションの基底例外クラス。
 */
public class KanbanException extends RuntimeException {

    /**
     * 指定されたメッセージを持つ新しい例外を構築します。
     *
     * @param message 詳細メッセージ
     */
    public KanbanException(String message) {
        super(message);
    }

    /**
     * 指定されたメッセージと原因を持つ新しい例外を構築します。
     *
     * @param message 詳細メッセージ
     * @param cause   原因
     */
    public KanbanException(String message, Throwable cause) {
        super(message, cause);
    }
}
