package org.itemize.data;

import com.google.common.collect.Streams;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minestom.server.item.ItemMetaBuilder;
import net.minestom.server.item.Material;
import net.minestom.server.tag.Tag;
import net.minestom.server.tag.TagReadable;
import org.jglrxavpok.hephaistos.nbt.*;

import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.Objects;

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
	private static final GsonComponentSerializer SERIALIZER = GsonComponentSerializer.gson();
	private static final Tag<String> TAG_ID = Tag.String("ID");
	private static final Tag<String> TAG_DISPLAY_NAME = Tag.String("DISPLAY_NAME");
	private static final Tag<String> TAG_MATERIAL = Tag.String("MATERIAL");
	private static final Tag<String> TAG_TYPE_DISPLAY_NAME = Tag.String("TYPE_DISPLAY_NAME");
	private static final Tag<NBTCompound> TAG_TYPE_DATA = Tag.NBT("TYPE_DATA");
	private static final Tag<String> TAG_RARITY_DISPLAY_NAME = Tag.String("RARITY_DISPLAY_NAME");
	private static final Tag<NBTCompound> TAG_RARITY_DATA = Tag.NBT("RARITY_DATA");
	private static final Tag<NBTList<NBTString>> TAG_LORE = Tag.NBT("LORE");
	private static final Tag<Integer> TAG_CMD = Tag.Integer("CMD");
	private static final Tag<NBTCompound> TAG_DATA = Tag.NBT("DATA");

	/**
	 * Applies this ItemData to the ItemMetaBuilder
	 * @param builder
	 */
	public void apply(ItemMetaBuilder builder) {
		builder.displayName(displayName);
		builder.customModelData(cmd);
		builder.lore(lore);

		// Tags (I apologize for text walls)
		builder.set(TAG_ID, ID);
		builder.set(TAG_DISPLAY_NAME, SERIALIZER.serialize(displayName));
		builder.set(TAG_MATERIAL, display.name());
		builder.set(TAG_TYPE_DISPLAY_NAME, SERIALIZER.serialize(type.displayName()));
		builder.set(TAG_TYPE_DATA, type.data());
		builder.set(TAG_RARITY_DISPLAY_NAME, SERIALIZER.serialize(rarity.displayName()));
		builder.set(TAG_RARITY_DATA, rarity.data());
		builder.set(TAG_CMD, cmd);
		builder.set(TAG_DATA, data);

		// Lore tag

		// Convert to NBTList
		NBTList<NBTString> lore = new NBTList<>(NBTTypes.TAG_Compound);

		// Convert and add all
		Arrays.stream(lore())
				.map(SERIALIZER::serialize)
				.map(NBTString::new)
				.forEach(lore::add);

		// Add tag
		builder.set(TAG_LORE, lore);
	}

	/**
	 * Retrieves this ItemData from the {@Link TagReadable}
	 */
	public static ItemData from(TagReadable tagReadable) {
		// Tags (I apologize for text walls)
		final String ID = Objects.requireNonNull(tagReadable.getTag(TAG_ID), "ID was null");
		final String displayName = Objects.requireNonNull(tagReadable.getTag(TAG_DISPLAY_NAME), "displayName was null");
		final String display = Objects.requireNonNull(tagReadable.getTag(TAG_MATERIAL), "display was null");
		final String typeDisplayName = Objects.requireNonNull(tagReadable.getTag(TAG_TYPE_DISPLAY_NAME), "typeDisplayName was null");
		final NBTCompound typeData = Objects.requireNonNull(tagReadable.getTag(TAG_TYPE_DATA), "typeData was null");
		final String rarityDisplayName = Objects.requireNonNull(tagReadable.getTag(TAG_RARITY_DISPLAY_NAME), "rarityDisplayName was null");
		final NBTCompound rarityData = Objects.requireNonNull(tagReadable.getTag(TAG_RARITY_DATA), "rarityData was null");
		final int cmd = Objects.requireNonNull(tagReadable.getTag(TAG_CMD), "cmd was null");
		final NBTCompound data = Objects.requireNonNull(tagReadable.getTag(TAG_DATA), "data was null");

		// Lore tag
		final NBTList<NBTString> lore = Objects.requireNonNull(tagReadable.getTag(TAG_LORE), "lore was null");

		Component[] loreArray = Streams.stream(lore)
			.map(NBTString::getValue)
			.map(SERIALIZER::deserialize)
			.toArray(Component[]::new);

		return new ItemData(
				ID,
				SERIALIZER.deserialize(displayName),
				Material.valueOf(display),
				new ItemType(SERIALIZER.deserialize(typeDisplayName), typeData),
				new ItemRarity(SERIALIZER.deserialize(rarityDisplayName), rarityData),
				loreArray,
				cmd,
				data
		);
	}
}
