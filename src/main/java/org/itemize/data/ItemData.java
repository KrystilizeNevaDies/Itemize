package org.itemize.data;

import net.minestom.server.item.ItemMetaBuilder;
import net.minestom.server.item.Material;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import net.kyori.adventure.text.Component;

public record ItemData(
		String ID,
		Component displayName,
		Material display,
		ItemType type,
		ItemRarity rarity,
		Component[] lore,
		int cmd,
		NBTCompound data
) {

	public void apply(ItemMetaBuilder builder) {
		builder.displayName(displayName);
		builder.customModelData(cmd);
		builder.lore(lore);
	}
}
