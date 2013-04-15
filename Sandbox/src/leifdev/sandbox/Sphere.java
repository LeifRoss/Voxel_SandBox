package leifdev.sandbox;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;



public class Sphere extends Primitive {

	
	public Sphere(){
		
		
		setup();
	}
	
	@Override
	public void setup() {
		
		int Resolution = 12;
		float Radius = -10250.0f;
		
		
		
		try{
			
			double PI = 3.14159;
			ArrayList<Float> v = new ArrayList<Float>(0);

			
			float X1,Y1,X2,Y2,Z1,Z2;
			float inc1,inc2,inc3,inc4,Radius1,Radius2;
			for(int i = 0; i < Resolution; i++) {
		         for(int h = (int)(-Resolution/2); h < (int)(Resolution/2); h++){

		inc1 = ((i/(float)Resolution)*2*(float)PI);
		inc2 = (((i+1)/(float)Resolution)*2*(float)PI);
		
		inc3 = ((h/(float)Resolution)*(float)PI);
		inc4 = (((h+1)/(float)Resolution)*(float)PI);

		 X1 = (float)Math.sin(inc1);
		 Y1 = (float)Math.cos(inc1);
		 X2 = (float)Math.sin(inc2);
		 Y2 = (float)Math.cos(inc2);

		 Radius1 = (float)(Radius*Math.cos(inc3));
		 Radius2 = (float)(Radius*Math.cos(inc4));


			Z1 = (float)(Radius*Math.sin(inc3));	
			Z2 = (float)(Radius*Math.sin(inc4));

			
			v.add((Radius1*X1)); v.add(Z1); v.add(Radius1*Y1);
			v.add(0.666f); v.add(0.5f);
			v.add((float)X1); v.add((float)Z1); v.add((float)Y1);
			
			v.add((Radius1*X2)); v.add(Z1); v.add(Radius1*Y2);
			v.add(0.666f); v.add(1.0f);
			v.add((float)X2); v.add((float)Z1); v.add((float)Y2);
		
			v.add((Radius2*X2)); v.add(Z2); v.add(Radius2*Y2);
			v.add(1.0f); v.add(1.0f);
			v.add((float)X2); v.add((float)Z2); v.add((float)Y2);
			
			v.add((Radius1*X1)); v.add(Z1); v.add(Radius1*Y1);
			v.add(0.666f); v.add(0.5f);
			v.add((float)X1); v.add((float)Z1); v.add((float)Y1);
	
			v.add((Radius2*X2)); v.add(Z2); v.add(Radius2*Y2);
			v.add(1.0f); v.add(1.0f);
			v.add((float)X2); v.add((float)Z2); v.add((float)Y2);
						
			v.add((Radius2*X1)); v.add(Z2); v.add(Radius2*Y1);
			v.add(1.0f); v.add(0.5f);
			v.add((float)X1); v.add((float)Z2); v.add((float)Y1);
			
		         }
			}
			
			
			
			
			
			
			
			
			float[] verticeData = new float[v.size()];

			for (int i = 0; i < v.size(); i++) {
			    Float f = v.get(i);
			    verticeData[i] = (f != null ? f : Float.NaN); // Or whatever default you want.
			}
			
           
		    v.clear(); v = null; 
			
		  // Sending data to OpenGL requires the usage of (flipped) byte buffers
				FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(verticeData.length);
				verticesBuffer.put(verticeData);
				verticesBuffer.flip();

				
				vertexCount = 6*verticeData.length / 48;
				

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

