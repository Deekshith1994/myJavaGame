import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class MenuInterface{
	
	public static BufferedImage MainIdleimage;
	public static BufferedImage MainPlayimage;
	public static BufferedImage MainControlsimage;
	public static BufferedImage MainAboutimage;
	public static BufferedImage Controlsimage;
	
	public int imagewidth;
	public int imageheight;
	
	
	
	
	
	Rectangle playbutton;
	Rectangle controlsbutton;
	Rectangle aboutbutton;
	
	public int butheight = 100;
	
	private int playbutx = 700;
	private int playbuty = 590;
	private int playbutwidth = 580;
	
	private float xscale;
	private float yscale;
	
	private int controlsbutx = 725;
	private int controlsbuty = 725;
	private int controlsbutwidth = 500;
	
	private int aboutbutx = 800;
	private int aboutbuty = 865;
	private int aboutbutwidth = 350;
	
	private boolean showControls = false ;
	
	private BufferedImage presentImage;
	
	private int controlsimgwidth = 200;
	private int controlsimgheight = 200;
	
	
	
	MovingBackground clouds11;
	private BufferedImage cloud11image;
	MovingBackground clouds12;
	private BufferedImage cloud12image;
	
	public MenuInterface(){
		try {
			
			URL MainIdleimageurl = this.getClass().getResource("MainIdle.png");
			MainIdleimage = ImageIO.read(MainIdleimageurl);
			
			URL MainPlayimageurl = this.getClass().getResource("MainPlay.png");
			MainPlayimage = ImageIO.read(MainPlayimageurl);
			
			URL MainControlsimageurl = this.getClass().getResource("MainControls.png");
			MainControlsimage = ImageIO.read(MainControlsimageurl);
			
			URL MainAboutimageurl = this.getClass().getResource("MainAbout.png");
			MainAboutimage = ImageIO.read(MainAboutimageurl);
			
			URL Controlsimageurl = this.getClass().getResource("Controls.png");
			Controlsimage = ImageIO.read(Controlsimageurl);
			
			
			URL cloud11imageurl = this.getClass().getResource("CLOUD1.png");
			cloud11image = ImageIO.read(cloud11imageurl);
			URL cloud12imageurl = this.getClass().getResource("CLOUD2.png");
			cloud12image = ImageIO.read(cloud12imageurl);
			
			
		}catch(IOException ex){};
		
		
		controlsimgwidth = Controlsimage.getWidth();
		controlsimgheight = Controlsimage.getHeight();
		
		
		clouds11 = new MovingBackground(cloud11image,20,0);
		clouds12 = new MovingBackground(cloud12image,40,0);
		
		imagewidth= MainIdleimage.getWidth();
		imageheight= MainIdleimage.getHeight();
		
		xscale = (float)Framework.framewidth / imagewidth;
		yscale = (float)Framework.frameheight / imageheight;

		playbutx *= xscale;
		playbuty *= yscale;
		controlsbutx *= xscale;
		controlsbuty *= yscale;
		aboutbutx *= xscale;
		aboutbuty *= yscale;
		playbutwidth *=xscale;
		controlsbutwidth *=xscale;
		aboutbutwidth *=xscale;
		butheight *= yscale;
			
		playbutton = new Rectangle(playbutx,playbuty,playbutwidth,butheight);
		controlsbutton = new Rectangle(controlsbutx,controlsbuty,controlsbutwidth,butheight);
		aboutbutton = new Rectangle(aboutbutx,aboutbuty,aboutbutwidth,butheight);
		
		}
	
	public void UpdateMenu(Point mouseposition) {
		
		Rectangle mouseRect = new Rectangle(mouseposition.x,mouseposition.y,1,1);
		
		
		if (mouseRect.intersects(playbutton)) {
			presentImage = MainPlayimage;
		}
		else if (mouseRect.intersects(controlsbutton)) {
			presentImage = MainControlsimage;
			showControls = true; 
			
		}
		else if (mouseRect.intersects(aboutbutton)) {
			presentImage = MainAboutimage;
		}
		else {
			showControls = false;
			presentImage = MainIdleimage;
			
		}
		
	}

	public void DrawMenu(Graphics2D g2d) {
		

			
		
			g2d.drawImage(presentImage, 0, 0, Framework.framewidth, Framework.frameheight, 0, 0, imagewidth, imageheight, null);
			clouds11.DrawBackground(g2d);
			clouds12.DrawBackground(g2d);
			
			if(showControls){
				g2d.drawImage(Controlsimage, (Framework.framewidth-controlsimgwidth)/2, (Framework.frameheight-controlsimgheight)/2, (Framework.framewidth+controlsimgwidth)/2, (Framework.frameheight+controlsimgheight)/2, 0, 0, controlsimgwidth, controlsimgheight, null);
				
			}
			
	}

	public void Clicked(Point mouseposition) {
		
		Rectangle mouseRect = new Rectangle(mouseposition.x,mouseposition.y,1,1);
		
		
		if (mouseRect.intersects(playbutton)) {
			
			Framework.gamestate = Framework.GAME_STATE.PLAYING;
		}
		else if (mouseRect.intersects(controlsbutton)) {

		}
		else if (mouseRect.intersects(aboutbutton)) {
		
		}
			
	}	
}
