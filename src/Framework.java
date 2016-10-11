import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


@SuppressWarnings("serial")
public class Framework extends Canvas {
	
	public static int framewidth;
	public static int frameheight;
	public static final long secInNanosec = 1000000000L;
	public static final long millisecInNanosec = 1000000L;
	
	public static final int GAME_FPS = 100;
	public static final long GAME_UPDATE_TIME = secInNanosec / GAME_FPS;
	
	
	public static enum GAME_STATE{PLAYING,LOGO_LOADING,GAMEOVER,MAIN_MENU,PAUSE,RESUME,VISUALISING};
	
	public static GAME_STATE gamestate;
	
	public static long gametime;
	public long lasttime;
	LogoAnim logo;
	public BufferedImage gameOverImage;
	public float GOxscale;
	public float GOyscale;
	
	
	public int GOtimex = 925;
	public int GOtimey = 805;
	public int GOkilledx = 925;
	public int GOkilledy = 880;
	public int GOscorex = 925;
	public int GOscorey = 1020;
	public int GObestx = 925;
	public int GObesty = 1160;
	
	
	Game newgame;
	MenuInterface menu;
	
	public Framework()
	
	
	{
		
		super();
		
		try{
			URL gameoverurl = this.getClass().getResource("GameOver.png");
			gameOverImage = ImageIO.read(gameoverurl);
			
		}catch (IOException e){};
		
		
		gamestate = GAME_STATE.VISUALISING;
		setVisible(true);
		
		
		Thread gameThread = new Thread() {
			
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
        
	}
	
	public void Initialize() {
		GOxscale = (float)Framework.framewidth / gameOverImage.getWidth();
		GOyscale = (float)Framework.frameheight / gameOverImage.getHeight();
		GOtimex *= GOxscale;
		GOtimey *= GOyscale;
		
		GOkilledx *= GOxscale;
		GOkilledy *= GOyscale;
		
		GOscorex *= GOxscale;
		GOscorey *= GOyscale;
		
		GObestx *= GOxscale;
		GObesty *= GOyscale;
		
		logo = new LogoAnim();
		newgame = new Game();
		menu = new MenuInterface();
		newgame.LoadGame();
	}




	public void GameLoop() {
		
		long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
		 
		long timeTaken;
		long timeLeft;
		long beginTime;
		while(true) {
			
			beginTime = System.nanoTime();
			
			switch(gamestate) {
			
				
				
				case PLAYING:
					gametime += System.nanoTime()- lasttime;
					newgame.Update(gametime);
					lasttime = System.nanoTime() ;
				break;
				
				
				case MAIN_MENU:
					newgame.LoadGame();
					lasttime = System.nanoTime() ;
					gametime = 0;
					menu.UpdateMenu(mousePosition());
				break;	
				
				
				case GAMEOVER:
					//...
				break;
				
				case PAUSE:
					//...
				break;
				
				case LOGO_LOADING:
					//...
				break;
				
				case VISUALISING:
					
					
					if (this.getWidth() > 1 && this.getHeight() > 1 && visualizingTime > secInNanosec) {
		                framewidth = this.getWidth();
		                frameheight = this.getHeight();
		                gamestate = GAME_STATE.LOGO_LOADING;
		                Initialize();
					}
					
					else {
						visualizingTime += System.nanoTime()- lastVisualizingTime;
						lastVisualizingTime = System.nanoTime();
						
					}
				break;	
			}
	        repaint();
	        
	        
	        timeTaken = System.nanoTime()-beginTime;
	        timeLeft = GAME_UPDATE_TIME - timeTaken;
	        timeLeft = timeLeft / millisecInNanosec ;
	        if(timeLeft<10) {
	        	timeLeft = 10;
	        }
	        try {	
	        	Thread.sleep(timeLeft);	
           } catch (InterruptedException ex) { }
		}	
	}
	
	
	private Point mousePosition() {
		
		try
        {
            Point mp = this.getMousePosition();
            
            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            return new Point(0, 0);
        }
	}




	@Override
	public void keyReleasedFramework(KeyEvent e) {		
		if(e.getKeyCode()== KeyEvent.VK_SPACE){
			if(gamestate == GAME_STATE.PLAYING){
				newgame.Firenewbullet();	
			}
		}
		
		
		if(e.getKeyCode()== KeyEvent.VK_ESCAPE){
			
			if(gamestate == GAME_STATE.MAIN_MENU){
				System.exit(0);
			}
			else if(gamestate == GAME_STATE.LOGO_LOADING) {
				gamestate = GAME_STATE.MAIN_MENU;
				
			}
			else if(gamestate == GAME_STATE.VISUALISING) {
				
			}
			else if(gamestate == GAME_STATE.GAMEOVER) {
				System.exit(0);
			}
			else{
				gamestate = GAME_STATE.MAIN_MENU;
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(gamestate == GAME_STATE.GAMEOVER) {
				gamestate = GAME_STATE.MAIN_MENU;
			}
		}
		
		
		if(e.getKeyCode()== KeyEvent.VK_P){
			

			if(gamestate == GAME_STATE.PLAYING){
				System.out.println("PAUSED");
				gamestate = GAME_STATE.PAUSE;
				System.out.println(gamestate);				
			}
		}
		
		if(e.getKeyCode()== KeyEvent.VK_R){
			
			if(gamestate == GAME_STATE.PAUSE){
				gamestate = GAME_STATE.PLAYING;	
			}
		}
		
		
	}
	
	@Override
	public void Draw(Graphics2D g2d) {
		
		switch(gamestate) {
			
		case PLAYING:
			newgame.Draw(g2d);
		break;
		
		case MAIN_MENU:
			menu.DrawMenu(g2d);
		break;
		
		case PAUSE:
			newgame.Draw(g2d);
			g2d.drawString("GAME PAUSED PRESS R TO CONTINUE", Framework.framewidth/2 - 250, Framework.frameheight/2);
		break;	
			
		case LOGO_LOADING:
			logo.drawlogo(g2d);
		break;
		
		case GAMEOVER:
			g2d.drawImage(gameOverImage, 0, 0, Framework.framewidth, Framework.frameheight, 0, 0, gameOverImage.getWidth(), gameOverImage.getHeight(), null);
			
			g2d.setFont(new Font("monoSpaced",Font.BOLD,28));
			g2d.setColor(Color.BLUE);
			g2d.drawString(""+newgame.timeString(newgame.time),GOtimex,GOtimey);
			g2d.drawString(""+newgame.killed,GOkilledx,GOkilledy);
			
			g2d.setFont(new Font("monoSpaced",Font.BOLD,50));
			g2d.drawString(""+newgame.score,GOscorex,GOscorey);
			g2d.drawString(""+newgame.highscore,GObestx,GObesty);
		break;
			
		}
			
		
	}

	@Override
	public void mouseClickedframework(MouseEvent m) {
		if(m.getButton()==1){
			menu.Clicked(mousePosition());
		}
	}
}
