package com.example.janken.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

@DisplayName("Score (戦績・スコア) のテスト")
class ScoreTest {

    @Test
    @DisplayName("初期状態では対戦数・勝利・敗北・引き分けがすべて0、勝率が0.0%であること")
    void should_haveZeroValues_when_createdInitialScore() {
        Score score = new Score(0, 0, 0, 0);

        assertThat(score.totalGames()).isEqualTo(0);
        assertThat(score.wins()).isEqualTo(0);
        assertThat(score.losses()).isEqualTo(0);
        assertThat(score.draws()).isEqualTo(0);
        assertThat(score.calculateWinRate()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("WINを追加した際、totalGamesとwinsがインクリメントされた新しいScoreを返すこと")
    void should_incrementWins_when_addResultWin() {
        Score score = new Score(0, 0, 0, 0);
        Score updated = score.addResult(GameResult.WIN);

        assertThat(updated.totalGames()).isEqualTo(1);
        assertThat(updated.wins()).isEqualTo(1);
        assertThat(updated.losses()).isEqualTo(0);
        assertThat(updated.draws()).isEqualTo(0);
        // 元のインスタンスは変更されない(不変性)
        assertThat(score.totalGames()).isEqualTo(0);
    }

    @Test
    @DisplayName("LOSEを追加した際、totalGamesとlossesがインクリメントされた新しいScoreを返すこと")
    void should_incrementLosses_when_addResultLose() {
        Score score = new Score(1, 1, 0, 0);
        Score updated = score.addResult(GameResult.LOSE);

        assertThat(updated.totalGames()).isEqualTo(2);
        assertThat(updated.wins()).isEqualTo(1);
        assertThat(updated.losses()).isEqualTo(1);
        assertThat(updated.draws()).isEqualTo(0);
    }

    @Test
    @DisplayName("DRAWを追加した際、totalGamesとdrawsがインクリメントされた新しいScoreを返すこと")
    void should_incrementDraws_when_addResultDraw() {
        Score score = new Score(2, 1, 1, 0);
        Score updated = score.addResult(GameResult.DRAW);

        assertThat(updated.totalGames()).isEqualTo(3);
        assertThat(updated.wins()).isEqualTo(1);
        assertThat(updated.losses()).isEqualTo(1);
        assertThat(updated.draws()).isEqualTo(1);
    }

    @Test
    @DisplayName("勝率が正確に計算されること (例: 3戦2勝 -> 66.6666...%)")
    void should_calculateCorrectWinRate_when_multipleGamesPlayed() {
        Score score = new Score(3, 2, 1, 0);

        double winRate = score.calculateWinRate();

        assertThat(winRate).isCloseTo(66.66666666666666, offset(0.0001));
    }

    @Test
    @DisplayName("10戦5勝5敗の場合、勝率が50.0%になること")
    void should_calculateFiftyPercentWinRate_when_fiveWinsOutofTen() {
        Score score = new Score(10, 5, 5, 0);

        assertThat(score.calculateWinRate()).isEqualTo(50.0);
    }
}
