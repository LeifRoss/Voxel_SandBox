package leifdev.sandbox;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Physics implements Runnable {

	private ArrayList<Chunk> chunks;
	private Chunk chunk;
	private Vector3f chunksize;
	boolean running;
	int[][][] cubes;
	float velY = 0.0f;
	Vector3f playerPos, bufferPos;
	@Override
	public void run() {
				
		runPhysics();
		
		
	}

	
	private void runPhysics(){
		
		
		vel = new Vector3f(0.0f,0.0f,0.0f);
		running = true;
		float delta,time,lastTime = 0.0f;

		bufferPos = new Vector3f(0.0f,0.0f,0.0f);
		
		
		
		
		while(running){
			
			// get time between last frame
			 time = Sys.getTime();   
			 delta = (time - lastTime)/1000.0f;
		     lastTime = time;
			
		    handlePlayerNew(delta);
			
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
		

	}
	
	private float playerSpeed = 20.0f;
	private Vector3f vel;
	private boolean spaceHeld = false;
	
	
	
	
	public void handlePlayerNew(float delta){
		
		if(!isInsideChunk(playerPos,chunk)){
		setClosestChunk();
		}
		
		float dx = 0.0f;
		float dy = 0.0f;
		Vector3f forward = Input.getForward();
		Vector3f right = Input.getRight();
		playerPos = Input.getShootPos();
		
		
		if( Keyboard.isKeyDown(Keyboard.KEY_W)){
			dx+=1.0f;
		}
		if( Keyboard.isKeyDown(Keyboard.KEY_S)){
			dx-=1.0f;
		}
		if( Keyboard.isKeyDown(Keyboard.KEY_A)){
			dy+=1.0f;
		}
		if( Keyboard.isKeyDown(Keyboard.KEY_D)){
			dy-=1.0f;
		}
		
		dy*=delta*playerSpeed;
		dx*=delta*playerSpeed;
		
		
		
		Vector3f bufferPos = new Vector3f(playerPos.x + forward.x*dx + right.getX()*dy,playerPos.y+2.0f,playerPos.z+forward.z*dx + right.getZ()*dy);
		
		if(dx !=0 || dy !=0) {
			
		
		if(!checkChunkCollision(bufferPos,chunk)) {
			
			 bufferPos.setY(playerPos.y);
			 if(!checkChunkCollision(bufferPos,chunk)) {
				 	
			playerPos.setX(playerPos.x + forward.getX()*dx + right.getX()*dy);
			playerPos.setZ(playerPos.z + forward.getZ()*dx + right.getZ()*dy); 
			
			
						
			 }
			}
		}
		
		bufferPos.setY(playerPos.y + 5.1f);
		bufferPos.setX(playerPos.x);
		bufferPos.setZ(playerPos.z);
		
		
		playerPos.setY(playerPos.getY() + velY*delta);
		
		
		if(!checkChunkCollision(bufferPos,chunk)) {
						
			   	velY = Math.max(Math.min(120.0f,velY + delta*98.1f),-120.0f);
		    	//playerPos.setY(playerPos.getY() + velY);		
			   	
			   	bufferPos.setY(playerPos.y - 0.5f);
			   	if(checkChunkCollision(bufferPos,chunk)) {
			   		velY = -velY;
			   		
			   	}
		}else{
			velY = 0.0f;
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				
				if(!spaceHeld) {
				velY = -playerSpeed*1.5f;
				playerPos.setY(playerPos.y-0.1f);
				}				
				spaceHeld = true;
					}else{		
				spaceHeld = false;
				}
			
			
			//velY = 0.0f;
			bufferPos.setY(playerPos.y + 5.0f);
			
			if(checkChunkCollision(bufferPos,chunk)) {
				playerPos.setY(playerPos.y - playerSpeed*delta);
			}
			
		}
		
		
		
		
		
		
	}
	
	
	
	
	public void handlePlayer(float delta){
		
			
			
		  playerPos = Input.getShootPos();
		     

		     
		    
			if (Keyboard.isKeyDown(Keyboard.KEY_W)){
				
				
				Vector3f nPos = new Vector3f(playerPos.x+Input.getEye().getX()*delta*playerSpeed,playerPos.y+2.0f,playerPos.z+Input.getEye().getZ()*delta*playerSpeed);
				
				if(!checkChunkCollision(nPos,chunk)) {
					 nPos = new Vector3f(playerPos.x+Input.getEye().getX()*delta*playerSpeed,playerPos.y-0.5f,playerPos.z+Input.getEye().getZ()*delta*playerSpeed);
					 if(!checkChunkCollision(nPos,chunk)) {
				playerPos.setX(playerPos.getX() + Input.getEye().getX()*delta*playerSpeed);
			//	playerPos.setY(playerPos.getY() + Input.getEye().getY()*delta*playerSpeed);
				playerPos.setZ(playerPos.getZ() + Input.getEye().getZ()*delta*playerSpeed);	
					 }
				}
				
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_S)){
				
				
				Vector3f nPos = new Vector3f(playerPos.x-Input.getEye().getX()*delta*playerSpeed,playerPos.y+2.0f,playerPos.z-Input.getEye().getZ()*delta*playerSpeed);
				
				if(!checkChunkCollision(nPos,chunk)) {
					
				playerPos.setX(playerPos.getX() - Input.getEye().getX()*delta*playerSpeed);
			//	playerPos.setY(playerPos.getY() - Input.getEye().getY()*delta*playerSpeed);
				playerPos.setZ(playerPos.getZ() - Input.getEye().getZ()*delta*playerSpeed);	
				
				}
			}
		    
			
			
			
		    
		    bufferPos.set(playerPos);
		    bufferPos.setY(bufferPos.getY() + 6.0f);
		    
		     if(!checkChunkCollision(bufferPos,chunk)){
		    	 
		    	 velY = Math.max(Math.min(5.0f,velY += delta/2.0f),0.0f);
		    	 
		    	// bufferPos.set( playerPos );
		    	 playerPos.setY(playerPos.getY() + velY);
 	 
		    	// Input.setShootPos(playerPos);
		     }else{
		    	 
		    	 if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
		    		 playerPos.setY(playerPos.getY()-4.0f);
		    	 }
		    	 
		    	 
		    	 velY = 0.0f;
		    	 bufferPos.setY(playerPos.getY() + 5.0f);
		    	  if(checkChunkCollision(bufferPos,chunk)){
		    	 playerPos.setY(playerPos.getY() - 14.0f*delta);
		    	  }
		     }
		
	}
	
	
	
	public void stop(){
		running = false;
	}
	public void start(){
		running = true;
	}
	
	
	public void addChunk(ArrayList<Chunk> in){
		this.chunks = in;

		setClosestChunk();
	}
	public void setClosestChunk(){
		
		playerPos = Input.getShootPos();
		float delta = 99999999.0f;
		Chunk selected = null;
		
		
		for(Chunk c : chunks){
			
			if(isInsideChunk(playerPos,c)){
				this.chunk = c;
				this.chunksize = chunk.getSize();	
				this.cubes = chunk.getCubes();	
				System.out.printf("Chunk set %f %f %f\n",c.getPos().x,c.getPos().y,c.getPos().z);
				return;
			}
			
			
			if(getDistance(playerPos,c) < delta){
				delta = getDistance(playerPos,c);
				selected = c;			
			}
			
		}
		
		if(selected != null){
			this.chunk = selected;
			System.out.printf("Chunk set %f %f %f\n",selected.getPos().x,selected.getPos().y,selected.getPos().z);
			
		}else{
			this.chunk = chunks.get(0);
		}
		
	
		this.chunksize = chunk.getSize();	
		this.cubes = chunk.getCubes();		
	}
	
	public float getDistance(Vector3f pos, Chunk c){
		float dx = pos.x - c.getPos().x;
		float dy = pos.y - c.getPos().y;
		float dz = pos.z - c.getPos().z;
		
		return (float)Math.sqrt(dx*dx + dy*dy + dz*dz);
	}
	
	
	
	public boolean checkChunkCollision(Vector3f pos, Chunk c){
		
		Vector3f chunksize = c.getSize();
		Vector3f chunkpos = c.getPos();
		
		// leif fix!!!
		Vector3f npos = new Vector3f((chunkpos.x - pos.x)*0.5f + chunksize.x*0.5f, (chunkpos.y - pos.y)*0.5f + chunksize.y*0.5f,(chunkpos.z - pos.z)*0.5f + chunksize.z*0.5f);
		
		
		float mulf = 0.5f;
		
		if(!isInsideChunk(pos,c)){
			return false;
		}
		
		
		// leif, fix!!!
		//int px = Math.min((int)chunksize.getX()-1,(int)((chunksize.getX()+0.5)-(pos.getX() + chunksize.getX())*mulf+chunkpos.getX()*2.0f));
		//int py = Math.min((int)chunksize.getY()-1,(int)((chunksize.getY()+0.5)-(pos.getY() + chunksize.getY())*mulf+chunkpos.getY()*2.0f));
		//int pz = Math.min((int)chunksize.getZ()-1,(int)((chunksize.getZ()+0.5)-(pos.getZ() + chunksize.getZ())*mulf+chunkpos.getZ()*2.0f));
		
		
	int px = Math.max(0, Math.min((int)chunksize.getX()-1,(int)(npos.x + 0.5 - chunkpos.x)));
	int py = Math.max(0, Math.min((int)chunksize.getY()-1,(int)(npos.y + 0.5 - chunkpos.y)));
	int pz = Math.max(0, Math.min((int)chunksize.getZ()-1,(int)(npos.z + 0.5 - chunkpos.z)));
			
		
		
	//
//	System.out.printf("x: %d y: %d z: %d\n",px,py,pz);
		
		
		int check = cubes[px][py][pz];
		
		
		
		if(check != 0){
			return true;
		}
		
		
		return false;
	}
	
	// check if a point is inside the given chunk
	public boolean isInsideChunk(Vector3f pos, Chunk c) {
		
		Vector3f chunksize = c.getSize();
		Vector3f chunkpos = c.getPos();
		float mul = 1.0f;
		
		if(pos.getX() > -chunkpos.getX()+chunksize.getX()*mul+0.5f || pos.getX() < -chunkpos.getX()-chunksize.getX()*mul || 
		   pos.getY() > -chunkpos.getY()+chunksize.getY()*mul+0.5f || pos.getY() < -chunkpos.getY()-chunksize.getY()*mul || 
		   pos.getZ() > -chunkpos.getZ()+chunksize.getZ()*mul+0.5f || pos.getZ() < -chunkpos.getZ()-chunksize.getZ()*mul  ){
			return false;
		}
		
		return true;
	}


	public void createCube() {
		
		Vector3f pos = new Vector3f(Input.getShootPos());				
		Vector3f eye = Input.getEye();
		Vector3f chunksize = chunk.getSize();
		Vector3f chunkpos = chunk.getPos();
		int px = 0, py = 0, pz = 0;
		
		float mulf = 0.5f;
		boolean run = true;
		float dist = 0.0f;
		
		while(run){
			
			dist += 0.5f;
			if(dist > 10) {
				return;
			}
			pos.setX(Input.getShootPos().getX() + eye.getX()*dist);
			pos.setY(Input.getShootPos().getY() + eye.getY()*dist);
			pos.setZ(Input.getShootPos().getZ() + eye.getZ()*dist);
			
		if(!isInsideChunk(pos,chunk)){
			break;
		}
		
		
		Vector3f npos = new Vector3f((chunkpos.x - pos.x)*0.5f + chunksize.x*0.5f, (chunkpos.y - pos.y)*0.5f + chunksize.y*0.5f,(chunkpos.z - pos.z)*0.5f + chunksize.z*0.5f);

		 px = Math.max(0, Math.min((int)chunksize.getX()-1,(int)(npos.x + 0.5 - chunkpos.x)));
		 py = Math.max(0, Math.min((int)chunksize.getY()-1,(int)(npos.y + 0.5 - chunkpos.y)));
		 pz = Math.max(0, Math.min((int)chunksize.getZ()-1,(int)(npos.z + 0.5 - chunkpos.z)));
	 
		 
		 
		if( cubes[px][py][pz] != 0){
			run = false;
		}
		
		}
		
		pos.setX(Input.getShootPos().getX() + eye.getX()*(dist-1.0f));
		pos.setY(Input.getShootPos().getY() + eye.getY()*(dist-1.0f));
		pos.setZ(Input.getShootPos().getZ() + eye.getZ()*(dist-1.0f));
		
		
		 px = Math.min((int)chunksize.getX()-1, (int)((chunksize.getX()+0.5)-(pos.getX() + chunksize.getX())*mulf+chunkpos.getX()*mulf));
		 py = Math.min((int)chunksize.getY()-1, (int)((chunksize.getY()+0.5)-(pos.getY() + chunksize.getY())*mulf+chunkpos.getY()*mulf));
		 pz = Math.min((int)chunksize.getZ()-1, (int)((chunksize.getZ()+0.5)-(pos.getZ() + chunksize.getZ())*mulf+chunkpos.getZ()*mulf));
		
		
			Vector3f npos = new Vector3f((chunkpos.x - pos.x)*0.5f + chunksize.x*0.5f, (chunkpos.y - pos.y)*0.5f + chunksize.y*0.5f,(chunkpos.z - pos.z)*0.5f + chunksize.z*0.5f);

			 px = Math.max(0, Math.min((int)chunksize.getX()-1,(int)(npos.x + 0.5 - chunkpos.x)));
			 py = Math.max(0, Math.min((int)chunksize.getY()-1,(int)(npos.y + 0.5 - chunkpos.y)));
			 pz = Math.max(0, Math.min((int)chunksize.getZ()-1,(int)(npos.z + 0.5 - chunkpos.z)));
		
		
		if(!isInsideChunk(pos,chunk)){
			return;
		}
		

		
	
		cubes[px][py][pz] = 3;
		
		chunk.setCubes(cubes);
		chunk.regenerate();
		
		
	}


	public void deleteCube() {
		Vector3f pos = new Vector3f(Input.getShootPos());				
		Vector3f eye = Input.getEye();
		Vector3f chunksize = chunk.getSize();
		Vector3f chunkpos = chunk.getPos();
		int px = 0, py = 0, pz = 0;
		

		boolean run = true;
		float dist = 0.0f;
		
		while(run){
			
			dist += 0.5f;
			if(dist > 10) {
				return;
			}
			pos.setX(Input.getShootPos().getX() + eye.getX()*dist);
			pos.setY(Input.getShootPos().getY() + eye.getY()*dist);
			pos.setZ(Input.getShootPos().getZ() + eye.getZ()*dist);
			
		if(!isInsideChunk(pos,chunk)){
			break;
		}
		
		
		Vector3f npos = new Vector3f((chunkpos.x - pos.x)*0.5f + chunksize.x*0.5f, (chunkpos.y - pos.y)*0.5f + chunksize.y*0.5f,(chunkpos.z - pos.z)*0.5f + chunksize.z*0.5f);

		 px = Math.max(0, Math.min((int)chunksize.getX()-1,(int)(npos.x + 0.5 - chunkpos.x)));
		 py = Math.max(0, Math.min((int)chunksize.getY()-1,(int)(npos.y + 0.5 - chunkpos.y)));
		 pz = Math.max(0, Math.min((int)chunksize.getZ()-1,(int)(npos.z + 0.5 - chunkpos.z)));

		if( cubes[px][py][pz] != 0){
			run = false;
		}
		
		}
		
		if(!isInsideChunk(pos,chunk)){
			return;
		}
		
		
		
	
		cubes[px][py][pz] = 0;
		
		chunk.setCubes(cubes);
		chunk.regenerate();
		
		
	}
	
	
}
