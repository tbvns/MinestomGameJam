package xyz.tbvns.player;

import net.minestom.server.entity.Player;
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
import java.util.HashMap;
import java.util.UUID;

/**
 * Listener class for basic player events.
 */
public class PlayerListener {

    // store player skin as value for username so we dont keep requesting from mojang's api
    // we shou.d probably maybe clear it every now and then tho
    private static final HashMap<String, PlayerSkin> cachedSkins = new HashMap<String, PlayerSkin>();

    public PlayerListener(GlobalEventHandler globalEventHandler) {
        //create item event node
        EventNode<PlayerEvent> node = EventNode.type("player-listener", EventFilter.PLAYER);

        // check if player name is in hashmap, if not, grab it and save it :3
        // then load skin if not null
        globalEventHandler.addListener(PlayerSkinInitEvent.class, event -> {
            Player player = event.getPlayer();
            String playerName = player.getUsername();
            PlayerSkin skin = null;
            if (cachedSkins.containsKey(playerName)) {
                skin = cachedSkins.get(playerName);
            } else {
                try {
                    UUID mojangUUID = MojangUtils.getUUID(event.getPlayer().getUsername());
                    skin = PlayerSkin.fromUuid(mojangUUID.toString());
                    cachedSkins.put(playerName, skin);
                } catch (IOException e) {
                    if (e.getMessage().contains("Couldn't find any profile")) return;
                    e.printStackTrace();
                }
            }
            if (skin == null) return;
            event.setSkin(skin);
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
