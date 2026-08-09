package com.example.janken.service;

import com.example.janken.model.GameResult;
import com.example.janken.model.GameRound;
import com.example.janken.model.Hand;
import com.example.janken.model.Score;

/**
 * JankenService の標準実装クラス。
 */
public class JankenServiceImpl implements JankenService {

    private final ComputerStrategy computerStrategy;
    private Score score;

    /**
     * コンピュータ戦略を注入して初期化します。
     *
     * @param computerStrategy コンピュータの手の決定戦略
     */
    public JankenServiceImpl(ComputerStrategy computerStrategy) {
        this.computerStrategy = computerStrategy;
        this.score = new Score(0, 0, 0, 0);
    }

    @Override
    public GameRound playRound(Hand playerHand) {
        Hand computerHand = computerStrategy.nextHand();
        GameResult result = playerHand.judgeAgainst(computerHand);
        this.score = this.score.addResult(result);
        return new GameRound(playerHand, computerHand, result);
    }

    @Override
    public Score getScore() {
        return this.score;
    }

    @Override
    public void resetScore() {
        this.score = new Score(0, 0, 0, 0);
    }
}
