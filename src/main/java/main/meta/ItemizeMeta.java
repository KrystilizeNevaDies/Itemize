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

	public static final ItemTag<NBT> TAGS = ItemTag.NBT("tags");
	public static final ItemTag<Long> ORIGIN = ItemTag.Long("origin");
	public static final ItemTag<String> ITEM_ID = ItemTag.String("item_id");

	public ItemizeMeta(ItemizeMeta.Builder builder) {
		super(builder);
	}

	public NBTCompound getTags() {
		return (NBTCompound) get(ItemizeMeta.TAGS);
	}

	public long getOrigin() {
		return get(ItemizeMeta.ORIGIN);
	}
	
	public String getID() {
		return get(ItemizeMeta.ITEM_ID);
	}

	public static class Builder extends ItemMetaBuilder {
		public Builder() {};

        @Override
        public @NotNull ItemizeMeta build() {
        	return new ItemizeMeta(this);
        }

        public Builder tags(NBTCompound tags) {
        	set(ItemizeMeta.TAGS, tags);
        	return this;
        }

        public Builder origin(long origin) {
        	set(ItemizeMeta.ORIGIN, origin);
        	return this;
        }

        public Builder data(ItemData data) {
        	set(ItemizeMeta.ITEM_ID, data.getID());
        	customModelData(data.getCmd());
    		displayName(data.getDisplayName());
    		lore(data.getLore());
        	return this;
        }

        @Override
        public void read(@NotNull NBTCompound nbt) {
        	if (nbt.containsKey(ItemizeMeta.TAGS.getKey())) {
        		tags(nbt.getCompound(ItemizeMeta.TAGS.getKey()));
            }
        	
        	if (nbt.containsKey(ItemizeMeta.ITEM_ID.getKey())) {
        		String ID = nbt.getString(ItemizeMeta.ITEM_ID.getKey());
        		
        		ItemData data = ItemData.ITEM_REGISTRY.get(ID);
        		
        		data(data);
            }
        	
        	if (nbt.containsKey(ItemizeMeta.ORIGIN.getKey())) {
        		origin(nbt.getLong(ItemizeMeta.ORIGIN.getKey()));
            }
        }

        @Override
        protected @NotNull Supplier<ItemMetaBuilder> getSupplier() {
            return Builder::new;
        }
    }
}
