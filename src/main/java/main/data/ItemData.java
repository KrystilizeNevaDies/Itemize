package main.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import main.meta.ItemizeMeta;
import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemMeta;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public interface ItemData {

	public String getID();

	public Component getDisplayName();

	public Material getDisplay();

	public ItemType getType();

	public ItemRarity getRarity();

	public Component[] getLore();

	public int getCmd();

	NBTCompound getTags();

	// Registry
	public static final Map<String, ItemData> ITEM_REGISTRY = new ConcurrentHashMap<>();

	/**
	 * Gets the ItemData associated with this ItemStack's ID
	 *
	 * @param ItemStack to get the item ID from
	 * @return ItemData
	 */
	public static ItemData from(ItemStack item) {
		final ItemMeta meta = item.getMeta();

		final String ID = meta.get(ItemizeMeta.ITEM_ID);
		
		if (ID == null) {
			new Throwable("Itemstack does not have an ID associated with it.").printStackTrace();
			return null;
		}
		
		final ItemData data = ItemData.ITEM_REGISTRY.get(ID);

		if (data == null) {
			new Throwable("ID: " + ID + " is not a valid item ID. Something has gone very wrong.").printStackTrace();
			return null;
		}
		
		return data;
	}

	public static void register(ItemDataProvider provider) {
		for (final ItemData data : provider.getItemData()) {
			ItemData.ITEM_REGISTRY.put(data.getID(), data);
		}
	}
}
