# AGENTS.md

## 本ドキュメントの目的
本ドキュメントでは、AIエージェントを用いて Java 25 (Maven) アプリケーションを開発するにあたって、AIエージェントが順守すべき開発フローを記載する。

## 開発フロー
**開発フローは必ず以下の流れに従って**進めてください。
1. **作業用ブランチの作成**: 作業内容を表した`feat/<作業内容>`の形式でブランチを作成すること。<作業内容>は英小文字で、単語間はハイフンで区切ること。
2. **エージェントの実行**: 以下のサブエージェントにより、作業内容に応じたエージェントを順次委譲して、親エージェントはそれを制御すること。また、各サブエージェントの作業完了ごとにコミットを実施すること。
   - 1-planner
   - 2-code-architect
   - 3-tdd-coder
   - 4-reviewer
   - 5-build-runner
   - 6-doc-writer
3. **開発成果物の確認**: すべてのサブエージェントの作業が完了したら、最終的な成果物を確認して、ユーザーに作業内容を報告すること。そのとき、完了できなかった部分は未完了として報告すること。

## 参照ルール・スキル
1. **共通規約 (Rules)**:
   - [git](.agents/rules/git-instructions.md)
   - [java](.agents/rules/java-instructions.md)
   - [test](.agents/rules/test-instructions.md)
2. **定型スキル (Skills)**:
   - [init-maven-project](.agents/skills/init-maven-project/SKILL.md)
   - [java-build-run](.agents/skills/java-build-run/SKILL.md)
   - [java-test-run](.agents/skills/java-test-run/SKILL.md)
3. **サブエージェント (Sub Agents)**:
   - [1-planner](.agents/agents/1-planner.agent.md)
   - [2-code-architect](.agents/agents/2-code-architect.agent.md)
   - [3-tdd-coder](.agents/agents/3-tdd-coder.agent.md)
   - [4-reviewer](.agents/agents/4-reviewer.agent.md)
   - [5-build-runner](.agents/agents/5-build-runner.agent.md)
   - [6-doc-writer](.agents/agents/6-doc-writer.agent.md)
