package main;

import entity.Possum;
import object.CatFood;
import object.Catnip;
import object.Chest;
import object.Vase;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp=gp;
	}
	public void setObject() {
		
	}
	public void setNPC() {
		gp.npc[0]=new Possum(gp);
		gp.npc[0].worldX=gp.tileSize*14;
		gp.npc[0].worldY=gp.tileSize*11;
	}
}
