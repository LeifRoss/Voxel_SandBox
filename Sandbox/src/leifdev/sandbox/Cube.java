package leifdev.sandbox;

import java.nio.FloatBuffer;


import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;



public class Cube extends Primitive {

	
	public Cube(){
		
		
		setup();
	}
	
	@Override
	public void setup() {
		
		
		try{
			
			 float[] cubeData = {
		     // right
			 1.0f, 1.0f, 1.0f,
			 1.0f, 0.0f, 0.0f,
			 0.66f, 1.0f,
			 
			 1.0f,-1.0f,-1.0f,
			 1.0f, 0.0f, 0.0f,
			 0.33f, 0.5f,
			 
			 1.0f, 1.0f,-1.0f,
			 1.0f, 0.0f, 0.0f,
			 0.33f, 1.0f,
		     
		     
		     1.0f,-1.0f,-1.0f,
		     1.0f, 0.0f, 0.0f,
		     0.33f, 0.5f,
		     
		     1.0f, 1.0f, 1.0f,
		     1.0f, 0.0f, 0.0f,
		     0.66f, 1.0f,
		     
		     1.0f,-1.0f, 1.0f,
		     1.0f, 0.0f, 0.0f,
		     0.66f, 0.5f, 
		     		
		     		     
		     // left
		     -1.0f,-1.0f,-1.0f,
			  -1.0f, 0.0f, 0.0f,
			  0.0f,  0.0f,
			  
		     -1.0f,-1.0f, 1.0f,
			  -1.0f, 0.0f, 0.0f,
			  0.33f, 0.0f,
			  
		     -1.0f, 1.0f, 1.0f,
			  -1.0f, 0.0f, 0.0f,
			  0.33f, 0.5f,
		     
		     -1.0f,-1.0f,-1.0f,
			  -1.0f, 0.0f, 0.0f,
			  0.0f, 0.0f,
			  
		     -1.0f, 1.0f, 1.0f,
			  -1.0f, 0.0f, 0.0f,
			  0.33f,0.5f,
			  
		     -1.0f, 1.0f,-1.0f,
			  -1.0f, 0.0f, 0.0f,
			  0.0f, 0.5f,
			  
			  
             // rear
		     1.0f, 1.0f,-1.0f,
		     0.0f, 0.0f, -1.0f,
		     0.33f, 0.5f,
		     
		     -1.0f,-1.0f,-1.0f,
		     0.0f, 0.0f, -1.0f,
		     0.66f, 0.0f,
		     
		     -1.0f, 1.0f,-1.0f,
		     0.0f, 0.0f, -1.0f,
		     0.66f, 0.5f,
		     
		     
		     1.0f, 1.0f,-1.0f,
		     0.0f, 0.0f, -1.0f,
		     0.33f, 0.5f,
		     
		     1.0f,-1.0f,-1.0f,
		     0.0f, 0.0f, -1.0f,
		     0.33f, 0.0f,
		     
		    -1.0f,-1.0f,-1.0f,  
		     0.0f, 0.0f, -1.0f,
		     0.66f, 0.0f,
				
		 
		     
		     //front
		     -1.0f, 1.0f, 1.0f,
		     0.0f, 0.0f, 1.0f,
		     0.33f, 1.0f,
		     
		     -1.0f,-1.0f, 1.0f,
		     0.0f, 0.0f, 1.0f,
		     0.33f, 0.5f,
		     
		      1.0f,-1.0f, 1.0f,
		      0.0f, 0.0f, 1.0f,
		      0.0f, 0.5f,
		      
		     
		      1.0f, 1.0f, 1.0f,
		      0.0f, 0.0f, 1.0f,
		      0.0f, 1.0f,
		      
		     -1.0f, 1.0f, 1.0f,
		     0.0f, 0.0f, 1.0f,
		     0.33f, 1.0f,
		     
		      1.0f,-1.0f, 1.0f,
		      0.0f, 0.0f, 1.0f,
		      0.0f, 0.5f,
		      
		      
			 //bottom
		      1.0f,-1.0f, 1.0f,
		      0.0f, -1.0f, 0.0f,
		      1.0f, 0.5f,
		      
		     -1.0f,-1.0f,-1.0f,
		     0.0f, -1.0f, 0.0f,
		     0.666f, 0.0f,
		     
		      1.0f,-1.0f,-1.0f,
		      0.0f, -1.0f, 0.0f,
		      1.0f, 0.0f,
		      
		     
		      1.0f,-1.0f, 1.0f,
		      0.0f, -1.0f, 0.0f,
		      1.0f, 0.5f,
		      
		     -1.0f,-1.0f, 1.0f,
		     0.0f, -1.0f, 0.0f,
		     0.666f, 0.5f,
		     
		     -1.0f,-1.0f,-1.0f,	
		     0.0f, -1.0f, 0.0f,
		     0.666f, 0.0f,
		     

		     
			 //top
		      1.0f, 1.0f, 1.0f,
		      0.0f, 1.0f, 0.0f,
		      0.6666f, 0.5f,
		      
		      1.0f, 1.0f,-1.0f,
		      0.0f, 1.0f, 0.0f,
		      0.666f, 1.0f,
		      
		     -1.0f, 1.0f,-1.0f,
		     0.0f, 1.0f, 0.0f,
		     1.0f, 1.0f,
		     
		     
		      1.0f, 1.0f, 1.0f,
		      0.0f, 1.0f, 0.0f,
		      0.6666f, 0.5f,
		      
		     -1.0f, 1.0f,-1.0f,
		     0.0f, 1.0f, 0.0f,
		     1.00f, 1.0f,
		     
		     -1.0f, 1.0f, 1.0f,	
		     0.0f, 1.0f, 0.0f,
		     1.00f, 0.5f

		};
		     
		     
			

		     
		  // Sending data to OpenGL requires the usage of (flipped) byte buffers
				FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(cubeData.length);
				verticesBuffer.put(cubeData);
				verticesBuffer.flip();

				
				vertexCount = 36;
				

				vao = GL30.glGenVertexArrays();
				GL30.glBindVertexArray(vao);
				

				vbo = GL15.glGenBuffers();
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
				GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);

				
				GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, Stride, 0);		
				GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, Stride, 3*4);
				GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Stride, 3*4 + 3*4);
				
				
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
				
				
				// Deselect (bind to 0) the VAO
				GL30.glBindVertexArray(0);
				
				//this.exitOnGLError("Error in setupQuad");  
			
			
			
		}catch(Exception e){
			
		}
		
	
		
		loaded = true;
	}

	
	
	
	
	
	
	
}

