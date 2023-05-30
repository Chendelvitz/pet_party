package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.CatFood;
import object.Heart;
import object.SuperObject;

public class UI {
	Graphics2D g2;
	GamePanel gp;
	Font arial_17,arial_20,arial_40,arial_60,arial_80,dialog_96,dialog_48;
	BufferedImage full_heart,half_heart,empty_heart;
	public boolean messageOn=false;
	public String message="";
	int messageCounter=0;
	public boolean gameFinished=false;
	public String currentDialogue="";
	public int commandNum=0;
	public int titleScreenState=0; //0: first screen, 1: second screen, etc.
//	double playTime;
//	DecimalFormat dFormat=new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp=gp;
		
		arial_17=new Font("Arial",Font.PLAIN,17);
		arial_20=new Font("Arial",Font.PLAIN,20);
		arial_40=new Font("Arial",Font.PLAIN,40);
		arial_60=new Font("Arial",Font.PLAIN,60);
		arial_80=new Font("Arial",Font.PLAIN,80);
		dialog_96=new Font("Monospaced",Font.BOLD,96);
		dialog_48=new Font("Monospaced",Font.BOLD,48);
		
		//CREATE HUD OBJECT
		SuperObject heart=new Heart(gp);
		full_heart=heart.image;
		half_heart=heart.image1;
		empty_heart=heart.image2;
	}
	public void showMessage(String text) {
		message=text;
		messageOn=true;
	}
	public void draw(Graphics2D g2) {
		this.g2=g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		//TITLE STATE
		if(gp.gameState==gp.titleState) {
			drawTitleScreen();
		}
		//PLAY STATE
		if(gp.gameState==gp.playState) {
			drawPlayerLife();	
		}
		//PAUSE STATE
		if(gp.gameState==gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		//DIALOGUE STATE
		if(gp.gameState==gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
	}
	public void drawPlayerLife() {
		int x=gp.tileSize;
		int y=gp.tileSize;
		int i=0;
		
		//DRAW MAX LIFE
		while(i<gp.player.maxLife/2) {
			g2.drawImage(empty_heart, x, y, null);
			i++;
			x+=gp.tileSize;
		}
		
		//RESET
		x=gp.tileSize;
		y=gp.tileSize;
		i=0;
		
		//DRAW CURRENT LIFE
		while(i<gp.player.life) {
			g2.drawImage(half_heart, x, y, null);
			i++;
			if(i<gp.player.life) {
				g2.drawImage(full_heart, x, y, null);
			}
			i++;
			x+=gp.tileSize;
		}
	}
	public void drawTitleScreen() {
		if(titleScreenState==0) {
			//TITLE NAME
			Color borderColor1=new Color(227,176,1);
			Color borderColor2=new Color(120,99,3);
			BasicStroke newStroke1=new BasicStroke(35);
			BasicStroke newStroke2=new BasicStroke(3);
			BasicStroke newStroke3=new BasicStroke(5);
			g2.setColor(new Color(70,70,200));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			g2.setColor(borderColor1);
			g2.setStroke(newStroke1);
			g2.drawLine(0,15,gp.screenWidth,15);
			g2.drawLine(gp.screenWidth-15,0,gp.screenWidth-15,gp.screenHeight);
			g2.drawLine(0,gp.screenHeight-15,gp.screenWidth,gp.screenHeight-15);
			g2.drawLine(15,0,15,gp.screenHeight);
			g2.setColor(borderColor2);
			g2.setStroke(newStroke3);
			g2.drawLine(30,30,gp.screenWidth-30,30);
			g2.drawLine(gp.screenWidth-30,30,gp.screenWidth-30,gp.screenHeight-30);
			g2.drawLine(gp.screenWidth-30,gp.screenHeight-30,30,gp.screenHeight-30);
			g2.drawLine(30,30,30,gp.screenHeight-30);
			g2.setFont(dialog_96);
			String text="Pet Party!";
			int x=getCenteredTextX(text);
			int y=gp.tileSize*3;

			//SHADOW
			g2.setColor(Color.black);
			g2.drawString(text,x+5,y+5);
			//MAIN COLOR
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			//CHARACTER IMAGES
			x=gp.screenWidth/2;
			y+=gp.tileSize*2;
			//g2.drawImage(gp.player.left1,x+150,y+100,gp.tileSize*2,gp.tileSize*2,null);
			//g2.drawImage(gp.possum.right1,x-250,y-100,gp.tileSize*2,gp.tileSize*2,null);
			//MENU
			g2.setFont(dialog_48);

			text="NEW GAME";
			x=getCenteredTextX(text);
			y+=gp.tileSize-32;
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			if(commandNum==0) {
				g2.drawImage(gp.possum.right1,x-gp.tileSize,y-gp.tileSize,gp.tileSize,gp.tileSize,null);
			}

			text="LOAD GAME";
			x=getCenteredTextX(text);
			y+=gp.tileSize+16;
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			if(commandNum==1) {
				g2.drawImage(gp.possum.right1,x-gp.tileSize,y-gp.tileSize,gp.tileSize,gp.tileSize,null);
			}
			
			text="SETTINGS";
			x=getCenteredTextX(text);
			y+=gp.tileSize+32-16;
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			if(commandNum==2) {
				g2.drawImage(gp.possum.right1,x-gp.tileSize,y-gp.tileSize,gp.tileSize,gp.tileSize,null);
			}
			
			text="CONTROLS";
			x=getCenteredTextX(text);
			y+=gp.tileSize+32-16;
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			if(commandNum==3) {
				g2.drawImage(gp.possum.right1,x-gp.tileSize,y-gp.tileSize,gp.tileSize,gp.tileSize,null);
			}

			text="QUIT";
			x=getCenteredTextX(text);
			y+=gp.tileSize+32-16;
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			if(commandNum==4) {
				g2.drawImage(gp.possum.right1,x-gp.tileSize,y-gp.tileSize,gp.tileSize,gp.tileSize,null);
			}
		}
		else if(titleScreenState==1) {
			//BACKGROUND & BORDER
			Color borderColor1=new Color(227,176,1);
			Color borderColor2=new Color(120,99,3);
			BasicStroke newStroke1=new BasicStroke(35);
			BasicStroke newStroke2=new BasicStroke(3);
			BasicStroke newStroke3=new BasicStroke(5);
			g2.setColor(new Color(70,70,200));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			g2.setColor(borderColor1);
			g2.setStroke(newStroke1);
			g2.drawLine(0,15,gp.screenWidth,15);
			g2.drawLine(gp.screenWidth-15,0,gp.screenWidth-15,gp.screenHeight);
			g2.drawLine(0,gp.screenHeight-15,gp.screenWidth,gp.screenHeight-15);
			g2.drawLine(15,0,15,gp.screenHeight);
			g2.setColor(borderColor2);
			g2.setStroke(newStroke3);
			g2.drawLine(30,30,gp.screenWidth-30,30);
			g2.drawLine(gp.screenWidth-30,30,gp.screenWidth-30,gp.screenHeight-30);
			g2.drawLine(gp.screenWidth-30,gp.screenHeight-30,30,gp.screenHeight-30);
			g2.drawLine(30,30,30,gp.screenHeight-30);
			g2.setFont(dialog_48);
			//CLASS SELECTION SCREEN
			String text="Select your class!";
			int x=getCenteredTextX(text);
			int y=gp.tileSize*2;
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			
			text="Scratcher";
			x=getCenteredTextX(text);
			y+=gp.tileSize*2;
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			if(commandNum==0) {
				g2.drawImage(gp.possum.right1,x-gp.tileSize,y-gp.tileSize,gp.tileSize,gp.tileSize,null);
			}
			
			text="Thief";
			x=getCenteredTextX(text);
			y+=gp.tileSize*2;
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			if(commandNum==1) {
				g2.drawImage(gp.possum.right1,x-gp.tileSize,y-gp.tileSize,gp.tileSize,gp.tileSize,null);
			}
			text="Thrower";
			x=getCenteredTextX(text);
			y+=gp.tileSize*2;
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			if(commandNum==2) {
				g2.drawImage(gp.possum.right1,x-gp.tileSize,y-gp.tileSize,gp.tileSize,gp.tileSize,null);
			}
			
			text="BACK";
			x=getCenteredTextX(text);
			y+=gp.tileSize*2;
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			if(commandNum==3) {
				g2.drawImage(gp.possum.right1,x-gp.tileSize,y-gp.tileSize,gp.tileSize,gp.tileSize,null);
			}
		}
		else if(titleScreenState==2) {
			//TITLE NAME
			Color borderColor1=new Color(227,176,1);
			Color borderColor2=new Color(120,99,3);
			BasicStroke newStroke1=new BasicStroke(35);
			BasicStroke newStroke2=new BasicStroke(3);
			BasicStroke newStroke3=new BasicStroke(5);
			g2.setColor(new Color(70,70,200));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			g2.setColor(borderColor1);
			g2.setStroke(newStroke1);
			g2.drawLine(0,15,gp.screenWidth,15);
			g2.drawLine(gp.screenWidth-15,0,gp.screenWidth-15,gp.screenHeight);
			g2.drawLine(0,gp.screenHeight-15,gp.screenWidth,gp.screenHeight-15);
			g2.drawLine(15,0,15,gp.screenHeight);
			g2.setColor(borderColor2);
			g2.setStroke(newStroke3);
			g2.drawLine(30,30,gp.screenWidth-30,30);
			g2.drawLine(gp.screenWidth-30,30,gp.screenWidth-30,gp.screenHeight-30);
			g2.drawLine(gp.screenWidth-30,gp.screenHeight-30,30,gp.screenHeight-30);
			g2.drawLine(30,30,30,gp.screenHeight-30);
			g2.setFont(dialog_48);
			String text="CONTROLS";
			int x=getCenteredTextX(text);
			int y=gp.tileSize*2;

			//SHADOW
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			//MAIN COLOR
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			
			text="INTERACT: E";
			x=getCenteredTextX(text);
			y=gp.tileSize*4;

			//SHADOW
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			//MAIN COLOR
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			
			text="MOVEMENT: WASD";
			x=getCenteredTextX(text);
			y=gp.tileSize*5;

			//SHADOW
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			//MAIN COLOR
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			
			text="INVENTORY: Q";
			x=getCenteredTextX(text);
			y=gp.tileSize*6;

			//SHADOW
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			//MAIN COLOR
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			
			text="ATTACK: SPACE";
			x=getCenteredTextX(text);
			y=gp.tileSize*7;

			//SHADOW
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			//MAIN COLOR
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			
			text="PAUSE: ESCAPE";
			x=getCenteredTextX(text);
			y=gp.tileSize*8;

			//SHADOW
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			//MAIN COLOR
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			
			text="BACK";
			x=getCenteredTextX(text);
			y=gp.tileSize*10;
			g2.setColor(Color.black);
			g2.drawString(text,x+3,y+3);
			g2.setColor(new Color(227,176,1));
			g2.drawString(text,x,y);
			g2.drawImage(gp.possum.right1,x-gp.tileSize,y-gp.tileSize,gp.tileSize,gp.tileSize,null);
		}
	}
	public void drawPauseScreen() {
		g2.setFont(arial_80);
		g2.setColor(Color.white);
		String text="PAUSED";
		int x=getCenteredTextX(text);
		int y=gp.screenHeight/2;
		
		g2.drawString(text,x,y);
	}
	public void drawDialogueScreen() {
		//WINDOW
		int x=gp.tileSize*8;
		int y=gp.tileSize*2-40;
		int width=gp.screenWidth-(gp.tileSize*10-40);
		int height=gp.tileSize*3;
		
		drawSubWindow(x,y,width,height);
		g2.setColor(Color.white);
		g2.setFont(arial_17);
		x+=12;
		y+=gp.tileSize/2;
		
		for(String line:currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y+=20;
		}
	}
	public void drawSubWindow(int x,int y,int width,int height) {
		Color c=new Color(227,176,1);
		g2.setStroke(new BasicStroke(5));
		g2.setColor(c);
		g2.drawRoundRect(x-5, y-5, width+10, height+10, 35, 35);
		
		c=new Color(120,99,3,100);
		g2.setColor(c);
		g2.fillRoundRect(x-5, y-5, width+10, height+10, 35, 35);
		
	}
	public int getCenteredTextX(String text) {
		int length=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
		int x=gp.screenWidth/2-length/2;
		return x;
	}
}
