package space.yurisi.universecorev2.world.generator;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.World;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class LobbyWorldGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int x, int z, BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);

        chunkData.setBlock(0, 0, 0, Material.AIR);

        return chunkData;
    }
}