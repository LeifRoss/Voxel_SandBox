#version 330 core

// Input vertex data, different for all executions of this shader.
layout(location = 0) in vec2 vertexPosition_screenspace;
layout(location = 1) in vec2 vertexUV;

// Output data ; will be interpolated for each fragment.
out vec2 UV;
uniform vec4 col;
out vec4 c;

void main(){

     gl_Position.xy = vertexPosition_screenspace;
	c = col;
	// UV of the vertex. No special space for this one.
	UV = vertexUV;
}

