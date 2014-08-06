package com.doteyplay.game.gamedata.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TextGameData implements IGameData
{

	public String text;
	public String name;

	@Override
	public void load(DataInputStream in) throws IOException
	{

		name = in.readUTF();
		text = in.readUTF();
	}

	@Override
	public void save(DataOutputStream out) throws IOException

	{
		out.writeUTF(name);
		out.writeUTF(text);
	}

}
