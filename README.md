# UniverseCoreV2

[![CI](https://github.com/SpaceServerUniverse/UniverseCoreV2/actions/workflows/ci.yaml/badge.svg)](https://github.com/SpaceServerUniverse/UniverseCoreV2/actions/workflows/ci.yaml)

SpaceServer Universe のコアプラグイン

## 使い方

1. `plugins/` に `UniverseCoreV2.jar` を配置します。
2. サーバーを起動し, `plugins/UniverseCoreV2/` に生成された `config.yml` を編集します。 ([設定方法](#discord-との連携))
3. サーバーを起動する。

### Discord との連携

1. [Discord Developer Portal](https://discord.com/developers/applications) でアプリケーションを作成します。
2. アプリケーションを開き, 以下の設定を有効/無効にします。
   - `Public Bot` -> 無効 (あなた以外のユーザーが Bot を追加できないようにするため)
   - `Message Content Intent` -> 有効 (メッセージの内容を取得するため)
3. Bot をギルドに招待します。
4. `config.yml` に以下の設定を追加します。
   ```yaml
   discord:
     token: "Bot のトークン"
     guild: "ギルド ID"
     channel: "チャンネル ID"
   ```
5. サーバーを再起動します。

## ライセンス

このプラグインは GNU General Public License v3.0 の下で公開されています。 詳細は [LICENSE](LICENSE) を参照してください。

<sub>
    Copyright (C) 2024 SpaceServerUniverse
</sub>
