package xyz.tbvns.projectils;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.Damage;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import xyz.tbvns.game.Color;

import java.time.Duration;

import static java.lang.Math.*;

public class ColorProjectile extends Entity implements Projectile {
    private final Instance instance;
    private final Player shooter;
    private final Color color;

    private double xOffset = 0;
    private double yOffset = 0;
    private double zOffset = 0;

    public ColorProjectile(Color color, Instance instance, Player shooter) {
        super(EntityType.BLOCK_DISPLAY);
        this.shooter = shooter;
        this.instance = instance;
        this.color = color;

        //edit projectile meta properties
        editEntityMeta(BlockDisplayMeta.class, blockDisplayMeta -> {
            blockDisplayMeta.setBlockState(color.getBlock());
            blockDisplayMeta.setScale(new Vec(color.getProjectileScale(), color.getProjectileScale(), color.getProjectileScale()));
            blockDisplayMeta.setBrightnessOverride(200);
            blockDisplayMeta.setHasNoGravity(true);
            blockDisplayMeta.setPosRotInterpolationDuration(1);
        });
        //edit projectile collision properties
        setBoundingBox(0.5, 0.5, 0.5);
    }

    @Override
    public void spawn() {
        //spawn the projectile
        setInstance(instance, shooter.getPosition().add(0, shooter.getEyeHeight(), 0));
        scheduleRemove(Duration.ofSeconds(3)); //TODO: remove on collision, but for testing right now 3 seconds is fine

        double xzLen = cos(Math.toRadians(position.pitch())); //not sure what this does exactly
        xOffset = xzLen * cos(Math.toRadians(position.yaw() + 90)); //calculates the x-offset per tick based on the starting position's yaw
        yOffset = sin(Math.toRadians(-position.pitch())); //calculates the y-offset per tick based on the starting position's pitch
        zOffset = xzLen * sin(Math.toRadians(position.yaw() + 90)); //calculates the z-offset per tick based on the starting position's yaw
    }

    @Override
    public void update(long time) {
        super.update(time);
        Pos point = new Pos(
                position.x() + xOffset * color.getProjectileSpeed(),
                position.y() + yOffset * color.getProjectileSpeed(),
                position.z() + zOffset * color.getProjectileSpeed(),
                position.yaw(),
                position.pitch()
        );
        teleport(point);

        //entity collisions (is there a better way to do this?)
        boolean collided = false;

        for (Entity entity : instance.getNearbyEntities(point, boundingBox.depth() * 2)) {
            if (!(entity instanceof ColorProjectile) && boundingBox.intersectEntity(point, entity)) {
                onCollideEntity(entity);
                collided = true;
            }
        }

        //block collisions
        Block b = instance.getBlock(point);
        if (!b.isAir()) {
            onCollideBlock(b);
            collided = true;
        }

        if (collided) remove();
    }

    @Override
    public void onCollideBlock(Block block) {
        System.out.println("Collided with block " + block);
    }

    @Override
    public void onCollideEntity(Entity entity) {
        System.out.println("Collided with entity " + entity);
        if (!entity.equals(shooter) && entity instanceof LivingEntity livingEntity) {
            livingEntity.damage(new Damage(DamageType.PLAYER_ATTACK, this, shooter, position, color.getProjectileDamage()));
        }
    }
}
