package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Heart extends SuperObject{
	GamePanel gp;
	public Heart(GamePanel gp) {
		this.gp=gp;
		name="Heart";
		try {
			image=ImageIO.read(getClass().getResourceAsStream("/objects/full_heart.png"));
			image1=ImageIO.read(getClass().getResourceAsStream("/objects/half_heart.png"));
			image2=ImageIO.read(getClass().getResourceAsStream("/objects/empty_heart.png"));
			image=uTool.scaleImage(image, gp.tileSize, gp.tileSize);
			image1=uTool.scaleImage(image1, gp.tileSize, gp.tileSize);
			image2=uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
