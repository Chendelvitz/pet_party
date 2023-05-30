package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
	KeyHandler keyHandler;
	
	public final int screenX;
	public final int screenY;
	
	
	public Player(GamePanel gp, KeyHandler keyHandler) {
		super(gp);
		
		this.keyHandler=keyHandler;
		
		screenX=gp.screenWidth/2-(gp.tileSize/2);
		screenY=gp.screenHeight/2-(gp.tileSize/2);
		
		solidArea=new Rectangle();
		solidArea.x=8;
		solidArea.y=16;
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		solidArea.width=32;
		solidArea.height=32;
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		worldX=gp.tileSize*14;
		worldY=gp.tileSize*15;
		speed=4;
		direction="down";
		
		//PLAYER STATUS
		maxLife=6;
		life=maxLife;
	}
	public void getPlayerImage() {
		up1=setup("/player/player_up_1");
		up2=setup("/player/player_up_2");
		down1=setup("/player/player_down_1");
		down2=setup("/player/player_down_2");
		left1=setup("/player/player_left_1");
		left2=setup("/player/player_left_2");
		right1=setup("/player/player_right_1");
		right2=setup("/player/player_right_2");
	}
	public void update() {
		if(keyHandler.upPressed==true||keyHandler.downPressed==true||keyHandler.leftPressed==true||keyHandler.rightPressed==true) {
			if(keyHandler.upPressed==true) {
				direction="up";
			}
			else if(keyHandler.leftPressed==true) {
				direction="left";
			}
			else if(keyHandler.downPressed==true) {
				direction="down";
			}
			else if(keyHandler.rightPressed==true) {
				direction="right";
			}
			
			// CHECK TILE COLLISION
			collisionOn=false;
			gp.collisionDetector.checkTile(this);
			
			//CHECK OBJECT COLLISION
			int objIndex=gp.collisionDetector.checkObject(this, true);
			pickUpObject(objIndex);
			
			//CHECK NPC COLLISION
			int npcIndex=gp.collisionDetector.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			//CHECK EVENT
			gp.eventHandler.checkEvent();
			
			gp.keyHandler.enterPressed=false;
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn==false) {
				switch(direction) {
				case "up":
					worldY-=speed;
					break;
				case "left":
					worldX-=speed;
					break;
				case "down":
					worldY+=speed;
					break;
				case "right":
					worldX+=speed;
					break;
				}
			}

			spriteCounter++;
			if(spriteCounter>15) {
				if(spriteNum==1) {
					spriteNum=2;
				}
				else if(spriteNum==2) {
					spriteNum=1;
				}
				spriteCounter=0;
			}
		}
	}
	public void pickUpObject(int i) {
		if(i!=999) {
			
		}
	}
	public void interactNPC(int i) {
		if(i!=999) {
			if(gp.keyHandler.enterPressed==true) {
				gp.gameState=gp.dialogueState;
				gp.npc[i].speak();
			}
		}
	}
	public void draw(Graphics2D g2) {
		BufferedImage image=null;
		
		switch(direction) {
		case "up":
			if(spriteNum==1) {
			image=up1;
			}
			if(spriteNum==2) {
				image=up2;
			}
			break;
		case "down":
			if(spriteNum==1) {
				image=down1;
			}
			if(spriteNum==2) {
				image=down2;
			}
			break;
		case "left":
			if(spriteNum==1) {
				image=left1;
			}
			if(spriteNum==2) {
				image=left2;
			}
			break;
		case "right":
			if(spriteNum==1) {
				image=right1;
			}
			if(spriteNum==2) {
				image=right2;
			}
			break;
		}
		g2.drawImage(image,screenX,screenY,null);
	}
}
