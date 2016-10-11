import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Enemy {
	
	private int finalwingspeed;
	private int wingspeed0 = 5;
	private int wingspeed1 = 3;
	private int wingspeed2 = 3;
	
	public static long timeLastCreated0;
	public static long timeLastCreated1;
	public static long timeLastCreated2;
	
	public static long timeBetweenEnemies0 = (long) ((1 * Framework.secInNanosec));
	public static long timeBetweenEnemies1 = (long) ((20 * Framework.secInNanosec));
	public static long timeBetweenEnemies2 = (long) ((30 * Framework.secInNanosec));
	
	public int emywidth;
	public int emyheight;
	public boolean isShot;
	public int emyhealth;
	private int emyclass;
	
	public boolean dir;
	
	
	public ArrayList<BufferedImage> enemyimageList = new ArrayList<BufferedImage>();
	public BufferedImage enemyShotimage;
	public BufferedImage enemyimage;
	private int imgid = 0;
	
	public int emyypos;
	public int emyxpos;
	private int emyspeed;
	
	public Enemy(int speed,int clas) {	
		
		
		dir = false;
		emyclass = clas;
		isShot = false;
		emyspeed = speed;		
		for(int i =1;i<6;i++){			
			switch(emyclass) {
			case 0:		
				try {
					URL enemyimageurl  = this.getClass().getResource("Crow/Crow"+i+".png");
					enemyimage = ImageIO.read(enemyimageurl);		
					}catch(IOException e) {};
			break;	
			case 1:
				try {
					URL enemyimageurl  = this.getClass().getResource("Vultcher/Vultcher"+i+".png");
					enemyimage = ImageIO.read(enemyimageurl);	
					}catch(IOException e) {};
			break;			
				
			case 2:	
				try {
					URL enemyimageurl  = this.getClass().getResource("Eagle/Eagle"+i+".png");
					enemyimage = ImageIO.read(enemyimageurl);
					}catch(IOException e) {};
			break;	
			}
			enemyimageList.add(enemyimage);
		}		
		switch(emyclass) {		
		case 0:
			finalwingspeed = wingspeed0;
			emyhealth = 1;
			try {
				URL enemyShotimageurl  = this.getClass().getResource("Crow/CrowShot.png");
				enemyShotimage = ImageIO.read(enemyShotimageurl);
				}catch(IOException e) {};
	break;		
	case 1:
		finalwingspeed = wingspeed1;
		emyhealth = 2;
		try {
			URL enemyShotimageurl  = this.getClass().getResource("Vultcher/VultcherShot.png");
			enemyShotimage = ImageIO.read(enemyShotimageurl);	
			}catch(IOException e) {};
	break;		
	case 2:
		finalwingspeed = wingspeed2;
		emyhealth = 3;
		try {
			URL enemyShotimageurl  = this.getClass().getResource("Eagle/EagleShot.png");
			enemyShotimage = ImageIO.read(enemyShotimageurl);
			
			}catch(IOException e) {};
	break;			
		}			
		emywidth = enemyimage.getWidth();
		emyheight = enemyimage.getHeight();
		emyxpos = Framework.framewidth ;
		emyypos = (int) (Math.random() * (Framework.frameheight-enemyimage.getWidth())) ;	
		
		if(!isShot){
			SoundEffect.birdFlying.play();
		}
	}	
	public void UpdateEmy() {	
		if(!isShot) {
			
			emyxpos -= emyspeed ;
			
			int rand = (int)(Math.random()*3);
			switch(rand) {
			
				case 0:
					
					if(emyypos - emyspeed*2 > 0 ){
						emyypos -= emyspeed*(Math.random()*2);
					}
				break;
				
				case 1:
					
					if(emyypos + emyspeed*2 < Framework.frameheight){
						emyypos += emyspeed*(Math.random()*2);
					}
					
				break;
				
				case 2:
				case 3:	
					emyypos -= 0;
				break;
				
			}
			
		}
		
		else if(isShot = true){
			emyxpos -= 3*emyspeed ;
			if(emyspeed > 100){
				emyypos += 5 * 100 + 10* (emyypos/1000) ;
			}
			else {
			emyypos += 5 *(emyspeed) + 10* (emyypos/1000) ;
			}
			enemyimage = enemyShotimage;
		}
		
		
		if(!dir){
			imgid++;
			if (imgid >= 5*finalwingspeed) {
				dir = !dir;
			}
		}
		
		if(dir){
			imgid--;
			if (imgid <= 0) {
				dir = !dir;
			}
		}		
	}			
	public boolean isItLeftScreen() {
		if(emyypos < Framework.frameheight && emyypos > - enemyimage.getHeight()) {
			return false;
		}
		else return true;
	}	
	public boolean isRunAway() {
		if(emyxpos < -emywidth) {
			return true;			
		}
		else return false;
	}	
	public void DrawEmy(Graphics2D g2d) {
		
		if(!isShot){
			g2d.drawImage(enemyimageList.get(imgid/finalwingspeed), emyxpos, emyypos, emyxpos+emywidth, emyypos+emyheight,0,0,enemyimage.getWidth(),enemyimage.getHeight(),null);
			
		}else {
			g2d.drawImage(enemyShotimage, emyxpos, emyypos, emyxpos+emywidth, emyypos+emyheight,0,enemyimage.getHeight(),enemyimage.getWidth(),0,null);
			
		}
		
	}	
}


