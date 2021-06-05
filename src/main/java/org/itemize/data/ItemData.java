package org.itemize.data;

import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import net.kyori.adventure.text.Component;

public interface ItemData {

	public String getID();

	public Component getDisplayName();

	public String getDisplay();

	public ItemType getType();

	public ItemRarity getRarity();

	public Component[] getLore();

	public int getCmd();

	NBTCompound getTags();
}
