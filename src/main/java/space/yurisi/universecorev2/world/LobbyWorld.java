package space.yurisi.universecorev2.world;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import space.yurisi.universecorev2.world.generator.LobbyWorldGenerator;

public class LobbyWorld {

    public LobbyWorld(){
        WorldCreator wc = new WorldCreator("lobby");
        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.FLAT);
        wc.generateStructures(false);
        wc.generator(new LobbyWorldGenerator());
        wc.createWorld();
    }
}
