#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_fragmentColor;
varying vec2 v_texCoord;
uniform sampler2D u_texture;

void main()
{
    vec4 color1 = texture2D(u_texture, v_texCoord) * v_fragmentColor;
	float brightness = (color1.r + color1.g + color1.b) * (1. / 3.);
	float gray = (1.5)*brightness;
	color1 = vec4(gray, gray, gray, color1.a)*vec4(0.8,1.2,1.5,1);
    gl_FragColor =color1;
}
