package com.example.janken.model;

import java.util.Arrays;
import java.util.Optional;

/**
 * じゃんけんの手（グー、チョキ、パー）を表す列挙型。
 */
public enum Hand {
    ROCK(1, "グー"),
    SCISSORS(2, "チョキ"),
    PAPER(3, "パー");

    private final int code;
    private final String name;

    Hand(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 手の数値コードを取得します。
     *
     * @return 数値コード (1: グー, 2: チョキ, 3: パー)
     */
    public int getCode() {
        return code;
    }

    /**
     * 手の日本語表示名を取得します。
     *
     * @return 表示名
     */
    public String getName() {
        return name;
    }

    /**
     * 相手の手に対して勝敗を判定します。
     *
     * @param opponent 相手の手
     * @return 判定結果 (WIN, LOSE, DRAW)
     */
    public GameResult judgeAgainst(Hand opponent) {
        if (this == opponent) {
            return GameResult.DRAW;
        }
        return switch (this) {
            case ROCK -> (opponent == SCISSORS) ? GameResult.WIN : GameResult.LOSE;
            case SCISSORS -> (opponent == PAPER) ? GameResult.WIN : GameResult.LOSE;
            case PAPER -> (opponent == ROCK) ? GameResult.WIN : GameResult.LOSE;
        };
    }

    /**
     * 数値コードから対応する Hand を取得します。
     *
     * @param code 数値コード
     * @return 対応する Hand (存在しない場合は Optional.empty)
     */
    public static Optional<Hand> fromCode(int code) {
        return Arrays.stream(values())
                .filter(hand -> hand.code == code)
                .findFirst();
    }
}
