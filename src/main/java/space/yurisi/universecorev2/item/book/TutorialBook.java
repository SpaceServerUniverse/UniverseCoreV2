package space.yurisi.universecorev2.item.book;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import space.yurisi.universecorev2.item.CustomItem;

public class TutorialBook extends CustomItem {

    public static final String id = "tutorial_book";

    public TutorialBook() {
        super(id, "§eガイドブック", ItemStack.of(Material.WRITTEN_BOOK));
    }


    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> {
            BookMeta meta = (BookMeta) item.getItemMeta();
            if (meta != null) {
                meta.setUnbreakable(true);
                meta.setTitle("§eSpaceServerUniverse ガイド");
                meta.setAuthor("§6Universe運営チーム ver.1.0.0");

                // ページ1: タイトルと基本コマンド
                meta.addPage("""
                        §l§d☆§6 コマンド一覧 §d☆§r
                
                        §6【基本コマンド】§r
                
                        §9/menu§r (§3/m§r)
                        メインメニューを開きます
                
                        §9/password§r <パスワードの文字列>
                        パスワードを設定します
                
                        §9/pplayer§r
                        あなたの役職を確認します
                        """
                );
                meta.addPage("""   
                        §9/lobby§r
                        ロビーに戻ります
                        """);

                // ページ2: 経済システム・土地
                meta.addPage("""
                        §6【経済システム】§r
                        §9/mymoney§r
                        所持金を確認
                        
                        §9/pay§r <プレイヤー名> <金額>
                        指定したプレイヤーにお金を払います
                        
                        §9/seemoney§r <プレイヤー名>
                        他のプレイヤーのお金を確認します
                        """);
                meta.addPage("""
                        §6【土地】§r
                        §9/land§r
                        土地を管理します
                        """);

                // ページ3: テレポート・ユーティリティ
                meta.addPage("""
                        §6【ワープシステム】§r
                        §9/mywarp§r (§3/mw§r)
                        マイワープの作成、管理を行います
                        
                        §9/xtp§r <x> <y> <z> <ワールド名>
                        指定したワールド、座標にテレポートします
                        
                        §9/tpp§r
                        プレイヤー間移動を行います
                        """);
                meta.addPage("""
                        §6【ユーティリティ】§r
                        
                        §9/trash§r (§3/gomi§r)
                        アイテムを捨てることができるゴミ箱を開きます
                        
                        §9/dice§r <最小値> <最大値>
                        サイコロを振ります
                        
                        §9/hat§r
                        手に持っているアイテムを頭にかぶります
                        """);

                // ページ4: ユーティリティ続き
                meta.addPage("""
                        §9/tag§r <名前>
                        称号を設定します
                        
                        §9/repair§r
                        アイテムを修復します
                        
                        §9/autocollect§r (§3/ac§r)
                        自動収集機能のオンオフを切り替えます
                        """);
                meta.addPage("""
                        §6【チェスト保護】§r
                        
                        §9/lock§r
                        コンテナ等を保護します
                        
                        §9/unlock§r
                        コンテナ等の保護を解除します
                        """);

                // ページ5: ショップ・武器
                meta.addPage("""
                        §6【ショップ・取引】§r
                        
                        §9/market§r
                        フリーマーケットの画面を開きます
                        
                        §9/item§r
                        右手に持ったアイテムの名前を表示します
                        
                        §9/head§r
                        プレイヤーヘッドを購入します(WIP)
                        """);
                meta.addPage("""
                        §6【ガチャ】§r
                        §9/gacha§r
                        ガチャを引きます！
                        """);

                // ページ6: その他・情報
                meta.addPage("""
                        §5【その他】§r
                        
                        §9/ammo§r
                        弾薬管理メニューを開きます
                        
                        §9/receive§r
                        アイテム受取ボックスを表示します
                        """);
                meta.addPage("""
                        §9/achievement§r
                        実績メニューを開きます
                        
                        §9/birthday§r
                        お誕生日カードメニューを開きます
                        
                        §9/achievement§r
                        実績メニュー
                        
                        §9/su§r
                        自滅します
                        """);

                // ページ7: リンク・顔文字
                meta.addPage("""
                        §6【情報・リンク】§r
                        
                        §9/wiki§r
                        Wiki へのURLを表示します
                        
                        §9/discord§r
                        Discord の招待URLを表示します
                        
                        §9/web§r
                        UniverseWeb へのURLを表示します
                        """);
                meta.addPage("""
                        §6【顔文字】§r
                        
                        §9/k§r
                        こんにちはの顔文字を送信します
                        §9/oti§r
                        おちるときのの顔文字を送信します
                        §9/otu§r
                        おつかれの顔文字を送信します
                        """);

                // 星のガイド
                meta.addPage(
                        """
                                "§l§d☆§6 宇宙の歩き方 §d☆§r
                                
                                §aロビー§r
                                私たちが最初に搭乗している宇宙船。ここからいろいろな星に行ける。
                                
                                §a地球§r
                                青く美しい惑星。生命が豊かだが、資源が限られているため居住エリアとされている。
                                """);
                meta.addPage(
                        """
                                §a月§r
                                地球の唯一の衛星。重力が弱く、エンドストーンが豊富に採取できる。
                                
                                §a太陽§r
                                太陽系の中心に位置する恒星。常に高温にさらされており、ネザーラックが大量に手に入る。
                                """
                );

                // 土地の購入方法
                meta.addPage(
                        """
                                "§l§d☆§e 土地の購入方法 §d☆§r
                                
                                §6土地の購入手順§r
                                1. §9/land§r コマンドを使用する
                                2. 購入したい土地の四隅のどれかをクリック
                                3. 対角線上のもう一方の角をクリック
                                4. §9/land buy§r コマンドで購入完了
                                """
                );
                meta.addPage(
                        """
                                §6土地の管理§r
                                - §9/land invite <プレイヤー名> §r: 指定したプレイヤーを現在いる土地の管理者に追加します
                                - §9/land kick <プレイヤー名> §r: 指定したプレイヤーの現在いる土地の管理権限を剝奪します
                                """);
                meta.addPage(
                        """
                                - §9/land transfer <プレイヤー名> §r: 指定したプレイヤーに現在いる土地の所有権を譲渡します
                                - §9/land sell §r: 現在いる土地を売却します(購入価格の50%が返金されます)
                                - §9/land here §r: 現在いる土地の情報を表示します
                                """);
                meta.addPage(
                        """
                                §6注意事項§r
                                - 一部ワールドでは土地購入が制限されている場合があります
                                - 不正行為が発覚した場合、土地が没収されることがあります
                                - また、購入して長期間放置された土地は管理者により回収されることがあります
                                """
                );

                // ガチャ
                meta.addPage(
                        """
                                "§l§d☆§6 ガチャの遊び方 §d☆§r
                                
                                §6ガチャの基本§r
                                ガチャは様々なアイテムをランダムに入手できるシステムです。
                                ガチャを引くにはガチャチケットが必要です。
                                """);
                meta.addPage(
                        """
                                §6ガチャの引き方§r
                                1. §9/gacha§r コマンドを使用します
                                2. 現在開催されているガチャが表示されます
                                3. 引きたいガチャを選択し、ガチャチケットを使用して引きます
                                """
                );
                meta.addPage(
                        """
                                §6注意事項§r
                                - イベントガチャの内容は定期的に更新されます
                                - インベントリが満杯の時は、プレゼントボックスにアイテムが送られます
                                """
                );

                // 禁止事項
                meta.addPage(
                        """
                                "§l§d☆§6 禁止事項 §d☆§r
                                
                                §6サーバー内での禁止行為§r
                                - チートや不正なMODの使用
                                - 他のプレイヤーへの迷惑行為や嫌がらせ
                                - 不適切な言動や差別的な発言、宗教・政治的な議論
                                """
                );
                meta.addPage(
                        """
                                - サーバーの運営を妨害する行為
                                - スパム行為や広告行為
                                - バグの悪用
                                """
                );
                meta.addPage(
                        """
                                §6土地に関する禁止事項§r
                                - 他人の土地への無断侵入や破壊行為
                                - 不正な方法で土地を取得する行為
                                - 卑猥な建築物の設置
                                """
                );
                meta.addPage(
                        """
                                §6違反時の対応§r
                                - 警告、土地没収、一時的または永久的なBANなどの処置が取られます
                                - 運営チームの判断により、その他の対応が行われる場合があります
                                - これらの行為を発見した場合、速やかに運営チームに報告してください
                                """
                );

                // さいご
                meta.addPage(
                        """
                                "§l§d☆§6 最後に §d☆§r
                                
                                §1さあ、宇宙の旅へ出発しましょう！
                                """
                );

                // 更新履歴
                meta.addPage(
                        """
                                "§l§d☆§6 更新履歴 §d☆§r
                                ver 1.0.0 - 初版リリース
                                """
                );

                item.setItemMeta(meta);
            }
            return item;
        };
    }
}
