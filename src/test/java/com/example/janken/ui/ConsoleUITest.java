package com.example.janken.ui;

import com.example.janken.model.GameResult;
import com.example.janken.model.GameRound;
import com.example.janken.model.Hand;
import com.example.janken.model.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ConsoleUI (コンソール入出力) のテスト")
class ConsoleUITest {

    private ByteArrayOutputStream outContent;
    private PrintStream printStream;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        printStream = new PrintStream(outContent, true, StandardCharsets.UTF_8);
    }

    private ConsoleUI createConsoleUI(String inputData) {
        ByteArrayInputStream inContent = new ByteArrayInputStream(inputData.getBytes(StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(inContent, StandardCharsets.UTF_8);
        return new ConsoleUI(scanner, printStream);
    }

    @Test
    @DisplayName("displayWelcomeでウェルカムメッセージが表示されること")
    void should_printWelcomeMessage_when_displayWelcomeIsCalled() {
        ConsoleUI consoleUI = createConsoleUI("");

        consoleUI.displayWelcome();

        assertThat(outContent.toString(StandardCharsets.UTF_8)).contains("じゃんけんゲームへようこそ");
    }

    @Test
    @DisplayName("displayMenuでメニューが表示されること")
    void should_printMenu_when_displayMenuIsCalled() {
        ConsoleUI consoleUI = createConsoleUI("");

        consoleUI.displayMenu();

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertThat(output).contains("1: グー");
        assertThat(output).contains("2: チョキ");
        assertThat(output).contains("3: パー");
        assertThat(output).contains("s: 戦績表示");
        assertThat(output).contains("0: 終了");
    }

    @Test
    @DisplayName("readInputでユーザーの入力行が取得できること")
    void should_readInputLine_when_readInputIsCalled() {
        ConsoleUI consoleUI = createConsoleUI("1\n");

        String input = consoleUI.readInput();

        assertThat(input).isEqualTo("1");
    }

    @Test
    @DisplayName("displayRoundResultで対戦結果が表示されること")
    void should_printRoundResult_when_displayRoundResultIsCalled() {
        ConsoleUI consoleUI = createConsoleUI("");
        GameRound round = new GameRound(Hand.ROCK, Hand.SCISSORS, GameResult.WIN);

        consoleUI.displayRoundResult(round);

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertThat(output).contains("あなた: グー");
        assertThat(output).contains("CPU:");
        assertThat(output).contains("チョキ");
        assertThat(output).contains("結果:   勝利");
    }

    @Test
    @DisplayName("displayScoreで戦績情報一覧が表示されること")
    void should_printScoreSummary_when_displayScoreIsCalled() {
        ConsoleUI consoleUI = createConsoleUI("");
        Score score = new Score(10, 6, 3, 1);

        consoleUI.displayScore(score);

        String output = outContent.toString(StandardCharsets.UTF_8);
        assertThat(output).contains("通算対戦数: 10");
        assertThat(output).contains("勝利:");
        assertThat(output).contains("6 勝");
        assertThat(output).contains("敗北:");
        assertThat(output).contains("3 敗");
        assertThat(output).contains("引き分け:");
        assertThat(output).contains("1 分");
        assertThat(output).contains("60.0 %");
    }

    @Test
    @DisplayName("displayErrorでエラーメッセージが表示されること")
    void should_printErrorMessage_when_displayErrorIsCalled() {
        ConsoleUI consoleUI = createConsoleUI("");

        consoleUI.displayError("無効な入力です");

        assertThat(outContent.toString(StandardCharsets.UTF_8)).contains("エラー: 無効な入力です");
    }

    @Test
    @DisplayName("displayGoodbyeで終了メッセージが表示されること")
    void should_printGoodbyeMessage_when_displayGoodbyeIsCalled() {
        ConsoleUI consoleUI = createConsoleUI("");

        consoleUI.displayGoodbye();

        assertThat(outContent.toString(StandardCharsets.UTF_8)).contains("対戦ありがとうございました");
    }
}
