package leifdev.sandbox;


import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;



public class Screen {
	
	private final String WINDOW_TITLE = "Sandbox";
	private final int WIDTH = 1024;
	private final int HEIGHT = 860;

   


	
	// 3d shader vars
	private int vsId = 0;
	private int fsId = 0;
	private int pId = 0;
		
	private int v3CameraPos, v3LightPos, v3InvWavelength,fCameraHeight,fCameraHeight2,fInnerRadius,fInnerRadius2,fOuterRadius,fOuterRadius2,fKrESun,fKmESun,fKr4PI,fKm4PI,fScale,fScaleDepth,fScaleOverScaleDepth,g,g2;
	
	
	private int vsIdA = 0;
	private int fsIdA = 0;
	private int pIdA = 0;
	
	private int v3CameraPosA, v3LightPosA, v3InvWavelengthA,fCameraHeightA,fCameraHeight2A,fInnerRadiusA,fInnerRadius2A,fOuterRadiusA,fOuterRadius2A,fKrESunA,fKmESunA,fKr4PIA,fKm4PIA,fScaleA,fScaleDepthA,fScaleOverScaleDepthA,gA,g2A;

	private int projectionMatrixLocationA = 0;
	private int viewMatrixLocationA = 0;
	private int modelMatrixLocationA = 0;
	

	private int[] texIds = new int[] {0, 0, 0, 0, 0,0};

	
	private int projectionMatrixLocation = 0;
	private int viewMatrixLocation = 0;
	private int modelMatrixLocation = 0;
	private int lightPositionLocation = 0;
	
	private int resolutionX;
	private int resolutionY;
	
	private Matrix4f projectionMatrix = null;
	private Matrix4f viewMatrix = null;
	private Matrix4f modelMatrix = null;
	private Matrix4f mvpMatrix = null;
	private FloatBuffer matrix44Buffer = null;
	private int textureID = 0;
	private float time;
	
	World world;
	private boolean running,game;
	private boolean gameRunning;
	
	
	public int getScreenResX(){
		return resolutionX;
	}
	
	public int getScreenResY(){
		return resolutionY;
	}
	
	public Screen() {

		gameRunning = false;
		resolutionX = 1024;
		resolutionY = 860;
		
		
		
		this.setupOpenGL();

		
		
		
		
		this.setup();
		this.setupShaders();
		this.setupTextures();
		this.setupMatrices();
		GUIHandler.setup();
		running = false;
		 game = true;
		
		 time = Sys.getTime();   
		float delta = time;
		 
		
		GUIHandler.setMenu("intro");
		
		while(!Display.isCloseRequested() && game) {
			Input.handleMenuInput();
			 
			 delta = (Sys.getTime() - time)/1000.0f;
			if(delta > 1.0f && delta < 1.2f){
				GUIHandler.setMenu("main");
			}
			 
			 
			 GUIHandler.renderMenu();
				Display.sync(60);
				Display.update();
			
			
		while (running) {
			
			this.renderCycle();
			
			Display.sync(60);
			Display.update();
			
			 if(Display.isCloseRequested()){
				 running = false; 
			 }
			
			}
		}
		
		this.destroyOpenGL();
	}

	private void setupMatrices() {
		// Setup projection matrix
		projectionMatrix = new Matrix4f();
		float fieldOfView = 60f;
		float aspectRatio = (float)WIDTH / (float)HEIGHT;
		float near_plane = 0.1f;
		float far_plane = 10000f;
		
		float y_scale = Utility.coTangent(Utility.degreesToRadians(fieldOfView / 2f));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far_plane - near_plane;
		
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
		
		// Setup view matrix
		viewMatrix = new Matrix4f();
		
		// Setup model matrix
		modelMatrix = new Matrix4f();
		
		// Setup mvp matrix
		mvpMatrix = new Matrix4f();
		
		// Create a FloatBuffer with the proper size to store our matrices later
		matrix44Buffer = BufferUtils.createFloatBuffer(16);
	}

	private void setupTextures() {
		texIds[0] = Utility.loadPNGTexture("assets/textures/layout.png", GL13.GL_TEXTURE0);
		texIds[1] = Utility.loadPNGTexture("assets/textures/dirt.png", GL13.GL_TEXTURE0);
		texIds[2] = Utility.loadPNGTexture("assets/textures/dirt1.png", GL13.GL_TEXTURE0);
		texIds[3] = Utility.loadPNGTexture("assets/textures/dirt2.png", GL13.GL_TEXTURE0);
		texIds[4] = Utility.loadPNGTexture("assets/textures/dirt3.png", GL13.GL_TEXTURE0);
		texIds[5] = Utility.loadPNGTexture("assets/textures/blocks.png", GL13.GL_TEXTURE0);
		
		Utility.exitOnGLError("setupTexture");
	}

