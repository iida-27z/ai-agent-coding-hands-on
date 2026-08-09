package com.example.janken.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Hand (じゃんけんの手) のテスト")
class HandTest {

    @Nested
    @DisplayName("コードと表示名のテスト")
    class PropertyTest {

        @Test
        @DisplayName("グー(ROCK)のコードが1、表示名が「グー」であること")
        void should_haveCorrectProperties_when_rock() {
            assertThat(Hand.ROCK.getCode()).isEqualTo(1);
            assertThat(Hand.ROCK.getName()).isEqualTo("グー");
        }

        @Test
        @DisplayName("チョキ(SCISSORS)のコードが2、表示名が「チョキ」であること")
        void should_haveCorrectProperties_when_scissors() {
            assertThat(Hand.SCISSORS.getCode()).isEqualTo(2);
            assertThat(Hand.SCISSORS.getName()).isEqualTo("チョキ");
        }

        @Test
        @DisplayName("パー(PAPER)のコードが3、表示名が「パー」であること")
        void should_haveCorrectProperties_when_paper() {
            assertThat(Hand.PAPER.getCode()).isEqualTo(3);
            assertThat(Hand.PAPER.getName()).isEqualTo("パー");
        }
    }

    @Nested
    @DisplayName("勝敗判定(judgeAgainst)のテスト")
    class JudgeTest {

        @ParameterizedTest(name = "{0} vs {1} -> {2}")
        @CsvSource({
                "ROCK, ROCK, DRAW",
                "ROCK, SCISSORS, WIN",
                "ROCK, PAPER, LOSE",
                "SCISSORS, ROCK, LOSE",
                "SCISSORS, SCISSORS, DRAW",
                "SCISSORS, PAPER, WIN",
                "PAPER, ROCK, WIN",
                "PAPER, SCISSORS, LOSE",
                "PAPER, PAPER, DRAW"
        })
        @DisplayName("全手対戦パターンの判定結果が正しいこと")
        void should_judgeCorrectly_when_handsCompared(Hand player, Hand opponent, GameResult expected) {
            // Arrange & Act
            GameResult result = player.judgeAgainst(opponent);

            // Assert
            assertThat(result).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("数値コードからのHand変換(fromCode)のテスト")
    class FromCodeTest {

        @Test
        @DisplayName("有効なコード(1,2,3)から適切なHandを取得できること")
        void should_returnHand_when_validCodeProvided() {
            assertThat(Hand.fromCode(1)).contains(Hand.ROCK);
            assertThat(Hand.fromCode(2)).contains(Hand.SCISSORS);
            assertThat(Hand.fromCode(3)).contains(Hand.PAPER);
        }

        @Test
        @DisplayName("無効なコード(0, 4, -1など)の場合はOptional.emptyを返すこと")
        void should_returnEmpty_when_invalidCodeProvided() {
            assertThat(Hand.fromCode(0)).isEmpty();
            assertThat(Hand.fromCode(4)).isEmpty();
            assertThat(Hand.fromCode(-1)).isEmpty();
        }
    }
}
