package com.example.kanban.domain.model;

import com.example.kanban.domain.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("TaskId のテスト")
class TaskIdTest {

    @Nested
    @DisplayName("生成テスト")
    class CreationTest {

        @Test
        @DisplayName("正の整数値を指定してTaskIdを生成できる")
        void should_createTaskId_when_positiveValueProvided() {
            // Arrange
            long value = 1L;

            // Act
            TaskId taskId = TaskId.of(value);

            // Assert
            assertThat(taskId.value()).isEqualTo(1L);
        }

        @Test
        @DisplayName("数値文字列を指定してTaskIdを生成できる")
        void should_createTaskId_when_validNumericStringProvided() {
            // Arrange
            String value = "42";

            // Act
            TaskId taskId = TaskId.of(value);

            // Assert
            assertThat(taskId.value()).isEqualTo(42L);
        }

        @ParameterizedTest
        @ValueSource(longs = {0L, -1L, -100L})
        @DisplayName("0以下の値を指定した場合、ValidationExceptionがスローされる")
        void should_throwValidationException_when_valueIsZeroOrNegative(long invalidValue) {
            // Arrange & Act & Assert
            assertThatThrownBy(() -> TaskId.of(invalidValue))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage("タスクIDは1以上の正の整数である必要があります: " + invalidValue);
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "  ", "abc", "0", "-1"})
        @DisplayName("不正な文字列を指定した場合、ValidationExceptionがスローされる")
        void should_throwValidationException_when_invalidStringProvided(String invalidString) {
            // Arrange & Act & Assert
            assertThatThrownBy(() -> TaskId.of(invalidString))
                    .isInstanceOf(ValidationException.class);
        }

        @Test
        @DisplayName("null文字列を指定した場合、ValidationExceptionがスローされる")
        void should_throwValidationException_when_nullStringProvided() {
            // Arrange & Act & Assert
            assertThatThrownBy(() -> TaskId.of((String) null))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage("タスクIDにnullを指定することはできません。");
        }
    }

    @Nested
    @DisplayName("等価性テスト")
    class EqualityTest {

        @Test
        @DisplayName("同じ値を持つTaskIdは等価である")
        void should_beEqual_when_sameValue() {
            // Arrange
            TaskId id1 = TaskId.of(1L);
            TaskId id2 = TaskId.of(1L);

            // Act & Assert
            assertThat(id1).isEqualTo(id2);
            assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
        }
    }
}
