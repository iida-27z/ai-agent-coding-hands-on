package com.example.janken.service;

import com.example.janken.model.Hand;

import java.util.Random;

/**
 * 乱数を用いてランダムに手（グー・チョキ・パー）を選択する ComputerStrategy の実装クラス。
 */
public class RandomComputerStrategy implements ComputerStrategy {

    private final Random random;

    public RandomComputerStrategy() {
        this.random = new Random();
    }

    /**
     * テスト等でシードを指定可能なコンストラクタ。
     *
     * @param random 乱数生成器
     */
    public RandomComputerStrategy(Random random) {
        this.random = random;
    }

    @Override
    public Hand nextHand() {
        Hand[] hands = Hand.values();
        return hands[random.nextInt(hands.length)];
    }
}
