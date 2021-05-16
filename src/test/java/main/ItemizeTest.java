package main;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import main.data.ItemData;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
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
import socialize.PlayerSocialsSystem;
import socialize.tracing.OriginReference;
import socialize.tracing.OriginRegistry;

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
    	
    	Itemize.init(null);
    	PlayerSocialsSystem.init(ORIGIN_REGISTRY);
    	
    	MinecraftServer.getCommandManager().register(new ItemCommand());

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
    
    private static OriginRegistry ORIGIN_REGISTRY = new OriginRegistry() {

    	private volatile Map<OriginReference, NBTCompound> REGISTRY = new ConcurrentHashMap<>();
    	
    	private OriginReference ROOT_REFERENCE = new OriginReference();
    	private NBTCompound ROOT = REGISTRY.put(ROOT_REFERENCE, new NBTCompound());
    	
		@Override
		public OriginReference registerOrigin(NBTCompound origin) {
			
			OriginReference reference = new OriginReference();
			
			REGISTRY.put(reference, origin);
			
			return reference;
		}

		@Override
		public void getOrigin(OriginReference reference, Consumer<NBTCompound> consumer) {
			consumer.accept(REGISTRY.get(reference));
		}

		@Override
		public Future<NBTCompound> getOrigin(OriginReference reference) {
			return CompletableFuture.completedFuture(REGISTRY.get(reference));
		}

		@Override
		public NBTCompound getRootOrigin() {
			return ROOT;
		}

		@Override
		public OriginReference getRootOriginReference() {
			return ROOT_REFERENCE;
		}
	};
    
    private static class ItemCommand extends Command {

		public ItemCommand() {
			super("item");
			
			String[] argRestrictions = ItemData.ITEM_REGISTRY.keySet().toArray(String[]::new);
			
			addSyntax(ItemCommand::run, ArgumentType.Word("id").from(argRestrictions));
			
		}
    	
		private static void run(CommandSender sender, CommandContext context) {
			Player player = sender.asPlayer();
			String ID = context.get("id");
			
			player.getInventory().addItemStack(
				Itemize.getProvider().create(
					ID, 
					PlayerSocialsSystem.getOriginRegistry().getRootOriginReference()
				)
			);
		}
    }
}
