package leifdev.sandbox;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

public class Chunk {

	
	private int[][][] cubes;
	private float[] cubeData;
	private int vao = 0;
	private int vbo = 0;
	private int vs = 48;
	private int Stride = 4*3 + 4*2 + 4*3;
	private int vertexCount;
	private int sizeX,sizeY,sizeZ;
	private Vector3f pos;
	private float texMul = (1.00f / 7.00f);
	private static final int FLOAT = Float.SIZE/Byte.SIZE;
	
	public Chunk(){
		
		cubes = new int[16][16][16];
		sizeX = sizeY = sizeZ = 16;		
		
		
	}
	public Chunk(int sx, int sy, int sz){
		
		cubes = new int[sx][sy][sz];
		
		this.sizeX = sx;
		this.sizeY = sy;
		this.sizeZ = sz;
	
	}
	
	
	public void addTree(int x, int y, int z){
		
		Random rand = new Random();
		int size = 5+rand.nextInt(5);
		rand = null;

		for(int i = 0; i < size; i++) {			
		
			if(y+i < sizeY){
			
				if(i < size-1){
				cubes[x][y+i][z] = 5;
				}
		
			if(i > size/3) {
				
				int radius = (int)(Math.cos((float)i/(float)size)*4);
				
		for(int k = -radius; k < radius; k++){
			for(int r = -radius; r < radius; r++){
				
				int mulr =(int)r;
				int mulk =(int)k;
				
				if( 
					x+mulk < sizeX && x+mulk > 0 &&
					z+mulr < sizeZ && z+mulr > 0 &&
					cubes[x+mulk][y+i][z+mulr]==0){
				cubes[x+mulk][y+i][z+mulr] = 6;
						}
				
					}
				}
			}
			
		}
	}
		
		
		
		
		
	}
	
	
	public void generate(Vector3f pos){
		
		this.pos = pos;
		TerrainGenerator terrain = new TerrainGenerator(30,this);
		int height = 10;
		
		
		 cubeData = new float[] {
			     // right
				 1.0f, 1.0f, 1.0f,
				 1.0f, 0.0f, 0.0f,
				 0.66f, 0.5f,
				 
				 1.0f,-1.0f,-1.0f,
				 1.0f, 0.0f, 0.0f,
				 0.33f, 1.0f,
				 
				 1.0f, 1.0f,-1.0f,
				 1.0f, 0.0f, 0.0f,
				 0.33f, 0.5f,
			     
			     
			     1.0f,-1.0f,-1.0f,
			     1.0f, 0.0f, 0.0f,
			     0.33f, 1.0f,
			     
			     1.0f, 1.0f, 1.0f,
			     1.0f, 0.0f, 0.0f,
			     0.66f, 0.5f,
			     
			     1.0f,-1.0f, 1.0f,
			     1.0f, 0.0f, 0.0f,
			     0.66f, 1.0f, 
			     		
			     		     
			     // left
			     -1.0f,-1.0f,-1.0f,
				  -1.0f, 0.0f, 0.0f,
				  0.0f,  0.5f,
				  
			     -1.0f,-1.0f, 1.0f,
				  -1.0f, 0.0f, 0.0f,
				  0.33f, 0.5f,
				  
			     -1.0f, 1.0f, 1.0f,
				  -1.0f, 0.0f, 0.0f,
				  0.33f, 0.0f,
			     
			     -1.0f,-1.0f,-1.0f,
				  -1.0f, 0.0f, 0.0f,
				  0.0f, 0.5f,
				  
			     -1.0f, 1.0f, 1.0f,
				  -1.0f, 0.0f, 0.0f,
				  0.33f,0.0f,
				  
			     -1.0f, 1.0f,-1.0f,
				  -1.0f, 0.0f, 0.0f,
				  0.0f, 0.0f,
				  
				  
	             // rear
			     1.0f, 1.0f,-1.0f,
			     0.0f, 0.0f, -1.0f,
			     0.33f, 0.0f,
			     
			     -1.0f,-1.0f,-1.0f,
			     0.0f, 0.0f, -1.0f,
			     0.66f, 0.5f,
			     
			     -1.0f, 1.0f,-1.0f,
			     0.0f, 0.0f, -1.0f,
			     0.66f, 0.0f,
			     
			     
			     1.0f, 1.0f,-1.0f,
			     0.0f, 0.0f, -1.0f,
			     0.33f, 0.0f,
			     
			     1.0f,-1.0f,-1.0f,
			     0.0f, 0.0f, -1.0f,
			     0.33f, 0.5f,
			     
			    -1.0f,-1.0f,-1.0f,  
			     0.0f, 0.0f, -1.0f,
			     0.66f, 0.5f,
					
			 
			     
			     //front
			     -1.0f, 1.0f, 1.0f,
			     0.0f, 0.0f, 1.0f,
			     0.33f, 0.5f,
			     
			     -1.0f,-1.0f, 1.0f,
			     0.0f, 0.0f, 1.0f,
			     0.33f, 1.0f,
			     
			      1.0f,-1.0f, 1.0f,
			      0.0f, 0.0f, 1.0f,
			      0.0f, 1.0f,
			      
			     
			      1.0f, 1.0f, 1.0f,
			      0.0f, 0.0f, 1.0f,
			      0.0f, 0.5f,
			      
			     -1.0f, 1.0f, 1.0f,
			     0.0f, 0.0f, 1.0f,
			     0.33f, 0.5f,
			     
			      1.0f,-1.0f, 1.0f,
			      0.0f, 0.0f, 1.0f,
			      0.0f, 1.0f,
			      
			      
				 //bottom
			      1.0f,-1.0f, 1.0f,
			      0.0f, -1.0f, 0.0f,
			      0.666f, 1.0f,
			      
			     -1.0f,-1.0f,-1.0f,
			      0.0f, -1.0f, 0.0f,
			      1.00f, 0.5f,
			     
			      1.0f,-1.0f,-1.0f,
			      0.0f, -1.0f, 0.0f,
			      1.00f, 1.0f,
			      
			     
			      1.0f,-1.0f, 1.0f,
			      0.0f, -1.0f, 0.0f,
			      0.666f, 1.0f,
			      
			      
			     -1.0f,-1.0f, 1.0f,
			      0.0f, -1.0f, 0.0f,
			      0.666f, 0.5f,
			      
			     
			     -1.0f,-1.0f,-1.0f,	
			      0.0f, -1.0f, 0.0f,
			      1.0f, 0.5f,
			      
			     

			     
				 //top
			      1.0f, 1.0f, 1.0f,
			      0.0f, 1.0f, 0.0f,
			      1.0f, 0.0f,
			      
			      1.0f, 1.0f,-1.0f,
			      0.0f, 1.0f, 0.0f,
			      0.666f, 0.0f,
			      
			      
			     -1.0f, 1.0f,-1.0f,
			      0.0f, 1.0f, 0.0f,
			      0.666f, 0.5f,
			     
			     
			      1.0f, 1.0f, 1.0f,
			      0.0f, 1.0f, 0.0f,
			      1.0f, 0.0f,
			      
			      
			      
			     -1.0f, 1.0f,-1.0f,
			      0.0f, 1.0f, 0.0f,
			      0.666f, 0.5f,
			     
			     
			     -1.0f, 1.0f, 1.0f,	
			      0.0f, 1.0f, 0.0f,
			      1.0f, 0.5f
			     

			};
		
		System.out.println(cubeData.length);
		
		
		// loop to fill the array with different kinds of blocks
		for(int x = 0; x < sizeX; x++){
			for(int z = 0; z < sizeZ; z++){
			
				
				for(int y = 0; y < sizeY; y++){
						
					
					// generate the terrain
					if(cubes[x][y][z]==0){
					cubes[x][y][z] = terrain.getTerrain(x+pos.x*2, y+pos.y*2, z+pos.z*2,x,y,z);
					}
	
						
					}
				}
		}
	
		
		  
		boolean render = false;
		float scalemul = 2.0f;
		
		int halfX = sizeX / 2;
		int halfY = sizeY / 2;
		int halfZ = sizeZ / 2;
		
		float blockType = 1.0f;
		
		 ArrayList<Float> chunkdata = new ArrayList<Float>();
		 
		int[] renderVisible = new int[6];
		
		// loop to generate vao
		for(int x = 0; x < sizeX; x++){
			for(int z = 0; z < sizeZ; z++){			
				for(int y = 0; y < sizeY; y++){
					
					if(cubes[x][y][z] != 0){
							blockType =(float) cubes[x][y][z];
							render = false;
						for(int i = 0; i < 6; i++){
							renderVisible[i] = 0;
						}
							
							
							if(x == sizeX-1){
								renderVisible[0] = 1;
								render = true;
							}else{
								if(cubes[x+1][y][z]==0){
									renderVisible[0] = 1;
									render = true;
								}	
							}
							
							if(x == 0){
								renderVisible[1] = 1;
								render = true;
							}else{
								if(cubes[x-1][y][z]==0){
									renderVisible[1] = 1;
									render = true;
								}	
							}

							if(y == sizeY-1){
								renderVisible[5] = 1;
								render = true;
							}else{
								if(cubes[x][y+1][z]==0){
									renderVisible[5] = 1;
									render = true;
								}	
							}							
							
							if(y == 0){
								renderVisible[4] = 1;
								render = true;
							}else{
								if(cubes[x][y-1][z]==0){
									renderVisible[4] = 1;
									render = true;
								}	
							}

							if(z == sizeZ-1){
								renderVisible[3] = 1;
								render = true;
							}else{
								if(cubes[x][y][z+1]==0){
									renderVisible[3] = 1;
									render = true;
								}	
							}

							if(z == 0){
								renderVisible[2] = 1;
								render = true;
							}else{
								if(cubes[x][y][z-1]==0){
									renderVisible[2] = 1;
									render = true;
								}	
							}
							

						
							if(render) {
								for(int i = 0; i < 6; i++) {
								   int	draw = renderVisible[i];

									if(draw==1) {
										
									//t.push_back(texture_data[idx+i*6]*glm::vec2(1.0f,Texture_Mul)+glm::vec2(0,1.00f-Texture_Mul*fragment[x][y][z]));

									
											
									// first polygon in face		
											// add polygon vertex
								
										
											chunkdata.add(cubeData[0+(i*vs)]+(x-halfX)*scalemul);
											chunkdata.add(cubeData[1+(i*vs)]+(y-halfY)*scalemul);
											chunkdata.add(cubeData[2+(i*vs)]+(z-halfZ)*scalemul);
											
											// add polygon normal
											chunkdata.add(cubeData[3+(i*vs)]);
											chunkdata.add(cubeData[4+(i*vs)]);
											chunkdata.add(cubeData[5+(i*vs)]);
											
											// add polygon texture coordinate
											chunkdata.add(cubeData[6+(i*vs)]);
											chunkdata.add(cubeData[7+(i*vs)]*texMul + (texMul*(blockType-1.0f)));
											
											
											
											// add polygon vertex
											chunkdata.add(cubeData[8+(i*vs)]+(x-halfZ)*scalemul);
											chunkdata.add(cubeData[9+(i*vs)]+(y-halfY)*scalemul);
											chunkdata.add(cubeData[10+(i*vs)]+(z-halfZ)*scalemul);
											
											// add polygon normal
											chunkdata.add(cubeData[11+(i*vs)]);
											chunkdata.add(cubeData[12+(i*vs)]);
											chunkdata.add(cubeData[13+(i*vs)]);
											
											// add polygon texture coordinate
											chunkdata.add(cubeData[14+(i*vs)]);
											chunkdata.add(cubeData[15+(i*vs)]*texMul + (texMul*(blockType-1.0f)));
											
											
											// add polygon vertex
											chunkdata.add(cubeData[16+(i*vs)]+(x-halfX)*scalemul);
											chunkdata.add(cubeData[17+(i*vs)]+(y-halfY)*scalemul);
											chunkdata.add(cubeData[18+(i*vs)]+(z-halfZ)*scalemul);
											
											// add polygon normal
											chunkdata.add(cubeData[19+(i*vs)]);
											chunkdata.add(cubeData[20+(i*vs)]);
											chunkdata.add(cubeData[21+(i*vs)]);
											
											// add polygon texture coordinate
											chunkdata.add(cubeData[22+(i*vs)]);
											chunkdata.add(cubeData[23+(i*vs)]*texMul + (texMul*(blockType-1.0f)));
									
										// second polygon in face	
											// add polygon vertex
											chunkdata.add(cubeData[24+(i*vs)]+(x-halfX)*scalemul);
											chunkdata.add(cubeData[25+(i*vs)]+(y-halfY)*scalemul);
											chunkdata.add(cubeData[26+(i*vs)]+(z-halfZ)*scalemul);
											
											// add polygon normal
											chunkdata.add(cubeData[27+(i*vs)]);
											chunkdata.add(cubeData[28+(i*vs)]);
											chunkdata.add(cubeData[29+(i*vs)]);
											
											// add polygon texture coordinate
											chunkdata.add(cubeData[30+(i*vs)]);
											chunkdata.add(cubeData[31+(i*vs)]*texMul + (texMul*(blockType-1.0f)));
											
											
											
											// add polygon vertex
											chunkdata.add(cubeData[32+(i*vs)]+(x-halfX)*scalemul);
											chunkdata.add(cubeData[33+(i*vs)]+(y-halfY)*scalemul);
											chunkdata.add(cubeData[34+(i*vs)]+(z-halfZ)*scalemul);
											
											// add polygon normal 
											chunkdata.add(cubeData[35+(i*vs)]); 
											chunkdata.add(cubeData[36+(i*vs)]); 
											chunkdata.add(cubeData[37+(i*vs)]); 
											
											// add polygon texture coordinate
											chunkdata.add(cubeData[38+(i*vs)]);
											chunkdata.add(cubeData[39+(i*vs)]*texMul + (texMul*(blockType-1.0f)));
											
											
											// add polygon vertex
											chunkdata.add(cubeData[40+(i*vs)]+(x-halfX)*scalemul);
											chunkdata.add(cubeData[41+(i*vs)]+(y-halfY)*scalemul);
											chunkdata.add(cubeData[42+(i*vs)]+(z-halfZ)*scalemul);
											
											// add polygon normal
											chunkdata.add(cubeData[43+(i*vs)]);
											chunkdata.add(cubeData[44+(i*vs)]);
											chunkdata.add(cubeData[45+(i*vs)]);
											
											
											// add polygon texture coordinate
											chunkdata.add(cubeData[46+(i*vs)]);
											chunkdata.add(cubeData[47+(i*vs)]*texMul + (texMul*(blockType-1.0f)));
											
											
										
									}
								}

							  }
							
							
							
						
					}
					
					
					
					
					}
				}
			}
		
		
		float[] verticedata = new float[chunkdata.size()];

		for (int i = 0; i < chunkdata.size(); i++) {
		    Float f = chunkdata.get(i);
		    verticedata[i] = (f != null ? f : Float.NaN); // Or whatever default you want.
		}
		
		
	//	FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(verticedata.length);
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(sizeX*sizeY*sizeZ*Stride);

		verticesBuffer.put(verticedata);
		verticesBuffer.flip();

		
		vertexCount = 6*verticedata.length / vs;
		

		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		

		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		//GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_DYNAMIC_DRAW);

		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER,FLOAT*(verticesBuffer.capacity()) ,
	             GL15.GL_DYNAMIC_DRAW);

