import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class MovingBackground {
	
	public BufferedImage backimage;
	
	private int noOfPositions;
	double[] xpositions;
	private long speed;
	private int ypos;

	
	public MovingBackground(BufferedImage image,long spd , int y) {
		
		
		backimage = image;
		noOfPositions = (Framework.framewidth/backimage.getWidth())+2;
		xpositions = new double[noOfPositions];
		speed = spd;
		ypos = y;
		
		for(int i=0;i<xpositions.length;i++){
			
			xpositions[i] = i*backimage.getWidth() ;
		}
	}
	
	
	public void UpdateBackgroung() {
		
		for(int i=0;i<xpositions.length;i++){		
			xpositions[i] -= speed ;
			
			if(xpositions[i]< -backimage.getWidth()) {
				xpositions[i] = -xpositions[i];
			}
		}
	}
	
	public void DrawBackground(Graphics g2d) {
		
		this.UpdateBackgroung();
		
		for(int i=0;i<xpositions.length;i++) {
			g2d.drawImage(backimage,(int)xpositions[i],ypos,null);
		}
	}
}
