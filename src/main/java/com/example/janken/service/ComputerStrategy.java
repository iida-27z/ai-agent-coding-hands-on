package com.example.janken.service;

import com.example.janken.model.Hand;

/**
 * コンピュータの手を選択する戦略インターフェース。
 */
public interface ComputerStrategy {

    /**
     * 次のコンピュータの手を決定して返します。
     *
     * @return 選択された Hand
     */
    Hand nextHand();
}
