package xyz.tbvns.projectils;

import net.minestom.server.entity.Entity;
import net.minestom.server.instance.block.Block;

public interface Projectile {
    void spawn();
    boolean onCollideBlock(Block block);
    boolean onCollideEntity(Entity entity);
}
