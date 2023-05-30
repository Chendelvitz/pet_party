package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Catnip extends SuperObject{
	GamePanel gp;
	public Catnip(GamePanel gp) {
		this.gp=gp;
		name="Catnip";
		try {
			image=ImageIO.read(getClass().getResourceAsStream("/objects/catnip.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}