package xyz.tbvns.item;

import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.event.trait.ItemEvent;
import xyz.tbvns.item.attribute.Clickable;

/**
 * Listener class for events that involve custom items.
 */
public class ItemListener {

    public ItemListener(GlobalEventHandler globalEventHandler) {
        //create item event node
        EventNode<ItemEvent> itemNode = EventNode.type("item-listener", EventFilter.ITEM);

        itemNode.addListener(PlayerUseItemEvent.class, event -> {
            ServerItem serverItem = ServerItem.getServerItem(event.getItemStack());
            if (serverItem != null && serverItem.getItemClass() instanceof Clickable clickable) {
                clickable.onClick(event);
            }
        });

        //register event node with global event handler
        globalEventHandler.addChild(itemNode);
    }

}
