package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
	//DEBUG
	boolean checkDrawTime=false;
	
	public KeyHandler(GamePanel gp) {
		this.gp=gp;
	}
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code=e.getKeyCode();
		
		//TITLE STATE
		if(gp.gameState==gp.titleState) {
			if(gp.ui.titleScreenState==0) {
				if(code==KeyEvent.VK_W) {
					gp.ui.commandNum--;
					if(gp.ui.commandNum<0) {
						gp.ui.commandNum=4;
					}
				}
				if(code==KeyEvent.VK_S) {
					gp.ui.commandNum++;
					if(gp.ui.commandNum>4) {
						gp.ui.commandNum=0;
					}
				}
				if(code==KeyEvent.VK_SPACE) {
					if(gp.ui.commandNum==0) {
						gp.ui.titleScreenState=1;
					}
					if(gp.ui.commandNum==1) {
						//add later
					}
					if(gp.ui.commandNum==2) {
						gp.ui.titleScreenState=3;
					}
					if(gp.ui.commandNum==3) {
						gp.ui.titleScreenState=2;
					}
					if(gp.ui.commandNum==4) {
						System.exit(0);
					}
				}
			}
			else if(gp.ui.titleScreenState==1) {
				if(code==KeyEvent.VK_W) {
					gp.ui.commandNum--;
					if(gp.ui.commandNum<0) {
						gp.ui.commandNum=3;
					}
				}
				if(code==KeyEvent.VK_S) {
					gp.ui.commandNum++;
					if(gp.ui.commandNum>3) {
						gp.ui.commandNum=0;
					}
				}
				if(code==KeyEvent.VK_SPACE) {
					if(gp.ui.commandNum==0) {
						//figher ability
						gp.gameState=gp.playState;
						gp.playMusic(0);
					}
					if(gp.ui.commandNum==1) {
						//thief ability
						gp.player.speed=5;
						gp.gameState=gp.playState;
						gp.playMusic(0);
					}
					if(gp.ui.commandNum==2) {
						//projectile ability
						gp.gameState=gp.playState;
						gp.playMusic(0);
					}
					if(gp.ui.commandNum==3) {
						gp.ui.titleScreenState=0;
					}
				}
			}
			else if(gp.ui.titleScreenState==2) {
				if(code==KeyEvent.VK_SPACE) {
					gp.ui.titleScreenState=0;
				}
			}
			else if(gp.ui.titleScreenState==3) {
				if(code==KeyEvent.VK_SPACE) {
					gp.ui.titleScreenState=0;
				}
			}
		}
		
		//PLAY STATE
		if(gp.gameState==gp.playState) {
			if(code==KeyEvent.VK_W) {
				upPressed=true;
			}
			if(code==KeyEvent.VK_A) {
				leftPressed=true;
			}
			if(code==KeyEvent.VK_S) {
				downPressed=true;
			}
			if(code==KeyEvent.VK_D) {
				rightPressed=true;
			}
			
			//PAUSE
			if(code==KeyEvent.VK_ESCAPE) {
				gp.gameState=gp.pauseState;
				gp.stopMusic();
			}
			if(code==KeyEvent.VK_SPACE) {
				enterPressed=true;
			}
			
			//DEBUG
			if(code==KeyEvent.VK_T) {
				if(checkDrawTime==false) {
					checkDrawTime=true;
				}
				else if(checkDrawTime==true) {
					checkDrawTime=false;
				}
			}

		}
		
		//PAUSE STATE
		else if(gp.gameState==gp.pauseState) {
			if(code==KeyEvent.VK_ESCAPE) {
				gp.gameState=gp.playState;
				gp.playMusic(0);
			}
		}
		
		//DIALOGUE STATE
		else if(gp.gameState==gp.dialogueState) {
			if(code==KeyEvent.VK_SPACE) {
				gp.gameState=gp.playState;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code=e.getKeyCode();
		
		if(code==KeyEvent.VK_W) {
			upPressed=false;
		}
		if(code==KeyEvent.VK_A) {
			leftPressed=false;
		}
		if(code==KeyEvent.VK_S) {
			downPressed=false;
		}
		if(code==KeyEvent.VK_D) {
			rightPressed=false;
		}
	}

}
