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
	private static final ItemTag<String> ITEM_ID = ItemTag.String("item_id");
	
	public ItemizeMeta(ItemizeMeta.Builder builder, NBTCompound tags, @NotNull ItemData data) {
		super(prepareBuilder(builder, tags, data));
	}
	
	private static ItemizeMeta.Builder prepareBuilder(ItemizeMeta.Builder builder, NBTCompound tags, @NotNull ItemData data) {
		// Check if values are set, if else, use default
		builder.set(TAGS, tags);
		builder.set(ITEM_ID, data.getID());
		
		// Set item data
		builder.customModelData(data.getCmd());
		builder.displayName(data.getDisplayName());
		builder.lore(data.getLore());
		
		return builder;
	}
	
	public NBTCompound getTags() {
		return (NBTCompound) get(TAGS);
	}
	
	public String getID() {
		return get(ITEM_ID);
	}
	
	public static class Builder extends ItemMetaBuilder {

		NBTCompound tags;
		ItemData data;
		
        @Override
        public @NotNull ItemizeMeta build() {
        	return new ItemizeMeta(this, tags, data);
        }
        
        public Builder tags(NBTCompound tags) {
        	this.tags = tags;
        	return this;
        }
        
        public Builder data(ItemData data) {
        	this.data = data;
        	return this;
        }
        
        @Override
        public void read(@NotNull NBTCompound nbt) {
        	if (nbt.containsKey(TAGS.getKey())) {
        		tags(nbt.getCompound(TAGS.getKey()));
            }
        }
        
        @Override
        protected @NotNull Supplier<ItemMetaBuilder> getSupplier() {
            return Builder::new;
        }
    }
}
