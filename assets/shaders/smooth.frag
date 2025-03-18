#version 120

varying vec2 v_texCoord;
uniform sampler2D u_texture;
uniform vec2 u_texelSize; // (1.0 / texture width, 1.0 / texture height)

void main() {
    vec4 color = texture2D(u_texture, v_texCoord);

    vec4 c1 = texture2D(u_texture, v_texCoord + vec2(-u_texelSize.x, -u_texelSize.y));
    vec4 c2 = texture2D(u_texture, v_texCoord + vec2( u_texelSize.x, -u_texelSize.y));
    vec4 c3 = texture2D(u_texture, v_texCoord + vec2(-u_texelSize.x,  u_texelSize.y));
    vec4 c4 = texture2D(u_texture, v_texCoord + vec2( u_texelSize.x,  u_texelSize.y));

    gl_FragColor = (color * 0.95) + (c1 + c2 + c3 + c4) * 0.05;
}
