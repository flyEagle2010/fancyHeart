package com.doteyplay.game.message.proto;


public class CurrentCursor {

	private int currentPosition = 0;
	private int processUpToPosition = -1;
	private boolean endOfStreamReached = false;
	
	public boolean isEndOfStreamReached() {
		return endOfStreamReached;
	}
	
	public void setEndOfStreamReached(boolean endOfStreamReached) {
		this.endOfStreamReached = endOfStreamReached;
	}
	
	public void addToPosition(int bytes) {
		currentPosition += bytes;
	}
	
	public int getCurrentPosition() {
		return currentPosition;
	}
	
	public int getProcessUpToPosition() {
		return processUpToPosition;
	}
	
	public void setProcessUpToPosition(int processUpToPosition) {
		this.processUpToPosition = processUpToPosition;
	}
	
}
