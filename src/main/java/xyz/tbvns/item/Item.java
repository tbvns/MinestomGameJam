package xyz.tbvns.item;

import lombok.Getter;
import net.minestom.server.item.Material;

/**
 * Base item class for custom items. This should be associated with a {@link ServerItem}.
 */
public class Item {

    /**
     * -- GETTER --
     *
     * @return The NBT ID of this custom item
     */
    @Getter
    private final String identifier;
    /**
     * -- GETTER --
     *
     * @return The {@link Material} of this custom item
     */
    @Getter
    private final Material material;
    /**
     * -- GETTER --
     *
     * @return The name of this custom item, in adventure formatting
     */
    @Getter
    private final String name;
    /**
     * -- GETTER --
     *
     * @return The multiple lines making up this item's description, in adventure formatting
     */
    @Getter
    private final String[] description;

    /**
     * Constructs a new custom item.
     * @param identifier The NBT ID of the custom item
     * @param material The {@link Material} of the custom item
     * @param name The name of the custom item, in adventure formatting
     * @param description The multiple lines making up this item's
     *                    description, in adventure formatting
     */
    public Item(String identifier, Material material, String name, String... description) {
        this.identifier = identifier;
        this.material = material;
        this.name = name;
        this.description = description;
    }


}
