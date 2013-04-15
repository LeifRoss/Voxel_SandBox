package leifdev.sandbox;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector4f;


public class GUIText extends GUIElement {

	
	private Vector4f color;
	
	
	
	public GUIText(String t,float x, float y, float sx, float sy){
		super();
		
		color = new Vector4f(1.0f,1.0f,1.0f,1.0f);
		
		
		this.setX(x);
		this.setY(y);
		this.setScaleX(sx);
		this.setScaleY(sy);
		
		
		char[] text = t.toCharArray();
		
		ArrayList<Float> data = new ArrayList<Float>();
	
		float offset = 0.0f;
		float height_mul = 1.0f / 16.0f;
		float width_mul = 1.0f / 16.0f;
		float i = 0.0f;
		float height = 0.0f;
		

		
		
		for(char c : text){
			
			
			
		float set = c - 32;
			
		 height = (float)Math.floor(set / 16.0f) + 2.0f;
			
		 offset = (float)Math.floor(set - 16.0f*height);
			
				
		 
				
				data.add(-1.0f*sx+x + i); data.add(1.0f*sy+y);
				data.add(width_mul*offset); data.add(height_mul*(height-1.0f));
				
				data.add(-1.0f*sx+x + i); data.add(-1.0f*sy+y);
				data.add(width_mul*offset); data.add(height_mul*height);
				
				data.add(1.0f*sx+x + i); data.add(-1.0f*sy+y);
				data.add((width_mul*offset)+width_mul); data.add(height_mul*height);
		
				
				data.add(1.0f*sx+x + i); data.add(1.0f*sy+y);
				data.add((width_mul*offset)+width_mul); data.add(height_mul*(height-1.0f));
								
				data.add(-1.0f*sx+x + i); data.add(1.0f*sy+y);
				data.add(width_mul*offset); data.add(height_mul*(height-1.0f));
								
				data.add(1.0f*sx+x + i); data.add(-1.0f*sy+y);
				data.add((width_mul*offset)+width_mul); data.add(height_mul*height);
				
				i += width_mul*sx*38.0f;
			
			}
			
		
		float[] verticedata = new float[data.size()];

		 vertexCount = data.size() / 4;
		
		
		for (int ts = 0; ts < data.size(); ts++) {
		    Float f = data.get(ts);
		    verticedata[ts] = (f != null ? f : Float.NaN); // Or whatever default you want.
		}
		
		
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(verticedata.length);
		verticesBuffer.put(verticedata);
		verticesBuffer.flip();

		
		 vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		

		 vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);

		
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 16, 0);		
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 16, 8);
				
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		
		
		
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		
		Utility.exitOnGLError("GUITEXT_constructor");
		
		
				
		
	}
	
	
	public GUIText(String t,float x, float y, float sx, float sy,Vector4f col){
		super();
		
		this.color = col;
		
		
		this.setX(x);
		this.setY(y);
		this.setScaleX(sx);
		this.setScaleY(sy);
		
		
		char[] text = t.toCharArray();
		
		ArrayList<Float> data = new ArrayList<Float>();
	
		float offset = 0.0f;
		float height_mul = 0.25f;
		float width_mul = 1.0f / 27.0f;
		float i = 0.0f;
		float height = 2.0f;
		boolean render = false;

		
		
		for(char c : text){
			
			
				
					render = true;
				if(c > 96 && c < 123) {
					
					height = 3.0f;
					offset = ((float)c - 97.0f);
				}else if(c > 47 && c < 58){
					height = 1.0f;
					offset = ((float)c - 48.0f);
				}else if(c > 64 && c < 91 ){
					height = 2.0f;
					offset = ((float)c - 65.0f);
				}else if(c == 32) {
					i += width_mul*sx*38.0f;
					render = false;
				}
				
				if(render) {
				
				data.add(-1.0f*sx+x + i); data.add(1.0f*sy+y);
				data.add(width_mul*offset); data.add(height_mul*(height-1.0f));
				
				data.add(-1.0f*sx+x + i); data.add(-1.0f*sy+y);
				data.add(width_mul*offset); data.add(height_mul*height);
				
				data.add(1.0f*sx+x + i); data.add(-1.0f*sy+y);
				data.add((width_mul*offset)+width_mul); data.add(height_mul*height);
		
				
				data.add(1.0f*sx+x + i); data.add(1.0f*sy+y);
				data.add((width_mul*offset)+width_mul); data.add(height_mul*(height-1.0f));
								
				data.add(-1.0f*sx+x + i); data.add(1.0f*sy+y);
				data.add(width_mul*offset); data.add(height_mul*(height-1.0f));
								
				data.add(1.0f*sx+x + i); data.add(-1.0f*sy+y);
				data.add((width_mul*offset)+width_mul); data.add(height_mul*height);
				
				i += width_mul*sx*38.0f;
				render = false;
				}
			}
	
		
		
		
		float[] verticedata = new float[data.size()];

		 vertexCount = data.size() / 4;
		
		
		for (int ts = 0; ts < data.size(); ts++) {
		    Float f = data.get(ts);
		    verticedata[ts] = (f != null ? f : Float.NaN); // Or whatever default you want.
		}
		
		
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(verticedata.length);
		verticesBuffer.put(verticedata);
		verticesBuffer.flip();

		
		 vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		

		 vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);

		
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 16, 0);		
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 16, 8);
				
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		
		
		
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		
		Utility.exitOnGLError("GUITEXT_constructor");
		
		
				
		
	}
	
	public Vector4f getColor(){
		return color;
	}
	
	@Override
	public void destroy() {
		
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(this.vbo);
		GL15.glDeleteBuffers(this.uvbo);
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vao);
		
		
	}

	@Override
	public void render() {
		
		
	}

}
