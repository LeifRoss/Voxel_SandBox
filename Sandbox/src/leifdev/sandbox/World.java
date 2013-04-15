package leifdev.sandbox;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

public class World {

	//Chunk chunk;
	private ArrayList<Chunk> chunks;
	Physics phys;
	
	
	
	public World(){
		

		
	}
	
	
	public void generate(String in){

		chunks = new ArrayList<Chunk>();
		
		Chunk chunk = new Chunk(32,50,32);
		chunk.generate(new Vector3f(0.0f,0.0f,0.0f));
		chunks.add(chunk);
		
		chunk = new Chunk(32,50,32);
		chunk.generate(new Vector3f(64.0f,0.0f,0.0f));
		chunks.add(chunk);
		
		chunk = new Chunk(32,50,32);
		chunk.generate(new Vector3f(0.0f,0.0f,64.0f));
		chunks.add(chunk);

		chunk = new Chunk(32,50,32);
		chunk.generate(new Vector3f(64.0f,0.0f,64.0f));
		chunks.add(chunk);
		
		
		phys = new Physics();
		phys.addChunk(chunks);
		
		
		Input.addPhysics(phys);
		Thread p = new Thread(phys);
		p.start();
			

		
	}
	
	
	
	public Chunk getChunk(int i){
		return chunks.get(i);
	}
	
	public int getSize(){
		return chunks.size();
	}
	
	public void destroy(){
		phys.stop();
		
		for(Chunk c : chunks){
		c.destroy();
		}
		chunks.clear();
		chunks = null;
		
		
	}

	
	
}
