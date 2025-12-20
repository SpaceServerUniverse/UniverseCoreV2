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

#### デバッグ鯖で OP を付与する方法

Docker 上のデバッグ鯖が起動した状態で 

1. `docker compose exec paper rcon-cli` を実行する
2. `op <自分のID>` を実行する

## 権限周り

SpaceServer Universe では権限周りを LuckPerms で管理しています.

LuckPerms には API が公開されており, それを利用することでプラグイン内で権限周りを制御することができます.

UniverseCoreV2 には LuckPerms の API を利用するためのユーティリティクラス **LuckPermsWrapper** が用意されています.

### 運営・開発者かどうかを確認する

運営・開発者かどうかを確認するには LuckPermsWrapper の `isUserInAdminOrDevGroup()` メソッドを使用します.

```java
/**
 * プレイヤーが管理者グループまたは開発者グループに所属しているかどうか確認します
 * @param player　プレイヤー
 * @return いずれかに所属している場合はtrue、所属していない場合はfalse
 */
public static boolean isUserInAdminOrDevGroup(Player player) {
   return player.hasPermission("group.admin") || player.hasPermission("group.developer");
}
```

### 特定のグループに所属しているかどうかを確認する

特定のグループに所属しているかどうかを確認するには LuckPermsWrapper の `isUserInGroup()` メソッドを使用します.

```java
/**
 * プレイヤーが指定したグループに所属しているかどうか確認します
 * @param player プレイヤー
 * @param group グループ名
 * @return 所属している場合はtrue、所属していない場合はfalse
 */
public static boolean isUserInGroup(Player player, String group) {
   return player.hasPermission("group." + group);
}
```

### 権限を持っているかどうかを確認する

権限を持っているかどうかを確認するには Player クラスの `hasPermission()` を使用します.
