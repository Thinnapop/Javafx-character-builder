package se233.chapter1.model.character;

import se233.chapter1.model.DamageType;

public class PriestCharacter extends BasedCharacter{
    public PriestCharacter(String name, String imgpath,DamageType type){
        this.name = name;
        this.type = DamageType.magical;
        this.imgpath = imgpath;
        this.fullHP = 40;
        this.basedPow = 20;
        this.basedDef = 100;
        this.basedRes = 100;
        this.hp = this.fullHP;
        this.power = this.basedPow;
        this.defense= basedDef;
        this.resistance = basedRes;
    }
}
