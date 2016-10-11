import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class gun{
	
	
	//GUN SPECIFICATIONS//
	
	private final  int xImageIdle = 980;
	private final  int xImageFiring = 1480;
	private final  int scaleratio =7;
	private final  int aspectratio = 3;
	
	public  static int nozzleOffsetx;
	public  static int nozzleOffsety;
	public  static BufferedImage gunimage;
	public  static boolean isFiring = false ;
	
	public  int gunwidth = xImageIdle;	
	public  int gunheight = gunwidth/aspectratio;
	private int gunxpos = Framework.framewidth/1000;
	private int gunypos = Framework.frameheight/4;
	private int gunspeed = 10 ;
	
	
	
	
	
	//GETTER FUNCTIONS//
	
	public int getgunxpos(){
		return gunxpos;
	}
	
	public int getgunypos() {
		return gunypos;
	}
	
	public gun() {
		
		try{
		URL gunimageurl = this.getClass().getResource("GUN.png");
		gunimage = ImageIO.read(gunimageurl);
		}catch(IOException ex){}
		
		
		gunwidth= xImageIdle/scaleratio ;	
		gunheight = gunwidth / aspectratio;
		nozzleOffsetx = gunwidth;
		nozzleOffsety = gunheight /7;
		
	}
	
	//GUN MOTION CONTROL//
		
	public void UpdateGun() {
		
		
		if(Canvas.keyBoardKeyState(KeyEvent.VK_SPACE)){
			isFiring = true;
		}
		
		else{
			isFiring = false;
		}
		
		// MOVE LEFT //
		
		if(Canvas.keyBoardKeyState(KeyEvent.VK_LEFT)&& gunxpos > 0){
			gunxpos -= gunspeed;
			gunypos -= 0;
		}
		
		// MOVE RIGHT //
		
		if(Canvas.keyBoardKeyState(KeyEvent.VK_RIGHT)&& gunxpos < Framework.framewidth - gunwidth){
			gunxpos += gunspeed;
			gunypos -= 0;
		}
		
		// MOVE UP //
		
		if(Canvas.keyBoardKeyState(KeyEvent.VK_UP) && gunypos > 0 ) {

			gunxpos -= 0;
			gunypos -= gunspeed;	
		}
		
		
		// MOVE DOWN //
		
		if(Canvas.keyBoardKeyState(KeyEvent.VK_DOWN) && gunypos < Framework.frameheight - gunheight) {
				
			gunxpos += 0;
			gunypos += gunspeed;
		}
	}
	
	//GUN IMAGE ON FRAME//
	
	public void DrawGun(Graphics2D g2d) {
		
		
		if(isFiring && Game.noOfBulletsLeft > 0) {
			g2d.drawImage(gunimage, gunxpos,gunypos, gunxpos+(xImageFiring/scaleratio)-5, gunypos + gunheight, 0, 0, xImageFiring, gunimage.getHeight(), null);
		}
		
		else {
			g2d.drawImage(gunimage, gunxpos, gunypos, gunxpos+(xImageIdle/scaleratio), gunypos + gunheight, 0, 0, xImageIdle, gunimage.getHeight(), null);
		}		
	}	
}
