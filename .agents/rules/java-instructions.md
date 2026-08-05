# Java コーディング規約

本プロジェクトで生成・変更する Java コードが準拠すべき品質ガイドラインです。

## 基本方針
1. **Javaバージョン**: 
   - Java 25 の機能（Record, Pattern Matching for switch, sealed classes 等）を活用すること。
2. **命名規則**:
   - クラス名: `UpperCamelCase` (例: `UserService`, `Calculator`)
   - メソッド・変数名: `lowerCamelCase` (例: `calculateTotal`, `itemCount`)
   - 定数: `UPPER_SNAKE_CASE` (例: `MAX_RETRY_COUNT`)
   - パッケージ名: 全て小文字 (例: `com.example.app.service`)
3. **責務分離とレイヤー境界**
   - 単一責任原則の徹底: 変更理由が2つ以上あるクラスは分割必須。
   - 継承の乱用禁止: 継承より委譲を優先。
   - 関心事の分離: 「入出力変換」「業務判断」「永続化」を同じクラスに同居させない。
   - Domainの隔離: 業務ルール（Domain）はInfrastructure（外部依存）に依存させず、副作用も持ち込まない。
4. **カプセル化と不変性**:
   - フィールドは原則 `private` とし、可能な限り `final` にする。
   - 値の保持を目的とするクラスには `record` を検討する。
   - 独自の意味を持つ値は値オブジェクト（Value Object）化する。
5. **テスト容易性と副作用の制御**
  - 非決定要素のDI化: 時刻・UUID・乱数・外部API・ファイルI/Oなどは直接呼ばず、依存注入（DI）や境界化を行う。
  - 参照透過性の維持: ロジックは純粋関数寄りに設計し、StreamやOptional内での副作用を禁止。
  - staticの制限: 新規 static ユーティリティは純粋関数で完結する場合のみ許可。
6. **ドキュメント**:
   - 全てのJavaコードに JavaDocを記述すること。
