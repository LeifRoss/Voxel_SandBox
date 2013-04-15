#version 330 core

out vec4 dgl_FragColor;

uniform vec3 v3LightPos;
uniform float g;
uniform float g2;

  invariant in vec3 v3Direction;

  in vec4	dgl_SecondaryColor;
  in vec4   dgl_Color;
  
  //in vec4 dgl_FragColor;

uniform mat4 MV;

void main (void)
{

   
   // vec4 gg_Color = vec4(gg_FrontColor.xyz,1);
	//vec4 gg_SecondaryColor = vec4(gg_FrontSecondaryColor.xyz,1);
	
	   //vec4 dgl_FragColor = vec4(0,1,0,0);
       // vec4 dgl_Color = vec4(1,1,0,1);

	
	float fCos = dot(v3LightPos, v3Direction) / length(v3Direction);
	float fMiePhase = 1.5 * ((1.0 - g2) / (2.0 + g2)) * (1.0 + fCos*fCos) / pow(1.0 + g2 - 2.0*g*fCos, 1.5);
	dgl_FragColor = dgl_Color + fMiePhase * dgl_SecondaryColor;
	dgl_FragColor.a = dgl_FragColor.b*2;
	
	
	
}
