import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class LogoAnim{
	

	private int temp;
	private int imageid;
	private ArrayList<BufferedImage> imageList;
	
	
	private int imagewidth;
	private int imageheight;

	
	public LogoAnim() {
	
		
		temp = 0;
		imageid = 0;
		imageList = new ArrayList<BufferedImage>();
				
		for(int i=1;i<21;i++) {
			
			BufferedImage tempImage = null;
			try {
				URL bulletimageurl  = this.getClass().getResource("AnimationLogo/"+i+".png");
				tempImage = ImageIO.read(bulletimageurl);
				}catch(IOException e){};
			
			imageList.add(tempImage);
		}
		imagewidth = imageList.get(0).getWidth();
		imageheight = imageList.get(0).getHeight();
	}
	
	
	
	public void Update() {

			temp++;
			if(temp >= 10) {
				
				imageid++;
				if(imageid >19){
					imageid--;
				}
			}
			
			if(temp>=70){
				Framework.gamestate = Framework.GAME_STATE.MAIN_MENU;
			}
	}
	
	public void drawlogo(Graphics2D g2d) {
		
		
			g2d.setColor(Color.BLACK);
			
			g2d.fillRect(0, 0, Framework.framewidth, Framework.frameheight);
			
			g2d.drawImage(imageList.get(imageid), (Framework.framewidth-imagewidth)/2, (Framework.frameheight-imageheight)/2, (Framework.framewidth+imagewidth)/2, (Framework.frameheight+imageheight)/2, 0,0,imagewidth,imageheight,null);
			
			try {
				Thread.sleep(25);
			}catch(InterruptedException ex){}
			
			
			Update();
			
			
	}
}
