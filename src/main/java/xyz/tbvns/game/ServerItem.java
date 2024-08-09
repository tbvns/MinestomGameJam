package xyz.tbvns.game;

import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.Nullable;
import xyz.tbvns.Utils;

import java.util.Arrays;

/**
 * Custom items that are present on the server.
 */
public enum ServerItem {

    MENU("menu", Material.NETHER_STAR, "<gold>Game Menu");

    private final String nbtSuffix;
    private final Material itemMaterial;
    private final String itemName;
    private final String[] itemDescription;

    ServerItem(String nbtSuffix, Material itemMaterial, String itemName, String... itemDescription) {
        this.nbtSuffix = nbtSuffix;
        this.itemMaterial = itemMaterial;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    /**
     * Builds this {@link ServerItem} into an {@link ItemStack}.
     */
    public ItemStack buildItem() {
        return ItemStack.of(itemMaterial).builder()
                .customName(Utils.formatWithoutItalics(itemName))
                .lore(Arrays.stream(itemDescription).map(Utils::formatWithoutItalics).toList())
                .set(Tag.String("id"), nbtSuffix)
                .build();
    }

    /**
     * Gets the server item of this {@link ItemStack}, if found.
     * @param itemStack The ItemStack to check
     * @return The {@link ServerItem} associated with this {@link ItemStack},
     * or null if not found
     */
    public @Nullable ServerItem getServerItem(ItemStack itemStack) {
        String nbt = itemStack.getTag(Tag.String("id"));
        for (ServerItem serverItem : values()) {
            if (nbt.equals(serverItem.nbtSuffix)) return serverItem;
        }
        return null;
    }

}
