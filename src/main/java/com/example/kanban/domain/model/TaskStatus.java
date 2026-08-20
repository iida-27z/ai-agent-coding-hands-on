package com.example.kanban.domain.model;

import com.example.kanban.domain.exception.ValidationException;

import java.util.Arrays;

/**
 * タスクの進捗ステータスを表現する列挙型。
 */
public enum TaskStatus {
    /**
     * 未着手ステータス。
     */
    TODO("TODO", "未着手", 1),

    /**
     * 進行中ステータス。
     */
    DOING("DOING", "進行中", 2),

    /**
     * 完了ステータス。
     */
    DONE("DONE", "完了", 3);

    private final String code;
    private final String displayName;
    private final int order;

    /**
     * 列挙値のコンストラクタ。
     *
     * @param code        コード文字列
     * @param displayName 日本語表示名
     * @param order       表示順序
     */
    TaskStatus(String code, String displayName, int order) {
        this.code = code;
        this.displayName = displayName;
        this.order = order;
    }

    /**
     * ステータスコードを取得します。
     *
     * @return ステータスコード
     */
    public String getCode() {
        return code;
    }

    /**
     * 日本語表示名を取得します。
     *
     * @return 日本語表示名
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 表示順序を取得します。
     *
     * @return 表示順序
     */
    public int getOrder() {
        return order;
    }

    /**
     * 文字列（コード名または番号）から {@link TaskStatus} を取得します。
     *
     * @param code コード文字列（"TODO", "DOING", "DONE", "1", "2", "3" 等）
     * @return 対応するTaskStatus
     * @throws ValidationException 一致するステータスが存在しない場合、またはnullの場合
     */
    public static TaskStatus fromCode(String code) {
        if (code == null) {
            throw new ValidationException("ステータスコードにnullを指定することはできません。");
        }
        String trimmed = code.trim();
        return switch (trimmed.toUpperCase()) {
            case "TODO", "1" -> TODO;
            case "DOING", "2" -> DOING;
            case "DONE", "3" -> DONE;
            default -> throw new ValidationException("無効なステータス指定です: " + code);
        };
    }
}