	// copy data from the client to the server
	        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER,0,verticesBuffer);
		
		
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, Stride, 0);		
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, Stride, 3*4);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Stride, 3*4 + 3*4);
		
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		
		// Deselect (bind to 0) the VAO
		GL30.glBindVertexArray(0);
		
		
		
	}
	
	
	
	
public void regenerate(){
		


		
		
		
	
		
		
		boolean render = false;
		float scalemul = 2.0f;
		
		int halfX = sizeX / 2;
		int halfY = sizeY / 2;
		int halfZ = sizeZ / 2;
		
		float blockType = 1.0f;
		
		 ArrayList<Float> chunkdata = new ArrayList<Float>();
		 
		int[] renderVisible = new int[6];
		
		// loop to generate vao
		for(int x = 0; x < sizeX; x++){
			for(int z = 0; z < sizeZ; z++){			
				for(int y = 0; y < sizeY; y++){
					
					if(cubes[x][y][z] != 0){
							blockType =(float) cubes[x][y][z];
							render = false;
						for(int i = 0; i < 6; i++){
							renderVisible[i] = 0;
						}
							
							
							if(x == sizeX-1){
								renderVisible[0] = 1;
								render = true;
							}else{
								if(cubes[x+1][y][z]==0){
									renderVisible[0] = 1;
									render = true;
								}	
							}
							
							if(x == 0){
								renderVisible[1] = 1;
								render = true;
							}else{
								if(cubes[x-1][y][z]==0){
									renderVisible[1] = 1;
									render = true;
								}	
							}

							if(y == sizeY-1){
								renderVisible[5] = 1;
								render = true;
							}else{
								if(cubes[x][y+1][z]==0){
									renderVisible[5] = 1;
									render = true;
								}	
							}							
							
							if(y == 0){
								renderVisible[4] = 1;
								render = true;
							}else{
								if(cubes[x][y-1][z]==0){
									renderVisible[4] = 1;
									render = true;
								}	
							}

							if(z == sizeZ-1){
								renderVisible[3] = 1;
								render = true;
							}else{
								if(cubes[x][y][z+1]==0){
									renderVisible[3] = 1;
									render = true;
								}	
							}

							if(z == 0){
								renderVisible[2] = 1;
								render = true;
							}else{
								if(cubes[x][y][z-1]==0){
									renderVisible[2] = 1;
									render = true;
								}	
							}
							

						
							if(render) {
								for(int i = 0; i < 6; i++) {
								   int	draw = renderVisible[i];

									if(draw==1) {
										
									//t.push_back(texture_data[idx+i*6]*glm::vec2(1.0f,Texture_Mul)+glm::vec2(0,1.00f-Texture_Mul*fragment[x][y][z]));

									
											
									// first polygon in face		
											// add polygon vertex
								
										
											chunkdata.add(cubeData[0+(i*vs)]+(x-halfX)*scalemul);
											chunkdata.add(cubeData[1+(i*vs)]+(y-halfY)*scalemul);
											chunkdata.add(cubeData[2+(i*vs)]+(z-halfZ)*scalemul);
											
											// add polygon normal
											chunkdata.add(cubeData[3+(i*vs)]);
											chunkdata.add(cubeData[4+(i*vs)]);
											chunkdata.add(cubeData[5+(i*vs)]);
											
											// add polygon texture coordinate
											chunkdata.add(cubeData[6+(i*vs)]);
											chunkdata.add(cubeData[7+(i*vs)]*texMul + (texMul*(blockType-1.0f)));
											
											
											
											// add polygon vertex
											chunkdata.add(cubeData[8+(i*vs)]+(x-halfZ)*scalemul);
											chunkdata.add(cubeData[9+(i*vs)]+(y-halfY)*scalemul);
											chunkdata.add(cubeData[10+(i*vs)]+(z-halfZ)*scalemul);
											
											// add polygon normal
											chunkdata.add(cubeData[11+(i*vs)]);
											chunkdata.add(cubeData[12+(i*vs)]);
											chunkdata.add(cubeData[13+(i*vs)]);
											
											// add polygon texture coordinate
											chunkdata.add(cubeData[14+(i*vs)]);
											chunkdata.add(cubeData[15+(i*vs)]*texMul + (texMul*(blockType-1.0f)));
											
											
											// add polygon vertex
											chunkdata.add(cubeData[16+(i*vs)]+(x-halfX)*scalemul);
											chunkdata.add(cubeData[17+(i*vs)]+(y-halfY)*scalemul);
											chunkdata.add(cubeData[18+(i*vs)]+(z-halfZ)*scalemul);
											
											// add polygon normal
											chunkdata.add(cubeData[19+(i*vs)]);
											chunkdata.add(cubeData[20+(i*vs)]);
											chunkdata.add(cubeData[21+(i*vs)]);
											
											// add polygon texture coordinate
											chunkdata.add(cubeData[22+(i*vs)]);
											chunkdata.add(cubeData[23+(i*vs)]*texMul + (texMul*(blockType-1.0f)));
									
										// second polygon in face	
											// add polygon vertex
											chunkdata.add(cubeData[24+(i*vs)]+(x-halfX)*scalemul);
											chunkdata.add(cubeData[25+(i*vs)]+(y-halfY)*scalemul);
											chunkdata.add(cubeData[26+(i*vs)]+(z-halfZ)*scalemul);
											
											// add polygon normal
											chunkdata.add(cubeData[27+(i*vs)]);
											chunkdata.add(cubeData[28+(i*vs)]);
											chunkdata.add(cubeData[29+(i*vs)]);
											
											// add polygon texture coordinate
											chunkdata.add(cubeData[30+(i*vs)]);
											chunkdata.add(cubeData[31+(i*vs)]*texMul + (texMul*(blockType-1.0f)));
											
											
											
											// add polygon vertex
											chunkdata.add(cubeData[32+(i*vs)]+(x-halfX)*scalemul);
											chunkdata.add(cubeData[33+(i*vs)]+(y-halfY)*scalemul);
											chunkdata.add(cubeData[34+(i*vs)]+(z-halfZ)*scalemul);
											
											// add polygon normal 
											chunkdata.add(cubeData[35+(i*vs)]); 
											chunkdata.add(cubeData[36+(i*vs)]); 
											chunkdata.add(cubeData[37+(i*vs)]); 
											
											// add polygon texture coordinate
											chunkdata.add(cubeData[38+(i*vs)]);
											chunkdata.add(cubeData[39+(i*vs)]*texMul + (texMul*(blockType-1.0f)));
											
											
											// add polygon vertex
											chunkdata.add(cubeData[40+(i*vs)]+(x-halfX)*scalemul);
											chunkdata.add(cubeData[41+(i*vs)]+(y-halfY)*scalemul);
											chunkdata.add(cubeData[42+(i*vs)]+(z-halfZ)*scalemul);
											
											// add polygon normal
											chunkdata.add(cubeData[43+(i*vs)]);
											chunkdata.add(cubeData[44+(i*vs)]);
											chunkdata.add(cubeData[45+(i*vs)]);
											
											
											// add polygon texture coordinate
											chunkdata.add(cubeData[46+(i*vs)]);
											chunkdata.add(cubeData[47+(i*vs)]*texMul + (texMul*(blockType-1.0f)));
											
											
										
									}
								}

							  }
							
							
							
						
					}
					
					
					
					
					}
				}
			}
		
		
		float[] verticedata = new float[chunkdata.size()];

		for (int i = 0; i < chunkdata.size(); i++) {
		    Float f = chunkdata.get(i);
		    verticedata[i] = (f != null ? f : Float.NaN); // Or whatever default you want.
		}
		
		//FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(sizeX*sizeY*sizeZ*Stride);
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(verticedata.length);
		verticesBuffer.put(verticedata);
		verticesBuffer.flip();

		
		vertexCount = 6*verticedata.length / vs;
		

	
		GL30.glBindVertexArray(vao);
		


		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER,0,verticesBuffer);		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		
		// Deselect (bind to 0) the VAO
		GL30.glBindVertexArray(0);
		
		
		
	}
	
	public int getVao(){
		return vao;
	}
	public int getVertexCount(){
		return vertexCount;
	}
	
	
	public int[][][] getCubes(){
		return cubes;
	}
	
	public void setCubes(int[][][] cubes){
		this.cubes = cubes;
	}
	
	public Vector3f getSize(){
		return new Vector3f(sizeX,sizeY,sizeZ);
	}
	
	public void destroy(){
		
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vbo);		
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vao);
		
	}
	public Vector3f getPos() {
		return pos;
	}
	
}


/*

										
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


*/

