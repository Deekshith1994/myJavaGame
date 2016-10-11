import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public abstract class Canvas extends JPanel implements KeyListener,MouseListener {
	
	
	
	private static boolean[] keyboardState = new boolean[525];
    private static boolean[] mouseState = new boolean[3];
    
    
 
    public Canvas()
    
    {
        // We use double buffer to draw on the screen.
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(new Color(135,206,250));

        
        // Adds the keyboard listener to JPanel to receive key events from this component.
        this.addKeyListener(this);
        // Adds the mouse listener to JPanel to receive mouse events from this component.
        this.addMouseListener(this);
    }
    
    public abstract void Draw(Graphics2D g2d);
    
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        super.paintComponent(g2d);
        Draw(g2d);
    }
    
    
    public static boolean mouseButtonState(int button){	
    	return mouseState[button-1];
    }
    
    
    //MOUSE//
    public void mouseKeyStatus(MouseEvent e,boolean status) {
    	if(e.getButton()==MouseEvent.BUTTON1){
    		mouseState[0]=status;
    	}
    	if(e.getButton()==MouseEvent.BUTTON2){
    		mouseState[1]=status;
    	}
    	if(e.getButton()==MouseEvent.BUTTON3){
    		mouseState[2]=status;
    	}
    	
    }
    
    
    public abstract void mouseClickedframework(MouseEvent m);

	@Override
	public void mouseClicked (MouseEvent m) {
		mouseClickedframework(m);
	}

	@Override
	public void mouseEntered(MouseEvent arg0)  {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseKeyStatus(e,true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseKeyStatus(e,false);
	}
	
	//KEYBOARD
	
	
	public static boolean keyBoardKeyState (int key) {		
		return keyboardState[key];
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		keyboardState[e.getKeyCode()] = true ;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyboardState[e.getKeyCode()] = false ;
		keyReleasedFramework(e);	
	}
	
	public abstract void keyReleasedFramework(KeyEvent e);
	@Override
	public void keyTyped(KeyEvent arg0){}	
}
