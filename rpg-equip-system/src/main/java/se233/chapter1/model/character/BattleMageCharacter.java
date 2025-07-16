package se233.chapter1.model.character;

import se233.chapter1.model.DamageType;

public class BattleMageCharacter extends BasedCharacter{
    public BattleMageCharacter(String name, String imgpath, int basedDef, int basedRes, DamageType type) {
        this.name = name;
        this.imgpath = imgpath;
        this.type = DamageType.hybrid;
        this.basedDef = basedDef;
        this.basedRes = basedRes;
        this.fullHP = 40;
        this.basedPow = 40;
        this.hp = this.fullHP;
        this.power = this.basedPow;
        this.defense = basedDef;
        this.resistance = basedRes;
    }
}
