package xyz.tbvns;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.mojang.MojangUtils;
import xyz.tbvns.item.ItemListener;
import xyz.tbvns.item.ServerItem;

import java.io.IOException;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        Instance instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

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

        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            event.setSpawningInstance(instance);
        });
        globalEventHandler.addListener(PlayerSpawnEvent.class, event -> {
            if (event.isFirstSpawn() && event.getInstance().equals(instance)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setItemStack(8, ServerItem.MENU.buildItem());
            }
        });

        new ItemListener(globalEventHandler);

        minecraftServer.start("0.0.0.0", 25565);
        System.out.println("Minecraft server started! Running Minecraft version " + MinecraftServer.VERSION_NAME + ".");
    }
}