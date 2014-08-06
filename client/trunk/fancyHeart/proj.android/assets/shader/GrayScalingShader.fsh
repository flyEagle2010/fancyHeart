#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_fragmentColor;
varying vec2 v_texCoord;
uniform sampler2D u_texture;

void main()
{
    vec4 normalColor = v_fragmentColor * texture2D(u_texture, v_texCoord);

    //float gray = 0.299*0.5*normalColor.r + 0.587*0.5*normalColor.g + 0.114*0.5*normalColor.b;
    //gl_FragColor = vec4(gray*0.8 + normalColor.r*0.2, gray*0.8 + normalColor.g*0.2, gray*0.8 + normalColor.b*0.2, normalColor.a*0.3);
	float gray = dot(normalColor.rgb, vec3(0.299 * 0.5, 0.587 * 0.5, 0.114 * 0.5));
    gl_FragColor = vec4(gray, gray, gray, normalColor.a * 0.5);
}
