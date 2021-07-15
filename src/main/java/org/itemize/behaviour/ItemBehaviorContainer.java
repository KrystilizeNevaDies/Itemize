package org.itemize.behaviour;

import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

/**
 * Represents an item meta which contains behaviour tags.
 * <br><br>
 * Behaviour tags are nbt that is used to represent some kind of logic or unique data
 * <br><br>
 * Some examples of behaviour tags:
 * <br>
 * {
 *     SIGNED: {UUID: "db64ffdc-cf78-4cd6-a06c-9973ed55481d", DATE: 1626312434, DISPLAY_NAME: "Krystilize"},
 *     ENHANCED: {LEVEL: 3, DATE: 1626311846},
 *     USE_EFFECTS: {EXPLOSION: {LEVEL: 4, TOTAL_EXP: 254}, THUNDER_STRIKE: {LEVEL: 2, TOTAL_EXP: 23}}
 * }
 */
public interface ItemBehaviorContainer {
    public @NotNull NBTCompound getBehaviourTags();
}
