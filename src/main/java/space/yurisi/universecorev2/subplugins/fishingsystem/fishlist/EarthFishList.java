package space.yurisi.universecorev2.subplugins.fishingsystem.fishlist;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.fishingsystem.Fish;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishFeed;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishingRarity;

import java.util.ArrayList;
import java.util.List;

public class EarthFishList extends BaseFishList {

    @NotNull
    public List<Fish> initFishes() {
        fishes.add(new Fish("マグロ", 100, 250, FishingRarity.UltraRare, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ホオジロザメ", 300, 600, FishingRarity.UltraRare, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("カジキ", 280, 360, FishingRarity.UltraRare, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("シャチ", 750, 980, FishingRarity.UltraRare, FishFeed.NOTHING, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ドリアスピス", 15, 40, FishingRarity.UltraRare, FishFeed.DIAMOND, ItemStack.of(Material.COD)));
        fishes.add(new Fish("エデスタス", 300, 600, FishingRarity.UltraRare, FishFeed.GOLD_INGOT, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ヘリコブリオン", 250, 350, FishingRarity.UltraRare, FishFeed.IRON_INGOT, ItemStack.of(Material.COD)));
        fishes.add(new Fish("リオレウロドン", 1200, 2500, FishingRarity.UltraRare, FishFeed.DIAMOND, ItemStack.of(Material.COD)));
        fishes.add(new Fish("ニューネッシー", 10000, 11000, FishingRarity.UltraRare, FishFeed.BREAD, ItemStack.of(Material.COD)));
        fishes.add(new Fish("キャディ", 900, 15000, FishingRarity.UltraRare, FishFeed.ROTTEN_FLESH, ItemStack.of(Material.COD)));
        return fishes;
    }
}
