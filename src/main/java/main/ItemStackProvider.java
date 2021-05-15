package main;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import main.meta.ItemizeMeta;
import net.minestom.server.item.ItemStack;

public interface ItemStackProvider {
	public ItemStack create(String ID);

	public ItemStack create(String ID, @NotNull Consumer<ItemizeMeta.Builder> metaBuilderConsumer);
}
