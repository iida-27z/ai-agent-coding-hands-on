package com.example.kanban;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Main のテスト")
class MainTest {

    @Test
    @DisplayName("Mainを実行すると初期データがロードされ、0入力で正常終了する")
    void should_runSuccessfully_when_mainInvokedWithExitInput() {
        // Arrange
        String input = "1\n0\n";
        var in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();

        var originalIn = System.in;
        var originalOut = System.out;

        try {
            System.setIn(in);
            System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));

            // Act & Assert
            assertThatCode(() -> Main.main(new String[]{}))
                    .doesNotThrowAnyException();

            String output = out.toString(StandardCharsets.UTF_8);
            assertThat(output).contains("カンバンボード アプリケーションへようこそ");
            assertThat(output).contains("KANBAN BOARD");
            assertThat(output).contains("[ TODO ]");
            assertThat(output).contains("[ DOING ]");
            assertThat(output).contains("[ DONE ]");
            assertThat(output).contains("ログイン画面の作成");
            assertThat(output).contains("アプリケーションを終了します。お疲れ様でした！");
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
