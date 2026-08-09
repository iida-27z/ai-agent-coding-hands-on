package com.example.janken.ui;

import com.example.janken.model.GameResult;
import com.example.janken.model.GameRound;
import com.example.janken.model.Hand;
import com.example.janken.model.Score;
import com.example.janken.service.JankenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GameController (メインループ制御) のテスト")
class GameControllerTest {

    @Mock
    private ConsoleUI consoleUI;

    @Mock
    private JankenService jankenService;

    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(consoleUI, jankenService);
        lenient().when(jankenService.getScore()).thenReturn(new Score(0, 0, 0, 0));
    }

    @Test
    @DisplayName("終了コマンド「0」が入力された場合、ウェルカム表示・メニュー表示・Goodbye表示を行いループを終了すること")
    void should_exitLoop_when_quitCommandEntered() {
        // Arrange
        given(consoleUI.readInput()).willReturn("0");

        // Act
        gameController.run();

        // Assert
        InOrder inOrder = inOrder(consoleUI);
        inOrder.verify(consoleUI).displayWelcome();
        inOrder.verify(consoleUI).displayMenu();
        inOrder.verify(consoleUI).readInput();
        inOrder.verify(consoleUI).displayGoodbye();
        verify(jankenService, never()).playRound(any());
    }

    @Test
    @DisplayName("有効な手「1」が入力された場合、playRoundが呼ばれ、結果が表示されること")
    void should_playRoundAndDisplayResult_when_validHandEntered() {
        // Arrange: 1回目の入力で「1(グー)」、2回目の入力で「0(終了)」
        given(consoleUI.readInput()).willReturn("1", "0");
        GameRound round = new GameRound(Hand.ROCK, Hand.SCISSORS, GameResult.WIN);
        given(jankenService.playRound(Hand.ROCK)).willReturn(round);

        // Act
        gameController.run();

        // Assert
        verify(jankenService).playRound(Hand.ROCK);
        verify(consoleUI).displayRoundResult(round);
        verify(consoleUI).displayGoodbye();
    }

    @Test
    @DisplayName("戦績コマンド「s」が入力された場合、getScoreが呼ばれ、スコアが表示されること")
    void should_displayScore_when_scoreCommandEntered() {
        // Arrange: 1回目の入力で「s」、2回目の入力で「0」
        given(consoleUI.readInput()).willReturn("s", "0");
        Score score = new Score(2, 1, 1, 0);
        given(jankenService.getScore()).willReturn(score);

        // Act
        gameController.run();

        // Assert
        verify(jankenService, atLeastOnce()).getScore();
        verify(consoleUI, atLeastOnce()).displayScore(score);
        verify(consoleUI).displayGoodbye();
    }

    @Test
    @DisplayName("不正な入力の場合、エラーメッセージが表示され、ループロジックが継続すること")
    void should_displayErrorMessage_when_invalidInputEntered() {
        // Arrange: 1回目「invalid」、2回目「0」
        given(consoleUI.readInput()).willReturn("invalid", "0");

        // Act
        gameController.run();

        // Assert
        verify(consoleUI).displayError(anyString());
        verify(consoleUI).displayGoodbye();
    }
}
