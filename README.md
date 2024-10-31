# UniverseCoreV2

[![CI](https://github.com/SpaceServerUniverse/UniverseCoreV2/actions/workflows/ci.yaml/badge.svg)](https://github.com/SpaceServerUniverse/UniverseCoreV2/actions/workflows/ci.yaml)
[![CodeFactor](https://www.codefactor.io/repository/github/spaceserveruniverse/universecorev2/badge)](https://www.codefactor.io/repository/github/spaceserveruniverse/universecorev2)

SpaceServer Universe のコアプラグイン

**Support: Minecraft 1.21.3**

## 使い方

1. `plugins/` に `UniverseCoreV2.jar` を配置します。
2. [LuckPerms](https://luckperms.net/) を導入します。
3. サーバーを起動する。

## 依存関係

UniverseCoreV2 は以下のプラグインの API を使用しているため, 起動時にこれらのプラグインを導入しておく必要があります.

- [LuckPerms](https://luckperms.net/)

### Discord との連携

1. [Discord Developer Portal](https://discord.com/developers/applications) でアプリケーションを作成します。
2. アプリケーションを開き, 以下の設定を有効/無効にします。
   - `Public Bot` -> 無効 (あなた以外のユーザーが Bot を追加できないようにするため)
   - `Message Content Intent` -> 有効 (メッセージの内容を取得するため)
3. Bot をギルドに招待します。
4. `UniverseCoreV2/subplugins/universe-discord.yml` に以下の設定を追加します。
   ```yaml
   token: ""
   guild-id: ""
   channel-id: ""
   ```
5. サーバーを再起動します。

## ライセンス

このプラグインは GNU General Public License v3.0 の下で公開されています。 詳細は [LICENSE](LICENSE) を参照してください。

<sub>
    Copyright (C) 2024 SpaceServerUniverse
</sub>
