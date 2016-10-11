import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Game {
	
	//GAME STATS//
	
	public int score = 0;
	private int health = 100;
	public long time; 
	private int runaways = 0;
	public int killed = 0;
	private Font font;
	public int highscore = 0;

	public static int noOfBulletsLeft;
	
	gun Gun = new gun();
	
	MovingBackground clouds1;
	private BufferedImage cloud1image;
	MovingBackground clouds2;
	private BufferedImage cloud2image;
	
	public ArrayList<bullet> BulletList = new ArrayList<bullet>();
	public ArrayList<Enemy> EnemyList = new ArrayList<Enemy>();
	public ArrayList<BulletPack> BulletPackList = new ArrayList<BulletPack>();
	
	int noOfBp = 0;
	int noOfEnemy = 0;
	int MaxnoOfEnemy = 15;
	static int statichealth =0;
	
	public void LoadGame() {
	
		noOfEnemy = 0;
		noOfBulletsLeft=100;
		Framework.gametime = 0;
		Enemy.timeLastCreated0 = 0;
		Enemy.timeLastCreated1 = 0;
		Enemy.timeLastCreated2 = 0;
		
		runaways = 0;
		killed = 0;
		score = 0;
		health = 100;
		statichealth = 0;
		try{
			URL cloud1imageurl = this.getClass().getResource("CLOUD1.png");
			cloud1image = ImageIO.read(cloud1imageurl);
			URL cloud2imageurl = this.getClass().getResource("CLOUD2.png");
			cloud2image = ImageIO.read(cloud2imageurl);
			
			
		}catch(IOException ex){};
		
		clouds1 = new MovingBackground(cloud1image,3,0);
		clouds2 = new MovingBackground(cloud2image,6,100);
		
		font = new Font(Font.MONOSPACED,Font.BOLD,20);
		
		
	}
	
	public void Firenewbullet() {
		
		if( noOfBulletsLeft > 0 ){
			SoundEffect.bulletFired.play();
			BulletList.add(new bullet(Gun.getgunxpos()+gun.nozzleOffsetx,Gun.getgunypos()+gun.nozzleOffsety));	
			noOfBulletsLeft--;
		}
	}
		
	
	
	
	public void Update(long gametime) {
		
		if(score >= highscore){
			highscore = score;
		}
		
		if(statichealth != health) {
			if(health < statichealth){
				statichealth--;		
			}
			if(health > statichealth){
				statichealth++;		
			}
		}
		
		
		if(health <= 0) {
			
			Framework.gamestate = Framework.GAME_STATE.GAMEOVER;
		}
		else {
			time = gametime;
			Gun.UpdateGun();
			UpdateBullets();
			
			if(gametime-Enemy.timeLastCreated0 > Enemy.timeBetweenEnemies0) {
				createEnemy(0);
				Enemy.timeLastCreated0 = gametime ;
			}
			if(gametime-Enemy.timeLastCreated1 > Enemy.timeBetweenEnemies1) {
				createEnemy(1);
				Enemy.timeLastCreated1 = gametime ;
			}
			if(gametime-Enemy.timeLastCreated2 > Enemy.timeBetweenEnemies2) {
				createEnemy(2);
				Enemy.timeLastCreated2 = gametime ;
			}
			
			
			UpdateEnemy();
			
			if(gametime-BulletPack.timeLastCreated > BulletPack.timeBetweenEnemies) {
				createBulletPack();
				BulletPack.timeLastCreated = gametime ;
			}
			
			UpdateBp();
		}
	}
	

	public void Draw(Graphics2D g2d) {

		// DRAW STATISTICS
		g2d.setFont(font);
		g2d.setColor(new Color(0,0,255));
		g2d.drawLine(0, 40, Framework.framewidth, 40);
		if(noOfBulletsLeft < 25) g2d.setColor(new Color(255,0,0));
		g2d.drawString("AMMO :"+noOfBulletsLeft,22,25);
		
		g2d.setColor(new Color(0,0,255));
		
		g2d.drawString("Time :" +timeString(time),200,25);
		g2d.drawString("Killed :"+killed, 400, 25);
		g2d.drawString("Run Away :"+runaways, 600, 25);
		
		g2d.setFont(new Font("monoSpaced",Font.BOLD,30));
		g2d.drawString("SCORE :"+score, Framework.framewidth-300, 30);
		
		
		g2d.setColor(new Color(0,112,0));
		if(health < 25) g2d.setColor(new Color(255,0,0));
		g2d.drawString("HEALTH", 30, Framework.frameheight-60);
		g2d.drawRect(30, Framework.frameheight-50, 200, 40);
		
			
		g2d.fillRect(30, Framework.frameheight-50, 2*statichealth, 40);
		
		
		
		clouds1.DrawBackground(g2d);
		clouds2.DrawBackground(g2d);
		Gun.DrawGun(g2d);
		
		if(BulletList.size()!=0) {
			for(int i=0 ; i < BulletList.size(); i++) {
				BulletList.get(i).DrawBul(g2d);
			}	
		}
		
		if(noOfEnemy!=0) {
			for(int i=0 ; i < EnemyList.size(); i++) {
				EnemyList.get(i).DrawEmy(g2d);
			}	
		}
		
		if(BulletPackList.size()!=0) {
			for(int i=0 ; i < BulletPackList.size(); i++) {
				BulletPackList.get(i).DrawBulletPack(g2d);
			}	
		}
		
		
			
	}
	
	public void createEnemy(int cla) {
			Enemy ey0 = new Enemy((int)(1+(time/(30*Framework.secInNanosec))),cla) ;
			
		if(noOfEnemy < MaxnoOfEnemy ) {	
			noOfEnemy++;
			EnemyList.add(ey0);
		}
	}
	
	public void createBulletPack() {
		BulletPack Bp = new BulletPack() ;
		noOfBp++;
		
		BulletPackList.add(Bp);
	}
	
	public void UpdateEnemy() {
		
		
		for(int i =0 ;i <EnemyList.size();i++) {
			
			Enemy eh = EnemyList.get(i);
			eh.UpdateEmy();
			
			if(eh.isItLeftScreen()) {
				EnemyList.remove(i);
				noOfEnemy--;
				continue;
			}
			
			
			if(eh.isRunAway()) {
				EnemyList.remove(i);
				health -= 5;
				runaways++;
				continue;
			}
			
			if(eh.emyhealth <=0) {
				if(!eh.isShot){
					killed++;
				}
				eh.isShot = true;
			}
			
			
			
			Rectangle emyrect = new Rectangle(eh.emyxpos,eh.emyypos,eh.emywidth,eh.emyheight);
            Rectangle gunrect = new Rectangle(Gun.getgunxpos(),Gun.getgunypos(),Gun.gunwidth-20,Gun.gunheight-20);		
				
			if( emyrect.intersects(gunrect)){
				if(!EnemyList.get(i).isShot){
					health -= 15 ;	
				}
				EnemyList.get(i).isShot = true;
				continue;
			}
		}
	}
	
	public void UpdateBp() {
			
			for(int i =0 ;i <BulletPackList.size();i++) {
				
				BulletPack Bp = BulletPackList.get(i);
				Bp.UpdateBulletpack();
				
				if(Bp.isItLeftScreen()) {
					BulletPackList.remove(i);
					noOfBp--;
					continue;
					
				}	
				
				Rectangle Bprect = new Rectangle(Bp.bpxpos,Bp.bpypos,Bp.bpwidth,Bp.bpheight);
	            Rectangle gunrect = new Rectangle(Gun.getgunxpos(),Gun.getgunypos(),Gun.gunwidth,Gun.gunheight);		
					
				if( Bprect.intersects(gunrect)){
					BulletPackList.remove(i);
					noOfBulletsLeft += 100 ;
					noOfBp--;
					score +=50;
					continue;
				}
			}
		}
	
	
	public String timeString(long time) {
		
		long seconds = time / Framework.secInNanosec % 60;
		long minutes =  time / Framework.secInNanosec /60;
		
		String secs = ""+seconds;
		String mins = ""+minutes;
		
		
		if(seconds  < 10){
			secs = "0"+ secs;
		}
		if(minutes<10){
			mins = "0"+ mins;
		}
		
		return mins + " " + secs ;
		
	}
	
	public void UpdateBullets() {
		for(int i = 0; i < BulletList.size(); i++)
        {
            bullet Bull = BulletList.get(i);
            
            Bull.UpdateBul();
            
            // Remove if it has left the screen //
            
            if(Bull.isItLeftScreen()){
                BulletList.remove(i);
                continue;
            }
            
            for(int j = 0 ;j < EnemyList.size();j++) {
            	
            	Enemy eh = EnemyList.get(j);
            	Rectangle bulrect = new Rectangle(Bull.bulxpos,Bull.bulypos,Bull.bulwidth,Bull.bulheight);
            	Rectangle emyrect = new Rectangle(eh.emyxpos+20,eh.emyypos+20,eh.emywidth-20,eh.emyheight-20);
            	
            	if(bulrect.intersects(emyrect)){
            		BulletList.remove(i);
            		EnemyList.get(j).emyhealth --;
            		score += 5;
            		break;
            	}
            }
        }
	}	
}
