package com.example.kanban.domain.exception;

/**
 * 入力バリデーションエラーを表す例外クラス。
 */
public class ValidationException extends KanbanException {

    /**
     * 指定されたメッセージを持つ新しい例外を構築します。
     *
     * @param message 詳細メッセージ
     */
    public ValidationException(String message) {
        super(message);
    }
}
