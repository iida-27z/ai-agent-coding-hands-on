package com.example.janken.service;

import com.example.janken.model.GameRound;
import com.example.janken.model.Hand;
import com.example.janken.model.Score;

/**
 * じゃんけんゲームのコア処理を提供するサービスインターフェース。
 */
public interface JankenService {

    /**
     * プレイヤーの手を受け取り、コンピュータと対戦を1ラウンド行います。
     * スコアを更新し、対戦結果を返します。
     *
     * @param playerHand プレイヤーの出た手
     * @return ラウンド結果情報
     */
    GameRound playRound(Hand playerHand);

    /**
     * 現在の通算スコア・戦績を取得します。
     *
     * @return 現在の Score
     */
    Score getScore();

    /**
     * 通算スコアを初期状態にリセットします。
     */
    void resetScore();
}
