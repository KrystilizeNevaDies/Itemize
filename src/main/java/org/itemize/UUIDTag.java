package org.itemize;

import net.minestom.server.tag.Tag;
import net.minestom.server.tag.TagReadable;
import net.minestom.server.tag.TagSerializer;
import net.minestom.server.tag.TagWritable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
public class UUIDTag implements TagSerializer<UUID> {

    private final @NotNull Tag<long[]> longArrayTag;

    private UUIDTag(@NotNull String key) {
        this.longArrayTag = Tag.LongArray(key);
    }

    public static @NotNull Tag<UUID> get(@NotNull String key) {
        return Tag.Structure(key, new UUIDTag(key));
    }

    @Override
    public @Nullable UUID read(@NotNull TagReadable tagReadable) {
        long[] lowHigh = tagReadable.getTag(longArrayTag);
        if (lowHigh == null) {
            return null;
        }
        return new UUID(lowHigh[0], lowHigh[1]);
    }

    @Override
    public void write(@NotNull TagWritable tagWritable, @Nullable UUID uuid) {
        if (uuid == null) {
            tagWritable.removeTag(longArrayTag);
            return;
        }
        long[] lowHigh = {
                uuid.getLeastSignificantBits(),
                uuid.getMostSignificantBits()
        };
        tagWritable.setTag(longArrayTag, lowHigh);
    }
}
