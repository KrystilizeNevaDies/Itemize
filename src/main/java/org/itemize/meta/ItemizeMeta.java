package org.itemize.meta;

import net.minestom.server.item.ItemMeta;
import net.minestom.server.item.ItemMetaBuilder;
import net.minestom.server.item.metadata.MapMeta;
import net.minestom.server.tag.Tag;
import org.itemize.behaviour.ItemBehaviorContainer;
import org.itemize.data.ItemData;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class ItemizeMeta extends ItemMeta implements ItemMetaBuilder.Provider<MapMeta.Builder>, ItemBehaviorContainer {

    private static final Tag<NBTCompound> KEY_TAGS = Tag.NBT("itemize_tags");

    private final ItemData itemData;
    private final UUID origin;

    protected ItemizeMeta(
            @NotNull ItemMetaBuilder metaBuilder,
            @NotNull ItemData itemData,
            @NotNull UUID origin
    ) {
        super(metaBuilder);
        this.itemData = itemData;
        this.origin = origin;
    }

    public @NotNull ItemData getItemData() {
        return itemData;
    }

    public @NotNull UUID getOrigin() {
        return origin;
    }

    @Override
    public @NotNull NBTCompound getBehaviourTags() {
        return getTag(KEY_TAGS);
    }

    public static class Builder extends ItemMetaBuilder {

        private ItemData itemData;
        private UUID origin;
        private NBTCompound tags;

        public Builder() {}

        public Builder itemData(@NotNull ItemData itemData) {
            itemData.apply(this);
            this.itemData = itemData;
            return this;
        }

        public Builder origin(@NotNull UUID origin) {
            this.origin = origin;
            return this;
        }

        public Builder tags(@NotNull NBTCompound tags) {
            this.tags = tags;
            return this;
        }

        @Override
        public @NotNull ItemMeta build() {
            if (itemData == null)
                throw new IllegalStateException("The field \"itemData\" must be set before ItemizeMeta.Builder#build is called.");

            if (origin == null)
                throw new IllegalStateException("The field \"origin\" must be set before ItemizeMeta.Builder#build is called.");

            this.set(KEY_TAGS, Objects.requireNonNullElseGet(tags, NBTCompound::new));

            return new ItemizeMeta(this, itemData, origin);
        }

        @Override
        public void read(@NotNull NBTCompound nbtCompound) {
            // TODO: NBT Reading for ItemizeMeta
        }

        @Override
        protected @NotNull Supplier<@NotNull ItemMetaBuilder> getSupplier() {
            return Builder::new;
        }
    }
}
