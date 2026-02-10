# UniverseCoreV2

[![CI](https://github.com/SpaceServerUniverse/UniverseCoreV2/actions/workflows/ci.yaml/badge.svg)](https://github.com/SpaceServerUniverse/UniverseCoreV2/actions/workflows/ci.yaml)
[![CodeFactor](https://www.codefactor.io/repository/github/spaceserveruniverse/universecorev2/badge)](https://www.codefactor.io/repository/github/spaceserveruniverse/universecorev2)

SpaceServer Universe のコアプラグイン

## セットアップ方法

- デバッグ環境は以下のコマンド 1つで起動するようになっています．
  - 起動するデバッグ環境はフラットワールドで生成され，使用するメモリを少しでも開発環境に割けるよう描画距離などが最適化されています．
  - またこのデバッグ環境はオフラインモードで起動します． Prism Launcher などのランチャーを使用してデバッグ用のサブアカウントを作成しておくと便利です．

```shell
# macOS / Linux / WSL2
./x start

# Windows
.\x.ps1 start

## Windows の場合は PowerShell が実行を嫌がらせで妨害してくることがあります
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope Process
```

## プラグインの導入

- UniverseCoreV2 のビルドは `start` コマンド実行時に同時に実行されて，ビルドされた Jar ファイルは自動的にデバッグ環境に導入されるようになっています．
- UniverseCoreV2 以外のプラグイン (例えば LuckPerms など) を導入したい場合は [`docker/plugins.txt`](./docker/plugins.txt) にプラグインのダウンロード URL を追加すると，それらのプラグインも自動的にデバッグ環境に導入されます．

```text
https://download.luckperms.net/1614/bukkit/loader/LuckPerms-Bukkit-5.5.26.jar
https://github.com/OneWalkDev/Ribbon/releases/download/v0.3.3/Ribbon-v0.3.3.jar
```

## 依存関係

UniverseCoreV2 は以下のプラグインの API を使用しているため, 起動時にこれらのプラグインを導入しておく必要があります.

- [LuckPerms](https://luckperms.net/)

### Discord との連携

1. [Discord Developer Portal](https://discord.com/developers/applications) でアプリケーションを作成します。
2. アプリケーションを開き, 以下の設定を有効/無効にします。
   - `Public Bot` -> 無効 (あなた以外のユーザーが Bot を追加できないようにするため)
   - `Message Content Intent` -> 有効 (メッセージの内容を取得するため)
3. Bot をギルドに招待します。
4. `config.yml` に以下の設定を追加します。
   ```yaml
   universe-discord:
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
