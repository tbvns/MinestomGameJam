package xyz.tbvns.item.custom;

import net.minestom.server.collision.Aerodynamics;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.Material;
import xyz.tbvns.game.Color;
import xyz.tbvns.item.Item;
import xyz.tbvns.item.attribute.Clickable;

import java.time.Duration;

public abstract class ColorGun extends Item implements Clickable {

    private final Color color;

    /**
     * Constructs a new color gun item.
     *
     * @param color The {@link Color} of this color gun
     * @param material The {@link Material} of the custom item
     */
    public ColorGun(Color color, Material material) {
        super("color_gun_" + color.name().toLowerCase(), material, color.name().toLowerCase() + " Color Gun",
                "<gray>Shoots a " + color.name().toLowerCase() + "bullet on right-click.");
        this.color = color;
    }

    @Override
    public void onClick(PlayerUseItemEvent event) {
        Instance instance = event.getInstance();
        Pos spawnPosition = event.getPlayer().getPosition().add(0, event.getPlayer().getEyeHeight(), 0); //TODO: maybe change this?

        //create projectile
        Entity entity = new Entity(EntityType.BLOCK_DISPLAY);
        //edit projectile meta properties
        entity.editEntityMeta(BlockDisplayMeta.class, blockDisplayMeta -> {
            blockDisplayMeta.setBrightnessOverride(15);
            blockDisplayMeta.setBlockState(color.getBlock());
        });
        //edit projectile movement and collision properties
        entity.setAerodynamics(new Aerodynamics(0, 0, 0)); //TODO: add vertical/horizontal drag if wanted
        entity.setBoundingBox(0.5, 0.5, 0.5);
        entity.setVelocity(event.getPlayer().getPosition().direction().mul(3.0)); //TODO: fix entity not moving
        //spawn the projectile
        entity.setInstance(instance, spawnPosition);

        entity.scheduleRemove(Duration.ofSeconds(3)); //TODO: remove on collision, but for testing right now 3 seconds is fine
    }

    @Override
    public void onClickBlock(PlayerUseItemOnBlockEvent event) {} //don't fire weapon when clicking a block
}
