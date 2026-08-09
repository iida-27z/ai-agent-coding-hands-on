package com.example.janken.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GameResult (勝敗結果Enum) のテスト")
class GameResultTest {

    @Test
    @DisplayName("WINの表示ラベルが「勝利」であること")
    void should_returnWinLabel_when_gameResultIsWin() {
        // Arrange & Act
        String label = GameResult.WIN.getLabel();

        // Assert
        assertThat(label).isEqualTo("勝利");
    }

    @Test
    @DisplayName("LOSEの表示ラベルが「敗北」であること")
    void should_returnLoseLabel_when_gameResultIsLose() {
        // Arrange & Act
        String label = GameResult.LOSE.getLabel();

        // Assert
        assertThat(label).isEqualTo("敗北");
    }

    @Test
    @DisplayName("DRAWの表示ラベルが「引き分け」であること")
    void should_returnDrawLabel_when_gameResultIsDraw() {
        // Arrange & Act
        String label = GameResult.DRAW.getLabel();

        // Assert
        assertThat(label).isEqualTo("引き分け");
    }
}
