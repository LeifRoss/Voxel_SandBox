package leifdev.sandbox;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector4f;


public class GUIHandler {

	public static boolean isTextureLoaded = false;
	public static int guitexture = 0;
	public static int fonttexture = 0;
	public static int backgroundtexture = 0;
	
	// 2d shader variables
	private static int program2dfs = 0;
	private static int program2dvs = 0;
	private static int program2d = 0;
	private static int textureID = 0;
	private static int colorLocation = 0;
	
	private static GUIMenu menu;
	
	
	// Arraylist to hold all elements to be rendered
	private static ArrayList<GUIElement> elements;
	private static ArrayList<GUIMenu> menus;
	
	

	
	
	
	
	// initialized the activity
	public static void setup(){
		
		if(!isTextureLoaded){
			backgroundtexture = Utility.loadPNGTexture("assets/textures/leif.png", GL13.GL_TEXTURE0);
			guitexture = Utility.loadPNGTexture("assets/textures/gui.png", GL13.GL_TEXTURE0);
			fonttexture = Utility.loadPNGTexture("assets/textures/arial.png", GL13.GL_TEXTURE0);
			isTextureLoaded = true;
		}
		
			menus = new ArrayList<GUIMenu>();
	
			setupMenus();
		
			elements = new ArrayList<GUIElement>();
			
			elements.add(new GUIIcon(0.0f, 0.0f,0.04f,0.01f,1));
			elements.add(new GUIIcon(0.0f, 0.0f,0.01f,0.04f,1));
			
			elements.add(new GUIIcon(0.0f, -0.9f,0.06f,0.06f,0));
			elements.add(new GUIIcon(0.13f, -0.9f,0.06f,0.06f,0));
			elements.add(new GUIIcon(-0.13f, -0.9f,0.06f,0.06f,0));
				
			
			
			
		// 2d shader		
				// Load the vertex shader
				program2dvs = Utility.loadShader("assets/shaders/vertex2d.glsl", 
						GL20.GL_VERTEX_SHADER);
				// Load the fragment shader
				program2dfs = Utility.loadShader("assets/shaders/fragment2d.glsl", 
						GL20.GL_FRAGMENT_SHADER);
				
				// Create a new shader program that links both shaders
				program2d = GL20.glCreateProgram();
				GL20.glAttachShader(program2d, program2dvs);
				GL20.glAttachShader(program2d, program2dfs);
				GL20.glLinkProgram(program2d);

				// Position information will be attribute 0
				GL20.glBindAttribLocation(program2d, 0, "vertexPosition_modelspace");
				// Color information will be attribute 1
				GL20.glBindAttribLocation(program2d, 1, "vertexUV");
				
				
				// Get matrices uniform locations
				textureID = GL20.glGetUniformLocation(program2d, "myTextureSampler");
				colorLocation = GL20.glGetUniformLocation(program2d, "col");
				
				
				GL20.glValidateProgram(program2d);					
		
	}
	
	// adds a element to be rendered
	public static void addElement(GUIElement e){
				elements.add(e);
	}
	
	// removes a element from rendering
	public static void deleteElement(GUIElement e){
		ArrayList<GUIElement> buffer = new ArrayList<GUIElement>();
			
		for(GUIElement elem : elements){
			if(!elem.equals(e)){
				buffer.add(elem);
			}
		}
		
		elements.clear();
		
		for(GUIElement elem : buffer){
		elements.add(elem);
		}
		
	}
	
	
	// renders the elements
	public static void render(){
		
		GL20.glUseProgram(program2d);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, guitexture);
		GL20.glUniform1i(textureID, 0);
		
