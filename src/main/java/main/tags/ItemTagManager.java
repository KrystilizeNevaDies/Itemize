package main.tags;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import main.tags.behaviour.ItemTagBehavior;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerTickEvent;

public class ItemTagManager {
	public static Map<String, ItemTagBehavior> ITEMTAG_REGISTRY = new ConcurrentHashMap<>();

	private static boolean initialized = false;

	public static void init() {
		if (ItemTagManager.initialized) {
			return;
		}

		ItemTagManager.initialized = true;

		MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerTickEvent.class, ItemTagManager::update);
	}

	public static void update(PlayerTickEvent event) {
		final Player player = event.getPlayer();
		// TODO: Item tag behaviour
	}

	public static void registerItemTag(String key, ItemTagBehavior behavior) {
		ItemTagManager.ITEMTAG_REGISTRY.put(key, behavior);
	}
}
