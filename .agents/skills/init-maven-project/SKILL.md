---
name: init-maven-project
description: MavenベースのJavaプロジェクトの初期ディレクトリ構造とpom.xmlを生成するスキル
---

# Init Maven Project Skill

このスキルは、新規Javaアプリケーション開発のためにMaven標準のディレクトリ構造および `pom.xml` を初期化します。

## 実行手順

1. **ディレクトリ構造の生成**:
   以下のディレクトリを作成します。
   - `src/main/java`
   - `src/main/resources`
   - `src/test/java`
   - `src/test/resources`

2. **`pom.xml` の作成**:
   プロジェクト直下に `pom.xml` を作成します。以下の標準設定を必ず含めること：
   - **Javaバージョン**: 25
   - **テストライブラリ**: JUnit 5 (Jupiter), AssertJ, Mockito
   - **プラグイン**: `maven-compiler-plugin`, `maven-surefire-plugin`

3. **.gitignore の配置**:
   Java / Maven 開発用（`target/`, `.idea/`, `*.class` 等を含む） `.gitignore` を生成します。
