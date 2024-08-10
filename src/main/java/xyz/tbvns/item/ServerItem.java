package xyz.tbvns.item;

import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.Nullable;
import xyz.tbvns.Utils;
import xyz.tbvns.item.custom.MenuItem;
import xyz.tbvns.item.custom.RedGun;

import java.util.Arrays;

/**
 * Custom items that are present on the server.
 */
public enum ServerItem {

    MENU(new MenuItem()),
    RED_GUN(new RedGun())
    ;

    private final Item itemClass;

    ServerItem(Item itemClass) {
        this.itemClass = itemClass;
    }

    /**
     * @return The {@link Item} class associated with this {@link ServerItem}.
     */
    public Item getItemClass() {
        return itemClass;
    }

    /**
     * Builds this {@link ServerItem} into an {@link ItemStack}.
     */
    public ItemStack buildItem() {
        return ItemStack.of(itemClass.getMaterial()).builder()
                .customName(Utils.formatWithoutItalics(itemClass.getName()))
                .lore(Arrays.stream(itemClass.getDescription()).map(Utils::formatWithoutItalics).toList())
                .set(Tag.String("id"), itemClass.getIdentifier())
                .build();
    }

    /**
     * Gets the server item of this {@link ItemStack}, if found.
     * @param itemStack The ItemStack to check
     * @return The {@link ServerItem} associated with this {@link ItemStack},
     * or null if not found
     */
    public static @Nullable ServerItem getServerItem(ItemStack itemStack) {
        String nbt = itemStack.getTag(Tag.String("id"));
        for (ServerItem serverItem : values()) {
            if (nbt.equals(serverItem.getItemClass().getIdentifier())) return serverItem;
        }
        return null;
    }

}
