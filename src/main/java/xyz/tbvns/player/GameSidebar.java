package xyz.tbvns.player;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.Sidebar;
import xyz.tbvns.Utils;

public class GameSidebar {

    private final Sidebar sidebar;

    /**
     * Constructs a game sidebar for a player.
     */
    public GameSidebar() {
        sidebar = new Sidebar(Utils.format("<green><bold>Color Tower Defense"));
        createLines();
    }

    private void createLines() {
        sidebar.createLine(new Sidebar.ScoreboardLine("space", Component.empty(), 3));
        sidebar.createLine(new Sidebar.ScoreboardLine("health", getHealthLineFormat(50), 2));
        sidebar.createLine(new Sidebar.ScoreboardLine("gold", getGoldLineFormat(0), 1));
        sidebar.createLine(new Sidebar.ScoreboardLine("space1", Component.empty(), 0));
    }

    public void updateGoldLine(int newGold) {
        sidebar.updateLineContent("gold", getGoldLineFormat(newGold));
    }

    private Component getGoldLineFormat(int gold) {
        return Utils.format("<gray>Gold: <gold>" + Utils.formatWithCommas(gold));
    }

    public void updateHealthLine(int health) {
        sidebar.updateLineContent("health", getHealthLineFormat(health));
    }

    private Component getHealthLineFormat(int health) {
        return Utils.format("<gray>Health: <red>" + health + "❤");
    }

    public void addViewer(Player v) {
        sidebar.addViewer(v);
    }

    public void removeViewer(Player v) {
        sidebar.removeViewer(v);
    }

}
