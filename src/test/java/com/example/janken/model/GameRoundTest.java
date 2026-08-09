package com.example.janken.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GameRound (ラウンド結果) のテスト")
class GameRoundTest {

    @Test
    @DisplayName("プレイヤーの手、コンピュータの手、勝敗結果が正しく保持されること")
    void should_holdCorrectValues_when_gameRoundCreated() {
        // Arrange & Act
        GameRound round = new GameRound(Hand.ROCK, Hand.SCISSORS, GameResult.WIN);

        // Assert
        assertThat(round.playerHand()).isEqualTo(Hand.ROCK);
        assertThat(round.computerHand()).isEqualTo(Hand.SCISSORS);
        assertThat(round.result()).isEqualTo(GameResult.WIN);
    }
}
