package com.doteyplay.game.gamedata.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IGameData
{
	public void load(DataInputStream in) throws IOException;
	
	public void save(DataOutputStream out) throws IOException;
}
