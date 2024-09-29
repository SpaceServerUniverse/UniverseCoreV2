package space.yurisi.universecorev2.subplugins.fishingsystem.fishlist;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.fishingsystem.Fish;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishFeed;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishingRarity;

import java.util.List;

public class LobbyFishList extends BaseFishList {

    public LobbyFishList(){
        setWorldName("ロビー");
    }

    @Override
    public void initFishes() {
        fishes.add(new Fish("シーラカンス", 180, 250, FishingRarity.UltraRare, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("メガロドン", 1800, 2000, FishingRarity.UltraRare, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("リュウグウノツカイ", 300, 1100, FishingRarity.UltraRare, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("オキナ", 300000, 600000, FishingRarity.UltraRare, FishFeed.DIAMOND, ItemStack.of(Material.COD)));
        fishes.add(new Fish("クラーケン", 20000, 28000, FishingRarity.UltraRare, FishFeed.GOLD_INGOT, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ダイオウイカ", 8000, 18000, FishingRarity.UltraRare, FishFeed.IRON_INGOT, ItemStack.of(Material.COD)));
        fishes.add(new Fish("赤えい", 1200000, 1500000, FishingRarity.UltraRare, FishFeed.RABBIT_FOOT, ItemStack.of(Material.COD)));
        fishes.add(new Fish("カメロケラス", 10000, 12000, FishingRarity.UltraRare, FishFeed.BREAD, ItemStack.of(Material.COD)));
        fishes.add(new Fish("アスピドケロン", 30000, 50000, FishingRarity.UltraRare, FishFeed.ROTTEN_FLESH, ItemStack.of(Material.COD)));

        fishes.add(new Fish("マンボウ", 0, 0, FishingRarity.SuperRare, FishFeed.NOTHING, ItemStack.of(Material.SALMON)));
        fishes.add(new Fish("ミツクリザメ", 0, 0, FishingRarity.SuperRare, FishFeed.NOTHING, ItemStack.of(Material.SALMON)));
        // TODO

        fishes.add(new Fish("スターゲイザーフィッシュ", 0, 0, FishingRarity.Rare, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ブロブフィッシュ", 0, 0, FishingRarity.Rare, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        // TODO

        fishes.add(new Fish("ミドリフサアンコウ", 0, 0, FishingRarity.Normal, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ミドリフサアンコウ", 0, 0, FishingRarity.Normal, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        //TODO

    }
}