		GL20.glUniform4f(colorLocation, 1.0f, 1.0f, 1.0f,1.0f);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		
		for(GUIElement e : elements){
			
			GL30.glBindVertexArray(e.getVao());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
		
			
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, e.getVertexCount());
			
		}
		
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		Utility.exitOnGLError("GUIHANDLER_Render");
	}
	
	
	
	public static void renderText(String in, float x, float y, float sx, float sy){
		renderText(in, x, y, sx, sy, new Vector4f(1.0f,1.0f,1.0f,1.0f));
	}
	
	
	public static void renderText(String in, float x, float y, float sx, float sy, Vector4f color){
		
		char[] text = in.toCharArray();
		
		ArrayList<Float> data = new ArrayList<Float>();
	
		float offset = 0.0f;
		float height_mul = 1.0f / 16.0f;
		float width_mul = 1.0f / 16.0f;
		float i = 0.0f;
		float height = 0.0f;
		

		
		
		for(char c : text){
			
			
			
		float set = c - 32;
			
		 height = (float)Math.floor(set / 16.0f) + 2.0f;
			
		 offset = (float)Math.floor(set - 16.0f*height);
			
				
		 
				
				data.add(-1.0f*sx+x + i); data.add(1.0f*sy+y);
				data.add(width_mul*offset); data.add(height_mul*(height-1.0f));
				
				data.add(-1.0f*sx+x + i); data.add(-1.0f*sy+y);
				data.add(width_mul*offset); data.add(height_mul*height);
				
				data.add(1.0f*sx+x + i); data.add(-1.0f*sy+y);
				data.add((width_mul*offset)+width_mul); data.add(height_mul*height);
		
				
				data.add(1.0f*sx+x + i); data.add(1.0f*sy+y);
				data.add((width_mul*offset)+width_mul); data.add(height_mul*(height-1.0f));
								
				data.add(-1.0f*sx+x + i); data.add(1.0f*sy+y);
				data.add(width_mul*offset); data.add(height_mul*(height-1.0f));
								
				data.add(1.0f*sx+x + i); data.add(-1.0f*sy+y);
				data.add((width_mul*offset)+width_mul); data.add(height_mul*height);
				
				i += width_mul*sx*38.0f;
			
			}
		
		
		float[] verticedata = new float[data.size()];

		int vertexCount = data.size() / 4;
		
		
		for (int t = 0; t < data.size(); t++) {
		    Float f = data.get(t);
		    verticedata[t] = (f != null ? f : Float.NaN); // Or whatever default you want.
		}
		
		
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(verticedata.length);
		verticesBuffer.put(verticedata);
		verticesBuffer.flip();

		
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		

		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);

		
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 16, 0);		
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 16, 8);
		
		
		
		
		GL20.glUseProgram(program2d);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fonttexture);
		GL20.glUniform1i(textureID, 0);
		GL20.glUniform4f(colorLocation, color.x, color.y, color.z,color.w);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		


		
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
			
		
		
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vbo);
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vao);
		
		Utility.exitOnGLError("GUIHANDLER_TextRender");
		
		
	}
	
	
	// destroys the activity / cleanup
	public static void destroy(){
		
		for(GUIElement e : elements){
			e.destroy();
		}
		elements.clear();
		elements = null;
		
		for(GUIMenu e : menus){
			e.destroy();
		}
		menus.clear();
		menus = null;
		
		GL11.glDeleteTextures(guitexture);
		GL11.glDeleteTextures(fonttexture);
		
		GL20.glDeleteShader(program2dvs);
		GL20.glDeleteShader(program2dfs);
		GL20.glDeleteProgram(program2d);

	}

	
	
	public static int getColorLocation(){
		return colorLocation;
	}
	
	
	// menu handlers
	
	public static int getGuiTexture(){
		return guitexture;
	}
	
	public static int getFontTexture(){
		return fonttexture;
	}
	
	
	public static void setMenu(String name){
		
		for(GUIMenu e : menus){
			if(name.equals(e.getName())){
				menu = e;
			}
			
		}
	
	}
	
public static void renderMenu(){
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		

		if(menu != null) {
	GL20.glUseProgram(program2d);
		

	GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, menu.getTexture());
		GL20.glUniform1i(textureID, 0);
		GL20.glUniform4f(colorLocation, 1.0f, 1.0f, 1.0f,1.0f);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		
		  
			
			GL30.glBindVertexArray(menu.getVao());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
		
			
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, menu.getVertexCount());
			
		
		
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		
			menu.renderObjects();
		
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
		
		
		Utility.exitOnGLError("GUIHANDLER_Render");
		
	}
	


