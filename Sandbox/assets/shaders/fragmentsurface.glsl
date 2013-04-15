#version 330 core

// Interpolated values from the vertex shaders
in vec2 UV;

out vec4 dgl_FragColor;

  in vec4	dgl_SecondaryColor;
  in vec4   dgl_Color;
uniform sampler2D myTextureSampler;


void main(){
vec4 MaterialDiffuseColor = texture2D( myTextureSampler, UV );
dgl_FragColor = dgl_Color + vec4(((0.25 * dgl_SecondaryColor)*MaterialDiffuseColor).rgb,MaterialDiffuseColor.a);



//dgl_FragColor = dgl_Color;

}