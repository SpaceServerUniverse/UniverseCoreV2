package space.yurisi.universecorev2.subplugins.fishingsystem.event;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.item.UniverseItem;
import java.util.Random;

public class FishEvent implements Listener {

    @EventHandler
    public void FishEvent(PlayerFishEvent event){

        Player player = event.getPlayer();
        FishHook hook = event.getHook();
        ItemStack fishitem = new ItemStack(Material.COD);
        ItemMeta fishmeta = fishitem.getItemMeta();
        fishmeta.setDisplayName("§eたらたら");
        fishitem.setItemMeta(fishmeta);

        if(event.getCaught() != null && getPlayerFishFeed(player) != 0){

            Item caught = (Item) event.getCaught();
            ItemStack item = caught.getItemStack();
            ItemMeta meta = item.getItemMeta();
            Random rand = new Random();

            int num = rand.nextInt(500);
            int iur = getRareFishingHook(player)[0];
            int isr = getRareFishingHook(player)[1];
            int ir = getRareFishingHook(player)[2];
            int ur = getRareFishingHook(player)[3];
            int sr = getRareFishingHook(player)[4];
            int r = getRareFishingHook(player)[5];

            Material cod = Material.COD;
            Material salmon = Material.SALMON;
            Material clown = Material.TROPICAL_FISH;
            Material puffer = Material.PUFFERFISH;

            if(num <= iur){
                item.setType(Material.DIAMOND_PICKAXE);
                meta.setDisplayName("§d恒星の輝きを放つピッケル");
                meta.addEnchant(Enchantment.EFFICIENCY,4, true);
                meta.addEnchant(Enchantment.UNBREAKING,3,true);
                item.setItemMeta(meta);
                player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("[釣りAI]§a輝いてる§c§l謎のピッケル§r§aが釣れた！"));
            }else if(num <= isr){
                caught.setItemStack(UniverseItem.getItem("repair_cream").getItem());
                player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("[釣りAI]§aねばねばの§c§l修復クリーム§r§aが釣れた！"));
            }else if(num <= ir){
                int num_record = rand.nextInt(16);
                if(num_record == 0){
                    item.setType(Material.MUSIC_DISC_13);
                }else if(num_record == 1){
                    item.setType(Material.MUSIC_DISC_CAT);
                }else if(num_record == 2){
                    item.setType(Material.MUSIC_DISC_BLOCKS);
                }else if(num_record == 3){
                    item.setType(Material.MUSIC_DISC_CHIRP);
                }else if(num_record == 4){
                    item.setType(Material.MUSIC_DISC_FAR);
                }else if(num_record == 5){
                    item.setType(Material.MUSIC_DISC_MALL);
                }else if(num_record == 6){
                    item.setType(Material.MUSIC_DISC_MELLOHI);
                }else if(num_record == 7){
                    item.setType(Material.MUSIC_DISC_STAL);
                }else if(num_record == 8){
                    item.setType(Material.MUSIC_DISC_STRAD);
                }else if(num_record == 9){
                    item.setType(Material.MUSIC_DISC_WARD);
                }else if(num_record == 10){
                    item.setType(Material.MUSIC_DISC_11);
                }else if(num_record == 11){
                    item.setType(Material.MUSIC_DISC_WAIT);
                }else if(num_record == 12){
                    item.setType(Material.MUSIC_DISC_PIGSTEP);
                }else if(num_record == 13){
                    item.setType(Material.MUSIC_DISC_OTHERSIDE);
                }else if(num_record == 14){
                    item.setType(Material.MUSIC_DISC_5);
                }else if(num_record == 15){
                    item.setType(Material.MUSIC_DISC_RELIC);
                }
                player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("[釣りAI]§aん？これは...§c§lレコード§r§aが釣れた！"));
            }else{
                num = rand.nextInt(101) + 1;
                int size = 0;
                String name = "";

                switch(player.getLocation().getWorld().getName()){
                    case "earth":
                        if(num <= ur){
                            switch(rand.nextInt(5)){
                                case 0:
                                    item.setType(clown);
                                    size = rand.nextInt(151) + 100;
                                    meta.setDisplayName("§eマグロ §f" + size + "cm");
                                    name = "マグロ";
                                    break;
                                case 1:
                                    item.setType(clown);
                                    size = rand.nextInt(301) + 300;
                                    meta.setDisplayName("§eホオジロザメ §f" + size + "cm");
                                    name = "ホオジロザメ";
                                    break;
                                case 2:
                                    item.setType(clown);
                                    size = rand.nextInt(81) + 280;
                                    meta.setDisplayName("§eカジキ §f" + size + "cm");
                                    name = "カジキ";
                                    break;
                                case 3:
                                    item.setType(clown);
                                    size = rand.nextInt(231) + 750;
                                    meta.setDisplayName("§eシャチ §f" + size + "cm");
                                    name = "シャチ";
                                    break;
                                case 4:
                                    switch(getPlayerFishFeed(player)){
                                        case 0:
                                            item.setType(clown);
                                            size = rand.nextInt(81) + 280;
                                            meta.setDisplayName("§eカジキ §f" + size + "cm");
                                            name = "カジキ";
                                            break;
                                        case 1:
                                            item.setType(clown);
                                            size = rand.nextInt(26) + 15;
                                            meta.setDisplayName("§eドリアスピス §f" + size + "cm");
                                            name = "ドリアスピス";
                                            break;
                                        case 2:
                                            item.setType(clown);
                                            size = rand.nextInt(301) + 300;
                                            meta.setDisplayName("§eエデスタス §f" + size + "cm");
                                            name = "エデスタス";
                                            break;
                                        case 3:
                                            item.setType(clown);
                                            size = rand.nextInt(101) + 250;
                                            meta.setDisplayName("§eヘリコプリオン §f" + size + "cm");
                                            name = "ヘリコプリオン";
                                            break;
                                        case 4:
                                            item.setType(clown);
                                            size = rand.nextInt(1301) + 1200;
                                            meta.setDisplayName("§eリオプレウロドン §f" + size + "cm");
                                            name = "リオプレウロドン";
                                            break;
                                        case 5:
                                            item.setType(clown);
                                            size = rand.nextInt(1001) + 10000;
                                            meta.setDisplayName("§eニューネッシー §f" + size + "cm");
                                            name = "ニューネッシー";
                                            break;
                                        case 6:
                                            item.setType(clown);
                                            size = rand.nextInt(14101) + 900;
                                            meta.setDisplayName("§eキャディ §f" + size + "cm");
                                            name = "キャディ";
                                            break;
                                    }
                            }
                            item.setItemMeta(meta);
                            Bukkit.broadcastMessage("§a" + player.getName() + "§eが地球で激レア海魚の§l§c" + name + "(" + size + "cm)§r§eを釣りあげた！！！！！");
                            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("[釣りAI]§eこれは！！とんでもない海魚の§5§l" + name + "(" + size + "cm)§r§eが釣れた！"));

                        }else if(num <= sr){
                            switch(rand.nextInt(5)){
                                case 0:
                                    item.setType(salmon);
                                    meta.setDisplayName("§cマダイ");
                                    name = "マダイ";
                                    break;
                                case 1:
                                    item.setType(salmon);
                                    meta.setDisplayName("§cノコギリザメ");
                                    name = "ノコギリザメ";
                                    break;
                                case 2:
                                    item.setType(salmon);
                                    meta.setDisplayName("§cジンベエザメ");
                                    name = "ジンベエザメ";
                                    break;
                                case 3:
                                    item.setType(salmon);
                                    meta.setDisplayName("§cシュモクザメ");
                                    name = "シュモクザメ";
                                    break;
                                case 4:
                                    item.setType(salmon);
                                    meta.setDisplayName("§cコバンザメ");
                                    name = "コバンザメ";
                                    break;
                            }
                            item.setItemMeta(meta);
                            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("[釣りAI]§eわお！！海魚の§d§l" + name + "§r§eが釣れた！"));

                        }else if(num <= r){
                            switch(rand.nextInt(5)){
                                case 0:
                                    item.setType(cod);
                                    meta.setDisplayName("§bサバ");
                                    name = "サバ";
                                    break;
                                case 1:
                                    item.setType(cod);
                                    meta.setDisplayName("§bサケ");
                                    name = "サケ";
                                    break;
                                case 2:
                                    item.setType(cod);
                                    meta.setDisplayName("§bブリ");
                                    name = "ブリ";
                                    break;
                                case 3:
                                    item.setType(cod);
                                    meta.setDisplayName("§bウツボ");
                                    name = "ウツボ";
                                    break;
                                case 4:
                                    item.setType(cod);
                                    meta.setDisplayName("§bカツオ");
                                    name = "カツオ";
                                    break;

                            }
                            item.setItemMeta(meta);
                            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("[釣りAI]§eおっ！海魚の§c§l" + name + "§r§eが釣れた！"));

                        }else{
                            switch(rand.nextInt(9)){
                                case 0:
                                    item.setType(cod);
                                    meta.setDisplayName("§aスズキ");
                                    name = "スズキ";
                                    break;
                                case 1:
                                    item.setType(cod);
                                    meta.setDisplayName("§aオニカサゴ");
                                    name = "オニカサゴ";
                                    break;
                                case 2:
                                    item.setType(cod);
                                    meta.setDisplayName("§aアカエイ");
                                    name = "アカエイ";
                                    break;
                                case 3:
                                    item.setType(puffer);
                                    meta.setDisplayName("§aクロサバフグ");
                                    name = "クロサバフグ";
                                    break;
                                case 4:
                                    item.setType(cod);
                                    meta.setDisplayName("§aサワラ");
                                    name = "サワラ";
                                    break;
                                case 5:
                                    item.setType(cod);
                                    meta.setDisplayName("§aタチウオ");
                                    name = "タチウオ";
                                    break;
                                case 6:
                                    item.setType(cod);
                                    meta.setDisplayName("§aボラ");
                                    name = "ボラ";
                                    break;
                                case 7:
                                    item.setType(cod);
                                    meta.setDisplayName("§aマルアジ");
                                    name = "マルアジ";
                                    break;
                                case 8:
                                    item.setType(cod);
                                    meta.setDisplayName("§aヤリイカ");
                                    name = "ヤリイカ";
                                    break;

                            }
                            item.setItemMeta(meta);
                            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("[釣りAI]§a海魚の§b§l" + name + "§r§aが釣れた！"));
                        }
                    case "lobby":
                        if(num <= ur){
                            switch(rand.nextInt(5)){
                                case 0:
                                    item.setType(clown);
                                    size = rand.nextInt(151 + 100);
                                    meta.setDisplayName("§eマグロ §f" + size + "cm");
                                    name = "マグロ";
                                    break;
                                case 1:
                                    item.setType(clown);
                                    size = rand.nextInt(301) + 300;
                                    meta.setDisplayName("§eホオジロザメ §f" + size + "cm");
                                    name = "ホオジロザメ";
                                    break;
                                case 2:
                                    item.setType(clown);
                                    size = rand.nextInt(81) + 280;
                                    meta.setDisplayName("§eカジキ §f" + size + "cm");
                                    name = "カジキ";
                                    break;
                                case 3:
                                    item.setType(clown);
                                    size = rand.nextInt(231) + 750;
                                    meta.setDisplayName("§eシャチ §f" + size + "cm");
                                    name = "シャチ";
                                    break;
                                case 4:
                                    switch(getPlayerFishFeed(player)){
                                        case 0:
                                            item.setType(clown);
                                            size = rand.nextInt(81) + 280;
                                            meta.setDisplayName("§eカジキ §f" + size + "cm");
                                            name = "カジキ";
                                            break;
                                        case 1:
                                            item.setType(clown);
                                            size = rand.nextInt(26) + 15;
                                            meta.setDisplayName("§eドリアスピス §f" + size + "cm");
                                            name = "ドリアスピス";
                                            break;
                                        case 2:
                                            item.setType(clown);
                                            size = rand.nextInt(301) + 300;
                                            meta.setDisplayName("§eエデスタス §f" + size + "cm");
                                            name = "エデスタス";
                                            break;
                                        case 3:
                                            item.setType(clown);
                                            size = rand.nextInt(101) + 250;
                                            meta.setDisplayName("§eヘリコプリオン §f" + size + "cm");
                                            name = "ヘリコプリオン";
                                            break;
                                        case 4:
                                            item.setType(clown);
                                            size = rand.nextInt(1301) + 1200;
                                            meta.setDisplayName("§eリオプレウロドン §f" + size + "cm");
                                            name = "リオプレウロドン";
                                            break;
                                        case 5:
                                            item.setType(clown);
                                            size = rand.nextInt(1001) + 10000;
                                            meta.setDisplayName("§eニューネッシー §f" + size + "cm");
                                            name = "ニューネッシー";
                                            break;
                                        case 6:
                                            item.setType(clown);
                                            size = rand.nextInt(14101) + 900;
                                            meta.setDisplayName("§eキャディ §f" + size + "cm");
                                            name = "キャディ";
                                            break;
                                    }
                            }
                            item.setItemMeta(meta);
                            Bukkit.broadcastMessage("§a" + player.getName() + "§eが地球で激レア海魚の§l§c" + name + "(" + size + "cm)§r§eを釣りあげた！！！！！");
                            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("[釣りAI]§eこれは！！とんでもない海魚の§5§l" + name + "(" + size + "cm)§r§eが釣れた！"));
                            canUseFishFeed(player);

                        }else if(num <= sr){
                            switch(rand.nextInt(5)){
                                case 0:
                                    item.setType(salmon);
                                    meta.setDisplayName("§cマダイ");
                                    name = "マダイ";
                                    break;
                                case 1:
                                    item.setType(salmon);
                                    meta.setDisplayName("§cノコギリザメ");
                                    name = "ノコギリザメ";
                                    break;
                                case 2:
                                    item.setType(salmon);
                                    meta.setDisplayName("§cジンベエザメ");
                                    name = "ジンベエザメ";
                                    break;
                                case 3:
                                    item.setType(salmon);
                                    meta.setDisplayName("§cシュモクザメ");
                                    name = "シュモクザメ";
                                    break;
                                case 4:
                                    item.setType(salmon);
                                    meta.setDisplayName("§cコバンザメ");
                                    name = "コバンザメ";
                                    break;
                            }
                            item.setItemMeta(meta);
                            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("[釣りAI]§eわお！！海魚の§d§l" + name + "§r§eが釣れた！"));
                            canUseFishFeed(player);

                        }else if(num <= r){
                            switch(rand.nextInt(5)){
                                case 0:
                                    item.setType(cod);
                                    meta.setDisplayName("§bサバ");
                                    name = "サバ";
                                    break;
                                case 1:
                                    item.setType(cod);
                                    meta.setDisplayName("§bサケ");
                                    name = "サケ";
                                    break;
                                case 2:
                                    item.setType(cod);
                                    meta.setDisplayName("§bブリ");
                                    name = "ブリ";
                                    break;
                                case 3:
                                    item.setType(cod);
                                    meta.setDisplayName("§bウツボ");
                                    name = "ウツボ";
                                    break;
                                case 4:
                                    item.setType(cod);
                                    meta.setDisplayName("§bカツオ");
                                    name = "カツオ";
                                    break;

                            }
                            item.setItemMeta(meta);
                            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("[釣りAI]§eおっ！海魚の§c§l" + name + "§r§eが釣れた！"));
                            canUseFishFeed(player);

                        }else{
                            switch(rand.nextInt(9)){
                                case 0:
                                    item.setType(cod);
                                    meta.setDisplayName("§aスズキ");
                                    name = "スズキ";
                                    break;
                                case 1:
                                    item.setType(cod);
                                    meta.setDisplayName("§aオニカサゴ");
                                    name = "オニカサゴ";
                                    break;
                                case 2:
                                    item.setType(cod);
                                    meta.setDisplayName("§aアカエイ");
                                    name = "アカエイ";
                                    break;
                                case 3:
                                    item.setType(puffer);
                                    meta.setDisplayName("§aクロサバフグ");
                                    name = "クロサバフグ";
                                    break;
                                case 4:
                                    item.setType(cod);
                                    meta.setDisplayName("§aサワラ");
                                    name = "サワラ";
                                    break;
                                case 5:
                                    item.setType(cod);
                                    meta.setDisplayName("§aタチウオ");
                                    name = "タチウオ";
                                    break;
                                case 6:
                                    item.setType(cod);
                                    meta.setDisplayName("§aボラ");
                                    name = "ボラ";
                                    break;
                                case 7:
                                    item.setType(cod);
                                    meta.setDisplayName("§aマルアジ");
                                    name = "マルアジ";
                                    break;
                                case 8:
                                    item.setType(cod);
                                    meta.setDisplayName("§aヤリイカ");
                                    name = "ヤリイカ";
                                    break;

                            }
                            item.setItemMeta(meta);
                            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("[釣りAI]§a海魚の§b§l" + name + "§r§aが釣れた！"));
                            canUseFishFeed(player);

                        }
                }
            }
        }
    }

    private static int getPlayerFishFeed(Player player){

        ItemStack item = player.getInventory().getItemInOffHand();
        Material material = item.getType();

        if(material == Material.DIAMOND){
            return 1;
        }else if(material == Material.GOLD_INGOT){
            return 2;
        }else if(material == Material.IRON_INGOT){
            return 3;
        }else if(material == Material.RABBIT_FOOT){
            return 4;
        }else if(material == Material.BREAD){
            return 5;
        }else if(material == Material.ROTTEN_FLESH){
            return 6;
        }
        return 0;
    }

    private static int[] getRareFishingHook(Player player) {

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey("fishing_hook", "custom");
        PersistentDataContainer data = meta.getPersistentDataContainer();

        if(!data.has(key, PersistentDataType.INTEGER)){
            return new int[]{1,2,5,1,10,60};
        }else if(data.get(key, PersistentDataType.INTEGER) == 1){
            return new int[]{1,2,5,3,12,62};
        }else if(data.get(key, PersistentDataType.INTEGER) == 2){
            return new int[]{1,2,5,5,18,65};
        }else if(data.get(key, PersistentDataType.INTEGER) == 3){
            return new int[]{2,4,7,8,20,70};
        }else if(data.get(key, PersistentDataType.INTEGER) == 4){
            return new int[]{3, 10, 20, 10, 30, 80};
        }
        return new int[]{1,2,5,1,10,60};
    }

    private static void canUseFishFeed(Player player) {

        ItemStack item = player.getInventory().getItemInOffHand();
        int feed = getPlayerFishFeed(player);
        String[] feedlist = {"ダイヤモンド","金","鉄","ミミズ","パン","怪しい薬"};

        if(feed == 0) return;
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            item.setAmount(item.getAmount() - 1);
            Bukkit.broadcastMessage("§a餌(" + feedlist[feed - 1] + ")が切れています。");

        }
    }
}

