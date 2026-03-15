package com.dm.content.skill.impl.woodcutting;

import com.dm.Config;
import com.dm.content.achievement.AchievementHandler;
import com.dm.content.achievement.AchievementKey;
import com.dm.content.activity.randomevent.RandomEventHandler;
import com.dm.content.clanchannel.content.ClanTaskKey;
import com.dm.content.pet.PetData;
import com.dm.content.pet.Pets;
import com.dm.content.prestige.PrestigePerk;
import com.dm.content.skill.impl.firemaking.FiremakingData;
import com.dm.content.skillcape.SkillCape;
import com.dm.game.action.SkillIdAction;
import com.dm.game.action.policy.WalkablePolicy;
import com.dm.game.task.impl.ObjectReplacementEvent;
import com.dm.game.world.World;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.skill.Skill;
import com.dm.game.world.items.Item;
import com.dm.game.world.items.ItemDefinition;
import com.dm.game.world.object.GameObject;
import com.dm.game.world.position.Area;
import com.dm.net.packet.out.SendMessage;
import com.dm.util.RandomUtils;
import com.dm.util.Utility;

import java.util.Optional;

/**
 * Handles the woodcutting action event.
 *
 * @author Daniel
 */
public class WoodcuttingAction extends SkillIdAction<Player> {
    private final GameObject object;
    private final TreeData tree;
    private final AxeData axe;

    WoodcuttingAction(Player mob, GameObject object, TreeData tree, AxeData axe) {
        super(mob, 3, false, Skill.WOODCUTTING);
        this.object = object;
        this.tree = tree;
        this.axe = axe;
    }

    private boolean chop() {
        if (getMob().inventory.getFreeSlots() == 0) {
            getMob().dialogueFactory.sendStatement("You can't carry anymore logs.").execute();
            return false;
        }

        getMob().animate(axe.animation);

        if (Woodcutting.success(getMob(), tree, axe)) {
            if (object == null || !object.active()) {
                return false;
            }

            BirdsNest.drop(getMob());
            RandomEventHandler.trigger(getMob());
            Pets.onReward(getMob(), PetData.BEAVER.getItem(), 6250);
            getMob().send(new SendMessage("You get some " + ItemDefinition.get(tree.item).getName() + ".", true));
            AchievementHandler.activate(getMob(), AchievementKey.WOODCUTTING, 1);
            getMob().playerAssistant.activateSkilling(1);
            getMob().skills.addExperience(Skill.WOODCUTTING, tree.experience * Config.WOODCUTTING_MODIFICATION);

            if ((SkillCape.isEquipped(getMob(), SkillCape.WOODCUTTING) || getMob().prestige.hasPerk(PrestigePerk.DOUBLE_WOOD)) && RandomUtils.success(.15)) {
                getMob().inventory.addOrDrop(new Item(tree.item, 1));
            }
            if (axe == AxeData.INFERNAL && Utility.random(3) == 0) {
                Optional<FiremakingData> firemakingData = FiremakingData.forId(tree.item);

                if (firemakingData.isPresent()) {
                    getMob().skills.addExperience(Skill.FIREMAKING, (firemakingData.get().getExperience() * Config.WOODCUTTING_MODIFICATION) / 2);
                } else {
                    getMob().inventory.add(tree.item, 1);
                }

            } else {
                getMob().inventory.add(tree.item, 1);
            }

            if (tree == TreeData.WILLOW_TREE || tree == TreeData.WILLOW_TREE1) {
                getMob().forClan(channel -> channel.activateTask(ClanTaskKey.CHOP_WILLOW_LOG, getMob().getName()));
            } else if (tree == TreeData.YEW_TREE) {
                getMob().forClan(channel -> channel.activateTask(ClanTaskKey.YEW_LOG, getMob().getName()));
            } else if (tree == TreeData.MAGIC_TREE) {
                getMob().forClan(channel -> channel.activateTask(ClanTaskKey.MAGIC_LOG, getMob().getName()));
            }
                if (object.active() && (tree.logs == 1 || tree.logs != 1 && Utility.random(8) <= 0)) {
                    this.cancel();
                    getMob().resetAnimation();
                    object.getGenericAttributes().set("logs", -1);
                        World.schedule(new ObjectReplacementEvent(object, tree.replacement, tree.respawn, () -> {
                            object.getGenericAttributes().set("logs", tree.logs);
                        }));
                }
            }
        return true;
    }

    @Override
    public void onSchedule() {
        if (!object.getGenericAttributes().has("logs")) {
            object.getGenericAttributes().set("logs", tree.logs);
        }

        getMob().animate(axe.animation);
    }

    @Override
    public void execute() {
        if (!getMob().skills.get(Skill.WOODCUTTING).isDoingSkill()) {
            cancel();
            return;
        }
        if (object == null || !object.active() || object.getGenericAttributes() == null) {
            cancel();
            return;
        }

        if (!chop()) {
            cancel();
        }
    }

    @Override
    public void onCancel(boolean logout) {
        getMob().resetFace();
        getMob().skills.get(Skill.WOODCUTTING).setDoingSkill(false);
    }

    @Override
    public WalkablePolicy getWalkablePolicy() {
        return WalkablePolicy.NON_WALKABLE;
    }

    @Override
    public String getName() {
        return "woodcutting-action";
    }
}
