package main.meta;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBT;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import main.data.ItemData;
import net.minestom.server.item.ItemMeta;
import net.minestom.server.item.ItemMetaBuilder;
import net.minestom.server.item.ItemTag;

public class ItemizeMeta extends ItemMeta implements ItemMetaBuilder.Provider<ItemizeMeta.Builder> {

	private static final ItemTag<NBT> TAGS = ItemTag.NBT("tags");
	private static final ItemTag<String> ORIGIN = ItemTag.String("origin");
	private static final ItemTag<String> ITEM_ID = ItemTag.String("item_id");

	public ItemizeMeta(ItemizeMeta.Builder builder, NBTCompound tags, String origin, @NotNull ItemData data) {
		super(ItemizeMeta.prepareBuilder(builder, tags, origin, data));
	}

	private static ItemizeMeta.Builder prepareBuilder(ItemizeMeta.Builder builder, NBTCompound tags, String origin, @NotNull ItemData data) {
		// Check if values are set, if else, use default
		builder.set(ItemizeMeta.TAGS, tags);
		builder.set(ItemizeMeta.ORIGIN, origin);
		builder.set(ItemizeMeta.ITEM_ID, data.getID());

		// Set item data
		builder.customModelData(data.getCmd());
		builder.displayName(data.getDisplayName());
		builder.lore(data.getLore());

		return builder;
	}

	public NBTCompound getTags() {
		return (NBTCompound) get(ItemizeMeta.TAGS);
	}

	public String getID() {
		return get(ItemizeMeta.ITEM_ID);
	}

	public static class Builder extends ItemMetaBuilder {

		NBTCompound tags;
		ItemData data;
		String origin;

        @Override
        public @NotNull ItemizeMeta build() {
        	assert(origin != null);
        	assert(data != null);

        	return new ItemizeMeta(this, tags, origin, data);
        }

        public Builder tags(NBTCompound tags) {
        	this.tags = tags;
        	return this;
        }

        public Builder origin(String origin) {
        	this.origin = origin;
        	return this;
        }

        public Builder data(ItemData data) {
        	this.data = data;
        	return this;
        }

        @Override
        public void read(@NotNull NBTCompound nbt) {
        	if (nbt.containsKey(ItemizeMeta.TAGS.getKey())) {
        		tags(nbt.getCompound(ItemizeMeta.TAGS.getKey()));
            }
        }

        @Override
        protected @NotNull Supplier<ItemMetaBuilder> getSupplier() {
            return Builder::new;
        }
    }
}
