package com.doteplay.editor.util.clipremap;

class Rect
{
	boolean isUsed;
	int id;

	int x, y, w, h;
	
	Rect()
	{
	}

	Rect(int _w, int _h)
	{
		w = _w;
		h = _h;
	}
}