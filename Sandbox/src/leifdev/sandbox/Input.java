package leifdev.sandbox;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Input {

	private static Screen screen;
	private static Vector3f shootPos;
	private static Vector3f eye;
	private static int textureSelector = 0;
	
	private static float yaw = 0;
	private static float pitch = 0;
	private static boolean mouseHeld1 = false;
	private static boolean mouseHeld2 = false;
	private static boolean escHeld = false;
	
	private static Physics phys;
	private static boolean isMouseLocked;
	
	
	
   static float dx       	 = 0.0f;
   static float dy       	 = 0.0f;
   static  float lastTime 	 = 0.0f; 
   static  float time     	 = 0.0f;
   private static boolean[] held = new boolean[128];
   
   
public static void setup(Screen s){
		
	
	
	
		//Keyboard.enableBuffering();
		//Keyboard.enableTranslation();	
	
		screen = s;
		isMouseLocked = false;
		eye = new Vector3f(0.0f,0,0);
		shootPos = new Vector3f(0,-40.0f,0);
		
		//Mouse.setGrabbed(true);
		textureSelector = 5;
	}
	
	public static void reset(){
		isMouseLocked = false;
		eye = new Vector3f(0.0f,0,0);
		shootPos = new Vector3f(0,-40.0f,0);
		
		//Mouse.setGrabbed(true);
		textureSelector = 5;
	}
	
	
	public static void handleInput() {
		//-- Input processing
		float delta;
		
		
		
		if(!escHeld && Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			escHeld = true;
			screen.setRunning(!screen.getRunning());	
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			escHeld = false;
		}
		
		
		if(!isMouseLocked) {
		Mouse.setGrabbed(true);
			isMouseLocked = true;
		}
		// handle the mouse input
		
		 time = Sys.getTime();
	        
		 	delta = (time - lastTime)/1000.0f;
	        
	        lastTime = time;
	 
	        dx = Mouse.getDX();
	        dy = Mouse.getDY();
		
		yaw += dx*0.005f;
		pitch -= dy*0.005f;	
			
		
		if(pitch > Math.PI/2) {
			pitch = (float)Math.PI/2;
		}else if(pitch < -Math.PI/2) {
			pitch = (float)-Math.PI/2;
		}
			
			eye.setX((float) (Math.sin(-yaw)*Math.cos(pitch)));
			eye.setZ((float) (Math.cos(yaw)*Math.cos(pitch)));
			eye.setY((float)Math.sin(pitch));		
			
			
			

			
			
			// check for mouseclicks
			if(!mouseHeld2 && Mouse.isButtonDown(1)){
				mouseHeld2 = true;
				
				phys.createCube();
				
			}
			if(!Mouse.isButtonDown(1)){
				mouseHeld2 = false;
			}
			
			if(!mouseHeld1 && Mouse.isButtonDown(0)){
				mouseHeld1 = true;
				
				phys.deleteCube();
				
			}
			if(!Mouse.isButtonDown(0)){
				mouseHeld1 = false;
			}
			
			

	}
	
	/*
	 * 	    eye.setX((float) (Math.sin(-yaw)*Math.cos(pitch)));
			eye.setZ((float) (Math.cos(yaw)*Math.cos(pitch)));
			eye.setY((float)Math.sin(pitch));		
	 * 
	 * 
	 */
	
	public static Vector3f getRight(){
		return new Vector3f((float)Math.sin(-yaw+Math.PI/2),(float)Math.sin(pitch),(float)Math.cos(yaw-Math.PI/2));
	}
	public static Vector3f getForward(){
		return new Vector3f((float)Math.sin(-yaw),(float)Math.sin(pitch),(float)Math.cos(yaw));

	}
	
	
	public static Vector3f getEye(){			
		return eye;
	}
	
	public static synchronized Vector3f getShootPos(){
		return shootPos;
	}
	public static synchronized void setShootPos(Vector3f in){
		shootPos = in;
	}
	
	public static String getText(){
		
		String handle = "";
		boolean shift = false;
		boolean alt = false;
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			shift = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_RMETA)){
			alt = true;
		}
		
		
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)){
				if(!held['q']) {		
						if(shift){
							handle+="Q";
						}else{
							handle+="q";
						}	
				}
					held['q'] = true;
				}else{
					held['q'] = false;
			}	
		if (Keyboard.isKeyDown(Keyboard.KEY_W)){
			if(!held['w']) {		
					if(shift){
						handle+="W";
					}else{
						handle+="w";
					}	
			}
				held['w'] = true;
			}else{
				held['w'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_E)){
			if(!held['e']) {		
					if(shift){
						handle+="E";
					}else{
						handle+="e";
					}	
			}
				held['e'] = true;
			}else{
				held['e'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_R)){
			if(!held['r']) {		
					if(shift){
						handle+="R";
					}else{
						handle+="r";
					}	
			}
				held['r'] = true;
			}else{
				held['r'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_T)){
			if(!held['t']) {		
					if(shift){
						handle+="T";
					}else{
						handle+="t";
					}	
			}
				held['t'] = true;
			}else{
				held['t'] = false;
		}		
		if (Keyboard.isKeyDown(Keyboard.KEY_Y)){
			if(!held['y']) {		
					if(shift){
						handle+="Y";
					}else{
						handle+="y";
					}	
			}
				held['y'] = true;
			}else{
				held['y'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_U)){
			if(!held['u']) {		
					if(shift){
						handle+="U";
					}else{
						handle+="u";
					}	
			}
				held['u'] = true;
			}else{
				held['u'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_I)){
			if(!held['i']) {		
					if(shift){
						handle+="I";
					}else{
						handle+="i";
					}	
			}
				held['i'] = true;
			}else{
				held['i'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_O)){
			if(!held['o']) {		
					if(shift){
						handle+="O";
					}else{
						handle+="o";
					}	
			}
				held['o'] = true;
			}else{
				held['o'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_P)){
			if(!held['p']) {		
					if(shift){
						handle+="P";
					}else{
						handle+="p";
					}	
			}
				held['p'] = true;
			}else{
				held['p'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_CIRCUMFLEX)){
			if(!held['^']) {		// bork
					if(shift){
						handle+="^";
					}else{
						handle+="¨";
					}	
			}
				held['^'] = true;
			}else{
				held['^'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_A)){
			if(!held['a']) {		
					if(shift){
						handle+="A";
					}else{
						handle+="a";
					}	
			}
				held['a'] = true;
			}else{
				held['a'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_S)){
			if(!held['s']) {		
					if(shift){
						handle+="S";
					}else{
						handle+="s";
					}	
			}
				held['s'] = true;
			}else{
				held['s'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_D)){
			if(!held['d']) {		
					if(shift){
						handle+="D";
					}else{
						handle+="d";
					}	
			}
				held['d'] = true;
			}else{
				held['d'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_F)){
			if(!held['f']) {		
					if(shift){
						handle+="F";
					}else{
						handle+="f";
					}	
			}
				held['f'] = true;
			}else{
				held['f'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_G)){
			if(!held['g']) {		
					if(shift){
						handle+="G";
					}else{
						handle+="g";
					}	
			}
				held['g'] = true;
			}else{
				held['g'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_H)){
			if(!held['h']) {		
					if(shift){
						handle+="H";
					}else{
						handle+="h";
					}	
			}
				held['h'] = true;
			}else{
				held['h'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_J)){
			if(!held['j']) {		
					if(shift){
						handle+="J";
					}else{
						handle+="j";
					}	
			}
				held['j'] = true;
			}else{
				held['j'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_K)){
			if(!held['k']) {		
					if(shift){
						handle+="K";
					}else{
						handle+="k";
					}	
			}
				held['k'] = true;
			}else{
				held['k'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_L)){
			if(!held['l']) {		
					if(shift){
						handle+="L";
					}else{
						handle+="l";
					}	
			}
				held['l'] = true;
			}else{
				held['l'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_APOSTROPHE)){
			if(!held['*']) {		// BORK
					if(shift){
						handle+="*";
					}else{
						handle+="'";
					}	
			}
				held['*'] = true;
			}else{
				held['*'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_KANA)){
			if(!held['<']) {		// bork
					if(shift){
						handle+=">";
					}else{
						handle+="<";
					}	
			}
				held['<'] = true;
			}else{
				held['<'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)){
			if(!held['z']) {		
					if(shift){
						handle+="Z";
					}else{
						handle+="z";
					}	
			}
				held['z'] = true;
			}else{
				held['z'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_X)){
			if(!held['x']) {		
					if(shift){
						handle+="X";
					}else{
						handle+="x";
					}	
			}
				held['x'] = true;
			}else{
				held['x'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_C)){
			if(!held['c']) {		
					if(shift){
						handle+="C";
					}else{
						handle+="c";
					}	
			}
				held['c'] = true;
			}else{
				held['c'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_V)){
			if(!held['v']) {		
					if(shift){
						handle+="V";
					}else{
						handle+="v";
					}	
			}
				held['v'] = true;
			}else{
				held['v'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_B)){
			if(!held['b']) {		
					if(shift){
						handle+="B";
					}else{
						handle+="b";
					}	
			}
				held['b'] = true;
			}else{
				held['b'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_N)){
			if(!held['n']) {		
					if(shift){
						handle+="N";
					}else{
						handle+="n";
					}	
			}
				held['n'] = true;
			}else{
				held['n'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_M)){
			if(!held['m']) {		
					if(shift){
						handle+="M";
					}else{
						handle+="m";
					}	
			}
				held['m'] = true;
			}else{
				held['m'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_COMMA)){
			if(!held[',']) {		
					if(shift){
						handle+=";";
					}else{
						handle+=",";
					}	
			}
				held[','] = true;
			}else{
				held[','] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_PERIOD)){
			if(!held['.']) {		
					if(shift){
						handle+=":";
					}else{
						handle+=".";
					}	
			}
				held['.'] = true;
			}else{
				held['.'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_UNDERLINE)){
			if(!held['-']) {		// bork
					if(shift){
						handle+="_";
					}else{
						handle+="-";
					}	
			}
				held['-'] = true;
			}else{
				held['-'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_1)){
			if(!held['1']) {		
					if(shift){
						handle+="!";
					}else{
						handle+="1";
					}	
			}
				held['1'] = true;
			}else{
				held['1'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_2)){
			if(!held['2']) {		
					if(shift && !alt){
						handle+=(char)34;
					}else if(alt){
						handle+="@";
					}else{
						handle+="2";
					}	
			}
				held['2'] = true;
			}else{
				held['2'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_3)){
			if(!held['3']) {		
					if(shift && !alt){
						handle+="#";
					}else if(alt){
						handle+="£";
					}else{				
						handle+="3";
					}	
			}
				held['3'] = true;
			}else{
				held['3'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_4)){
			if(!held['4']) {		
					if(shift && !alt){
						handle+="¤";
					}else if(alt){
						handle+="$";
					}else{
						handle+="4";
					}	
			}
				held['4'] = true;
			}else{
				held['4'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_5)){
			if(!held['5']) {		
					if(shift){
						handle+="%";
					}else{
						handle+="5";
					}	
			}
				held['5'] = true;
			}else{
				held['5'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_6)){
			if(!held['6']) {		
					if(shift){
						handle+="&";
					}else{
						handle+="6";
					}	
			}
				held['6'] = true;
			}else{
				held['6'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_7)){
			if(!held['7']) {		
					if(shift && !alt){
						handle+="/";
					}else if(alt){
						handle+="{";
					}else{
						handle+="7";
					}	
			}
				held['7'] = true;
			}else{
				held['7'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_8)){
			if(!held['8']) {		
					if(shift && !alt){
						handle+="(";
					}else if(alt){
						handle+="[";
					}else{
						handle+="8";
					}	
			}
				held['8'] = true;
			}else{
				held['8'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_9)){
			if(!held['9']) {		
					if(shift && !alt){
						handle+=")";
					}else if(alt){
						handle+="]";
					}else{
						handle+="9";
					}	
			}
				held['9'] = true;
			}else{
				held['9'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_0)){
			if(!held['0']) {		
					if(shift && !alt){
						handle+="=";
					}else if(alt){ 
						handle+="}";
					}else{
						handle+="0";
					}	
			}
				held['0'] = true;
			}else{
				held['0'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_ADD)){
			if(!held['+']) {		// bork
					if(shift){
						handle+="?";
					}else{
						handle+="+";
					}	
			}
				held['+'] = true;
			}else{
				held['+'] = false;
		}	
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			if(!held[' ']) {		// bork
						handle+=" ";			
			}
				held[' '] = true;
			}else{
				held[' '] = false;
		}	
		
		//System.out.println(handle);
		return handle;
	}
	
	
	
	public static int getTextureSelector(){
		return textureSelector;
	}

	public static float getPitch() {
		return pitch;
	}
	
	public static float getYaw() {
		return yaw;
	}




	public static void addPhysics(Physics physics) {
		phys = physics;
		
	}
	
	
	public static void handleMenuInput(){
		
		if(!escHeld && Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			escHeld = true;
			
			if( screen.gameStarted() ){
			screen.setRunning(!screen.getRunning());	
			}
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			escHeld = false;
		}
		
		
		
		if(isMouseLocked){
			Mouse.setGrabbed(false);
			isMouseLocked = false;
		}
		
		
		
		
		
		
		
		
	}
	
	





	public static synchronized void handleCommand(String in){
		
		System.out.println(in);
		
		
		
		try{
			
		String[] parse = in.split(" ");	
		
			
		switch(parse[0].toLowerCase()){
		 case "exit":
			 screen.destroy();
			 break;
		 case "resume":
			 screen.setRunning(true);
			 break;
		 case "menu":
			 GUIHandler.setMenu(parse[1]);
			 break;
		 case "start":
			 screen.newGame();
			 break;
		 case "disconnect":
			 GUIHandler.setMenu("main");
			 screen.destroyGame();
			 
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	// functiont that returns a localized / normalized mouse position -1 to 1
	public static Vector2f getLocalizedMousePosition(){
		return new Vector2f(((2.0f*Mouse.getX())/screen.getScreenResX())-1.0f,((2.0f*Mouse.getY())/screen.getScreenResY())-1.0f);
	}
	
	
}
