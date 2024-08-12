package xyz.tbvns.item.custom;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.Material;
import org.apache.commons.lang3.StringUtils;
import xyz.tbvns.game.Color;
import xyz.tbvns.item.Item;
import xyz.tbvns.item.attribute.Clickable;
import xyz.tbvns.player.GamePlayer;
import xyz.tbvns.projectils.ColorProjectile;
import xyz.tbvns.projectils.Projectile;

import java.lang.reflect.InvocationTargetException;

public class ColorGun extends Item implements Clickable {

    private final Color color;
    private final Class<? extends ColorProjectile> colorProjectile;

    /**
     * Constructs a new color gun item.
     *
     * @param color    The {@link Color} of this color gun
     * @param material The {@link Material} of the custom item
     */
    public ColorGun(Color color, Material material) {
        super("color_gun_" + color.name().toLowerCase(), material, color.getColorCode() + StringUtils.capitalize(color.name().toLowerCase()) +
                        " Color Gun", "<gray>Shoots a " + color.name().toLowerCase() + "bullet on right-click.");
        this.color = color;
        this.colorProjectile = null; //default
    }

    /**
     * Constructs a new color gun item with a custom {@link ColorProjectile}.
     *
     * @param color    The {@link Color} of this color gun
     * @param material The {@link Material} of the custom item
     * @param colorProjectile The {@link ColorProjectile} to use when shooting this weapon
     */
    public ColorGun(Color color, Material material, Class<? extends ColorProjectile> colorProjectile) {
        super("color_gun_" + color.name().toLowerCase(), material, color.getColorCode() + StringUtils.capitalize(color.name().toLowerCase())
                        + " Color Gun", "<gray>Shoots a " + color.name().toLowerCase() + "bullet on right-click.");
        this.color = color;
        this.colorProjectile = colorProjectile;
    }

    @Override
    public void onClick(PlayerUseItemEvent event) {
        Instance instance = event.getInstance();

        Projectile projectile = instantiate(instance, (GamePlayer) event.getPlayer());
        projectile.spawn();
        event.getPlayer().playSound(Sound.sound().type(Key.key("block.dispenser.dispense"))
                .pitch(Math.max(2 - color.getProjectileScale(), 0.0f)).build());
    }

    @Override
    public void onClickBlock(PlayerUseItemOnBlockEvent event) {} //don't fire weapon when clicking a block

    private ColorProjectile instantiate(Instance instance, GamePlayer gamePlayer) {
        if (colorProjectile != null) {
            try {
                return colorProjectile.getDeclaredConstructor(Color.class, Instance.class, GamePlayer.class).newInstance(color, instance, gamePlayer);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new ColorProjectile(color, instance, gamePlayer);
    }
}
