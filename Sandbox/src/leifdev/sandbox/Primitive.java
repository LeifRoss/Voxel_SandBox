package leifdev.sandbox;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public abstract class Primitive {

	
	protected int vbo, uvbo, nbo, vertexCount, vao;
	protected boolean loaded;
	
	
	protected final int Stride =  4*3 + 4*2 + 4*3;
	
	public Primitive(){
		loaded = false;
			
	}
	
	
	public abstract void setup();
	
	
	
	public int getVertexCount(){
		return vertexCount;
	}
	public int getVbo(){
		return vbo;
	}
	public int getUvbo(){
		return uvbo;
	}
	public int getNbo(){
		return nbo;
	}
	
	public int getVao(){
		return vao;
	}
	
	
	public boolean isLoaded(){
		return loaded;
	}
	
	
	
	public void destroy(){
		
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vbo);
		GL15.glDeleteBuffers(nbo);
		GL15.glDeleteBuffers(uvbo);
		
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vao);
		
	}
	
	
}
