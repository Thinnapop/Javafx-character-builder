package se233.chapter1.controller;

import se233.chapter1.model.DamageType;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

import java.util.ArrayList;

public class GenItemList {
    public static ArrayList<BasedEquipment> setUpItem(){
        ArrayList<BasedEquipment> itemList = new ArrayList<BasedEquipment>(5);
        itemList.add(new Weapon("Sword", 10, DamageType.physical,"assets/sword.png"));
        itemList.add(new Weapon("Staff", 40, DamageType.magical,"assets/magic_staff_33x33.png"));
        itemList.add(new Weapon("Gun", 20, DamageType.physical,"assets/gun.png"));
        itemList.add(new Weapon("Wand", 30, DamageType.magical,"assets/staff.png"));
        itemList.add(new Armor("Shirt", 0,50,"assets/shirt.png"));
        itemList.add(new Armor("armor", 50,0,"assets/armor.png"));
        itemList.add(new Weapon("dagger", 5, DamageType.physical,"assets/dagger.png"));
        itemList.add(new Weapon("scythe", 40, DamageType.physical,"assets/scythe.png"));

        return itemList;
    }
}
