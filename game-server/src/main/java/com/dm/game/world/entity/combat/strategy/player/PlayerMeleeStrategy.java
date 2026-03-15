package com.dm.game.world.entity.combat.strategy.player;


import com.dm.content.activity.Activity;
import com.dm.content.activity.impl.duelarena.DuelArenaActivity;
import com.dm.content.activity.impl.duelarena.DuelRule;
import com.dm.content.itemaction.impl.ViggorasChainmace;
import com.dm.game.Animation;
import com.dm.game.UpdatePriority;
import com.dm.game.world.entity.combat.CombatType;
import com.dm.game.world.entity.combat.attack.FightType;
import com.dm.game.world.entity.combat.effect.impl.CombatPoisonEffect;
import com.dm.game.world.entity.combat.hit.CombatHit;
import com.dm.game.world.entity.combat.hit.Hit;
import com.dm.game.world.entity.combat.strategy.basic.MeleeStrategy;
import com.dm.game.world.entity.mob.Mob;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.game.world.items.Item;
import com.dm.game.world.items.containers.equipment.Equipment;
import com.dm.net.packet.out.SendMessage;

public class PlayerMeleeStrategy extends MeleeStrategy<Player> {
    private static final PlayerMeleeStrategy INSTANCE = new PlayerMeleeStrategy();

    protected PlayerMeleeStrategy() {
    }

    @Override
    public boolean canAttack(Player attacker, Mob defender) {
        if (Activity.search(attacker, DuelArenaActivity.class).isPresent()) {
            DuelArenaActivity activity = Activity.search(attacker, DuelArenaActivity.class).get();
            return !activity.rules.contains(DuelRule.NO_MELEE);
        }


        Item weapon = attacker.equipment.get(Equipment.WEAPON_SLOT);
        if(weapon != null && weapon.getId() == ViggorasChainmace.VIGGORAS_CHAINMACE_CHARGED_ID && attacker.viggorasChainmaceCharges < 1) {
            attacker.send(new SendMessage("Your Viggora's chainmace is out of charges!"));
            attacker.getCombat().reset();
            return false;
        }

        return true;
    }

    @Override
    public void attack(Player attacker, Mob defender, Hit hit) {
        Item weapon = attacker.equipment.get(Equipment.WEAPON_SLOT);
        if(weapon != null) {
            switch(weapon.getId()) {
                case ViggorasChainmace.VIGGORAS_CHAINMACE_CHARGED_ID -> {
                    attacker.viggorasChainmaceCharges--;
                }
            }
        }
    }

    @Override
    public void start(Player attacker, Mob defender, Hit[] hits) {
        if (attacker.isSpecialActivated()) {
            attacker.getCombatSpecial().drain(attacker);
        }

        attacker.animate(getAttackAnimation(attacker, defender), true);

        if (!defender.isPlayer())
            addCombatExperience(attacker, hits);
    }

    @Override
    public void hit(Player attacker, Mob defender, Hit hit) {
        if (hit.getDamage() < 1) {
            return;
        }

        CombatPoisonEffect.getPoisonType(attacker.equipment.getWeapon()).ifPresent(defender::poison);
    }

    @Override
    public CombatHit[] getHits(Player attacker, Mob defender) {
        return new CombatHit[]{nextMeleeHit(attacker, defender)};
    }

    @Override
    public int getAttackDelay(Player attacker, Mob defender, FightType fightType) {
        return attacker.getCombat().getFightType().getDelay();
    }

    @Override
    public int getAttackDistance(Player attacker, FightType fightType) {
        return fightType.getDistance();
    }

    @Override
    public Animation getAttackAnimation(Player attacker, Mob defender) {
        int animation = attacker.getCombat().getFightType().getAnimation();

        if (attacker.equipment.hasShield()) {
            Item weapon = attacker.equipment.getShield();
            FightType fightType = attacker.getCombat().getFightType();
            animation = weapon.getAttackAnimation(fightType).orElse(animation);
        }

        if (attacker.equipment.hasWeapon()) {
            Item weapon = attacker.equipment.getWeapon();
            FightType fightType = attacker.getCombat().getFightType();
            animation = weapon.getAttackAnimation(fightType).orElse(animation);
        }

        if (attacker.overrides.hasOverride(Equipment.WEAPON_SLOT)) {
            final var item = attacker.overrides.get(Equipment.WEAPON_SLOT);
            animation = attacker.overrides.getFightType(item).getAnimation();
        }

        return new Animation(animation, UpdatePriority.HIGH);
    }

    /*@Override
    public int modifyAccuracy(Player attacker, Mob defender, int roll) {
        if (CombatUtil.isFullVoid(attacker) && attacker.equipment.contains(11665)) {
            roll *= 1.10;
        }
        return roll;
    }

    @Override
    public int modifyAggressive(Player attacker, Mob defender, int roll) {
        if (CombatUtil.isFullVoid(attacker) && attacker.equipment.contains(11665)) {
            roll *= 1.10;
        }
        return roll;
    }*/

    @Override
    public CombatType getCombatType() {
        return CombatType.MELEE;
    }

    public static PlayerMeleeStrategy get() {
        return INSTANCE;
    }

}
