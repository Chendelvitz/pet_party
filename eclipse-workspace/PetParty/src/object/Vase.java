package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Vase extends SuperObject{
	GamePanel gp;
	public Vase(GamePanel gp) {
		this.gp=gp;
		name="Vase";
		try {
			image=ImageIO.read(getClass().getResourceAsStream("/objects/vase.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e){
			e.printStackTrace();
		}
		collision=true;
	}
}
