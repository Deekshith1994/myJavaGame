
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class BulletPack {
	
	
	public static long timeLastCreated;
	public static long timeBetweenEnemies = (long) (25* Framework.secInNanosec) ;
	public int bpwidth = 15;
	public int bpheight = 15;
	
	public static BufferedImage bpimage;
	public int bpypos;
	public int bpxpos;
	private int bpspeed = 7 + (int)(Math.random());

	
	public BulletPack() {
		
		try {
			URL bpimageurl  = this.getClass().getResource("BulletPack.png");
			bpimage = ImageIO.read(bpimageurl);
			}catch(IOException e) {};
		
			
		bpwidth = bpimage.getWidth();
		bpheight = bpimage.getHeight();
		bpxpos = Framework.framewidth ;
		bpypos = (int) (Math.random() * (Framework.frameheight-bpimage.getWidth())) ;
		
	
	}
	
	public void UpdateBulletpack() {	
		bpxpos -= bpspeed ;
		
	}		
	
	public boolean isItLeftScreen() { 
		if(bpxpos+bpimage.getWidth() > 0 && bpypos+bpimage.getHeight()>0 && bpypos<Framework.framewidth ) {
			return false;
		}
		else return true;
	}
	public void DrawBulletPack(Graphics2D g2d) {
		
		g2d.drawImage(bpimage, bpxpos, bpypos,bpxpos+bpwidth,bpypos+bpheight,0,0,bpimage.getWidth(),bpimage.getHeight(),null);
	}	
}
