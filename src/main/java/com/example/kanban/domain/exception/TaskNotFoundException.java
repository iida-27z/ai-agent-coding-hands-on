package com.example.kanban.domain.exception;

/**
 * 対象のタスクが見つからない場合にスローされる例外クラス。
 */
public class TaskNotFoundException extends KanbanException {

    /**
     * 指定されたメッセージを持つ新しい例外を構築します。
     *
     * @param message 詳細メッセージ
     */
    public TaskNotFoundException(String message) {
        super(message);
    }
}
