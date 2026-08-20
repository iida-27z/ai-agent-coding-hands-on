package com.example.kanban.domain.model;

import com.example.kanban.domain.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Task のテスト")
class TaskTest {

    @Nested
    @DisplayName("生成テスト")
    class CreationTest {

        @Test
        @DisplayName("有効なパラメータでTaskを生成できる（説明あり）")
        void should_createTask_when_validParametersProvided() {
            // Arrange
            TaskId id = TaskId.of(1L);
            String title = "タスクのタイトル";
            String description = "タスクの詳細説明";
            TaskStatus status = TaskStatus.TODO;

            // Act
            Task task = new Task(id, title, description, status);

            // Assert
            assertThat(task.id()).isEqualTo(id);
            assertThat(task.title()).isEqualTo(title);
            assertThat(task.description()).isEqualTo(description);
            assertThat(task.status()).isEqualTo(status);
        }

        @Test
        @DisplayName("説明がnullの場合は空文字として正規化される")
        void should_normalizeDescriptionToEmpty_when_nullProvided() {
            // Arrange
            TaskId id = TaskId.of(1L);
            String title = "タイトル";

            // Act
            Task task = new Task(id, title, null, TaskStatus.TODO);

            // Assert
            assertThat(task.description()).isEqualTo("");
        }

        @Test
        @DisplayName("IDがnullの場合はValidationExceptionがスローされる")
        void should_throwValidationException_when_idIsNull() {
            // Arrange & Act & Assert
            assertThatThrownBy(() -> new Task(null, "タイトル", "説明", TaskStatus.TODO))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage("タスクIDにnullを指定することはできません。");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        @DisplayName("タイトルがnullまたは空白の場合はValidationExceptionがスローされる")
        void should_throwValidationException_when_titleIsNullOrBlank(String invalidTitle) {
            // Arrange & Act & Assert
            assertThatThrownBy(() -> new Task(TaskId.of(1L), invalidTitle, "説明", TaskStatus.TODO))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage("タスクタイトルは必須項目であり、空白のみにすることはできません。");
        }

        @Test
        @DisplayName("ステータスがnullの場合はValidationExceptionがスローされる")
        void should_throwValidationException_when_statusIsNull() {
            // Arrange & Act & Assert
            assertThatThrownBy(() -> new Task(TaskId.of(1L), "タイトル", "説明", null))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage("タスクステータスにnullを指定することはできません。");
        }
    }

    @Nested
    @DisplayName("不変更新メソッドのテスト")
    class MutationTest {

        @Test
        @DisplayName("withStatusで新しいステータスを持つTaskが生成され、元のTaskは変更されない")
        void should_returnNewTaskWithUpdatedStatus_when_withStatusCalled() {
            // Arrange
            Task original = new Task(TaskId.of(1L), "タイトル", "説明", TaskStatus.TODO);

            // Act
            Task updated = original.withStatus(TaskStatus.DOING);

            // Assert
            assertThat(updated.id()).isEqualTo(original.id());
            assertThat(updated.title()).isEqualTo(original.title());
            assertThat(updated.description()).isEqualTo(original.description());
            assertThat(updated.status()).isEqualTo(TaskStatus.DOING);

            assertThat(original.status()).isEqualTo(TaskStatus.TODO);
        }

        @Test
        @DisplayName("withStatusにnullを指定するとValidationExceptionがスローされる")
        void should_throwValidationException_when_withStatusGivenNull() {
            // Arrange
            Task task = new Task(TaskId.of(1L), "タイトル", "説明", TaskStatus.TODO);

            // Act & Assert
            assertThatThrownBy(() -> task.withStatus(null))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage("タスクステータスにnullを指定することはできません。");
        }

        @Test
        @DisplayName("withDetailsでタイトルと説明が更新された新しいTaskが生成される")
        void should_returnNewTaskWithUpdatedDetails_when_withDetailsCalled() {
            // Arrange
            Task original = new Task(TaskId.of(1L), "タイトル", "説明", TaskStatus.TODO);

            // Act
            Task updated = original.withDetails("新タイトル", "新説明");

            // Assert
            assertThat(updated.id()).isEqualTo(original.id());
            assertThat(updated.title()).isEqualTo("新タイトル");
            assertThat(updated.description()).isEqualTo("新説明");
            assertThat(updated.status()).isEqualTo(original.status());
        }
    }
}
