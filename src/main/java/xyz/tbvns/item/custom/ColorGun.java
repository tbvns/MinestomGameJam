package xyz.tbvns.item.custom;

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
import xyz.tbvns.projectils.Projectile;
import xyz.tbvns.projectils.RedProjectile;

import java.time.Duration;

public abstract class ColorGun extends Item implements Clickable {

    private final Color color;

    /**
     * Constructs a new color gun item.
     *
     * @param color    The {@link Color} of this color gun
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

        Projectile projectile = new RedProjectile(instance, event.getPlayer(), 0.5);
        projectile.spawn();

    }

    @Override
    public void onClickBlock(PlayerUseItemOnBlockEvent event) {} //don't fire weapon when clicking a block
}
