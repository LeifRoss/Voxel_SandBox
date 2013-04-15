package leifdev.sandbox;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;


public class GUITextbox extends GUIElement {

	private float t_mul = 1.0f / 8.0f;
	
	private Vector4f color;
	private boolean mouseHeld, deleteHeld;
	private String command;
	private boolean focus;
	private String text = "";
	
	
	public GUITextbox(float x, float y, float sx, float sy, int type, String command){
		super();
		this.command = command;
		
		//this.held = new boolean[128];
		

		
		color = new Vector4f(1.0f,1.0f,1.0f,1.0f);
		mouseHeld = false;
		deleteHeld = false;
		focus = false;
		this.setX(x);
		this.setY(y);
		this.setScaleX(sx);
		this.setScaleY(sy);
		
		
		
		float[] vertexdata = {
			     -1.0f*sx+x, 1.0f*sy+y, 
			     0.0f, t_mul*type,
	     
			     -1.0f*sx+x,-1.0f*sy+y, 
			     0.0f, t_mul*type+t_mul,
    
			      1.0f*sx+x,-1.0f*sy+y, 
			      1.0f, t_mul*type+t_mul,
	     
			      1.0f*sx+x, 1.0f*sy+y, 
			      1.0f, t_mul*type,
		      
			     -1.0f*sx+x, 1.0f*sy+y, 
			     0.0f, t_mul*type,

			     1.0f*sx+x,-1.0f*sy+y,
			     1.0f, t_mul*type+t_mul
		};
		

		
		this.vertexCount = 6;
		
		
		
		this.vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(this.vao);
		
		  // Sending data to OpenGL requires the usage of (flipped) byte buffers
				FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertexdata.length);
				verticesBuffer.put(vertexdata);
				verticesBuffer.flip();

				this.vbo = GL15.glGenBuffers();
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vbo);
				GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
				
				GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, Stride, 0);		
				GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Stride, 2*4);
				
				
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
				
				
				
		
				
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
				
		
	}
	
	public GUITextbox(float x, float y, float sx, float sy, int type, Vector4f col, String command){
		super();
		
		this.command = command;
		this.color = col;
		mouseHeld = false;
		focus = false;
		
		this.setX(x);
		this.setY(y);
		this.setScaleX(sx);
		this.setScaleY(sy);
		
		
		
		float[] vertexdata = {
			     -1.0f*sx+x, 1.0f*sy+y, 
			     0.0f, t_mul*type,
	     
			     -1.0f*sx+x,-1.0f*sy+y, 
			     0.0f, t_mul*type+t_mul,
    
			      1.0f*sx+x,-1.0f*sy+y, 
			      1.0f, t_mul*type+t_mul,
	     
			      1.0f*sx+x, 1.0f*sy+y, 
			      1.0f, t_mul*type,
		      
			     -1.0f*sx+x, 1.0f*sy+y, 
			     0.0f, t_mul*type,

			     1.0f*sx+x,-1.0f*sy+y,
			     1.0f, t_mul*type+t_mul
		};
		

		
		this.vertexCount = 6;
		
		
		
		this.vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(this.vao);
		
		  // Sending data to OpenGL requires the usage of (flipped) byte buffers
				FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertexdata.length);
				verticesBuffer.put(vertexdata);
				verticesBuffer.flip();

				this.vbo = GL15.glGenBuffers();
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vbo);
				GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
				
				GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, Stride, 0);		
				GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Stride, 2*4);
				
				
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
				
				
				
		
				
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
				
		
	}
	
	
	
	@Override
	public void destroy() {
		
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(this.vbo);
		GL15.glDeleteBuffers(this.uvbo);
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vao);
		
		
	}

	
	
	
	
	public Vector4f getColor(){
		Vector2f mouse = Input.getLocalizedMousePosition();

		if(mouse.x > x-scaleX && mouse.x < x+scaleX && mouse.y > y-scaleY && mouse.y < y+scaleY){
			
			if(!mouseHeld && Mouse.isButtonDown(0)){
				mouseHeld = true;
		
			}else if(!Mouse.isButtonDown(0) && mouseHeld) {
				// parse command
				focus = true;				
			//	System.out.println("focus on");
				mouseHeld = false;
			}
							
			return new Vector4f(1.0f,1.0f,1.0f,1.0f);
		}else if(Mouse.isButtonDown(0)) {
				focus = false;
		//		System.out.println("focus off");
			}
		
		
		
		mouseHeld = false;
		
		return color;
	}
	
	
	@Override
	public void render() {
		
		
	}

	
	
	public void handleInput() {
		
		GUIHandler.renderText(text, x - scaleX/1.25f, y, scaleX/25.0f, scaleY/2.0f, new Vector4f(1.0f,0.0f,0.0f,1.0f));
		
		if(focus){
								
		text += Input.getText();
	      //   System.out.println(command);
			if(Keyboard.isKeyDown(Keyboard.KEY_BACK)){
				if(!deleteHeld) {
					if(text.length()> 0){
					text = text.substring(0, text.length()-1);
					}
				deleteHeld = true;
				}
			}else{
				deleteHeld = false;
			}
			
			
			if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
				focus = false;
				Input.handleCommand(command+" "+text);
				text = "";
			}
		}		
	}

}
