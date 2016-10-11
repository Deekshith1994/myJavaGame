import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class bullet extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static BufferedImage bulletimage;
	public int bulwidth = 15;
	public int bulheight = 15;
	
	public int bulypos;
	public int bulxpos;
	

	
	private int bulspeed = 20;

	public bullet(int startx,int starty) {
	
		
		try {
			URL bulletimageurl  = this.getClass().getResource("Bullet.png");
			bulletimage = ImageIO.read(bulletimageurl);
			}catch(IOException e) {};
			
			
		bulxpos = startx ;
		bulypos = starty ;
	}
	
	public void UpdateBul() {	
		bulxpos += bulspeed ;	
	}		
	
	public boolean isItLeftScreen() {
		if(bulxpos > 0 && bulxpos < Framework.framewidth &&
		   bulypos > 0 && bulypos < Framework.frameheight) {
			return false;
		}
		else return true;
	}
	
	
	public void DrawBul(Graphics2D g2d) {
		
		g2d.drawImage(bulletimage, bulxpos, bulypos,null);
	
	}	
}

