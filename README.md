# じゃんけんコンソールアプリ (Janken Console App)

Java 25 および Maven 3.9 を利用して構築された、対話型のじゃんけんコンソールアプリケーションです。

---

## 概要・機能紹介

本アプリケーションは、コンソール上でコンピュータとじゃんけん対戦を行う CLI ゲームです。  
TDD（テスト駆動開発）アプローチおよびドメイン駆動の設計思想に基づいて作成されており、プレゼンテーション層（コンソールUI）とドメインロジック（手・判定・スコア）が明確に分離されています。

### 主な機能
- **対戦機能**: グー・チョキ・パーを選択してコンピュータ（ランダム手）と対戦
- **戦績・スコア管理**: 通算対戦数、勝利数、敗北数、引き分け数、勝率（%）をリアルタイムに記録・表示
- **堅牢な入力ハンドリング**: 不正入力に対するエラーメッセージと再入力要求、大文字小文字を問わないコマンドパース

---

## 動作環境

- **Java**: Java 25 以上 (`OpenJDK 25` / `JDK 25`)
- **Build Tool**: Apache Maven 3.9 以上

---

## ビルド手順

プロジェクトのコンパイル、テスト実行、および実行可能 JAR ファイルの作成を行います。

```bash
mvn clean package
```

ビルドが成功すると、`target/` ディレクトリ配下に `janken-app-1.0.0-SNAPSHOT.jar` が生成されます。

---

## アプリ実行手順

以下のいずれかの方法でアプリケーションを起動できます。

### 1. 実行可能 JAR ファイルによる実行 (推奨)

```bash
java -jar target/janken-app-1.0.0-SNAPSHOT.jar
```

### 2. Exec Maven Plugin による実行

```bash
mvn exec:java
```

---

## 操作・コマンド仕様

起動後、対話プロンプトに以下の入力を行うことで操作できます。

| 操作 / コマンド | 説明 | 入力例 |
|---|---|---|
| **1 / グー / g** | 「グー」を選択して対戦 | `1` |
| **2 / チョキ / c** | 「チョキ」を選択して対戦 | `2` |
| **3 / パー / p** | 「パー」を選択して対戦 | `3` |
| **s / score** | 現在の通算戦績・勝率を表示 | `s`, `score` |
| **q / quit / 0 / exit** | アプリケーションを終了 | `q`, `quit`, `0`, `exit` |

---

## プロジェクト構成・関連ドキュメント

本プロジェクトの設計書、要件定義書、コードレビュー記録は `docs/` ディレクトリに集約されています。

- 📋 [要件定義書・タスク計画](file:///workspace/docs/specs/feat-janken-console-app.md) (`docs/specs/feat-janken-console-app.md`)
- 🏗️ [基本設計書](file:///workspace/docs/archs/feat-janken-console-app.md) (`docs/archs/feat-janken-console-app.md`)
- 🔍 [コードレビュー結果報告書](file:///workspace/docs/reviews/feat-janken-console-app_review.md) (`docs/reviews/feat-janken-console-app_review.md`)

---

## ディレクトリ構造

```
.
├── README.md
├── pom.xml
├── docs/
│   ├── specs/
│   │   └── feat-janken-console-app.md
│   ├── archs/
│   │   └── feat-janken-console-app.md
│   └── reviews/
│       └── feat-janken-console-app_review.md
└── src/
    ├── main/java/com/example/janken/
    │   ├── Main.java
    │   ├── model/       # Hand, GameResult, Score, GameRound
    │   ├── service/     # JankenService, ComputerStrategy
    │   └── ui/          # ConsoleUI, GameController, InputValidator
    └── test/java/com/example/janken/
        ├── model/
        ├── service/
        └── ui/
```
