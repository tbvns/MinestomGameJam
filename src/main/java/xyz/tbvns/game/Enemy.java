package xyz.tbvns.game;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.entity.damage.Damage;
import net.minestom.server.network.packet.server.play.EntityVelocityPacket;
import org.jetbrains.annotations.NotNull;
import xyz.tbvns.Utils;
import xyz.tbvns.config.objects.EnemieObject;

import java.util.List;
import java.util.Random;

/**
 * Abstract framework for enemies that can be summoned by a player
 * and appear on enemy player tower defense tracks.
 */
@Getter
public class Enemy extends EntityCreature {

    protected final EnemieObject enemieObject;
    private int tickAlive = 0;
    @Getter private Pos shift;

    //TODO: link a mob to a team if we do team support
    public Enemy(@NotNull EnemieObject enemieObject) {
        super(EntityType.fromNamespaceId(enemieObject.getModelName()));
        this.enemieObject = enemieObject;

        double maxShift = enemieObject.getMaxShift(); //Add this to the config when enemies are ported to configs
        Random random = new Random();
        shift = new Pos(random.nextDouble(-maxShift, maxShift), 0, random.nextDouble(-maxShift, maxShift));

        // Initialize entity attributes
        this.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(enemieObject.getSpeed());
        this.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(enemieObject.getMaxHealth());
        this.setHealth((float) enemieObject.getMaxHealth());

        this.setCustomNameVisible(true);
        this.setCustomName(getCustomNameText());
    }

    /**
     * Damages this TowerDefenseEnemy.
     * @param damage The damage amount
     */
    public void damage(Player p, float damage) {
        this.damage(Damage.fromEntity(p, damage));
        this.setCustomName(getCustomNameText());
        if (this.getHealth() <= 0) {
            //TODO: add money for upgrade
        }
    }

    /**
     * Actions this enemy takes when reaching the end of a tower defense track.
     */
    public void reachEnd() {
        this.remove();
        //TODO: logic on damaged player
    }

    // I feel like this can be optimized or condensed. Any ideas?
    private Component getCustomNameText() {
        Component nameText = Utils.format("<green>" + enemieObject.getName());
        nameText = nameText.append(Component.text(Utils.formatWithCommas(getHealth()), ChatColor.RED.toColor()));
        nameText = nameText.append(Component.text("/", ChatColor.GRAY.toColor()));
        nameText = nameText.append(Component.text(Utils.formatWithCommas(enemieObject.getMaxHealth()), ChatColor.RED.toColor()));
        return nameText;
    }

    /**
     * Ticks this enemy, incrementing its ticks alive
     * and sending animation packets to all players
     * in the tower defense game.
     * @param towerDefensePlayers The list of players
     *                            whom the entity animation
     *                            should be displayed to
     */
    public void tick(List<Player> towerDefensePlayers) {
        tickAlive++;
        for (Player player : towerDefensePlayers) {
            Vec dir = position.direction();
            if (!dir.isZero()) dir = dir.normalize();

            Vec walk = dir.mul(5 * enemieObject.getSpeed());
            player.sendPacket(new EntityVelocityPacket(this.getEntityId(), (short) walk.x(), (short) walk.y(), (short) walk.z()));
        }
    }
}
