package xyz.tbvns.item.custom;

import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.Material;
import xyz.tbvns.game.Color;

import java.util.Objects;

public class RedGun extends ColorGun {
    public RedGun() {
        super(Color.RED, Material.LEATHER_HORSE_ARMOR);
    }
}
