package org.itemize;

import java.util.UUID;
import java.util.function.Consumer;

import net.minestom.server.item.ItemMetaBuilder;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.ItemStackBuilder;
import org.itemize.data.ItemData;
import org.itemize.data.ItemDataProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final public class ItemStackProvider {
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

		// Build meta
		builder.meta(meta -> {
			// Expose to consumer
			if (metaBuilderConsumer != null)
				metaBuilderConsumer.accept(meta);

			// Apply ItemData
			itemData.apply(meta);

			return meta;
		});

		return builder.build();
	};
}
