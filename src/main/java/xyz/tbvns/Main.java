package xyz.tbvns;

import net.hollowcube.schem.Rotation;
import net.hollowcube.schem.Schematic;
import net.hollowcube.schem.SchematicReader;
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

        //TODO: Remove this test code
        /*TEST CODE*/LivingEntity cow = new LivingEntity(EntityType.COW);
        /*TEST CODE*/cow.setInstance(lobbyInstance);
        /*TEST CODE*/lobbyInstance.setBlock(0, -20, 0, Block.STONE);
        //End of test code

        for (int x = 0; x < 20; x++) {
            for (int z = 0; z < 20; z++) {
                lobbyInstance.loadChunk(x - 10, z - 10);
            }
        }

        Schematic schematic = new SchematicReader().read(Main.class.getResourceAsStream("/map.schem"));
        schematic.build(Rotation.NONE, true).apply(lobbyInstance, -130, -1, -130, () -> {
            System.out.println("Map built !");
        });

        //set up listeners
        new PlayerListener(globalEventHandler);
        new ItemListener(globalEventHandler);

        minecraftServer.start("0.0.0.0", 25565);
        System.out.println("Minecraft server started! Running Minecraft version " + MinecraftServer.VERSION_NAME + ".");
    }
}