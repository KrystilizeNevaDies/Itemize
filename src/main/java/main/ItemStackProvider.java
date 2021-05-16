package main;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import main.meta.ItemizeMeta;
import net.minestom.server.item.ItemStack;
import socialize.tracing.OriginReference;

public interface ItemStackProvider {
	public ItemStack create(String ID, OriginReference origin);

	public ItemStack create(String ID, OriginReference origin, @NotNull Consumer<ItemizeMeta.Builder> metaBuilderConsumer);
}
