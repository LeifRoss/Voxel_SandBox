package leifdev.sandbox;

public class TerrainGenerator {

	
	final double PI = Math.PI;
	private float scale;
	private Chunk chunk;
	
	public TerrainGenerator(float scalar, Chunk chunk){
		scale = scalar;
		this.chunk = chunk;
	}
	
	
	public float randomSeed(int x, int y) {
		int n = x+y*57;
		n = (n<<13)^n;
		return (1.0f - ((n*(n*n + 15731 + 789221) + 1376312589) & 2147483647 ) / 1073741824.0f);  
	}
	
	public float randomSeed3d(int x, int y, int z) {
		int n = x+y*57+z*30;
		n = (n<<13)^n;
		return (1.0f - ((n*(n*n + 15731 + 789221) + 1376312589) & 2147483647 ) / 1073741824.0f);  
	}
	
	
	public float cosineInterpolate(float a, float b,float x) {
		float ft = (float) (x * PI);
		float f = (float) ((1.0f-Math.cos(ft))*0.5f);
		return (a*(1.0f-f)+b*f);
		}
	
	
	
	
	public float smoothedNoise2d(float xf, float yf){
		
		int x =(int) xf;
		int y =(int) yf;
		
		  float corners = (randomSeed(x-1,y-1) +randomSeed(x+1,y-1) + randomSeed(x-1,y+1) + randomSeed(x+1,y+1))/16;
		  float sides = (randomSeed(x-1,y)+randomSeed(x+1,y)+randomSeed(x,y-1)+randomSeed(x,y+1))/8;
		  float center = randomSeed(x,y)/4;
		  return (corners + sides + center);
		}
	
	public float smoothedNoise3d(float xf, float yf, float zf){
		
		int x =(int) xf;
		int y =(int) yf;
		int z = (int)zf;
		
		  float corners = (randomSeed3d(x-1,y-1,z+1) +randomSeed3d(x+1,y-1,z+1) + randomSeed3d(x-1,y+1,z+1) + randomSeed3d(x+1,y+1,z+1)+randomSeed3d(x-1,y-1,z-1) +randomSeed3d(x+1,y-1,z-1) + randomSeed3d(x-1,y+1,z-1) + randomSeed3d(x+1,y+1,z-1))/16;
		  float sides = (randomSeed3d(x-1,y,z)+randomSeed3d(x+1,y,z)+randomSeed3d(x,y-1,z)+randomSeed3d(x,y+1,z)+randomSeed3d(x,y,z+1)+randomSeed3d(x,y,z-1))/8;
		  float center = randomSeed3d(x,y,z)/4;
		  return (corners + sides + center);
		}
	
	
	public float interpolatedNoise2d(float x, float y){
		int integerX = (int) x;
		int integerY = (int) y;

		float fractionalX = x-integerX;
		float fractionalY = y-integerY;

		float v1 = smoothedNoise2d(integerX,integerY);
		float v2 = smoothedNoise2d(integerX+1,integerY);
		float v3 = smoothedNoise2d(integerX,integerY+1);
		float v4 = smoothedNoise2d(integerX+1,integerY+1);

		float i1 = cosineInterpolate(v1,v2,fractionalX);
		float i2 = cosineInterpolate(v3,v4,fractionalX);

		return cosineInterpolate(i1,i2,fractionalY);
		}
	
	public float interpolatedNoise3d(float x, float y, float z){
		int integerX = (int) x;
		int integerY = (int) y;
		int integerZ = (int) z;
		
		float fractionalX = x-integerX;
		float fractionalY = y-integerY;
		float fractionalZ = z-integerZ;

		float v1 = smoothedNoise3d(integerX,integerY,integerZ);
		float v2 = smoothedNoise3d(integerX+1,integerY,integerZ);
		float v3 = smoothedNoise3d(integerX,integerY+1,integerZ);
		float v4 = smoothedNoise3d(integerX+1,integerY+1,integerZ);


		float v6 = smoothedNoise3d(integerX+1,integerY,integerZ+1);
		float v7 = smoothedNoise3d(integerX,integerY+1,integerZ+1);
		float v8 = smoothedNoise3d(integerX+1,integerY+1,integerZ+1);
		
		
		float i1 = cosineInterpolate(v1,v2,fractionalX);
		float i2 = cosineInterpolate(v3,v4,fractionalX);
		float i3 = cosineInterpolate(i1,i2,fractionalY);
		
		float i4 = cosineInterpolate(v6,v7,fractionalY);
		float i5 = cosineInterpolate(v7,v8,fractionalY);
		float i6 = cosineInterpolate(i4,i5,fractionalZ);
		
		return cosineInterpolate(i3,i6,fractionalZ);
		}
	
	
	public float perlinNoise2d(float x, float y) {
		float total = 0;
		float p = 0.25f;
		float n = 5;
		float frequency = 0;
		float amplitude = 0;


		for(int i = 0; i < n; i++) {

			frequency = (float)Math.pow(2.0f,i);
			amplitude = (float)Math.pow(p,i);
			total = total + interpolatedNoise2d(((x/scale)*frequency),( (y/scale)*frequency))*amplitude;
		}

		return total;
		}

	public float perlinNoise3d(float x, float y, float z){
		float total = 0;
		float p = 0.25f;
		float n = 5;
		float frequency = 0;
		float amplitude = 0;


		for(int i = 0; i < n; i++) {

			frequency = (float)Math.pow(2.0f,i);
			amplitude = (float)Math.pow(p,i);
			total = total + interpolatedNoise3d(((x/scale)*frequency),((y/scale)*frequency),((z/scale)*frequency))*amplitude;
		}

		return total;	
		
		
		
	}
	
	
	
	public int getTerrain(float x, float y, float z, int relX, int relY, int relZ){
		
		int height = (int)(10+this.perlinNoise2d(x*5.0f, z*5.0f)*9);
		int result = 0;
		if(y == 0) {
			return 4;
		}
		
		
		if(y==height){
		
			if(height > 5){
				return 1;
			}else{
				return 3;
			}
			
		}else if(y < height){
			result = 2;

			height = ((int)(this.perlinNoise3d(500+x*5, 500+z*5,500+y*5)*3));
			
			if(height>0){
				return 0;
			}
			
			
		}else{			
			// y > height
			int h = height;
			height = (int)(this.perlinNoise2d(200+x*25.0f, 200+z*25.0f)*2);
			
			if(height > 0 && y == h + 1){
				
				//System.out.printf("%f %f %f\n", x,y,z);
				this.chunk.addTree(relX,relY,relZ);
				return 5;
			}
			
			
		}
		
		

		
		
		return result;
	}
	
	
	
	
	
	
	
	/*
	 if (y == height) {
		result=1;
	 }else if(y < height && y > height-2){
		 result=2;
	 }else if(y <= height-2 && y > height-4){
		 result=4;
	 }else if(y < height){
		result=6;	
	 }
	
	 if(y == height && y > 3 && y < 6){
		 result = 3;
	 }
	 
	 if(y < height -1 && y > height -5){
		 result=0;	
	 }
	*/
	
}


