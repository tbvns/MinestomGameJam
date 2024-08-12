package xyz.tbvns.projectils;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.damage.Damage;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import xyz.tbvns.game.Color;
import xyz.tbvns.game.Enemy;
import xyz.tbvns.player.GamePlayer;

import java.time.Duration;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ColorProjectile extends Entity implements Projectile {
    protected final Instance instance;
    protected final GamePlayer shooter;
    protected final Color color;

    private double xOffset = 0;
    private double yOffset = 0;
    private double zOffset = 0;

    private double yVel;

    public ColorProjectile(Color color, Instance instance, GamePlayer shooter) {
        super(EntityType.BLOCK_DISPLAY);
        this.shooter = shooter;
        this.instance = instance;
        this.color = color;
        this.yVel = color.getInitialYVel();

        //edit projectile meta properties
        editEntityMeta(BlockDisplayMeta.class, blockDisplayMeta -> {
            blockDisplayMeta.setBlockState(color.getBlock());
            blockDisplayMeta.setScale(new Vec(color.getProjectileScale(), color.getProjectileScale(), color.getProjectileScale()));
            blockDisplayMeta.setBrightnessOverride(200);
            blockDisplayMeta.setHasNoGravity(true);
            blockDisplayMeta.setPosRotInterpolationDuration(1);
        });
        //edit projectile collision properties
        setBoundingBox(color.getProjectileScale() * 1.2, color.getProjectileScale() * 1.2, color.getProjectileScale() * 1.2);
    }

    @Override
    public void spawn() {
        //spawn the projectile
        setInstance(instance, shooter.getPosition().add(0, shooter.getEyeHeight(), 0));
        scheduleRemove(Duration.ofSeconds(3)); //auto-remove after 3 seconds if not collided with anything

        double xzLen = cos(Math.toRadians(position.pitch())); //not sure what this does exactly
        xOffset = xzLen * cos(Math.toRadians(position.yaw() + 90)); //calculates the x-offset per tick based on the starting position's yaw
        yOffset = sin(Math.toRadians(-position.pitch())); //calculates the y-offset per tick based on the starting position's pitch
        zOffset = xzLen * sin(Math.toRadians(position.yaw() + 90)); //calculates the z-offset per tick based on the starting position's yaw
    }

    @Override
    public void update(long time) {
        super.update(time);

        if (color.isHasGravity()) yVel -= 0.1;
        if (yVel < -5) yVel = -5;

        Pos point = new Pos(
                position.x() + xOffset * color.getProjectileSpeed(),
                position.y() + yOffset * color.getProjectileSpeed() + yVel,
                position.z() + zOffset * color.getProjectileSpeed(),
                position.yaw(),
                position.pitch()
        );

        //entity collisions, calculate before movement happens (is there a better way to do this?)
        //shouldRemove is a boolean value set by the collision functions. If a collision occurs and the
        //value of shouldRemove is false, and the collision function returns true, shouldRemove is set to
        //true. Afterward, the shouldRemove value stays true until the entity gets removed in the check
        //just after the collision checks.
        boolean shouldRemove = false;

        for (Entity entity : instance.getNearbyEntities(point, color.getProjectileScale())) {
            if (!(entity instanceof ColorProjectile) && boundingBox.intersectEntity(point, entity)) {
                boolean remove = onCollideEntity(entity);
                if (!shouldRemove && remove) shouldRemove = true;
            }
        }

        //block collisions
        Block b = instance.getBlock(point);
        if (!b.isAir()) {
            boolean remove = onCollideBlock(b);
            if (!shouldRemove && remove) shouldRemove = true;
        }

        if (shouldRemove) {
            remove();
            return;
        }

        //if not removed, then actually move
        teleport(point);
    }

    @Override
    public boolean onCollideBlock(Block block) {
        return true;
    }

    @Override
    public boolean onCollideEntity(Entity entity) {
        if (entity.equals(shooter)) return false;
        if (entity instanceof Enemy enemy) {
            enemy.damage(shooter, color, color.getProjectileDamage());
        } else if (entity instanceof LivingEntity livingEntity) {
            livingEntity.damage(new Damage(DamageType.PLAYER_ATTACK, this, shooter, position, color.getProjectileDamage()));
        }
        return true;
    }
}
