package org.dm.defs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.dm.game.world.entity.combat.ranged.RangedAmmunition;
import com.dm.game.world.entity.combat.ranged.RangedWeaponDefinition;
import com.dm.game.world.entity.combat.ranged.RangedWeaponType;
import com.dm.game.world.entity.combat.weapon.WeaponInterface;
import com.dm.game.world.items.ItemDefinition;
import com.dm.game.world.items.containers.equipment.EquipmentType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Jire
 */
public final class ItemDefLoader {

    private ItemDefLoader() {
        // Private constructor for utility class
    }

    public static final Int2ObjectMap<ItemDef> map = new Int2ObjectOpenHashMap<>();

    public static void load() {
        load(new GsonBuilder().setPrettyPrinting().create());
    }

    public static void load(Gson gson) {
        File directory = new File("data/def/items-json/");
        File[] files = directory.listFiles();

        if (files == null) return;

        for (File file : files) {
            if (!file.getName().endsWith(".json")) continue;

            String nameWithoutExt = file.getName().substring(0, file.getName().lastIndexOf('.'));
            Integer id;
            try {
                id = Integer.parseInt(nameWithoutExt);
            } catch (NumberFormatException e) {
                continue;
            }

            try (FileReader reader = new FileReader(file)) {
                ItemDef itemDef = gson.fromJson(reader, ItemDef.class);
                map.put(id, itemDef);

                ItemDefinition oldDef = ItemDefinition.DEFINITIONS[id];
                if (oldDef == null) {
                    ItemDefinition newDef = new ItemDefinition(id, itemDef.getName());
                    updateFrom(newDef, itemDef);
                    ItemDefinition.DEFINITIONS[id] = newDef;
                } else {
                    updateFrom(oldDef, itemDef);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void updateFrom(ItemDefinition def, ItemDef itemDef) {
        String name = itemDef.getName();

        boolean noted = itemDef.getLinkedIdItem() != 0 && itemDef.getLinkedIdNoted() == 0;
        def.setStackable(itemDef.isStackable() || noted);
        def.setTradeable(itemDef.isTradeable());

        if (noted) {
            def.setUnnotedId(itemDef.getLinkedIdItem());
        } else if (itemDef.getLinkedIdNoted() != 0) {
            def.setNotedId(itemDef.getLinkedIdNoted());
        }

        def.setBase_value(itemDef.getCost());
        def.setLowAlch(itemDef.getLowAlch());
        def.setHighAlch(itemDef.getHighAlch());
        def.setWeight(itemDef.getWeight());

        ItemEquipDef equipment = itemDef.getEquipment();
        if (equipment != null) {
            String equipmentSlot = equipment.getSlot();
            def.setTwoHanded("2h".equals(equipmentSlot));
            def.setBonuses(equipment.toBonusArray());

            ItemEquipDefRequirement req = equipment.getRequirements();
            if (req != null) {
                def.setRequirements(req.toArray());
            }
            equipment.getRequirements();

            if (def.getEquipmentType() == null || def.getEquipmentType() == EquipmentType.NOT_WIELDABLE) {
                if (def.isTwoHanded()) {
                    def.setEquipmentType(EquipmentType.WEAPON);
                } else if (startsWithIgnoreCase(name, "hood") || endsWithIgnoreCase(name, "hood")) {
                    def.setEquipmentType(EquipmentType.FACE);
                } else if (containsIgnoreCase(name, "full helm") || name.equals("Masori mask")
                        || name.equals("Masori mask (f)") || name.equals("Gas mask")) {
                    def.setEquipmentType(EquipmentType.HELM);
                } else if (containsIgnoreCase(name, "bloodbark helm")) {
                    def.setEquipmentType(EquipmentType.HAT);
                } else if (containsIgnoreCase(name, "med helm")) {
                    def.setEquipmentType(EquipmentType.FACE);
                } else if (containsIgnoreCase(name, "dragon hunter lance")) {
                    def.setEquipmentType(EquipmentType.WEAPON);
                    def.setStand_animation(813);
                    def.setWalk_animation(1205);
                    def.setRun_animation(2563);
                    def.setWeaponInterface(WeaponInterface.HUNTER_LANCE);
                } else if (containsIgnoreCase(name, "sled")) {
                    def.setEquipmentType(EquipmentType.WEAPON);
                    def.setStand_animation(1461);
                    def.setWalk_animation(1468);
                    def.setRun_animation(1467);
                } else if (containsIgnoreCase(name, "Tumeken's shadow")) {
                    def.setEquipmentType(EquipmentType.WEAPON);
                    def.setStand_animation(9494);
                    def.setWalk_animation(1703);
                    def.setRun_animation(1707);
                    def.setWeaponInterface(WeaponInterface.SHADOW);
                } else if (containsIgnoreCase(name, "Inquisitor's mace")) {
                    def.setWeaponInterface(WeaponInterface.INQUISITOR_MACE);
                    def.setEquipmentType(EquipmentType.WEAPON);
                } else if (containsIgnoreCase(name, "helm")) {
                    def.setEquipmentType(startsWithIgnoreCase(name, "dharok") ? EquipmentType.FACE : EquipmentType.HELM);
                } else if (containsIgnoreCase(name, "mask")) {
                    def.setEquipmentType(EquipmentType.MASK);
                } else {
                    EquipmentType newType = EquipmentType.forNewName(equipmentSlot);
                    if (newType != null) def.setEquipmentType(newType);
                }
            }
        }

        ItemEquipWeaponDef weapon = itemDef.getWeapon();
        if (weapon != null) {
            if (def.getWeaponInterface() == null) {
                def.setWeaponInterface(WeaponInterface.forNewName(weapon.getWeaponType()));
            }

            boolean replaceRangedDef = def.getRangedDefinition().isEmpty();
            if (!replaceRangedDef) {
                RangedWeaponDefinition rDef = def.getRangedDefinition().get();
                replaceRangedDef = rDef.getAllowed() == null || rDef.getAllowed().length == 0;
            }

            if (replaceRangedDef) {
                RangedWeaponType type = RangedWeaponType.THROWN;
                RangedAmmunition allowed = RangedAmmunition.forItemId(def.getId());
                if (allowed != null) {
                    def.setRangedDefinition(new RangedWeaponDefinition(type, allowed));
                }
            }
        }
    }

    // Helper methods to replicate Kotlin's String functions
    private static boolean startsWithIgnoreCase(String str, String prefix) {
        return str.toLowerCase().startsWith(prefix.toLowerCase());
    }

    private static boolean endsWithIgnoreCase(String str, String suffix) {
        return str.toLowerCase().endsWith(suffix.toLowerCase());
    }

    private static boolean containsIgnoreCase(String str, String search) {
        return str.toLowerCase().contains(search.toLowerCase());
    }

    public static void main(String[] args) {
        load();
    }
}