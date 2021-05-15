package main.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import main.data.DefaultItemDataProvider.DefaultItemData;
import net.kyori.adventure.text.Component;

public interface ItemData {
	
	String getID();
	
	Component getDisplayName();
	
	ItemType getType();
	
	ItemRarity getRarity();
	
	Component[] getLore();
	
	int getCmd();
	
	NBTCompound getTags();
	
	// Registry
	public static final Map<String, ItemData> ITEM_REGISTRY = new ConcurrentHashMap<>();
	
	public static void register(ItemDataProvider provider) {
		for (ItemData data : provider.getItemData()) {
			ITEM_REGISTRY.put(data.getID(), data);
		}
	}
}
