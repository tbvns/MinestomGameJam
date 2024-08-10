package xyz.tbvns;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import xyz.tbvns.item.ItemListener;
import xyz.tbvns.player.PlayerListener;

public class Main {
    /**
     * The {@link Instance} representing the main lobby world.
     */
    public static Instance lobbyInstance;

    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        lobbyInstance = MinecraftServer.getInstanceManager().createInstanceContainer();
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        LivingEntity cow = new LivingEntity(EntityType.COW);
        cow.setInstance(lobbyInstance);
        lobbyInstance.setBlock(0, -20, 0, Block.STONE);

        //set up listeners
        new PlayerListener(globalEventHandler);
        new ItemListener(globalEventHandler);

        minecraftServer.start("0.0.0.0", 25565);
        System.out.println("Minecraft server started! Running Minecraft version " + MinecraftServer.VERSION_NAME + ".");
    }
}