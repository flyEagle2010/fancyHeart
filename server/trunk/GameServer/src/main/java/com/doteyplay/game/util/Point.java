package com.doteyplay.game.util;

public class Point implements Positional
{
	public int x;
	public int y;
	public float z;
	
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.z = 0f;
	}
	
	public Point()
	{
		
	}
	
	public Point(Positional p)
	{
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getZ();
	}

	/**
	 * @return the x
	 */
	@Override
	public int getX()
	{
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return the y
	 */
	@Override
	public int getY()
	{
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	public void set(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(Positional pos)
	{
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}
	
	public String toString()
	{
		return "[" + x + ", " + y + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
//		result = prime * result + (int)z;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
//		if(z!=other.z)
//			return false;
		
		return true;
	}

	@Override
	public float getZ() {
		return z;
	}
	
	
}