public static void setupMenus(){
	// intro menu
	GUIMenu intro = new GUIMenu(0,0,1.0f,1.0f,"intro","assets/textures/leif.png");
	menus.add(intro);
	
	
	// main menu			
	GUIMenu menu = new GUIMenu(0,0,1.0f,1.0f,"main","assets/textures/leif.png");			
	menu.addIcon(0.0f, 0.8f, 0.6f, 0.15f,1);
	menu.addText("SANDBOX", -0.35f, 0.8f, 0.05f, 0.1f);
	menu.addIcon(0, 0, 0.6f, 0.6f,1);
	
	menu.addButton(0,  0.0f, 0.3f, 0.08f, 1,"menu multiplayer");
	menu.addButton(0, -0.3f, 0.3f, 0.08f, 1,"exit");
	menu.addButton(0,  0.3f, 0.3f, 0.08f, 1,"menu singleplayer");
	
	menu.addText("Singleplayer", -0.2f, 0.3f, 0.018f, 0.04f);
	menu.addText("Multiplayer", -0.2f, 0.0f, 0.018f, 0.04f);
	menu.addText("exit", -0.2f, -0.3f, 0.018f, 0.04f);			
	menus.add(menu);
	
	
	// pause menu
	menu = new GUIMenu(0,0,1.0f,1.0f,"pause","assets/textures/leif.png");
	
	menu.addIcon(-0.35f, 0.8f, 0.6f, 0.15f,1);
	menu.addText("SANDBOX", -0.75f, 0.8f, 0.05f, 0.1f);
	
	
	menu.addIcon(-0.6f, 0, 0.3f, 0.6f,1);
	
	menu.addButton(-0.6f, 0.0f, 0.2f, 0.06f, 1,"menu settings");
	menu.addButton(-0.6f, -0.4f, 0.2f, 0.06f, 1,"exit");
	menu.addButton(-0.6f,  0.2f, 0.2f, 0.06f, 1,"resume");
	menu.addButton(-0.6f,  -0.2f, 0.2f, 0.06f, 1,"disconnect");
	
	
	menu.addText("resume", -0.74f, 0.2f, 0.018f, 0.04f);
	menu.addText("settings", -0.74f, 0.0f, 0.018f, 0.04f);
	menu.addText("quit", -0.74f, -0.2f, 0.018f, 0.04f);
	menu.addText("exit", -0.74f, -0.4f, 0.018f, 0.04f);
	
	menus.add(menu);
	
	
	
	
	// menu settings
	GUIMenu settings = new GUIMenu(0,0,1.0f,1.0f,"settings","assets/textures/leif.png");
	settings.addIcon(0, 0, 0.8f, 0.6f, 1);
	
	settings.addIcon(0, 0.8f, 0.4f, 0.16f, 1);
	settings.addText("Settings", -0.29f, 0.8f, 0.035f, 0.06f);
	settings.addButton(0.7f, -0.8f, 0.23f, 0.07f, 1,"menu pause");
	settings.addText("back", 0.6f, -0.8f, 0.03f, 0.05f);
	
	settings.addTextbox(0.0f, 0.3f, 0.4f, 0.06f, 3,new Vector4f(0.8f,0.8f,0.8f,1.0f),"menu");
	
	menus.add(settings);
	
	
	
	// menu singleplayer
	menu = new GUIMenu(0,0,1.0f,1.0f,"singleplayer","assets/textures/leif.png");
	menu.addIcon(0, 0, 0.8f, 0.6f, 1);
	
	menu.addIcon(0, 0.8f, 0.6f, 0.16f, 1);
	menu.addText("Start singleplayer", -0.49f, 0.8f, 0.025f, 0.06f);
	
	
	menu.addButton(0.7f, -0.8f, 0.23f, 0.07f, 1,"menu main");
	menu.addText("back", 0.6f, -0.8f, 0.03f, 0.05f);
	
	menu.addButton(0.0f,  0.0f, 0.23f, 0.07f, 1,"start t");
	menu.addText("Start", -0.12f, 0.0f, 0.03f, 0.05f);
	
	menu.addTextbox(0.0f, 0.3f, 0.4f, 0.06f, 3,new Vector4f(0.8f,0.8f,0.8f,1.0f),"start");
	
	menus.add(menu);
	
	// menu multiplayer
	menu = new GUIMenu(0,0,1.0f,1.0f,"multiplayer","assets/textures/leif.png");
	menu.addIcon(0, 0, 0.8f, 0.6f, 1);
	
	menu.addIcon(0, 0.8f, 0.6f, 0.16f, 1);
	menu.addText("Start multiplayer", -0.49f, 0.8f, 0.025f, 0.06f);
	
	
	menu.addButton(0.7f, -0.8f, 0.23f, 0.07f, 1,"menu main");
	menu.addText("back", 0.6f, -0.8f, 0.03f, 0.05f);
	
	menu.addButton(0.0f,  0.0f, 0.23f, 0.07f, 1,"connect");
	menu.addText("Start", -0.12f, 0.0f, 0.03f, 0.05f);
	
	menu.addTextbox(0.0f, 0.3f, 0.4f, 0.06f, 3,new Vector4f(0.8f,0.8f,0.8f,1.0f),"c");
	
	menus.add(menu);
	
	
	
	
}
	
	
	
	
}
