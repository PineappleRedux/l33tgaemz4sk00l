package common.l33tGaem.entities;

import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import common.l33tGaem.init.Gaem;

/**Class to manage the positions of entities and mapping the textures to their position**/
public class EntityManager {
	/**just a test entity... a tentity **/
	private static Entity entity; 
	
	private static boolean test = true;

	public static void manage() {
		if(test) {
			Entity entity = new Entity(3, 3);
		}
	}
	
	public static void render(Graphics g) throws IOException {
		g.drawImage(ImageIO.read(Gaem.class.getResource("res/field.png")), entity.getX(), entity.getY(), null); //set arg0 to the path of a picture
	}
	
}
