package xyz.tbvns.projectils;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.damage.Damage;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.ParticlePacket;
import net.minestom.server.particle.Particle;
import xyz.tbvns.game.Color;
import xyz.tbvns.game.Enemy;
import xyz.tbvns.player.GamePlayer;

public class YellowProjectile extends ColorProjectile {
    public YellowProjectile(Color color, Instance instance, GamePlayer shooter) {
        super(color, instance, shooter);
    }

    @Override
    public boolean onCollideBlock(Block block) {
        explode();
        return true;
    }

    @Override
    public boolean onCollideEntity(Entity entity) {
        if (entity.equals(this.shooter)) return false;
        explode();
        return true;
    }

    private void explode() {
        instance.getPlayers().forEach(p -> {
            p.sendPacket(new ParticlePacket(Particle.EXPLOSION, position, new Pos(0,0,0), 1f, 1));
            p.playSound(Sound.sound().type(Key.key("entity.generic.explode")).volume(1.5f).build(), position);
        });
        for (Entity entity : instance.getNearbyEntities(position, 5)) {
            if (entity instanceof GamePlayer) continue;
            if (entity instanceof Enemy enemy) {
                enemy.damage(shooter, color, color.getProjectileDamage());
            } else if (entity instanceof LivingEntity livingEntity) {
                livingEntity.damage(new Damage(DamageType.PLAYER_ATTACK, this, shooter, position, color.getProjectileDamage()));
            }
        }
    }
}
