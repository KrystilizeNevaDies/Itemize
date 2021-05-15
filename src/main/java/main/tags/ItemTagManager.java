package main.tags;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jglrxavpok.hephaistos.nbt.NBT;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import main.meta.ItemizeMeta;
import main.tags.behaviour.ItemTagBehavior;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.item.ItemMeta;
import net.minestom.server.item.ItemStack;
import net.minestom.server.utils.time.TimeUnit;

public class ItemTagManager {
	public static Map<String, ItemTagBehavior> registeredTags = new ConcurrentHashMap<>();

	private static boolean initialized = false;

	public static void init() {
		if (ItemTagManager.initialized) {
			return;
		}

		ItemTagManager.initialized = true;

		MinecraftServer.getSchedulerManager()
			.buildTask(ItemTagManager::update)
			.repeat(1, TimeUnit.TICK)
			.schedule();
	}

	public static void update() {
		// Iterate over every item of every player
		for (final Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
			final PlayerInventory inventory = player.getInventory();

			for (int i = 0; i < PlayerInventory.INVENTORY_SIZE; i++) {
				final ItemStack item  = inventory.getItemStack(i);
				final ItemMeta meta = item.getMeta();

				// Check if item meta is correct type
				if ((meta instanceof ItemizeMeta)) {
					final NBTCompound tags = ((ItemizeMeta) meta).getTags();

					// Apply tags
					for (final String tagKey : tags.getKeys()) {

						if (ItemTagManager.registeredTags.containsKey(tagKey)) {

							final ItemTagBehavior behavior = ItemTagManager.registeredTags.get(tagKey);

							final int updateInterval = behavior.getUpdateInterval();

							if ((player.getInstance().getWorldAge() % updateInterval) == 0) {
								ItemTagManager.updateTag(behavior, tags.get(tagKey), player, item, i);
							}
						}
					}
				}
			}
		}
	}

	public static void updateTag(ItemTagBehavior behavior, NBT nbtValue, Player player, ItemStack item, int slot) {
		boolean isVisible = false;
		boolean isEquipped = false;

		switch(slot) {
			case 5:
			case 6:
			case 7:
			case 8:
				isEquipped = true;
			case 45:
				isVisible = true;
				break;
			default:
				if (player.getHeldSlot() == slot) {
					isEquipped = true;
				}
				break;
		}

		if (isVisible) {
			behavior.onVisible(nbtValue, item, player);
		}

		if (isEquipped) {
			behavior.onEquipped(nbtValue, item, player);
		}
	}

	public static void registerItemTag(String key, ItemTagBehavior behavior) {
		ItemTagManager.registeredTags.put(key, behavior);
	}
}
