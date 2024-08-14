package xyz.tbvns.towers;

import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;
import xyz.tbvns.config.objects.TowerArmor;
import xyz.tbvns.config.objects.TowerType;
import xyz.tbvns.game.Enemy;
import xyz.tbvns.player.GamePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Getter
public class Tower extends EntityCreature {

    private final TowerType type;
    private final GamePlayer owner;
    private int level;
    private long lastAttackTime = System.currentTimeMillis();
    private final List<Enemy> targets = new ArrayList<>();

    public Tower(@NotNull EntityType entityType, TowerType type, GamePlayer owner, int level) {
        super(entityType);
        this.type = type;
        this.owner = owner;
        this.level = level;
        setItems();
    }

    /**
     * Controls the behaviour of the tower each tick
     */
    public void tick() {
        if (System.currentTimeMillis() - lastAttackTime > type.getAttackSpeedFromLevel(level)) {
            lastAttackTime = System.currentTimeMillis();
            targets.clear();

            AtomicReference<Enemy> target = new AtomicReference<>();
            getInstance().getEntities().forEach(e -> {
                if (e instanceof Enemy enemy && enemy.getHealth() > 0) {
                    Pos tower = getPosition();
                    Pos enemyPos = e.getPosition();
                    double d2 = Math.pow((tower.x() - enemyPos.x()), 2) + Math.pow((tower.z() - enemyPos.z()), 2); //2D distance squared
                    double atk2 = (type.getAttackRangeFromLevel(level) * type.getAttackRangeFromLevel(level)); //attack range squared
                    if (d2 <= atk2) {
                        if (target.get() == null) {
                            target.set((Enemy) e);
                        } else if (target.get().getTickAlive() * target.get().getEnemieObject().getSpeed() < enemy.getTickAlive() * enemy.getEnemieObject().getSpeed()) {
                            target.set((Enemy) e);
                        }
                    }
                }
            });

            if (type.isSplash() && !targets.isEmpty()) {
                //Splash damage
                if (target.get() != null) lookAt(target.get()); else lookAt(targets.getFirst());
                for (Enemy t : targets) {
                    t.damage(owner, type.getColor(), type.getAttackDamageFromLevel(level));
                }
            } else if (target.get() != null) {
                //Single target
                target.get().damage(owner, type.getColor(), type.getAttackDamageFromLevel(level));
                lookAt(target.get());
            }
        }
    }

