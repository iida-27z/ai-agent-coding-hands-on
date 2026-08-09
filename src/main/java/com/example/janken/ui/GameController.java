package com.example.janken.ui;

import com.example.janken.model.GameRound;
import com.example.janken.model.Hand;
import com.example.janken.model.Score;
import com.example.janken.service.JankenService;

import java.util.Optional;

/**
 * 全体のゲーム進行メインループを制御するコントローラクラス。
 */
public class GameController {

    private final ConsoleUI consoleUI;
    private final JankenService jankenService;

    public GameController(ConsoleUI consoleUI, JankenService jankenService) {
        this.consoleUI = consoleUI;
        this.jankenService = jankenService;
    }

    /**
     * じゃんけんゲームのメインループを開始します。
     */
    public void run() {
        consoleUI.displayWelcome();

        while (true) {
            consoleUI.displayMenu();
            String input = consoleUI.readInput();

            if (InputValidator.isQuitCommand(input)) {
                Score finalScore = jankenService.getScore();
                if (finalScore != null && finalScore.totalGames() > 0) {
                    consoleUI.displayScore(finalScore);
                }
                consoleUI.displayGoodbye();
                break;
            }

            if (InputValidator.isScoreCommand(input)) {
                Score score = jankenService.getScore();
                consoleUI.displayScore(score);
                continue;
            }

            Optional<Hand> handOpt = InputValidator.parseHand(input);
            if (handOpt.isPresent()) {
                Hand playerHand = handOpt.get();
                GameRound round = jankenService.playRound(playerHand);
                consoleUI.displayRoundResult(round);
            } else {
                consoleUI.displayError("1〜3の手、s(戦績)、または0(終了)を入力してください。");
            }
        }
    }
}
