package leifdev.sandbox;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;


public class GUIMenu extends GUIElement {

	
	private String name;
	private int texture = 0;
	private ArrayList<GUIText> textobjects;
	private ArrayList<GUIIcon> iconobjects;
	private ArrayList<GUIButton> buttonobjects;
	private ArrayList<GUITextbox> textboxobjects;
	
	public GUIMenu(float x, float y, float sx, float sy, String name, String texture){
		super();
		
		this.texture = Utility.loadPNGTexture(texture, GL13.GL_TEXTURE0);
		this.name = name;
		
		textobjects = new ArrayList<GUIText>();
		iconobjects = new ArrayList<GUIIcon>();
		buttonobjects = new ArrayList<GUIButton>();
		textboxobjects = new ArrayList<GUITextbox>();
		
		
		this.setX(x);
		this.setY(y);
		this.setScaleX(sx);
		this.setScaleY(sy);
		
		
		
		float[] vertexdata = {
			     -1.0f*sx+x, 1.0f*sy+y, 
			     0.0f, 0.0f,
	     
			     -1.0f*sx+x,-1.0f*sy+y, 
			     0.0f, 1.0f,
    
			      1.0f*sx+x,-1.0f*sy+y, 
			      1.0f, 1.0f,
	     
			      1.0f*sx+x, 1.0f*sy+y, 
			      1.0f, 0.0f,
		      
			     -1.0f*sx+x, 1.0f*sy+y, 
			     0.0f, 0.0f,

			     1.0f*sx+x,-1.0f*sy+y,
			     1.0f, 1.0f
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
	
	
	public String getName(){
		return name;
	}
	
	public int getTexture(){
		return texture;
	}
	
	
	
	public void addText(String text, float x, float y, float sx, float sy){
		
		textobjects.add(new GUIText(text,x,y,sx,sy));
		
	}
	
	public void addIcon(float x, float y, float sx, float sy, int type){
		iconobjects.add(new GUIIcon(x,y,sx,sy,type));	
	}
	public void addIcon(float x, float y, float sx, float sy, int type,Vector4f col){
		iconobjects.add(new GUIIcon(x,y,sx,sy,type,col));	
	}
	
	public void addButton(float x, float y, float sx, float sy, int type,String command){
		buttonobjects.add(new GUIButton(x,y,sx,sy,type,command));	
	}
	public void addButton(float x, float y, float sx, float sy, int type,Vector4f col, String command){
		buttonobjects.add(new GUIButton(x,y,sx,sy,type,col,command));	
	}
	
	public void addTextbox(float x, float y, float sx, float sy, int type,String command){
		textboxobjects.add(new GUITextbox(x,y,sx,sy,type,command));	
	}
	public void addTextbox(float x, float y, float sx, float sy, int type,Vector4f col, String command){
		textboxobjects.add(new GUITextbox(x,y,sx,sy,type,col,command));	
	}
	
	
	@Override
	public void destroy() {
		
		
		for(GUIText e : textobjects){
			e.destroy();
		}
		textobjects.clear();
		textobjects = null;
		
		
		for(GUIIcon e : iconobjects){
			e.destroy();
		}
		iconobjects.clear();
		iconobjects = null;
		
		
		for(GUIButton e : buttonobjects){
			e.destroy();
		}
		buttonobjects.clear();
		buttonobjects = null;
		
		for(GUITextbox e : textboxobjects){
			e.destroy();
		}
		textboxobjects.clear();
		textboxobjects = null;
		
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(this.vbo);
		GL15.glDeleteBuffers(this.uvbo);
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vao);
		GL11.glDeleteTextures(texture);
		
		
		
	}

	
	public void renderObjects(){
		
		
		

		GL20.glUniform4f(GUIHandler.getColorLocation(), 1.0f, 1.0f, 1.0f,1.0f);

		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, GUIHandler.getGuiTexture());

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		  
		
		for(GUIIcon e : iconobjects){
			Vector4f c = e.getColor();
			
			GL20.glUniform4f(GUIHandler.getColorLocation(), c.x, c.y, c.z,c.w);
			
			GL30.glBindVertexArray(e.getVao());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
		
			
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, e.getVertexCount());	
		
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
		

		
		
		
		//System.out.println(mouse.x+" "+mouse.y);
		
		for(GUIButton e : buttonobjects){
			
			e.handleMouse();
			
			Vector4f c = e.getColor();
			
			GL20.glUniform4f(GUIHandler.getColorLocation(), c.x, c.y, c.z,c.w);
			
			GL30.glBindVertexArray(e.getVao());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
		
			
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, e.getVertexCount());	
		
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
		
		
		for(GUITextbox e : textboxobjects){

			
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, GUIHandler.getGuiTexture());
			
			
			
			Vector4f c = e.getColor();
			
			GL20.glUniform4f(GUIHandler.getColorLocation(), c.x, c.y, c.z,c.w);
			
			GL30.glBindVertexArray(e.getVao());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
		
			
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, e.getVertexCount());	
		
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
			
			e.handleInput();
			
		}
		
		
		
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, GUIHandler.getFontTexture());	
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		for(GUIText e : textobjects){
			Vector4f c = e.getColor();
			GL20.glUniform4f(GUIHandler.getColorLocation(), c.x, c.y, c.z,c.w);
			
			GL30.glBindVertexArray(e.getVao());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
		
			
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, e.getVertexCount());	
		
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
		
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		
		
	}
	
	
	@Override
	public void render() {
		
		
	}

}
