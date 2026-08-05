# テストコード規約

本プロジェクトにおける自動テスト方針および JUnit / AssertJ / Mockito 記述ルールです。

## テスト方針 (ユニットテスト)

1. **テスティングフレームワーク**:
   - JUnit (`org.junit.jupiter.api.*`) および AssertJ (`org.assertj.core.api.Assertions.*`) 、Mockito (`org.mockito.*`) を標準使用とすること。
2. **テストの構成 (AAAパターン)**:
   - テストメソッドは Arrange (準備), Act (実行), Assert (検証) の構造を明確に区分して記述すること。
3. **テストメソッド名**:
   - should_[期待される動作]_when_[条件] の形式で、テスト対象の動作と期待結果が直感的にわかる名前をつけること。
   - `@DisplayName`アノテーションで日本語の説明を補足すること。
4. **アサーション**:
   - AssertJ の流れるようなアサーション (例: `assertThat(actual).isEqualTo(expected)`) を使用すること。
5. **モックの使用**:
   - Mockito を使用して外部依存をモック化し、`@Mock`や `@InjectMocks` を使用しながら、テスト対象の単体性を確保すること。
6. **テストの独立性**:
   - 各テストケースは独立して実行可能であり、他のテスト実行順序に依存しないこと。
