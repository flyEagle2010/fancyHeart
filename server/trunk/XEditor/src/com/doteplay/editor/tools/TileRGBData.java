/**
 * 
 */
package com.doteplay.editor.tools;

import com.doteplay.editor.EditorConfig;

/**      
 * @version 1.0  
 */
public class TileRGBData
{
	public int[] rgbArray;
	public int tileX,tileY;
	public boolean mirrorH,mirrorV; 
	
	public TileRGBData(int x,int y)
	{
		tileX=x;
		tileY=y;
		rgbArray=new int[EditorConfig.GW*EditorConfig.GH];
	}
	public TileRGBData(int x,int y,int tw,int th)
	{
		tileX=x;
		tileY=y;
		rgbArray=new int[tw*th];
	}
}