	private void setupOpenGL() {
		// Setup an OpenGL context with API version 3.2
		try {
			PixelFormat pixelFormat = new PixelFormat();
			ContextAttribs contextAtrributes = new ContextAttribs(3, 3)
				.withForwardCompatible(true)
				.withProfileCore(true);
			
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle(WINDOW_TITLE);
			Display.create(pixelFormat, contextAtrributes);
			
			GL11.glViewport(0, 0, WIDTH, HEIGHT);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Setup an XNA like background color
	//	GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
		GL11.glClearColor(0.1f, 0.2f, 0.3f, 0f);

		
		
		// Map the internal OpenGL coordinate system to the entire screen
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		
		
		
		Utility.exitOnGLError("setupOpenGL");
	}
	
	private Sphere atmosphere;
	private void setup() {
		
		atmosphere = new Sphere();
		
		Input.setup(this);
		
		world = new World();
		
		Utility.exitOnGLError("setup");
	}
	
	private void setupShaders() {		
		
		// 3d shader
		
		// Load the vertex shader
		vsId = Utility.loadShader("assets/shaders/vertexsurface.glsl", 
				GL20.GL_VERTEX_SHADER);
		// Load the fragment shader
		fsId = Utility.loadShader("assets/shaders/fragmentsurface.glsl", 
				GL20.GL_FRAGMENT_SHADER);
		
		// Create a new shader program that links both shaders
		pId = GL20.glCreateProgram();
		GL20.glAttachShader(pId, vsId);
		GL20.glAttachShader(pId, fsId);
		GL20.glLinkProgram(pId);

		// Position information will be attribute 0
		GL20.glBindAttribLocation(pId, 0, "vertexPosition_modelspace");
		// Color information will be attribute 1
		GL20.glBindAttribLocation(pId, 1, "vertexUV");
		// Textute information will be attribute 2
		GL20.glBindAttribLocation(pId, 2, "vertexNormal_modelspace");
		
		// Get matrices uniform locations
		projectionMatrixLocation = GL20.glGetUniformLocation(pId, "MVP");
		viewMatrixLocation = GL20.glGetUniformLocation(pId, "V");
		modelMatrixLocation = GL20.glGetUniformLocation(pId, "M");
		//lightPositionLocation = GL20.glGetUniformLocation(pId, "LightPosition_worldspace");
		textureID = GL20.glGetUniformLocation(pId, "myTextureSampler");
		
		v3CameraPos = GL20.glGetUniformLocation(pId, "v3CameraPos");
		
		
	     v3LightPos = GL20.glGetUniformLocation(pId,"v3LightPos");
		 v3InvWavelength = GL20.glGetUniformLocation(pId,"v3InvWavelength");
		 fCameraHeight = GL20.glGetUniformLocation(pId,"fCameraHeight");
		 fCameraHeight2 = GL20.glGetUniformLocation(pId,"fCameraHeight2");
		 fInnerRadius = GL20.glGetUniformLocation(pId,"fInnerRadius");
		 fInnerRadius2 = GL20.glGetUniformLocation(pId,"fInnerRadius2");
		 fOuterRadius = GL20.glGetUniformLocation(pId,"fOuterRadius");
		 fOuterRadius2 = GL20.glGetUniformLocation(pId,"fOuterRadius2");
		 fKrESun = GL20.glGetUniformLocation(pId,"fKrESun");
	     fKmESun = GL20.glGetUniformLocation(pId,"fKmESun");
		 fKr4PI = GL20.glGetUniformLocation(pId,"fKr4PI");
		 fKm4PI = GL20.glGetUniformLocation(pId,"fKm4PI");
		 fScale = GL20.glGetUniformLocation(pId,"fScale");
		 fScaleDepth = GL20.glGetUniformLocation(pId,"fScaleDepth");
		 fScaleOverScaleDepth = GL20.glGetUniformLocation(pId,"fScaleOverScaleDepth");
	//	 g = GL20.glGetUniformLocation(pId,"g");
	//	 g2 = GL20.glGetUniformLocation(pId,"g2");
		
		

		GL20.glValidateProgram(pId);
	
		
		
		
		// setup atmospheric shader
		
		// Load the vertex shader
		vsIdA = Utility.loadShader("assets/shaders/vertex_atmosphere.glsl", 
				GL20.GL_VERTEX_SHADER);
		// Load the fragment shader
		fsIdA = Utility.loadShader("assets/shaders/fragment_atmosphere.glsl", 
				GL20.GL_FRAGMENT_SHADER);
		
		// Create a new shader program that links both shaders
		pIdA = GL20.glCreateProgram();
		GL20.glAttachShader(pIdA, vsIdA);
		GL20.glAttachShader(pIdA, fsIdA);
		GL20.glLinkProgram(pIdA);

		// Position information will be attribute 0
		GL20.glBindAttribLocation(pIdA, 0, "vertexPosition_modelspace");
		// Textute information will be attribute 2
		GL20.glBindAttribLocation(pIdA, 2, "vertexNormal_modelspace");
		
		// Get matrices uniform locations
		projectionMatrixLocationA = GL20.glGetUniformLocation(pIdA, "MVP");
		viewMatrixLocationA = GL20.glGetUniformLocation(pIdA, "V");
		modelMatrixLocationA = GL20.glGetUniformLocation(pIdA, "M");

		
		v3CameraPosA = GL20.glGetUniformLocation(pIdA, "v3CameraPos");
		
		
	     v3LightPosA = GL20.glGetUniformLocation(pIdA,"v3LightPos");
		 v3InvWavelengthA = GL20.glGetUniformLocation(pIdA,"v3InvWavelength");
		 fCameraHeightA = GL20.glGetUniformLocation(pIdA,"fCameraHeight");
		 fCameraHeight2A = GL20.glGetUniformLocation(pIdA,"fCameraHeight2");
		 fInnerRadiusA = GL20.glGetUniformLocation(pIdA,"fInnerRadius");
		 fInnerRadius2A = GL20.glGetUniformLocation(pIdA,"fInnerRadius2");
		 fOuterRadiusA = GL20.glGetUniformLocation(pIdA,"fOuterRadius");
		 fOuterRadius2A = GL20.glGetUniformLocation(pIdA,"fOuterRadius2");
		 fKrESunA = GL20.glGetUniformLocation(pIdA,"fKrESun");
	     fKmESunA = GL20.glGetUniformLocation(pIdA,"fKmESun");
		 fKr4PIA = GL20.glGetUniformLocation(pIdA,"fKr4PI");
		 fKm4PIA = GL20.glGetUniformLocation(pIdA,"fKm4PI");
		 fScaleA = GL20.glGetUniformLocation(pIdA,"fScale");
		 fScaleDepthA = GL20.glGetUniformLocation(pIdA,"fScaleDepth");
		 fScaleOverScaleDepthA = GL20.glGetUniformLocation(pIdA,"fScaleOverScaleDepth");
		 gA = GL20.glGetUniformLocation(pIdA,"g");
		 g2A = GL20.glGetUniformLocation(pIdA,"g2");
		
		

		GL20.glValidateProgram(pIdA);
	
		
		
		
		
		
		
		
		
		
		
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_DST_ALPHA);
		
		
		Utility.exitOnGLError("setupShaders");
	}
	

