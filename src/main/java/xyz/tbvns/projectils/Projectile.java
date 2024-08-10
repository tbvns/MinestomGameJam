package xyz.tbvns.projectils;

import net.minestom.server.entity.Entity;
import net.minestom.server.instance.block.Block;

public interface Projectile {
    void spawn();
    void onCollideBlock(Block block);
    void onCollideEntity(Entity entity);
}
