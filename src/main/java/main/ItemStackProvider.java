package main;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import main.meta.ItemizeMeta;
import net.minestom.server.item.ItemStack;

public interface ItemStackProvider {
	public ItemStack create(String ID, long origin);

	public ItemStack create(String ID, long origin, @NotNull Consumer<ItemizeMeta.Builder> metaBuilderConsumer);
}
