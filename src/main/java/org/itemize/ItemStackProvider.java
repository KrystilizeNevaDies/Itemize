package org.itemize;

import java.util.UUID;
import java.util.function.Consumer;

import net.minestom.server.item.ItemStack;
import net.minestom.server.item.ItemStackBuilder;
import org.itemize.data.ItemData;
import org.itemize.data.ItemDataProvider;
import org.itemize.meta.ItemizeMeta;
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

	public @NotNull ItemStack create(@NotNull String ID, @NotNull UUID origin, @Nullable Consumer<ItemizeMeta.Builder> metaBuilderConsumer) {
		ItemData itemData = itemDataProvider.get(ID);

		if (itemData == null)
			throw new IllegalArgumentException("No ItemData found for ID: " + ID + ". DataProvider: " + itemDataProvider);

		// Setup item stack builder
		ItemStackBuilder builder = ItemStack.builder(itemData.display());

		// Build meta
		ItemizeMeta.Builder meta = new ItemizeMeta.Builder();
		meta.itemData(itemData);
		meta.origin(origin);

		// Expose method to builder
		if (metaBuilderConsumer != null)
			metaBuilderConsumer.accept(meta);

		// Apply meta
		builder.meta(meta.build());

		return builder.build();
	};
}
