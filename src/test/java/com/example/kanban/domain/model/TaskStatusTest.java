package com.example.kanban.domain.model;

import com.example.kanban.domain.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("TaskStatus のテスト")
class TaskStatusTest {

    @Nested
    @DisplayName("定義とプロパティテスト")
    class PropertyTest {

        @Test
        @DisplayName("3つのステータスが期待される属性値で定義されている")
        void should_haveCorrectProperties_when_defined() {
            // Assert TODO
            assertThat(TaskStatus.TODO.getCode()).isEqualTo("TODO");
            assertThat(TaskStatus.TODO.getDisplayName()).isEqualTo("未着手");
            assertThat(TaskStatus.TODO.getOrder()).isEqualTo(1);

            // Assert DOING
            assertThat(TaskStatus.DOING.getCode()).isEqualTo("DOING");
            assertThat(TaskStatus.DOING.getDisplayName()).isEqualTo("進行中");
            assertThat(TaskStatus.DOING.getOrder()).isEqualTo(2);

            // Assert DONE
            assertThat(TaskStatus.DONE.getCode()).isEqualTo("DONE");
            assertThat(TaskStatus.DONE.getDisplayName()).isEqualTo("完了");
            assertThat(TaskStatus.DONE.getOrder()).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("fromCode変換テスト")
    class FromCodeTest {

        @ParameterizedTest
        @CsvSource({
                "TODO, TODO",
                "todo, TODO",
                "DOING, DOING",
                "doing, DOING",
                "DONE, DONE",
                "done, DONE",
                "1, TODO",
                "2, DOING",
                "3, DONE"
        })
        @DisplayName("有効なコードまたは番号から対応するTaskStatusを取得できる")
        void should_returnTaskStatus_when_validCodeProvided(String code, TaskStatus expected) {
            // Act
            TaskStatus actual = TaskStatus.fromCode(code);

            // Assert
            assertThat(actual).isEqualTo(expected);
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "  ", "UNKNOWN", "4", "-1"})
        @DisplayName("無効なコードを指定した場合、ValidationExceptionがスローされる")
        void should_throwValidationException_when_invalidCodeProvided(String invalidCode) {
            // Arrange & Act & Assert
            assertThatThrownBy(() -> TaskStatus.fromCode(invalidCode))
                    .isInstanceOf(ValidationException.class)
                    .hasMessageContaining("無効なステータス指定です: " + invalidCode);
        }

        @Test
        @DisplayName("nullを指定した場合、ValidationExceptionがスローされる")
        void should_throwValidationException_when_nullProvided() {
            // Arrange & Act & Assert
            assertThatThrownBy(() -> TaskStatus.fromCode(null))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage("ステータスコードにnullを指定することはできません。");
        }
    }
}