	public void setRunning(boolean in){
		this.running = in;
	}
	public boolean getRunning(){
		return running;
	}
	
	float timer = 0.0f;
	
	private void renderCycle() {
		Input.handleInput();
		
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		
		
		GL20.glUseProgram(pId);
		
		
		
		// glsl shader input
		
		timer+=0.02f;
		
		Vector3f playerPos = new Vector3f(-Input.getShootPos().x,-Input.getShootPos().y,-Input.getShootPos().z);
		Vector3f corePos = new Vector3f(playerPos.x,9735.0f,playerPos.z);
		
		Vector3f lightPos = new Vector3f((float)Math.cos(timer)*300000.0f,(float)Math.sin(timer)*300000.0f,0.0f);
		
		Vector3f lightNormal = new Vector3f(lightPos.x - corePos.x, lightPos.y - corePos.y, lightPos.z - corePos.z);
		lightNormal.normalise();
		
		float distanceFromCore = Utility.distance(corePos,playerPos);
		
		
		float vOne = (float)(1.0f / Math.pow(0.650f, 4.0f));
		float vTwo = (float)(1.0f / Math.pow(0.570f, 4.0f));
		float vThree = (float)(1.0f / Math.pow(0.475f, 4.0f));
		
		
		// matrix manipulation
				
		
		viewMatrix = new Matrix4f();
	   	mvpMatrix  = new Matrix4f();
		
		// Translate camera

		Matrix4f.rotate(Input.getPitch(), new Vector3f(1.0f,0.0f,0.0f), viewMatrix, viewMatrix);
		Matrix4f.rotate(Input.getYaw(), new Vector3f(0.0f,1.0f,0.0f), viewMatrix, viewMatrix);
		Matrix4f.translate(Input.getShootPos(), viewMatrix, viewMatrix);
		
		
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	
		
		viewMatrix.store(matrix44Buffer); matrix44Buffer.flip();
		GL20.glUniformMatrix4(viewMatrixLocation, false, matrix44Buffer);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texIds[Input.getTextureSelector()]);
		GL20.glUniform1i(textureID, 0);
		
		
		for(int i = 0; i < world.getSize(); i++){
		modelMatrix = new Matrix4f();
			
		Matrix4f.translate(world.getChunk(i).getPos(), modelMatrix, modelMatrix);
		modelMatrix.store(matrix44Buffer); matrix44Buffer.flip();
		GL20.glUniformMatrix4(modelMatrixLocation, false, matrix44Buffer);

		Matrix4f.mul(projectionMatrix, viewMatrix, mvpMatrix);
		Matrix4f.mul(mvpMatrix, modelMatrix, mvpMatrix);
		
		
		mvpMatrix.store(matrix44Buffer); matrix44Buffer.flip();
		GL20.glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
		
		
	//	GL20.glUniform3f(lightPositionLocation, 0.0f, 600.0f, 450.0f);
		
		// leif something is wrong with this, fix!!
		
		GL20.glUniform3f(v3CameraPos, playerPos.x,playerPos.y-100,playerPos.z);
		GL20.glUniform3f(v3LightPos, lightNormal.x , lightNormal.y, lightNormal.z);
		GL20.glUniform3f(v3InvWavelength, vOne, vTwo, vThree);
		GL20.glUniform1f(fCameraHeight, distanceFromCore);
		GL20.glUniform1f(fCameraHeight2, distanceFromCore*distanceFromCore);
		GL20.glUniform1f(fInnerRadius, 9735.0f);
		GL20.glUniform1f(fInnerRadius2, 9735.0f*9735.0f);
		GL20.glUniform1f(fOuterRadius, 10250.0f);
		GL20.glUniform1f(fOuterRadius2, 10250.0f*10250.0f);
		GL20.glUniform1f(fKrESun, 0.0025f * 20.0f);
		GL20.glUniform1f(fKmESun, 0.0015f * 20.0f);
		GL20.glUniform1f(fKr4PI,  0.0025f * (1.0f / 515.0f) * 3.141592653f);
		GL20.glUniform1f(fKm4PI,  0.0015f * (1.0f / 515.0f) * 3.141592653f);
		GL20.glUniform1f(fScale,  1.0f / 515.0f);
		GL20.glUniform1f(fScaleDepth,  215.0f);
		GL20.glUniform1f(fScaleOverScaleDepth,  1.0f / 501.0f / 251.0f);
		//GL20.glUniform1f(g,  -0.990f );
		//GL20.glUniform1f(g2, -0.990f * -0.990f);
		

		/*
		
		uniform vec3 v3CameraPos;		// The camera's current position
uniform vec3 v3LightPos;		// The direction vector to the light source
uniform vec3 v3InvWavelength;	// 1 / pow(wavelength, 4) for the red, green, and blue channels
uniform float fCameraHeight;	// The camera's current height
uniform float fCameraHeight2;	// fCameraHeight^2
uniform float fOuterRadius;		// The outer (atmosphere) radius
uniform float fOuterRadius2;	// fOuterRadius^2
uniform float fInnerRadius;		// The inner (planetary) radius
uniform float fInnerRadius2;	// fInnerRadius^2
uniform float fKrESun;			// Kr * ESun
uniform float fKmESun;			// Km * ESun
uniform float fKr4PI;			// Kr * 4 * PI
uniform float fKm4PI;			// Km * 4 * PI
uniform float fScale;			// 1 / (fOuterRadius - fInnerRadius)
uniform float fScaleDepth;		// The scale depth (i.e. the altitude at which the atmosphere's average density is found)
uniform float fScaleOverScaleDepth;	// fScale / fScaleDepth

		
		*/
		
		GL30.glBindVertexArray(world.getChunk(i).getVao());
		
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);

	
		// Draw the vertices
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, world.getChunk(i).getVertexCount());
		

		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
		}
		
		GL20.glUseProgram(pIdA);
		
		// render the atmosphere!
		
		
		viewMatrix.store(matrix44Buffer); matrix44Buffer.flip();
		GL20.glUniformMatrix4(viewMatrixLocationA, false, matrix44Buffer);

		
		modelMatrix = new Matrix4f();
		
		Matrix4f.translate(corePos, modelMatrix, modelMatrix);
		modelMatrix.store(matrix44Buffer); matrix44Buffer.flip();
		GL20.glUniformMatrix4(modelMatrixLocationA, false, matrix44Buffer);

		Matrix4f.mul(projectionMatrix, viewMatrix, mvpMatrix);
		Matrix4f.mul(mvpMatrix, modelMatrix, mvpMatrix);
		
		
		mvpMatrix.store(matrix44Buffer); matrix44Buffer.flip();
		GL20.glUniformMatrix4(projectionMatrixLocationA, false, matrix44Buffer);
		
		
	//	GL20.glUniform3f(lightPositionLocation, 0.0f, 600.0f, 450.0f);
		
		// leif something is wrong with this, fix!!
		
		GL20.glUniform3f(v3CameraPosA, playerPos.x,playerPos.y-100,playerPos.z);
		GL20.glUniform3f(v3LightPosA, lightNormal.x , lightNormal.y, lightNormal.z);
		GL20.glUniform3f(v3InvWavelengthA, vOne, vTwo,vThree);
		GL20.glUniform1f(fCameraHeightA, distanceFromCore);
		GL20.glUniform1f(fCameraHeight2A, distanceFromCore*distanceFromCore);
		GL20.glUniform1f(fInnerRadiusA, 9735.0f);
		GL20.glUniform1f(fInnerRadius2A, 9735.0f*9735.0f);
		GL20.glUniform1f(fOuterRadiusA, 10250.0f);
		GL20.glUniform1f(fOuterRadius2A, 10250.0f*10250.0f);
		GL20.glUniform1f(fKrESunA, 0.0025f * 30.0f);
		GL20.glUniform1f(fKmESunA, 0.0015f * 30.0f);
		GL20.glUniform1f(fKr4PIA,  0.0025f * (1.0f / 25.0f) * 3.141592653f);
		GL20.glUniform1f(fKm4PIA,  0.0015f * (1.0f / 25.0f) * 3.141592653f);
		GL20.glUniform1f(fScaleA,  1.0f / 515.0f);
		GL20.glUniform1f(fScaleDepthA,  510.0f);
		GL20.glUniform1f(fScaleOverScaleDepthA,  1.0f / 515.0f / 251.0f);
		GL20.glUniform1f(gA,  -0.990f );
		GL20.glUniform1f(g2A, -0.990f * -0.990f);
		
		
		GL30.glBindVertexArray(atmosphere.getVao());
		
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);

	
		// Draw the vertices
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, atmosphere.getVertexCount());
		

		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
		
		
		
		
		
		
				
		GUIHandler.render();
				
		GL20.glUseProgram(0);
		
		Utility.exitOnGLError("renderCycle");
	}
	
	
	public void destroy(){
		game = false;
	}
	
	private void destroyOpenGL() {	
		// Delete the texture
		GL11.glDeleteTextures(texIds[0]);
		GL11.glDeleteTextures(texIds[1]);
		GL11.glDeleteTextures(texIds[2]);
		GL11.glDeleteTextures(texIds[3]);
		GL11.glDeleteTextures(texIds[4]);
		GL11.glDeleteTextures(texIds[5]);
		atmosphere.destroy();
		world.destroy();
		// Delete the shaders
		GL20.glUseProgram(0);
		GL20.glDetachShader(pId, vsId);
		GL20.glDetachShader(pId, fsId);
		
		GL20.glDeleteShader(vsId);
		GL20.glDeleteShader(fsId);
		GL20.glDeleteProgram(pId);
		

		
		
		GUIHandler.destroy();
		Utility.exitOnGLError("destroyOpenGL");
		
		Display.destroy();
	}

	public void newGame() {
		world.generate("lolseed");
		running = true;
		GUIHandler.setMenu("pause");
		Input.reset();
		gameRunning = true;
	}

	public void destroyGame() {
		world.destroy();
		running = false;	
		gameRunning = false;
	}
	
	public boolean gameStarted() {	
		return gameRunning;
	}
	
	
	
	

	

}