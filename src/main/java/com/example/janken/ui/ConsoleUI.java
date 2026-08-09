package com.example.janken.ui;

import com.example.janken.model.GameRound;
import com.example.janken.model.Score;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 標準入出力インターフェースおよび表示メッセージを管理する UI クラス。
 */
public class ConsoleUI {

    private final Scanner scanner;
    private final PrintStream out;

    /**
     * デフォルトコンストラクタ (System.in, System.out を使用)。
     */
    public ConsoleUI() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        this.out = System.out;
    }

    /**
     * 入出力ストリームを指定可能なコンストラクタ (テスト用)。
     *
     * @param scanner 入力用 Scanner
     * @param out     出力用 PrintStream
     */
    public ConsoleUI(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    /**
     * ウェルカムメッセージを表示します。
     */
    public void displayWelcome() {
        out.println("==========================================");
        out.println("      じゃんけんゲームへようこそ！       ");
        out.println("==========================================");
    }

    /**
     * メニュー選択肢を表示します。
     */
    public void displayMenu() {
        out.println();
        out.println("手を選択してください [1: グー, 2: チョキ, 3: パー, s: 戦績表示, 0: 終了]: ");
    }

    /**
     * ユーザーからの入力行を読込みます。
     *
     * @return 入力文字列 (EOF等の場合は空文字)
     */
    public String readInput() {
        out.print("> ");
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return "";
    }

    /**
     * 1ラウンドの対戦結果を表示します。
     *
     * @param round 対戦ラウンド結果
     */
    public void displayRoundResult(GameRound round) {
        out.println("------------------------------------------");
        out.println("あなた: " + round.playerHand().getName());
        out.println("CPU:   " + round.computerHand().getName());
        out.println("結果:   " + round.result().getLabel());
        out.println("------------------------------------------");
    }

    /**
     * 現在の戦績・スコア情報を表示します。
     *
     * @param score スコア情報
     */
    public void displayScore(Score score) {
        out.println("==========================================");
        out.println("               【 現在の戦績 】           ");
        out.println("------------------------------------------");
        out.println("  通算対戦数: " + score.totalGames() + " 回");
        out.println("  勝利:       " + score.wins() + " 勝");
        out.println("  敗北:       " + score.losses() + " 敗");
        out.println("  引き分け:   " + score.draws() + " 分");
        out.printf("  勝率:       %.1f %%\n", score.calculateWinRate());
        out.println("==========================================");
    }

    /**
     * エラーメッセージを表示します。
     *
     * @param message エラーの内容
     */
    public void displayError(String message) {
        out.println("エラー: " + message);
    }

    /**
     * 終了メッセージを表示します。
     */
    public void displayGoodbye() {
        out.println();
        out.println("対戦ありがとうございました！また遊んでね！");
    }
}
