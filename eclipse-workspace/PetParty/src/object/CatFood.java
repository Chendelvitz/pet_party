package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class CatFood extends SuperObject{
	GamePanel gp;
	public CatFood(GamePanel gp) {
		this.gp=gp;
		name="CatFood";
		try {
			image=ImageIO.read(getClass().getResourceAsStream("/objects/cat_food.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
