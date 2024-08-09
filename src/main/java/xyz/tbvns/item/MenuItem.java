package xyz.tbvns.item;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
import net.minestom.server.item.Material;
import xyz.tbvns.Utils;
import xyz.tbvns.item.attribute.Clickable;

public class MenuItem extends Item implements Clickable {

    public MenuItem() {
        super("menu", Material.NETHER_STAR, "<gold>Game Menu");
    }

    @Override
    public void onClick(PlayerUseItemEvent event) {
        if (event.getHand() == Player.Hand.MAIN && !event.isCancelled()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Utils.format("<gray>You clicked on the menu!"));
        }
    }

    @Override
    public void onClickBlock(PlayerUseItemOnBlockEvent event) {
        if (event.getHand() == Player.Hand.MAIN) {
            event.getPlayer().sendMessage(Utils.format("<gray>You clicked on the menu!"));
        }
    }
}
