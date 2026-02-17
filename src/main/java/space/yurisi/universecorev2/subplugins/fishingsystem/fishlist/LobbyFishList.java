package space.yurisi.universecorev2.subplugins.fishingsystem.fishlist;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.fishingsystem.Fish;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishFeed;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishingRarity;

import java.util.List;

public class LobbyFishList extends BaseFishList {

    // TODO: ロビー限定の魚をどこかのワールドへフォールバックするか削除する
    public LobbyFishList(){
        setWorldName("ロビー");
    }

    @Override
    public void initFishes() {
        fishes.add(new Fish("シーラカンス", 180, 250, FishingRarity.UltraRare, FishFeed.NOTHING, ItemStack.of(Material.TROPICAL_FISH)));
        fishes.add(new Fish("メガロドン", 1800, 2000, FishingRarity.UltraRare, FishFeed.NOTHING, ItemStack.of(Material.TROPICAL_FISH)));
        fishes.add(new Fish("リュウグウノツカイ", 300, 1100, FishingRarity.UltraRare, FishFeed.NOTHING, ItemStack.of(Material.TROPICAL_FISH)));
        fishes.add(new Fish("オキナ", 300000, 600000, FishingRarity.UltraRare, FishFeed.DIAMOND, ItemStack.of(Material.TROPICAL_FISH)));
        fishes.add(new Fish("クラーケン", 20000, 28000, FishingRarity.UltraRare, FishFeed.GOLD_INGOT, ItemStack.of(Material.TROPICAL_FISH)));
        fishes.add(new Fish("ダイオウイカ", 8000, 18000, FishingRarity.UltraRare, FishFeed.IRON_INGOT, ItemStack.of(Material.TROPICAL_FISH)));
        fishes.add(new Fish("赤えい", 1200000, 1500000, FishingRarity.UltraRare, FishFeed.RABBIT_FOOT, ItemStack.of(Material.TROPICAL_FISH)));
        fishes.add(new Fish("カメロケラス", 10000, 12000, FishingRarity.UltraRare, FishFeed.BREAD, ItemStack.of(Material.TROPICAL_FISH)));
        fishes.add(new Fish("アスピドケロン", 30000, 50000, FishingRarity.UltraRare, FishFeed.ROTTEN_FLESH, ItemStack.of(Material.TROPICAL_FISH)));

        fishes.add(new Fish("マンボウ", FishingRarity.SuperRare, FishFeed.NOTHING, ItemStack.of(Material.SALMON)));
        fishes.add(new Fish("ミツクリザメ", FishingRarity.SuperRare, FishFeed.NOTHING, ItemStack.of(Material.SALMON)));
        fishes.add(new Fish("ラブカ", FishingRarity.SuperRare, FishFeed.NOTHING, ItemStack.of(Material.SALMON)));
        fishes.add(new Fish("リフィーシードラゴン", FishingRarity.SuperRare, FishFeed.NOTHING, ItemStack.of(Material.SALMON)));
        fishes.add(new Fish("ガラスイカ", FishingRarity.SuperRare, FishFeed.NOTHING, ItemStack.of(Material.SALMON)));

        fishes.add(new Fish("スターゲイザーフィッシュ", FishingRarity.Rare, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ブロブフィッシュ", FishingRarity.Rare, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ホウライエソ", FishingRarity.Rare, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ミズウオ", FishingRarity.Rare, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("オニハダカ", FishingRarity.Rare, FishFeed.NOTHING, ItemStack.of(Material.COD)));

        fishes.add(new Fish("ミドリフサアンコウ", FishingRarity.Normal, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ツボダイ", FishingRarity.Normal, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("デメニギス", FishingRarity.Normal, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ハブロフリューネ・モリス", FishingRarity.Normal, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("フサアンコウ", FishingRarity.Normal, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("コンニャクウオ", FishingRarity.Normal, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ダンゴウオ", FishingRarity.Normal, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ヨミノアシロ", FishingRarity.Normal, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("シンカイクサウオ", FishingRarity.Normal, FishFeed.NOTHING, ItemStack.of(Material.COD)));

    }
}
