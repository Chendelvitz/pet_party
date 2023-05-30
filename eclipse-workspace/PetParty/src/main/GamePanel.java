package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import entity.Possum;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	// SCREEN SETTINGS
	final int originalTileSize=16; // 16x16 tile
	final int scale=3; // scale factor from pixels to visual scale
	
	public final int tileSize=originalTileSize*scale; // 48x48 tile
	public final int maxScreenCol=16; // number of tiles horizontally
	public final int maxScreenRow=12; // number of tiles vertically
	public final int screenWidth=tileSize*maxScreenCol; // 768 pixels
	public final int screenHeight=tileSize*maxScreenRow; // 576 pixels
	
	// WORLD SETTINGS
	public final int maxWorldCol=30;
	public final int maxWorldRow=25;
	
	// FPS
	int FPS=60;
	
	TileManager tileManager=new TileManager(this);
	public KeyHandler keyHandler=new KeyHandler(this);
	Sound music=new Sound();
	Sound se=new Sound();
	public CollisionDetector collisionDetector=new CollisionDetector(this);
	public AssetSetter assetSetter=new AssetSetter(this);
	public UI ui=new UI(this);
	public EventHandler eventHandler=new EventHandler(this);
	Thread gameThread;
	
	//ENTITY AND OBJECT
	public Player player=new Player(this, keyHandler);
	public Possum possum=new Possum(this);
	public SuperObject obj[]=new SuperObject[10];
	public Entity npc[]=new Entity[10];
	
	//GAME STATE
	
	public int gameState;
	public final int titleState=0;
	public final int playState=1;
	public final int pauseState=2;
	public final int dialogueState=3;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}
	public void setupGame() {
		assetSetter.setObject();
		assetSetter.setNPC();
		//playMusic(0);
		gameState=titleState;
	}
	public void startGameThread() {
		gameThread=new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {
		double drawInterval=1000000000/FPS;
		double delta=0;
		long lastTime=System.nanoTime();
		long currentTime;
		long timer=0;
		int drawCount=0;
		while(gameThread != null) {
			currentTime=System.nanoTime();
			delta+=(currentTime-lastTime)/drawInterval;
			timer+=(currentTime-lastTime);
			lastTime=currentTime;		
			if(delta>=1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			if(timer>=1000000000) {
				System.out.println("FPS: "+drawCount);
				drawCount=0;
				timer=0;
			}
		}
	}
	public void update() {
		if(gameState==playState) {
			//PLAYER
			player.update();
			//NPC
			for(int i=0;i<npc.length;i++) {
				if(npc[i]!=null) {
					npc[i].update();
				}
			}
		}
		if(gameState==pauseState) {
			//nothing
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
		
		//DEBUG
		long drawStart=0;
		if(keyHandler.checkDrawTime==true) {
			drawStart=System.nanoTime();
		}
		
		//TITLE SCREEN
		if(gameState==titleState) {
			ui.draw(g2);
		}
		//OTHERS
		else {
			//TILES
			tileManager.draw(g2);
			
			//OBJECT
			for(int i=0;i<obj.length;i++) {
				if(obj[i]!=null) {
					obj[i].draw(g2, this);
				}
			}
			
			//NPC
			for(int i=0;i<npc.length;i++) {
				if(npc[i]!=null) {
					npc[i].draw(g2);
				}
			}
			Color borderColor1=new Color(227,176,1);
			Color borderColor2=new Color(120,99,3);
			BasicStroke newStroke1=new BasicStroke(35);
			BasicStroke newStroke3=new BasicStroke(5);
			g2.setColor(borderColor1);
			g2.setStroke(newStroke1);
			g2.drawLine(0,15,screenWidth,15);
			g2.drawLine(screenWidth-15,0,screenWidth-15,screenHeight);
			g2.drawLine(0,screenHeight-15,screenWidth,screenHeight-15);
			g2.drawLine(15,0,15,screenHeight);
			g2.setColor(borderColor2);
			g2.setStroke(newStroke3);
			g2.drawLine(30,30,screenWidth-30,30);
			g2.drawLine(screenWidth-30,30,screenWidth-30,screenHeight-30);
			g2.drawLine(screenWidth-30,screenHeight-30,30,screenHeight-30);
			g2.drawLine(30,30,30,screenHeight-30);
			//g2.setStroke(newStroke2);
			//g2.drawString("FPS: "+FPS, 715, screenHeight-10);
			
			//PLAYER
			player.draw(g2);
			
			//UI
			ui.draw(g2);
		}
		
		if(keyHandler.checkDrawTime==true) {
			long drawEnd=System.nanoTime();
			long passed=drawEnd-drawStart;
			g2.setColor(Color.WHITE);
			g2.drawString("Draw Time: "+passed, 10,400);
			System.out.println("Draw Time: "+passed);
		}
		
		//RENDERING ENHANCEMENT
		g2.dispose();
	}
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}
