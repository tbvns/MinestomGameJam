package xyz.tbvns.player;

import lombok.Getter;
import net.minestom.server.entity.Player;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;
import xyz.tbvns.Utils;

import java.util.UUID;

/**
 * Custom player wrapper
 */
@Getter
public class GamePlayer extends Player {

    private int gold;
    private int playerHealth;
    private final GameSidebar gameSidebar;

    public GamePlayer(@NotNull UUID uuid, @NotNull String username, @NotNull PlayerConnection playerConnection) {
        super(uuid, username, playerConnection);
        this.playerHealth = 50;
        this.gold = 0;
        this.gameSidebar = new GameSidebar();
    }

    public void setGold(int newGold) {
        this.gold = newGold;
        gameSidebar.updateGoldLine(newGold);
    }

    public void addGold(int gold) {
        setGold(this.gold + gold);
        sendActionBar(Utils.format("<gold>+" + Utils.formatWithCommas(gold) + " gold"));
    }

    public void setHealth(int newHealth) {
        this.playerHealth = newHealth;
        gameSidebar.updateHealthLine(newHealth);
    }

}
