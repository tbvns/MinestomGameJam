package xyz.tbvns.config.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TowerLevel {
    public TowerLevel() {} //For jackson

    float price;
    float attackDamage;
    float attackSpeed;
    int attackRange;
    TowerArmor armorSet;
    String itemInHand;
    int guiPos;
}
