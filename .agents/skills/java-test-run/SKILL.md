---
name: java-test-run
description: JUnit 5を用いた単体テスト・結合テストの実行とトレース解析を行うスキル
---

# Java Test Run Skill

このスキルは、Maven Surefire プラグインを通じてテストを自動実行し、結果を検証・ログ出力するための手順を提供します。

## コマンド手順

1. **全単体テストの実行**:
   ```bash
   mvn test
   ```

2. **特定のテストクラスのみ実行**:
   ```bash
   mvn test -Dtest=AppTest
   ```

3. **テスト失敗の解析ガイド**:
   - テスト失敗時には `target/surefire-reports/` 内のログを確認します。
   - 失敗したテストメソッド名、スタックトレース、および期待値と実際の値（AssertionFailure）の差分を記録します。
   - 修正はテストケースを消去するのではなく、本実装コードまたはテストケースの条件を見直すことで対応します。
