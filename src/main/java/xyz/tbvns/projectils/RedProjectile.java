package xyz.tbvns.projectils;

import lombok.AllArgsConstructor;
import net.minestom.server.collision.Aerodynamics;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
import net.minestom.server.event.entity.EntityTickEvent;
import net.minestom.server.event.instance.InstanceTickEvent;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.NotNull;
import xyz.tbvns.game.Color;

import java.time.Duration;

import static java.lang.Math.*;

public class RedProjectile extends Entity implements Projectile {
    final Instance instance;
    final Player player;
    final double speed;

    public RedProjectile(Instance instance, Player player, double speed) {
        super(EntityType.BLOCK_DISPLAY);
        this.player = player;
        this.instance = instance;
        this.speed = speed;

        //edit projectile meta properties
        editEntityMeta(BlockDisplayMeta.class, blockDisplayMeta -> {
            blockDisplayMeta.setBrightnessOverride(15);
            blockDisplayMeta.setBlockState(Color.RED.getBlock());
        });
        //edit projectile movement and collision properties
        setAerodynamics(new Aerodynamics(0, 0, 0)); //TODO: add vertical/horizontal drag if wanted
        setBoundingBox(0.5, 0.5, 0.5);
        setVelocity(player.getPosition().direction().mul(3.0)); //TODO: fix entity not moving

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
        System.out.println(position);
    }

    @Override
    public void tick() {
        //DON'T TOUCH THAT IDK WHAT IT DOES
        double xzLen = cos(position.pitch());
        double x = xzLen * cos(position.yaw());
        double y = sin(position.pitch());
        double z = xzLen * sin(-position.yaw());
        //END OF BLACK MAGIC

        Pos point = new Pos( position.x() + x * speed, position.y() + y * speed, position.z() + z * speed, position.yaw(), position.pitch());
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
