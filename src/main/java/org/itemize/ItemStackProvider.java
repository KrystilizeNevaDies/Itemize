package org.itemize;

import java.util.UUID;
import java.util.function.Consumer;

import net.minestom.server.item.ItemMetaBuilder;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.ItemStackBuilder;
import net.minestom.server.tag.Tag;
import org.itemize.data.ItemData;
import org.itemize.data.ItemDataProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemStackProvider {
	protected static final Tag<long[]> TAG_ORIGIN = Tag.LongArray("OriginUUID");
	private final ItemDataProvider itemDataProvider;

	public ItemStackProvider(@NotNull ItemDataProvider dataProvider) {
		this.itemDataProvider = dataProvider;
	}

	public @NotNull ItemDataProvider getItemDataProvider() {
		return itemDataProvider;
	}

	public @NotNull ItemStack create(@NotNull String ID, @NotNull UUID origin, @Nullable Consumer<ItemMetaBuilder> metaBuilderConsumer) {
		ItemData itemData = itemDataProvider.get(ID);

		if (itemData == null)
			throw new IllegalArgumentException("No ItemData found for ID: " + ID + ". DataProvider: " + itemDataProvider);

		// Setup item stack builder
		ItemStackBuilder builder = ItemStack.builder(itemData.display());

		// Prepare item
		prepare(builder, ID, itemData, origin);

		// Expose to consumer
		builder.meta(meta -> {
			if (metaBuilderConsumer != null)
				metaBuilderConsumer.accept(meta);
			return meta;
		});

		return builder.build();
	}

	protected void prepare(ItemStackBuilder builder, String ID, ItemData itemData, UUID origin) {
		// Display
		builder.displayName(itemData.displayName());
		builder.lore(itemData.lore());

		// Meta
		builder.meta(itemMetaBuilder -> {
			// ItemData
			itemData.apply(itemMetaBuilder);

			// Origin
			itemMetaBuilder.set(TAG_ORIGIN, new long[] {origin.getLeastSignificantBits(), origin.getMostSignificantBits()});
			return itemMetaBuilder;
		});
	}
}
