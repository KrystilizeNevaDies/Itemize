package main.data;

import java.util.Set;

import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import net.kyori.adventure.text.Component;
import net.minestom.server.item.Material;
/**
 * This is for backend usage, not designed for public access
 *
 * @author Krystilize
 *
 */
public class DefaultItemDataProvider implements ItemDataProvider {

	public static enum DefaultItemData implements ItemData {
		TEST_FURNITURE("Test Chair", Material.PAPER, ItemType.FURNITURE, ItemRarity.MYTHICAL, "This is a test chair! Use it in your room and sit on it!", 22, new NBTCompound()),
		TEST_BACK("Test Backpack", Material.PAPER, ItemType.BACK, ItemRarity.EPIC, "This is a test backpack! Used for wearing and testing! Put it on your back slot!", 31, new NBTCompound()),
		TEST_ITEM("Test Item", Material.PAPER, ItemType.ITEM, ItemRarity.COMMON, "This is a test item! Used for holding and testing!", 4, new NBTCompound()),
		TEST_HAT("Test Hat", Material.PAPER, ItemType.HAT, ItemRarity.NORMAL, "This is a test hat! Used for wearing and testing!", 1, new NBTCompound()),
		TEST_COIN("Test Coin 1x", Material.PAPER, ItemType.CURRENCY, ItemRarity.NORMAL, "This is a test coin! Put it in your bank! You Sussy Baka", 55, new NBTCompound()),
		TEST_BADGE("Test Badge", Material.PAPER, ItemType.BADGE, ItemRarity.RARE, "This is a test badge! Put it in your badges slot on your player profile!", 2, new NBTCompound());

		String ID;
		Component displayName;
		Material display;
		ItemType type;
		ItemRarity rarity;
		Component[] lore;
		int cmd;
		NBTCompound tags;

		DefaultItemData(String displayName, Material display, ItemType type, ItemRarity rarity, String lore, int cmd, NBTCompound tags) {
			ID = name();
			this.displayName = Component.text(displayName);
			this.type = type;
			this.rarity = rarity;
			this.lore = new Component[] {Component.text(lore)};
			this.cmd = cmd;
			this.tags = tags;
		}

		@Override
		public String getID() {
			return ID;
		}

		@Override
		public Component getDisplayName() {
			return displayName;
		}

		@Override
		public Material getDisplay() {
			return display;
		}

		@Override
		public ItemType getType() {
			return type;
		}

		@Override
		public ItemRarity getRarity() {
			return rarity;
		}

		@Override
		public Component[] getLore() {
			return lore;
		}

		@Override
		public int getCmd() {
			return cmd;
		}

		@Override
		public NBTCompound getTags() {
			return tags;
		}
	}

	@Override
	public Set<ItemData> getItemData() {
		return Set.of(DefaultItemData.values());
	}
}
