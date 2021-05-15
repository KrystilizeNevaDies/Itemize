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
		TEST_FURNITURE("Test Chair", ItemType.FURNITURE, ItemRarity.MYTHICAL, "This is a test chair! Use it in your room and sit on it!", 22),
		TEST_BACK("Test Backpack", ItemType.BACK, ItemRarity.EPIC, "This is a test backpack! Used for wearing and testing! Put it on your back slot!", 31),
		TEST_ITEM("Test Item", ItemType.ITEM, ItemRarity.COMMON, "This is a test item! Used for holding and testing!", 4),
		TEST_HAT("Test Hat", ItemType.HAT, ItemRarity.NORMAL, "This is a test hat! Used for wearing and testing!", 1),
		TEST_COIN("Test Coin 1x", ItemType.CURRENCY, ItemRarity.NORMAL, "This is a test coin! Put it in your bank! You Sussy Baka", 55),
		TEST_BADGE("Test Badge", ItemType.BADGE, ItemRarity.RARE, "This is a test badge! Put it in your badges slot on your player profile!", 2);

		String ID;
		Component displayName;
		ItemType type;
		ItemRarity rarity;
		Component[] lore;
		int cmd;
		NBTCompound tags;

		DefaultItemData(String displayName, ItemType type, ItemRarity rarity, String lore, int cmd) {
			ID = name();
			tags = new NBTCompound();
			this.displayName = Component.text(displayName);
			this.type = type;
			this.rarity = rarity;
			this.lore = new Component[] {Component.text(lore), Component.text("cmd: " + cmd)};
			this.cmd = cmd;
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
			return Material.LEATHER_HORSE_ARMOR;
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