    /**
     * Initializes the tower using its current level.
     */
    public void setItems() {
        String itemInHand = type.getItemInHandFromLevel(level);
        Material itemMaterial = Material.fromNamespaceId(itemInHand);
        if (itemMaterial != null) setItemInMainHand(ItemStack.builder(itemMaterial).build());

        TowerArmor setType = type.getArmorFromLevel(level);
        switch (setType) {
            case LEATHER -> {
                if (type.getArmorPieceFromID(0)) setHelmet(ItemStack.builder(Material.LEATHER_HELMET).build());
                if (type.getArmorPieceFromID(1)) setChestplate(ItemStack.builder(Material.LEATHER_CHESTPLATE).build());
                if (type.getArmorPieceFromID(2)) setLeggings(ItemStack.builder(Material.LEATHER_LEGGINGS).build());
                if (type.getArmorPieceFromID(3)) setBoots(ItemStack.builder(Material.LEATHER_BOOTS).build());
            }
            case CHAINMAIL -> {
                if (type.getArmorPieceFromID(0)) setHelmet(ItemStack.builder(Material.CHAINMAIL_HELMET).build());
                if (type.getArmorPieceFromID(1)) setChestplate(ItemStack.builder(Material.CHAINMAIL_CHESTPLATE).build());
                if (type.getArmorPieceFromID(2)) setLeggings(ItemStack.builder(Material.CHAINMAIL_LEGGINGS).build());
                if (type.getArmorPieceFromID(3)) setBoots(ItemStack.builder(Material.CHAINMAIL_BOOTS).build());
            }
            case IRON -> {
                if (type.getArmorPieceFromID(0)) setHelmet(ItemStack.builder(Material.IRON_HELMET).build());
                if (type.getArmorPieceFromID(1)) setChestplate(ItemStack.builder(Material.IRON_CHESTPLATE).build());
                if (type.getArmorPieceFromID(2)) setLeggings(ItemStack.builder(Material.IRON_LEGGINGS).build());
                if (type.getArmorPieceFromID(3)) setBoots(ItemStack.builder(Material.IRON_BOOTS).build());
            }
            case GOLD -> {
                if (type.getArmorPieceFromID(0)) setHelmet(ItemStack.builder(Material.GOLDEN_HELMET).build());
                if (type.getArmorPieceFromID(1)) setChestplate(ItemStack.builder(Material.GOLDEN_CHESTPLATE).build());
                if (type.getArmorPieceFromID(2)) setLeggings(ItemStack.builder(Material.GOLDEN_LEGGINGS).build());
                if (type.getArmorPieceFromID(3)) setBoots(ItemStack.builder(Material.GOLDEN_BOOTS).build());
            }
            case DIAMOND -> {
                if (type.getArmorPieceFromID(0)) setHelmet(ItemStack.builder(Material.DIAMOND_HELMET).build());
                if (type.getArmorPieceFromID(1)) setChestplate(ItemStack.builder(Material.DIAMOND_CHESTPLATE).build());
                if (type.getArmorPieceFromID(2)) setLeggings(ItemStack.builder(Material.DIAMOND_LEGGINGS).build());
                if (type.getArmorPieceFromID(3)) setBoots(ItemStack.builder(Material.DIAMOND_BOOTS).build());
            }
            case NETHERITE -> {
                if (type.getArmorPieceFromID(0)) setHelmet(ItemStack.builder(Material.NETHERITE_HELMET).build());
                if (type.getArmorPieceFromID(1)) setChestplate(ItemStack.builder(Material.NETHERITE_CHESTPLATE).build());
                if (type.getArmorPieceFromID(2)) setLeggings(ItemStack.builder(Material.NETHERITE_LEGGINGS).build());
                if (type.getArmorPieceFromID(3)) setBoots(ItemStack.builder(Material.NETHERITE_BOOTS).build());
            }
            default -> {
                setHelmet(ItemStack.builder(Material.AIR).build());
                setChestplate(ItemStack.builder(Material.AIR).build());
                setLeggings(ItemStack.builder(Material.AIR).build());
                setBoots(ItemStack.builder(Material.AIR).build());
            }
        }
    }

    /**
     * Attempts to upgrade this tower.
     */
    public void upgrade() {
        // Check if tower can be upgraded
        if (level == type.getMaxLevel()) {
            owner.sendMessage(Component.text("This tower is already max level!"));
            return;
        }

        float upgradeCost = type.getPriceFromLevel(level + 1); //Since level start at one and array start a 0, there is no need to add 1

        // Check if player has enough money to upgrade
        if (owner.getGold() < upgradeCost) {
            owner.sendMessage(Component.text("You do not have enough gold to upgrade this tower!").color(TextColor.color(255, 0, 0)));
            return;
        }

        // Remove gold
        //TODO: action bar with remove gold and upgraded tower message
        owner.setGold(owner.getGold() - Math.round(upgradeCost));

        // Upgrade tower
        level += 1;
        setItems();
        //TODO: more text formatting, maybe add name of tower? Or move this to action bar
        owner.sendMessage(Component.text("Upgraded tower to level " + level + " !"));
    }

    /**
     * Sells this tower.
     */
    public void sell() {
        // Remove the entities in-game
        this.remove();

        // Calculate gold return
        double goldReturn = (type.getPriceFromLevel(level) * 0.8);

        // Send message and play sound (add more formatting later)
        owner.sendMessage(Component.text("Sold tower for " + goldReturn + " gold!"));
        owner.playPlayerSound(Key.key("entity.silverfish.death"));
        owner.setGold((int) (owner.getGold() + Math.round(goldReturn)));
        owner.getCurrentPlacedTowers().remove(this);
    }

}
