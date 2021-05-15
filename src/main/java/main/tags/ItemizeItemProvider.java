package main.tags;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import main.ItemStackProvider;
import main.data.ItemData;
import main.meta.ItemizeMeta;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.ItemStackBuilder;

public class ItemizeItemProvider implements ItemStackProvider {

	/**
	 * Creates a new itemstack using the registered data for the ID
	 */
	@Override
	public ItemStack create(String ID) {
		return create(ID, _m -> {});
	}

	/**
	 * Creates a new itemstack using the registered data for the ID and the specified meta builder consumer
	 */
	@Override
	public ItemStack create(String ID, @NotNull Consumer<ItemizeMeta.Builder> metaBuilderConsumer) {
		final ItemData data = ItemizeItemProvider.getData(ID);

		// Create new builder
		final ItemStackBuilder builder = ItemStack.builder(data.getDisplay());

		// Build and apply meta
		builder.meta(ItemizeMeta.class, metaBuilder -> {
			data.buildMeta(metaBuilder);

			metaBuilderConsumer.accept(metaBuilder);
		});

		// Build and return new item stack
		return builder.build();
	}

	private static ItemData getData(String ID) {
		return ItemData.ITEM_REGISTRY.get(ID);
	}
}
