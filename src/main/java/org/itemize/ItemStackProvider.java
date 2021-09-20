package org.itemize;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minestom.server.item.ItemMetaBuilder;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.ItemStackBuilder;
import net.minestom.server.tag.Tag;
import org.itemize.data.ItemData;
import org.itemize.data.ItemDataProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemStackProvider {
	protected static final @NotNull Tag<UUID> TAG_ORIGIN = UUIDTag.get("itemize:tag_origin");
	private final ItemDataProvider itemDataProvider;

	public ItemStackProvider(@NotNull ItemDataProvider dataProvider) {
		this.itemDataProvider = dataProvider;
	}

	public @NotNull ItemDataProvider getItemDataProvider() {
		return itemDataProvider;
	}

	public @NotNull ItemStack create(
			@NotNull Supplier<ItemData> itemDataSupplier,
			@NotNull UUID origin,
			@Nullable Consumer<ItemMetaBuilder> metaBuilderConsumer
	) {
		return create(itemDataSupplier.get(), origin, metaBuilderConsumer);
	}

	public @NotNull ItemStack create(
			@NotNull String ID,
			@NotNull UUID origin,
			@Nullable Consumer<ItemMetaBuilder> metaBuilderConsumer
	) {
		ItemData itemData = itemDataProvider.get(ID);

		if (itemData == null) {
			throw new IllegalArgumentException("No ItemData found for ID: " + ID + ". DataProvider: " + itemDataProvider);
		}

		return create(itemData, origin, metaBuilderConsumer);
	}

	public @NotNull ItemStack create(
			@NotNull ItemData itemData,
			@NotNull UUID origin,
			@Nullable Consumer<ItemMetaBuilder> metaBuilderConsumer
	) {
		// Setup item stack builder
		ItemStackBuilder builder = ItemStack.builder(itemData.display());

		// Prepare item
		prepare(builder, itemData, origin);

		// Expose to consumer
		builder.meta(meta -> {
			if (metaBuilderConsumer != null)
				metaBuilderConsumer.accept(meta);
			return meta;
		});

		return builder.build();
	}

	protected void prepare(ItemStackBuilder builder, ItemData itemData, UUID origin) {
		// Display
		builder.displayName(itemData.displayName());
		builder.lore(itemData.lore());

		// Meta
		builder.meta(itemMetaBuilder -> {
			// ItemData
			itemData.apply(itemMetaBuilder);

			// Origin
			itemMetaBuilder.set(TAG_ORIGIN, origin);
			return itemMetaBuilder;
		});
	}
}
