package main;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.Position;
import net.minestom.server.world.biomes.Biome;

public class ItemizeTest {
    public static void main(String... args) {
    	final MinecraftServer server = MinecraftServer.init();

    	final InstanceContainer instance = MinecraftServer.getInstanceManager().createInstanceContainer();

    	instance.setChunkGenerator(new DevelopmentChunkGenerator());


    	final GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();

    	handler.addEventCallback(PlayerLoginEvent.class, event -> {
    		event.setSpawningInstance(instance);
    	});




    	handler.addEventCallback(PlayerSpawnEvent.class, event -> {
    		final Player player = event.getPlayer();
    		player.teleport(new Position(0, 5, 0));


    	});

    	server.start("0.0.0.0", 25565);
    }


    private static class DevelopmentChunkGenerator implements ChunkGenerator {
    	@Override
    	public void generateChunkData(@NotNull ChunkBatch batch, int chunkX, int chunkZ) {
    		for (int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
				for (int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
    				batch.setBlock(x, 0, z, Block.STONE);
    				batch.setBlock(x, 1, z, Block.STONE);
    			}
			}
    	}

    	@Override
    	public void fillBiomes(@NotNull Biome[] biomes, int chunkX, int chunkZ) {
    		Arrays.fill(biomes, Biome.PLAINS);
    	}

    	@Override
    	public @Nullable List<ChunkPopulator> getPopulators() {
    		return null;
    	}

    }

}
