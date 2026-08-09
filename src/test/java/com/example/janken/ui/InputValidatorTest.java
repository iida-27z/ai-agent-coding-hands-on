package com.example.janken.ui;

import com.example.janken.model.Hand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("InputValidator (入力バリデータ) のテスト")
class InputValidatorTest {

    @Nested
    @DisplayName("手(Hand)のパースのテスト")
    class ParseHandTest {

        @Test
        @DisplayName("入力「1」が Hand.ROCK に変換されること")
        void should_returnRock_when_inputIsOne() {
            Optional<Hand> hand = InputValidator.parseHand("1");

            assertThat(hand).contains(Hand.ROCK);
        }

        @Test
        @DisplayName("入力「2」が Hand.SCISSORS に変換されること")
        void should_returnScissors_when_inputIsTwo() {
            Optional<Hand> hand = InputValidator.parseHand("2");

            assertThat(hand).contains(Hand.SCISSORS);
        }

        @Test
        @DisplayName("入力「3」が Hand.PAPER に変換されること")
        void should_returnPaper_when_inputIsThree() {
            Optional<Hand> hand = InputValidator.parseHand("3");

            assertThat(hand).contains(Hand.PAPER);
        }

        @Test
        @DisplayName("前後に空白が含まれていても正しく Hand にパースされること")
        void should_trimAndParse_when_inputHasSpaces() {
            Optional<Hand> hand = InputValidator.parseHand("  1  ");

            assertThat(hand).contains(Hand.ROCK);
        }

        @ParameterizedTest
        @ValueSource(strings = {"0", "4", "-1", "abc", "", "  ", "123"})
        @DisplayName("不正な文字列や範囲外の数値の場合は Optional.empty を返すこと")
        void should_returnEmpty_when_invalidInput(String input) {
            Optional<Hand> hand = InputValidator.parseHand(input);

            assertThat(hand).isEmpty();
        }

        @Test
        @DisplayName("nullが渡された場合は Optional.empty を返すこと")
        void should_returnEmpty_when_nullInput() {
            Optional<Hand> hand = InputValidator.parseHand(null);

            assertThat(hand).isEmpty();
        }
    }

    @Nested
    @DisplayName("終了コマンド(isQuitCommand)のテスト")
    class QuitCommandTest {

        @ParameterizedTest
        @ValueSource(strings = {"0", "q", "Q", "quit", "QUIT", "exit", "EXIT", " 0 "})
        @DisplayName("終了を表す文字列の場合は true を返すこと")
        void should_returnTrue_when_quitCommand(String input) {
            assertThat(InputValidator.isQuitCommand(input)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"1", "2", "3", "s", "score", "abc", ""})
        @DisplayName("終了コマンドでない場合は false を返すこと")
        void should_returnFalse_when_notQuitCommand(String input) {
            assertThat(InputValidator.isQuitCommand(input)).isFalse();
        }

        @Test
        @DisplayName("nullが渡された場合は false を返すこと")
        void should_returnFalse_when_nullInput() {
            assertThat(InputValidator.isQuitCommand(null)).isFalse();
        }
    }

    @Nested
    @DisplayName("戦績表示コマンド(isScoreCommand)のテスト")
    class ScoreCommandTest {

        @ParameterizedTest
        @ValueSource(strings = {"s", "S", "score", "SCORE", " s "})
        @DisplayName("戦績表示を表す文字列の場合は true を返すこと")
        void should_returnTrue_when_scoreCommand(String input) {
            assertThat(InputValidator.isScoreCommand(input)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"1", "2", "3", "0", "q", "abc", ""})
        @DisplayName("戦績表示コマンドでない場合は false を返すこと")
        void should_returnFalse_when_notScoreCommand(String input) {
            assertThat(InputValidator.isScoreCommand(input)).isFalse();
        }

        @Test
        @DisplayName("nullが渡された場合は false を返すこと")
        void should_returnFalse_when_nullInput() {
            assertThat(InputValidator.isScoreCommand(null)).isFalse();
        }
    }
}
