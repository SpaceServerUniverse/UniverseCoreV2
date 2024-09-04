package space.yurisi.universecorev2.subplugins.rankcounter.utils;

import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.count.*;
import space.yurisi.universecorev2.exception.KillDeathCountNotFoundException;
import space.yurisi.universecorev2.exception.LifeCountNotFoundException;
import space.yurisi.universecorev2.exception.OreCountNotFoundException;
import space.yurisi.universecorev2.exception.PlayerCountNotFoundException;

public class Counters {

    private final DatabaseManager databaseManager;

    private final Count count;

    private final KillDeathCount killDeathCount;

    private final LifeCount lifeCount;

    private final OreCount oreCount;

    private final PlayerCount playerCount;

    public Counters(Count count) throws KillDeathCountNotFoundException, LifeCountNotFoundException, OreCountNotFoundException, PlayerCountNotFoundException {
        this.databaseManager = UniverseCoreV2API.getInstance().getDatabaseManager();
        this.count = count;
        this.killDeathCount = this.databaseManager.getKillDeathCountRepository().getKillDeathCount(count);
        this.lifeCount = this.databaseManager.getLifeCountRepository().getLifeCount(count);
        this.oreCount = this.databaseManager.getOreCountRepository().getOreCount(count);
        this.playerCount = this.databaseManager.getPlayerCountRepository().getPlayerCount(count);
    }

    public Count getCount() {
        return count;
    }

    public KillDeathCount getKillDeathCount() {
        return killDeathCount;
    }

    public LifeCount getLifeCount() {
        return lifeCount;
    }

    public OreCount getOreCount() {
        return oreCount;
    }

    public PlayerCount getPlayerCount() {
        return playerCount;
    }

    public void saveAll(){
        this.databaseManager.getKillDeathCountRepository().updateKillDeathCount(this.getKillDeathCount());
        this.databaseManager.getLifeCountRepository().updateLifeCount(this.getLifeCount());
        this.databaseManager.getOreCountRepository().updateOreCount(this.getOreCount());
        this.databaseManager.getPlayerCountRepository().updatePlayerCount(this.getPlayerCount());
    }
}
