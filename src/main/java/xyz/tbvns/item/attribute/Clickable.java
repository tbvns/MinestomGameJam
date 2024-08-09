package xyz.tbvns.item.attribute;

import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;

/**
 * Represents an item that can be clicked.
 */
public interface Clickable {

    /**
     * Code that runs when this item is clicked.
     */
    void onClick(PlayerUseItemEvent event);

    /**
     * Code that runs when this item is clicked on a block.
     */
    void onClickBlock(PlayerUseItemOnBlockEvent event);

}
