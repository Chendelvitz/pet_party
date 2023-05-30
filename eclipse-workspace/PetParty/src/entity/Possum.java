package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class Possum extends Entity{
	public Possum(GamePanel gp) {
		super(gp);
		
		direction="down";
		speed=1;
		//solidArea=new Rectangle(8,8,32,32);
		
		getImage();
		setDialogue();
	}
	public void setDefaultValues() {
		worldX=gp.tileSize*15;
		worldY=gp.tileSize*11;
		speed=4;
		direction="down";
	}
	public void getImage() {
		up1=setup("/npc/possum_up_1");
		up2=setup("/npc/possum_up_2");
		down1=setup("/npc/possum_down_1");
		down2=setup("/npc/possum_down_2");
		left1=setup("/npc/possum_left_1");
		left2=setup("/npc/possum_left_2");
		right1=setup("/npc/possum_right_1");
		right2=setup("/npc/possum_right_2");
	}
	public void setDialogue() {
		dialogues[0]="Hi! *sniff *sniff. My name is Possum. \nI am Emma's hamster. Nice to meet you!";
		dialogues[1]="You have a diverse and arduous journey \nahead of you. Explore this land and you \nwill be met with all of its beauties and \nhardships. *sniff *sniff";
	}
	public void setAction() {
		actionLockCounter++;
		
		if(actionLockCounter==120) {
			Random random=new Random();
			int i=random.nextInt(100)+1;//pick up a number from 1-100
			
			if(i<=25) {
				direction="up";	
			}
			if(i>25&&i<=50) {
				direction="down";
			}
			if(i>50&&i<=75) {
				direction="left";
			}
			if(i>75&&i<=100) {
				direction="right";
			}
			
			actionLockCounter=0;
		}
	}
	public void speak() {
		
		//specific dialogue
		
		super.speak();
	}
}
