package com.example.janken.service;

import com.example.janken.model.GameResult;
import com.example.janken.model.GameRound;
import com.example.janken.model.Hand;
import com.example.janken.model.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("JankenServiceImpl (じゃんけんサービス実装) のテスト")
class JankenServiceImplTest {

    @Mock
    private ComputerStrategy computerStrategy;

    private JankenService jankenService;

    @BeforeEach
    void setUp() {
        jankenService = new JankenServiceImpl(computerStrategy);
    }

    @Test
    @DisplayName("初期状態でスコアが全項目0であること")
    void should_haveZeroScore_when_newlyInitialized() {
        Score score = jankenService.getScore();

        assertThat(score.totalGames()).isEqualTo(0);
        assertThat(score.wins()).isEqualTo(0);
        assertThat(score.losses()).isEqualTo(0);
        assertThat(score.draws()).isEqualTo(0);
    }

    @Test
    @DisplayName("プレイヤーが勝利した場合、GameRoundとScoreが正しく更新されること")
    void should_playRoundAndUpdateScore_when_playerWins() {
        // Arrange
        given(computerStrategy.nextHand()).willReturn(Hand.SCISSORS);

        // Act
        GameRound round = jankenService.playRound(Hand.ROCK);

        // Assert
        assertThat(round.playerHand()).isEqualTo(Hand.ROCK);
        assertThat(round.computerHand()).isEqualTo(Hand.SCISSORS);
        assertThat(round.result()).isEqualTo(GameResult.WIN);

        Score score = jankenService.getScore();
        assertThat(score.totalGames()).isEqualTo(1);
        assertThat(score.wins()).isEqualTo(1);
        assertThat(score.losses()).isEqualTo(0);
        assertThat(score.draws()).isEqualTo(0);
    }

    @Test
    @DisplayName("プレイヤーが敗北した場合、GameRoundとScoreが正しく更新されること")
    void should_playRoundAndUpdateScore_when_playerLoses() {
        // Arrange
        given(computerStrategy.nextHand()).willReturn(Hand.PAPER);

        // Act
        GameRound round = jankenService.playRound(Hand.ROCK);

        // Assert
        assertThat(round.playerHand()).isEqualTo(Hand.ROCK);
        assertThat(round.computerHand()).isEqualTo(Hand.PAPER);
        assertThat(round.result()).isEqualTo(GameResult.LOSE);

        Score score = jankenService.getScore();
        assertThat(score.totalGames()).isEqualTo(1);
        assertThat(score.wins()).isEqualTo(0);
        assertThat(score.losses()).isEqualTo(1);
        assertThat(score.draws()).isEqualTo(0);
    }

    @Test
    @DisplayName("引き分けの場合、GameRoundとScoreが正しく更新されること")
    void should_playRoundAndUpdateScore_when_draw() {
        // Arrange
        given(computerStrategy.nextHand()).willReturn(Hand.ROCK);

        // Act
        GameRound round = jankenService.playRound(Hand.ROCK);

        // Assert
        assertThat(round.playerHand()).isEqualTo(Hand.ROCK);
        assertThat(round.computerHand()).isEqualTo(Hand.ROCK);
        assertThat(round.result()).isEqualTo(GameResult.DRAW);

        Score score = jankenService.getScore();
        assertThat(score.totalGames()).isEqualTo(1);
        assertThat(score.wins()).isEqualTo(0);
        assertThat(score.losses()).isEqualTo(0);
        assertThat(score.draws()).isEqualTo(1);
    }

    @Test
    @DisplayName("resetScoreが呼ばれると、スコアがリセットされること")
    void should_resetScore_when_resetScoreIsCalled() {
        // Arrange
        given(computerStrategy.nextHand()).willReturn(Hand.SCISSORS);
        jankenService.playRound(Hand.ROCK);
        assertThat(jankenService.getScore().totalGames()).isEqualTo(1);

        // Act
        jankenService.resetScore();

        // Assert
        Score resetScore = jankenService.getScore();
        assertThat(resetScore.totalGames()).isEqualTo(0);
        assertThat(resetScore.wins()).isEqualTo(0);
        assertThat(resetScore.losses()).isEqualTo(0);
        assertThat(resetScore.draws()).isEqualTo(0);
    }
}
