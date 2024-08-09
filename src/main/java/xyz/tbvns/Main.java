package xyz.tbvns;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.instance.Instance;
import xyz.tbvns.item.ItemListener;
import xyz.tbvns.player.PlayerListener;

public class Main {
    public static Instance lobbyInstance;

    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        lobbyInstance = MinecraftServer.getInstanceManager().createInstanceContainer();
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        //set up listeners
        new PlayerListener(globalEventHandler);
        new ItemListener(globalEventHandler);

        minecraftServer.start("0.0.0.0", 25565);
        System.out.println("Minecraft server started! Running Minecraft version " + MinecraftServer.VERSION_NAME + ".");
    }
}