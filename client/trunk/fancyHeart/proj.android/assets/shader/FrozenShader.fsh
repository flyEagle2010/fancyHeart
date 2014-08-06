#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_fragmentColor;
varying vec2 v_texCoord;
uniform sampler2D u_texture;

void main()
{
    vec4 normalColor = v_fragmentColor * texture2D(u_texture, v_texCoord);
	normalColor *= vec4(0.8, 1, 0.8, 1);
	normalColor.b += normalColor.a * 0.2;
    gl_FragColor = normalColor;
}

