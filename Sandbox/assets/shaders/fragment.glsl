#version 330 core

// Interpolated values from the vertex shaders
in vec2 UV;
in vec3 Position_worldspace;
in vec3 Normal_cameraspace;
in vec3 EyeDirection_cameraspace;
in vec3 LightDirection_cameraspace;

// Ouput data
out vec4 color;

// Values that stay constant for the whole mesh.
uniform sampler2D myTextureSampler;
uniform mat4 MV;
uniform vec3 LightPosition_worldspace;

void main(){

	vec4 LightColor = vec4(1,1,1,1);
	float LightPower = 120.0f;
	

	vec4 MaterialDiffuseColor = texture2D( myTextureSampler, UV ).rgba;
	vec4 MaterialAmbiantColor = vec4(0.6,0.6,0.6,1) * MaterialDiffuseColor;
	vec4 MaterialSpecularColor = vec4(0.75,0.75,0.75,1);


	float distance = length( LightPosition_worldspace - Position_worldspace );

	vec3 n = normalize( Normal_cameraspace );
	vec3 l = normalize( LightDirection_cameraspace );
	float cosTheta = clamp( dot( n,l ), 0,1 );
	
	vec3 E = normalize(EyeDirection_cameraspace);
	vec3 R = reflect(-l,n);

	float cosAlpha = clamp( dot( E,R ), 0,1 );
	
	color = MaterialAmbiantColor + MaterialDiffuseColor * LightColor * LightPower * cosTheta / (distance) + MaterialSpecularColor * LightColor * LightPower * pow(cosAlpha,5) / (distance);

}