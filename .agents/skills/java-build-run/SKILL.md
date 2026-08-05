---
name: java-build-run
description: Mavenを用いたJavaアプリケーションのコンパイル・ビルド・実行を行うスキル
---

# Java Build & Run Skill

このスキルは、Mavenを利用してJavaコードのコンパイル、パッケージング、および実行を行うための手順を提供します。

## コマンド手順

1. **プロジェクトのクリーン＆コンパイル**:
   ```bash
   mvn clean compile
   ```
   - コンパイルエラーが発生した場合はログを取得し、問題のあるソースコードと行番号を特定します。

2. **アプリケーションのパッケージング (JAR作成)**:
   ```bash
   mvn clean package -DskipTests
   ```
   - `target/` ディレクトリに実行可能またはライブラリJARが生成されたことを確認します。

3. **アプリケーションの実行**:
   メインクラスを指定して実行する場合：
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.App"
   ```
   またはJARファイルから直接実行：
   ```bash
   java -jar target/<project-name>-<version>.jar
   ```
