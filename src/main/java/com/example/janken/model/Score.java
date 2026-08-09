package com.example.janken.model;

/**
 * じゃんけんゲームの通算成績・スコアを管理する Record。
 * 不変オブジェクトとして設計されています。
 *
 * @param totalGames 通算対戦数
 * @param wins       勝利数
 * @param losses     敗北数
 * @param draws      引き分け数
 */
public record Score(int totalGames, int wins, int losses, int draws) {

    /**
     * 対戦結果を追加し、新しい Score インスタンスを取得します。
     *
     * @param result 対戦結果 (WIN, LOSE, DRAW)
     * @return 更新された新しい Score インスタンス
     */
    public Score addResult(GameResult result) {
        return switch (result) {
            case WIN -> new Score(totalGames + 1, wins + 1, losses, draws);
            case LOSE -> new Score(totalGames + 1, wins, losses + 1, draws);
            case DRAW -> new Score(totalGames + 1, wins, losses, draws + 1);
        };
    }

    /**
     * 勝率 (%) を計算して取得します。
     * 対戦数が 0 の場合は 0.0 を返します。
     *
     * @return 勝率 (0.0 ～ 100.0)
     */
    public double calculateWinRate() {
        if (totalGames == 0) {
            return 0.0;
        }
        return (wins / (double) totalGames) * 100.0;
    }
}
