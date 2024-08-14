package xyz.tbvns.player;

import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.entity.Player;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;
import xyz.tbvns.Utils;
import xyz.tbvns.towers.Tower;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Custom player wrapper
 */
@Getter
public class GamePlayer extends Player {

    private int gold;
    private int playerHealth;
    private final GameSidebar gameSidebar;
    private final List<Tower> currentPlacedTowers = new ArrayList<>();

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

    public void playPlayerSound(Key soundKey) {
        playPlayerSound(soundKey, 1f, 1f);
    }

    public void playPlayerSound(Key soundKey, float volume) {
        playPlayerSound(soundKey, volume, 1f);
    }

    public void playPlayerSound(Key soundKey, float volume, float pitch) {
        playSound(Sound.sound().type(soundKey).source(Sound.Source.PLAYER).volume(volume).pitch(pitch).build(), Sound.Emitter.self());
    }

}
