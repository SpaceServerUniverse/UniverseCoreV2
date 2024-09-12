# UniverseCoreV2 開発ガイド

## 環境構築

1. リポジトリを Fork する (リポジトリ画面右上の `Fork` ボタンを押す)
   - `SpaceServerUniverse` のメンバーは Fork する必要はありません
2. リポジトリをローカルにクローンする
   ```bash
   git clone https://github.com/<あなたのGitHub ID>/UniverseCoreV2.git
   ```
3. リポジトリをクローンしたディレクトリに移動してコンパイルする
   ```bash
   cd UniverseCoreV2
   ./gradlew shadowJar
   ```
4. `build/libs` ディレクトリに `UniverseCoreV2-<バージョン>-all.jar` が生成されます

> [!NOTE]
>
> `./gradlew build` でコンパイルされる成果物は, 依存ライブラリを含まないため実行時にエラーが発生します.

### デバッグ用の Minecraft サーバーを起動する

Docker を利用してデバッグ用の Minecraft サーバーを起動できます.

1. Docker をインストールする ([インストール方法](https://docs.docker.com/engine/install/))
2. `docker` ディレクトリに移動する
3. `docker compose up -d` を実行する

`docker` ディレクトリ配下に Paper, DB, phpbyadmin のディレクトリが生成され, それぞれのコンテナが起動します.

UniverseCoreV2 をデバッグする際は次の手順を踏みます.

1. `./gradlew shadowJar` を実行してビルドする
2. `docker/paper/plugins` ディレクトリにビルドした jar ファイルをコピーする ( `/build/libs/` にあります)
3. `docker compose restart` を実行する
4. `localhost:25565` で Minecraft サーバーに接続する

