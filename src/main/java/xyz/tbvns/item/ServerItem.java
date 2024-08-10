package xyz.tbvns.item;

import lombok.Getter;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.Nullable;
import xyz.tbvns.Utils;
import xyz.tbvns.game.Color;
import xyz.tbvns.item.custom.ColorGun;
import xyz.tbvns.item.custom.MenuItem;

import java.util.Arrays;

/**
 * Custom items that are present on the server.
 */
@Getter
public enum ServerItem {

    MENU(new MenuItem()),
    RED_GUN(new ColorGun(Color.RED, Material.LEATHER_HORSE_ARMOR)),
    GREEN_GUN(new ColorGun(Color.GREEN, Material.IRON_HORSE_ARMOR)),
    BLUE_GUN(new ColorGun(Color.BLUE, Material.DIAMOND_HORSE_ARMOR)),
    ;

    /**
     * -- GETTER --
     *
     * @return The {@link Item} class associated with this {@link ServerItem}.
     */
    private final Item itemClass;

    ServerItem(Item itemClass) {
        this.itemClass = itemClass;
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
