package com.example.janken.service;

import com.example.janken.model.Hand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RandomComputerStrategy (ランダムコンピュータ戦略) のテスト")
class RandomComputerStrategyTest {

    @Test
    @DisplayName("nextHandがnullでないHandを返すこと")
    void should_returnNonNullHand_when_nextHandIsCalled() {
        ComputerStrategy strategy = new RandomComputerStrategy();

        Hand hand = strategy.nextHand();

        assertThat(hand).isNotNull();
    }

    @RepeatedTest(20)
    @DisplayName("繰り返し呼び出した際に必ず有効なHand(ROCK, SCISSORS, PAPER)のいずれかを返すこと")
    void should_returnValidHand_when_calledRepeatedly() {
        ComputerStrategy strategy = new RandomComputerStrategy();

        Hand hand = strategy.nextHand();

        assertThat(hand).isIn(Hand.ROCK, Hand.SCISSORS, Hand.PAPER);
    }
}
