package com.example.kanban.ui.io;

/**
 * コンソール入出力を抽象化するインターフェース。
 */
public interface ConsoleIo {

    /**
     * 文字列を出力します（改行なし）。
     *
     * @param message 出力するメッセージ
     */
    void print(String message);

    /**
     * 文字列を出力し、末尾に改行を追加します。
     *
     * @param message 出力するメッセージ
     */
    void println(String message);

    /**
     * フォーマットされた文字列を出力します。
     *
     * @param format 書式文字列
     * @param args   引数
     */
    void printf(String format, Object... args);

    /**
     * プロンプトを表示してユーザーから1行の文字列入力を取得します。
     *
     * @param prompt プロンプト文字列
     * @return 入力された文字列（EOF等の場合はnull）
     */
    String readLine(String prompt);
}
