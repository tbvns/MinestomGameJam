package xyz.tbvns.projectils;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
import net.minestom.server.event.entity.EntityTickEvent;
import net.minestom.server.instance.Instance;
import xyz.tbvns.game.Color;

import java.time.Duration;

import static java.lang.Math.*;

public class ColorProjectile extends Entity implements Projectile {
    private final Instance instance;
    private final Player player;
    private final double speed;

    private double xOffset = 0;
    private double yOffset = 0;
    private double zOffset = 0;

    public ColorProjectile(Color color, Instance instance, Player player, double speed) {
        super(EntityType.BLOCK_DISPLAY);
        this.player = player;
        this.instance = instance;
        this.speed = speed;

        //edit projectile meta properties
        editEntityMeta(BlockDisplayMeta.class, blockDisplayMeta -> {
            blockDisplayMeta.setBrightnessOverride(200);
            blockDisplayMeta.setBlockState(color.getBlock());
            blockDisplayMeta.setHasNoGravity(true);
        });
        //edit projectile collision properties
        setBoundingBox(0.5, 0.5, 0.5);

        eventNode().addListener(EntityTickEvent.class, entityTickEvent -> {
            if (entityTickEvent.getEntity().equals(this)) {
                tick();
            }
        });
    }

    @Override
    public void spawn() {
        //spawn the projectile
        setInstance(instance, player.getPosition().add(0, player.getEyeHeight(), 0));
        scheduleRemove(Duration.ofSeconds(3)); //TODO: remove on collision, but for testing right now 3 seconds is fine

        double xzLen = cos(Math.toRadians(position.pitch())); //not sure what this does exactly
        xOffset = xzLen * cos(Math.toRadians(position.yaw() + 90)); //calculates the x-offset per tick based on the starting position's yaw
        yOffset = sin(Math.toRadians(-position.pitch())); //calculates the y-offset per tick based on the starting position's pitch
        zOffset = xzLen * sin(Math.toRadians(position.yaw() + 90)); //calculates the z-offset per tick based on the starting position's yaw
    }

    @Override
    public void tick() {

        Pos point = new Pos(
                position.x() + xOffset * speed,
                position.y() + yOffset * speed,
                position.z() + zOffset * speed,
                position.yaw(),
                position.pitch()
        );
        teleport(point);
    }

    @Override
    public void onCollideBlock() {
        eventNode().getChildren().clear();
    }

    @Override
    public void onCollideEntity() {
        eventNode().getChildren().clear();
    }
}
