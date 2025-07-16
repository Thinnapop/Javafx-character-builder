package se233.chapter1.controller;

import se233.chapter1.Launcher;
import se233.chapter1.model.DamageType;
import se233.chapter1.model.character.*;
import se233.chapter1.model.item.BasedEquipment;

import java.util.ArrayList;
import java.util.Random;

import static se233.chapter1.Launcher.equippedArmor;
import static se233.chapter1.Launcher.equippedWeapon;

public class GenCharacter {
    public static BasedCharacter setUpCharacter(){
        BasedCharacter character ;
        Random rand =  new Random();
        int type = rand.nextInt(4)+1;
        int basedDef = rand.nextInt(50)+1;
        int basedRes = rand.nextInt(50)+1;
        ArrayList<BasedEquipment> inventory = Launcher.getAllEquipments();
        BasedCharacter characterPane = Launcher.getMainCharacter();
        if (Launcher.getEquippedWeapon() != null) {
            characterPane.unequipWeapon();
            inventory.add(equippedWeapon);
            Launcher.setEquippedWeapon(null);
        }
        if (Launcher.getEquippedArmor() != null) {
            characterPane.unequipArmor();
            inventory.add(equippedArmor);
            Launcher.setEquippedArmor(null);
        }
        if (type == 1){
            character = new MagicalCharacter("Yuki(Mage)","assets/wizard.png",basedDef,basedRes);
            character.unequipBoth();
        }else if (type == 2){
            character = new PhysicalCharacter("Jaden(Knight)","assets/knight.png",basedRes,basedRes,DamageType.physical);
            character.unequipBoth();
        }
        else if (type == 3){
            character = new BattleMageCharacter("Eva(BattleMage)","assets/battlemage.png",basedDef,basedRes,DamageType.hybrid);
            character.unequipBoth();
        }
        else {
            character = new PriestCharacter("Seer(Priest)","assets/priest.png", DamageType.magical);
            character.unequipBoth();
        }
        Launcher.setAllEquipments(inventory);
        return character;
    }
}
