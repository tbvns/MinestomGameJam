package xyz.tbvns.player;

import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.trait.PlayerEvent;
import net.minestom.server.utils.mojang.MojangUtils;
import xyz.tbvns.Main;
import xyz.tbvns.item.ServerItem;

import java.io.IOException;
import java.util.UUID;

/**
 * Listener class for basic player events.
 */
public class PlayerListener {

    public PlayerListener(GlobalEventHandler globalEventHandler) {
        //create item event node
        EventNode<PlayerEvent> node = EventNode.type("player-listener", EventFilter.PLAYER);

        //load skin from mojang api
        globalEventHandler.addListener(PlayerSkinInitEvent.class, event -> {
            try {
                UUID mojangUUID = MojangUtils.getUUID(event.getPlayer().getUsername());
                PlayerSkin skin = PlayerSkin.fromUuid(mojangUUID.toString());
                event.setSkin(skin);
            } catch (IOException e) {
                e.printStackTrace();
                //log.warn("Player {}'s skin failed to load!", event.getPlayer().getUsername());
            }
        });

        //set up player on server join
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            event.setSpawningInstance(Main.lobbyInstance);
        });
        globalEventHandler.addListener(PlayerSpawnEvent.class, event -> {
            if (event.getInstance().equals(Main.lobbyInstance)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setItemStack(0, ServerItem.RED_GUN.buildItem());
                event.getPlayer().getInventory().setItemStack(8, ServerItem.MENU.buildItem());
            }
        });

        globalEventHandler.addChild(node);
    }

}
