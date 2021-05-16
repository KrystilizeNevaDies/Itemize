package main.tags;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import main.ItemStackProvider;
import main.data.ItemData;
import main.meta.ItemizeMeta;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.ItemStackBuilder;
import socialize.tracing.OriginReference;

public class ItemizeItemProvider implements ItemStackProvider {

	/**
	 * Creates a new itemstack using the registered data for the ID
	 */
	@Override
	public ItemStack create(String ID, OriginReference origin) {
		return create(ID, origin, _m -> {});
	}

	/**
	 * Creates a new itemstack using the registered data for the ID and the specified meta builder consumer
	 */
	@Override
	public ItemStack create(String ID, OriginReference origin, @NotNull Consumer<ItemizeMeta.Builder> metaBuilderConsumer) {
		final ItemData data = ItemizeItemProvider.getData(ID);

		// Create new builder
		final ItemStackBuilder builder = ItemStack.builder(data.getDisplay());

		// Build meta
		ItemizeMeta.Builder metaBuilder = new ItemizeMeta.Builder();
		metaBuilderConsumer.accept(metaBuilder);
		metaBuilder.data(data);
		metaBuilder.origin(origin);
		
		// Apply the meta
		builder.meta(metaBuilder.build());

		// Build and return new item stack
		return builder.build();
	}

	private static ItemData getData(String ID) {
		return ItemData.ITEM_REGISTRY.get(ID);
	}
}
