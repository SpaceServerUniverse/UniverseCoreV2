package space.yurisi.universecorev2.subplugins.fishingsystem.fishlist;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.fishingsystem.Fish;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishFeed;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishingRarity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseFishList {

    protected List<Fish> fishes = new ArrayList<>();

    public BaseFishList() {
        initFishes();
    }

    @NotNull
    abstract List<Fish> initFishes();

    public Fish getFishByRarityAndFeed(FishingRarity rarity, FishFeed feed) {
        fishes.stream()
                .filter(fish -> fish.rarity() == rarity)
                .filter(fish -> fish.feed() == feed)
                .collect(Collectors.toList());
    }
}
