package com.example.janken;

import com.example.janken.service.ComputerStrategy;
import com.example.janken.service.JankenService;
import com.example.janken.service.JankenServiceImpl;
import com.example.janken.service.RandomComputerStrategy;
import com.example.janken.ui.ConsoleUI;
import com.example.janken.ui.GameController;

/**
 * じゃんけんコンソールアプリケーションのメインエントリーポイントクラス。
 */
public class Main {

    /**
     * アプリケーションを起動します。
     *
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        ComputerStrategy computerStrategy = new RandomComputerStrategy();
        JankenService jankenService = new JankenServiceImpl(computerStrategy);
        ConsoleUI consoleUI = new ConsoleUI();
        GameController gameController = new GameController(consoleUI, jankenService);

        gameController.run();
    }
}
