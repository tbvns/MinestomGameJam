package xyz.tbvns.item;

import net.minestom.server.item.Material;

/**
 * Base item class for custom items. This should be associated with a {@link ServerItem}.
 */
public class Item {

    private final String identifier;
    private final Material material;
    private final String name;
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

    /**
     * @return The NBT ID of this custom item
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @return The {@link Material} of this custom item
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @return The name of this custom item, in adventure formatting
     */
    public String getName() {
        return name;
    }

    /**
     * @return The multiple lines making up this item's description, in adventure formatting
     */
    public String[] getDescription() {
        return description;
    }
}
