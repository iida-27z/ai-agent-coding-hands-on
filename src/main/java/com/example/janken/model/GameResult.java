package com.example.janken.model;

/**
 * じゃんけんの対戦結果を表す列挙型。
 */
public enum GameResult {
    WIN("勝利"),
    LOSE("敗北"),
    DRAW("引き分け");

    private final String label;

    GameResult(String label) {
        this.label = label;
    }

    /**
     * 表示用ラベルを取得します。
     *
     * @return 勝敗結果の表示ラベル
     */
    public String getLabel() {
        return label;
    }
}
