package com.example.janken.ui;

import com.example.janken.model.Hand;

import java.util.Optional;
import java.util.Set;

/**
 * ユーザーのコンソール入力文字列の検証およびHand変換を行うユーティリティクラス。
 */
public final class InputValidator {

    private static final Set<String> QUIT_COMMANDS = Set.of("0", "q", "quit", "exit");
    private static final Set<String> SCORE_COMMANDS = Set.of("s", "score");

    private InputValidator() {
        // インスタンス化防止
    }

    /**
     * 入力文字列を Hand に変換します。
     *
     * @param input 入力文字列
     * @return 変換後の Hand (無効な場合は Optional.empty)
     */
    public static Optional<Hand> parseHand(String input) {
        if (input == null) {
            return Optional.empty();
        }
        String trimmed = input.trim();
        try {
            int code = Integer.parseInt(trimmed);
            return Hand.fromCode(code);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * 入力文字列が終了コマンドであるか判定します。
     *
     * @param input 入力文字列
     * @return 終了コマンドの場合 true
     */
    public static boolean isQuitCommand(String input) {
        if (input == null) {
            return false;
        }
        return QUIT_COMMANDS.contains(input.trim().toLowerCase());
    }

    /**
     * 入力文字列が戦績表示コマンドであるか判定します。
     *
     * @param input 入力文字列
     * @return 戦績表示コマンドの場合 true
     */
    public static boolean isScoreCommand(String input) {
        if (input == null) {
            return false;
        }
        return SCORE_COMMANDS.contains(input.trim().toLowerCase());
    }
}
