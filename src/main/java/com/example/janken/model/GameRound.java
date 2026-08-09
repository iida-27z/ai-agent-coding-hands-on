package com.example.janken.model;

/**
 * 1回のじゃんけん対戦の結果情報を保持する Record。
 *
 * @param playerHand   プレイヤーが出した手
 * @param computerHand コンピュータが出した手
 * @param result       対戦結果 (WIN, LOSE, DRAW)
 */
public record GameRound(Hand playerHand, Hand computerHand, GameResult result) {
}
