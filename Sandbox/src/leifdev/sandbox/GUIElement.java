package leifdev.sandbox;


public abstract class GUIElement {


	protected float x,y,scaleX,scaleY;

	protected int vbo, uvbo,vao, vertexCount;
	
	protected final int Stride = 16;
	
	
	public GUIElement(){
		

				
		
	}
	
	

	
	
	public abstract void destroy();
	public abstract void render();
	
	
	
// setters	
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	
	public void setScaleX(float sx){
		this.scaleX = sx;
	}
	public void setScaleY(float sy){
		this.scaleY = sy;
	}
	
// getters	
	public float getX(){
	return x;
	}
	public float getY(){
	return y;
	}
	
	public int getVbo(){
		return vbo;
	}
	public int getUvbo(){
		return uvbo;
	}
	public int getVao(){
		return vao;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	
}
