package com.example.kanban.ui.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

/**
 * 標準入出力（System.in, System.out）を利用した {@link ConsoleIo} の標準実装クラス。
 */
public class StandardConsoleIo implements ConsoleIo {

    private final Scanner scanner;
    private final PrintStream out;

    /**
     * 標準入出力を利用するデフォルトコンストラクタ。
     */
    public StandardConsoleIo() {
        this(System.in, System.out);
    }

    /**
     * 任意のInputStreamとPrintStreamを指定するコンストラクタ（テスト等での利用を想定）。
     *
     * @param in  入力ストリーム
     * @param out 出力ストリーム
     */
    public StandardConsoleIo(InputStream in, PrintStream out) {
        this.scanner = new Scanner(Objects.requireNonNull(in, "in must not be null"));
        this.out = Objects.requireNonNull(out, "out must not be null");
    }

    @Override
    public void print(String message) {
        out.print(message);
    }

    @Override
    public void println(String message) {
        out.println(message);
    }

    @Override
    public void printf(String format, Object... args) {
        out.printf(format, args);
    }

    @Override
    public String readLine(String prompt) {
        if (prompt != null && !prompt.isEmpty()) {
            out.print(prompt);
            out.flush();
        }
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;
    }
}
