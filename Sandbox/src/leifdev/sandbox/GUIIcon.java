package leifdev.sandbox;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;


public class GUIIcon extends GUIElement {

	private float t_mul = 1.0f / 8.0f;
	
	private Vector4f color;
	
	
	
	
	public GUIIcon(float x, float y, float sx, float sy, int type){
		super();
		
		color = new Vector4f(1.0f,1.0f,1.0f,1.0f);
		
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
	
	public GUIIcon(float x, float y, float sx, float sy, int type, Vector4f col){
		super();
		
		this.color = col;
		
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
		return color;
	}
	
	
	@Override
	public void render() {
		
		
	}

}
