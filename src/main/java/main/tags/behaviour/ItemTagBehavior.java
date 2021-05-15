package main.tags.behaviour;

import org.jglrxavpok.hephaistos.nbt.NBT;

import main.meta.ItemizeMeta;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;

/**
 * Item tag behavior dictates what a specific item tag should do to an item
 * 
 * @author Krystilize
 *
 */
public interface ItemTagBehavior {
	public void onBuilder(ItemizeMeta.Builder builder, NBT tagValue);
	
	/**
	 * This is ran (every update) when the item is equipped in an armor slot 
	 */
	public void onEquipped(NBT tagValue, ItemStack item, Player player);
	
	/**
	 * This is ran (every update) when the item itself is visible e.g. held in hand or equipped on player
	 */
	public void onVisible(NBT tagValue, ItemStack item, Player player);
	
	/**
	 * Gets the number of ticks per behavior update
	 * 
	 * @return num of ticks
	 */
	public int getUpdateInterval();
}
